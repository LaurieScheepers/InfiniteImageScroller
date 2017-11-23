package laurcode.com.infiniteimagescroller.base;

import android.content.Context;

import io.realm.Realm;

/**
 * The presenter interface. Every presenter in the app must either implement this interface or extend BasePresenter
 * indicating the MvpView type that wants to be attached with.
 *
 * <p/>
 * Created by laurie on 2017/09/13.
 */

public interface Presenter<BV extends BaseView, VM extends BaseViewModel> {

    Context getContext();

    void onCreate();

    void onDestroy();

    void attachView(BV view);

    void detachView();

    void bindViewModel(VM viewModel);

    void bindToViewModel();

    void unbindViewModel();

    void loadData();

    BV getView();

    VM getViewModel();

    Realm getRealm();
}
