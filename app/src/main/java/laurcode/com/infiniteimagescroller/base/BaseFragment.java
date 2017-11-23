package laurcode.com.infiniteimagescroller.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.concurrent.atomic.AtomicBoolean;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.subjects.PublishSubject;
import laurcode.com.infiniteimagescroller.exceptions.RootViewNotFoundException;
import laurcode.com.infiniteimagescroller.util.ViewUtil;
import timber.log.Timber;

/**
 * The base Fragment class. All fragments will inherit from this class. The fragment has the connected
 * presenter and view model objects as member variables so any children classes will automatically follow
 * the MVPVM pattern.
 *
 * <p/>
 * Created by laurie on 2017/09/13.
 */

public abstract class BaseFragment<DB extends ViewDataBinding, VM extends BaseViewModel,
        P extends BasePresenter> extends Fragment implements BaseView {

    private BaseActivity activity;
    private Unbinder butterknifeUnbinder;

    /**
     * A bundle that is optionally set by children
     */
    private Bundle bundle;

    protected DB binding;

    protected P presenter;

    protected VM viewModel;

    /**
     * The tag belonging to this fragment. Used when retrieving a fragment by findFragmentByTag().
     */
    private String fragmentTag;

    protected abstract void init(View view, Bundle savedInstanceState);

    protected abstract void onBundleReceived(@NonNull Bundle bundle);

    protected abstract @LayoutRes int getLayoutResId();

    private View rootView;

    private AtomicBoolean waitingForBundle = new AtomicBoolean();

    /**
     * A child fragment that was requested to be added to this fragment, but the fragment wasn't ready yet.
     */
    private Fragment pendingChildFragment;

    /**
     * Returns true if this fragment follows the MVPVM pattern
     */
    protected abstract boolean isMvpvm();

    /**
     * Gets this fragment's presenter class. Return null if none.
     */
    @Nullable
    protected abstract Class getPresenterClass();

    /**
     * Gets this fragment's view model class. Return null if none.
     */
    @Nullable
    protected abstract Class getViewModelClass();

    /**
     * Binds the view model and presenter associated with this fragment to the generated fragment's view data binding object
     * @param binding the generated view data binding class
     * @param viewModel the view model to bind
     * @param presenter the presenter to bind
     */
    protected abstract void bindToFragment(DB binding, VM viewModel, P presenter);

    /**
     * Callback that is invoked when a child fragment is added to this fragment
     */
    @Override
    public abstract void onChildFragmentAttached(BaseFragment fragment);

    private PublishSubject<Bundle> bundlePublishSubject;

    private PublishSubject<Fragment> childFragmentPublishSubject;

    public @IdRes int containerId;

    public BaseFragment() {
        // Required empty public constructor
    }

    @Override
    @CallSuper
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null && !getArguments().isEmpty()) {
            setBundle(getArguments());
        }

        if (TextUtils.isEmpty(fragmentTag)) {
            fragmentTag = getClass().getSimpleName();
        }

        setHasOptionsMenu(false);
    }

    @SuppressWarnings("unchecked")
    @Override
    @CallSuper
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, getLayoutResId(), container, false);
        rootView = binding.getRoot();

        if (getArguments() != null && !getArguments().isEmpty()) {
            setBundle(getArguments());
        }

        butterknifeUnbinder = ButterKnife.bind(this, rootView);

        if (presenter == null) {
            try {
                if (getPresenterClass() != null) {
                    presenter = (P) getPresenterClass().newInstance();
                } else {
                    presenter = null;
                }
            } catch (Exception e) {
                Timber.e(e, "Failed to create presenter for " + getClass().getSimpleName());
            }
        }

        if (viewModel == null) {
            try {
                if (getViewModelClass() != null) {
                    viewModel = (VM) getViewModelClass().newInstance();
                } else {
                    viewModel = null;
                }
            } catch (Exception e) {
                Timber.e(e, "Failed to create view model for " + getClass().getSimpleName());
            }
        }

        if (presenter != null && viewModel != null) {

            // Bind the view model and presenter to the fragment
            bindToFragment(binding, viewModel, presenter);

            // Bind the view model to the presenter
            //noinspection unchecked
            presenter.bindViewModel(viewModel);

            // Bind the presenter to the view model
            presenter.bindToViewModel();

            // Attach the view to the presenter
            //noinspection unchecked
            presenter.attachView(this);

            // And bind the view to the viewmodel
            viewModel.bindView(this);
        }

        init(rootView, savedInstanceState);

        if (presenter != null && isMvpvm()) {
            // Sanity checks
            presenter.checkViewModelBound();
            presenter.checkViewAttached();

            // Now, if those checks succeed, create the presenter
            presenter.onCreate();

            // Do initial load of data
            // NOTE: This is important, we only do this for fragments that do not have a bundle waiting.
            // Essentially this means the presenter can already start loading data because there is
            // no dependence on something else (which is the bundle).

            if (!waitingForBundle.get()) {
                letPresenterLoadData();
            }
        }

        // Shouldn't happen
        if (rootView == null) {
            View view = super.onCreateView(inflater, container, savedInstanceState);

            if (view == null) {
                throw new RuntimeException("This view is null. Something went wrong in configuring this fragment.");
            }

            return view;
        }

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Shouldn't be possible but check anyway and fix
        if (rootView == null) {
            rootView = view;
        }

        // Notify the bundle subject if the fragment is ready to notify (which is here)
        if (bundle != null && bundlePublishSubject != null && presenter != null) {
            bundlePublishSubject.onNext(bundle);
        }

        // Notify the child fragment subject if the fragment is ready to notify (which is here)
        if (pendingChildFragment != null && childFragmentPublishSubject != null) {
            childFragmentPublishSubject.onNext(pendingChildFragment);
        }
    }

    /**
     * Allows the presenter to load the data required for the fragment
     */
    private void letPresenterLoadData() {
        if (presenter == null) {
            throw new RuntimeException("Can not load data with a presenter that does not exist.");
        }

        // Tell the presenter to load the data // TODO should this be done on a background thread? Maybe run some benchmarks
        presenter.loadData();

        // Let the fragment know the data has been loaded
        onDataLoaded();
    }

    @Override
    @CallSuper
    public void onAttach(Context context) {
        super.onAttach(context);

        // Sanity check
        if (context instanceof BaseActivity) {
            BaseActivity activity = (BaseActivity) context;

            this.activity = activity;

            if (TextUtils.isEmpty(fragmentTag) || TextUtils.isEmpty(activity.getCurrentFragmentTag())) {
                fragmentTag = getClass().getSimpleName();
                activity.setCurrentFragmentTag(fragmentTag);
            }

            activity.onFragmentAttached(this);
        }
    }

    @Override
    public void onDetach() {

        super.onDetach();

        // Fragment is going to be destroyed soon, clear the tag so that the detached fragment can't be found in the hosting activity.
        fragmentTag = null;
        activity.setCurrentFragmentTag(null);

        activity.onFragmentDetached(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (activity != null) {
            if (TextUtils.isEmpty(fragmentTag)) {
                fragmentTag = getClass().getSimpleName();
            }

            activity.setCurrentFragmentTag(fragmentTag);
            activity.onFragmentResumed(this);
        }
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);

        if (childFragment instanceof BaseFragment) {
            onChildFragmentAttached((BaseFragment) childFragment);
        }
    }

    public VM getViewModel() {
        return viewModel;
    }

    public P getPresenter() {
        return presenter;
    }

    @Override
    public abstract void onDataLoaded();

    @NonNull
    public View getRootView() {
        if (rootView == null) {
            throw new RootViewNotFoundException();
        }

        return rootView;
    }

    @Override
    public void showError(@StringRes int resId) {
        if (activity != null) {
            activity.showError(resId);
        }
    }

    @Override
    public boolean isNetworkConnected() {
        return activity != null && activity.isNetworkConnected();
    }

    @Override
    public void hideKeyboard() {
        if (activity!= null) {
            activity.hideKeyboard();
        }
    }

    public BaseActivity getBaseActivity() {
        return activity;
    }

    @Override
    public Context getContext() {
        return rootView.getContext();
    }

    @Override
    public void setBundle(Bundle bundle) {
        // Check for empty bundle, which should never happen as it is useless.
        if (bundle != null && bundle.isEmpty()) {
            throw new RuntimeException("You are setting a bundle that has no information. There is no point to this.");
        }

        // We have received a bundle - this means that we should wait until calling onBundleReceived() for the fragment (see below)
        waitingForBundle.set(true);

        this.bundle = bundle;
        setArguments(bundle);

        // Bundle has been set, create the subject
        bundlePublishSubject = PublishSubject.create();

        // Filter upon non-null bundle and call the callback once it's ready
        bundlePublishSubject.filter(bundle1 -> bundle1 != null && !bundle1.isEmpty()).subscribe(bundle2 -> {
            if (bundle2 != null && !bundle2.isEmpty() && presenter != null) {
                BaseFragment.this.onBundleReceived(bundle2);

                if (waitingForBundle.get()) {
                    // This presenter had to wait for some predicate data to arrive, now it is here, so load the data
                    BaseFragment.this.letPresenterLoadData();
                    waitingForBundle.set(false);
                }
            }
        });
    }

    /**
     * Adds a child fragment to this fragment.
     * @param containerId the layout id of the container
     * @param fragment the fragment to add
     * @param addToBackStack flag indicating that the fragment should be added to the backstack
     */
    @SuppressWarnings({"UnnecessaryReturnStatement", "Convert2MethodRef"})
    public void addChildFragment(@IdRes int containerId, @NonNull Fragment fragment, boolean addToBackStack) {
        if (binding == null) {
            Exception exception = new RuntimeException("Fragment not ready yet to be added as a child fragment.");

            Timber.e(exception);
            return;
        }

        // Save the pending child fragment that must be added
        pendingChildFragment = fragment;

        FragmentManager childFragmentManager = getChildFragmentManager();
        FragmentTransaction childFragmentTransaction = childFragmentManager.beginTransaction();

        // Fragment can only be added if the container exists.
        // If the container can already be found, add the fragment immediately.
        // Otherwise, if onCreateView() has't been called yet, we have to wait until adding the pending child fragment.
        if (ViewUtil.doesViewExistInFragment(this, containerId)) {
            childFragmentTransaction.add(containerId, fragment);

            // Include in backstack if flag is set
            if (addToBackStack) {
                childFragmentTransaction.addToBackStack(null);
            }

            childFragmentTransaction.commit();

            // Hide this fragment
            ViewUtil.hideView(getRootView());

            // And set the fragment as being visible in this container fragment
            ViewUtil.showViewInFragment(this, containerId);

            return;
        } else {
            // Bundle has been set, create the subject
            childFragmentPublishSubject = PublishSubject.create();

            // Filter upon non-null child fragment and call the callback once it's ready
            childFragmentPublishSubject.filter(childFragment -> childFragment != null).subscribe(fragment1 -> {
                if (fragment1 != null) {
                    addChildFragment(containerId, fragment1, addToBackStack);
                    pendingChildFragment = null;
                    childFragmentPublishSubject = null;
                }
            });
        }
    }

    /**
     * Replaces this fragment with another one
     * @param containerId the layout id of the container
     * @param fragment the fragment to replace the current one
     * @param addToBackStack flag indicating that the fragment should be added to the backstack
     */
    public void replaceFragment(@IdRes int containerId, @NonNull Fragment fragment, boolean addToBackStack) {
        if (binding == null) {
            Exception exception = new RuntimeException("Fragment not ready yet to be replaced.");

            Timber.e(exception);
            return;
        }

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Fragment can only be replaced
        fragmentTransaction.replace(containerId, fragment);

        // Include in backstack if flag is set
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }

        fragmentTransaction.commit();

        // And set the fragment as being visible
        ViewUtil.showViewInFragment(this, containerId);
    }

    public Bundle getBundle() {
        return bundle;
    }

    @Override
    public void onDestroy() {
        if (butterknifeUnbinder != null) {
            butterknifeUnbinder.unbind();
            butterknifeUnbinder = null;
        }

        if (presenter != null) {
            presenter.onDestroy();
            presenter = null;
        }

        binding = null;
        presenter = null;
        viewModel = null;

        super.onDestroy();
    }

    public String getFragmentTag() {
        return fragmentTag;
    }

    public void setFragmentTag(String fragmentTag) {
        this.fragmentTag = fragmentTag;
    }

    /**
     * Callbacks for fragment-related lifecycle things.
     */
    @SuppressWarnings({"WeakerAccess", "unused"})
    public interface Callback {

        void onFragmentAttached(BaseFragment fragment);

        void onFragmentDetached(BaseFragment fragment);

        void onFragmentResumed(BaseFragment fragment);
    }
}
