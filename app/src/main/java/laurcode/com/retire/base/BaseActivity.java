package laurcode.com.retire.base;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.IdRes;
import android.support.annotation.IntegerRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import javax.annotation.Nonnegative;

import butterknife.ButterKnife;
import laurcode.com.retire.R;
import laurcode.com.retire.exceptions.RootViewNotFoundException;
import laurcode.com.retire.util.KeyboardUtil;
import laurcode.com.retire.util.NetworkUtil;
import timber.log.Timber;

/**
 * The base Activity class. All other activities inherit from this. The base activity has the connected
 * presenter and view model objects as member variables, so any children classes will automatically
 * follow the MVPVM pattern.
 *
 * <p/>
 * Created by laurie on 2017/09/13.
 */

@SuppressWarnings("unused")
public abstract class BaseActivity<DB extends ViewDataBinding, VM extends BaseViewModel,
        P extends BasePresenter> extends AppCompatActivity implements BaseView, BaseFragment.Callback {

    protected DB binding;

    protected P presenter;

    protected VM viewModel;

    private String currentFragmentTag;

    @Nonnegative
    protected abstract @LayoutRes int getLayoutResId();

    protected abstract void init(Bundle savedInstanceState);

    /**
     * Returns true if this activity follows the MVPVM pattern
     */
    protected abstract boolean isMvpvm();

    /**
     * Gets this activity's presenter class. Return null if none.
     */
    @Nullable
    protected abstract Class<P> getPresenterClass();

    /**
     * Gets this activity's view model class. Return null if none.
     */
    @Nullable
    protected abstract Class<VM> getViewModelClass();

    /**
     * Binds the view model and presenter associated with this activity to the generated activity's view data binding object
     * @param binding the generated view data binding class
     * @param viewModel the view model to bind
     * @param presenter the presenter to bind
     */
    protected abstract void bindToActivity(DB binding, VM viewModel, P presenter);

    @SuppressWarnings("unchecked")
    @Override
    @CallSuper
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, getLayoutResId());

        presenter = (P) getLastCustomNonConfigurationInstance();

        if (presenter == null) {
            try {
                if (getPresenterClass() != null) {
                    presenter = getPresenterClass().newInstance();
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
                    viewModel = getViewModelClass().newInstance();
                } else {
                    viewModel = null;
                }
            } catch (Exception e) {
                Timber.e(e, "Failed to create view model for " + getClass().getSimpleName());
            }
        }

        if (presenter != null && viewModel != null) {

            // Bind the view model and presenter to the activity
            bindToActivity(binding, viewModel, presenter);

            // Bind the view model to the presenter
            presenter.bindViewModel(viewModel);

            // Bind the presenter to the view model
            presenter.bindToViewModel();

            // Attach the view (fragment or activity) to the presenter
            presenter.attachView(this);

            // And bind the view to the viewmodel
            //noinspection unchecked
            viewModel.bindView(this);
        }

        init(savedInstanceState);

        if (presenter != null && isMvpvm()) {
            // Sanity checks
            presenter.checkViewModelBound();
            presenter.checkViewAttached();

            // Now, if those checks succeed, create the presenter
            presenter.onCreate();

            // Do the initial load of data
            presenter.loadData();

            // And let the activity handle the onDataLoaded() callback // TODO should I call this on a background thread?
            onDataLoaded();
        }
    }

    @Override
    @CallSuper
    public void setContentView(int layoutResId) {
        super.setContentView(layoutResId);
        ButterKnife.bind(this);
    }

    public VM getViewModel() {
        return viewModel;
    }

    public P getPresenter() {
        return presenter;
    }

    @NonNull
    public View getRootView() {
        View rootView = findViewById(android.R.id.content);

        if (rootView == null) {
            rootView = getWindow().getDecorView().findViewById(android.R.id.content);
        }
        if (rootView == null) {
            throw new RootViewNotFoundException();
        }

        return rootView;
    }

    /**
     * Convenience method to start an Activity from any other Activity
     * @param activity the Activity class
     */
    public void startActivity(@NonNull Class<? extends BaseActivity> activity) {
        startActivity(new Intent(this, activity));
    }

    /**
     * Convenience method to start an Activity and finish the current Activity. The old activity then won't be in the backstack.
     * @param activity the Activity class
     */
    public void startAndFinishCurrentActivity(@NonNull Class<? extends BaseActivity> activity) {
        startActivity(activity);
        finish();
    }

    /**
     * Convenience method to add a fragment dynamically to its container layout
     * @param fragment the Fragment to add
     */
    public void addFragment(@NonNull BaseFragment fragment, @IdRes int container) {

        if (findViewById(container) != null) {
            String tag = fragment.getClass().getSimpleName();
            fragment.setFragmentTag(tag);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(container, fragment, fragment.getFragmentTag());
            transaction.commit();

            currentFragmentTag = fragment.getFragmentTag();
        } else {
            throw new RuntimeException("Trying to add fragment but there is no container");
        }
    }

    /**
     * Convenience method to replace a fragment dynamically to its container layout
     * @param fragment the Fragment to add
     */
    public void replaceFragment(@NonNull BaseFragment fragment, boolean addToBackStack, @IdRes int container) {

        if (findViewById(container) != null) {
            String tag = fragment.getClass().getSimpleName();
            fragment.setFragmentTag(tag);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            if (addToBackStack) {
                transaction.addToBackStack(null);
            }

            transaction.replace(container, fragment, fragment.getFragmentTag());
            transaction.commit();

            currentFragmentTag = fragment.getFragmentTag();
        } else {
            throw new RuntimeException("Trying to add fragment but there is no container.");
        }
    }

    /**
     * Convenience method to get a fragment that was dynamically added to its container layout
     */
    @Nullable
    public BaseFragment getFragment(@IdRes int container) {

        if (findViewById(container) != null) {
            return (BaseFragment) getSupportFragmentManager().findFragmentByTag(currentFragmentTag);
        }

        return null;
    }

    public String getCurrentFragmentTag() {
        return currentFragmentTag;
    }

    public void setCurrentFragmentTag(String currentFragmentTag) {
        this.currentFragmentTag = currentFragmentTag;
    }

    @Override
    public Context getContext() {
        return getRootView().getContext();
    }

    @Override
    @CallSuper
    protected void onDestroy() {

        if (presenter != null) {
            presenter.onDestroy();
            presenter = null;
        }

        binding = null;
        viewModel = null;

        super.onDestroy();
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return presenter;   // Let the presenter survive orientation change!
    }

    @Override
    public abstract void onDataLoaded();

    @Override
    public void showError(@StringRes int resId) {
        // TODO show snackbar
    }

    @Override
    public abstract void onFragmentAttached(BaseFragment fragment);

    @Override
    public abstract void onFragmentDetached(BaseFragment fragment);

    @Override
    public boolean isNetworkConnected() {
        return NetworkUtil.isNetworkConnected(this);
    }

    @Override
    public void hideKeyboard() {
        KeyboardUtil.hideKeyboard(this);
    }
}
