package laurcode.com.infiniteimagescroller.base;

import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

import io.realm.Realm;
import laurcode.com.infiniteimagescroller.exceptions.ViewModelNotBoundException;
import laurcode.com.infiniteimagescroller.exceptions.ViewNotAttachedException;

/**
 * Base class that implements the Presenter interface and provides a base implementation for attachView()
 * and detachView(). It also handles keeping a reference to the view that
 * can be accessed from the children classes by calling getView().
 *
 * <p/>
 *
 * Created by laurie on 2017/09/13.
 */

@SuppressWarnings("WeakerAccess")
public abstract class BasePresenter<BV extends BaseView, VM extends BaseViewModel> implements Presenter<BV, VM> {

    protected BV view;
    protected VM viewModel;
    protected Realm realm;

    @Override
    @CallSuper
    public void onCreate() {
        realm = Realm.getDefaultInstance();
    }

    @Override
    @CallSuper
    public void onDestroy() {
        if (realm != null) {
            realm.close();
            realm = null;
        }

        detachView();
        unbindViewModel();
    }

    @Override
    public void attachView(@NonNull BV view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }

    @Override
    public void bindViewModel(VM viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void bindToViewModel() {
        this.viewModel.presenter = this;
    }

    @Override
    public void unbindViewModel() {
        viewModel = null;
    }

    @Override
    public abstract void loadData();

    public boolean isViewAttached() {
        return view != null;
    }

    public boolean isViewModelBound() {
        return viewModel != null;
    }

    @Override
    public BV getView() {
        return view;
    }

    @Override
    public VM getViewModel() {
        return viewModel;
    }

    @Override
    public Realm getRealm() {
        return realm;
    }

    @Override
    public Context getContext() {
        return getView().getContext();
    }

    public void checkViewAttached() {
        if (!isViewAttached()) {
            throw new ViewNotAttachedException();
        }
    }

    public void checkViewModelBound() {
        if (!isViewModelBound()) {
            throw new ViewModelNotBoundException();
        }
    }
}
