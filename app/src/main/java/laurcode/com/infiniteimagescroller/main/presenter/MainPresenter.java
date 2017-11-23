package laurcode.com.infiniteimagescroller.main.presenter;

import laurcode.com.infiniteimagescroller.base.BasePresenter;
import laurcode.com.infiniteimagescroller.main.view.IMainView;
import laurcode.com.infiniteimagescroller.main.viewmodel.MainViewModel;

/**
 * Method definitions for the presenter for the main fragment
 * <p>
 * Created by lauriescheepers on 2017/11/23.
 */

public class MainPresenter extends BasePresenter<IMainView, MainViewModel> implements IMainPresenter {

    @Override
    public void loadData() {
        // TODO here we load the photos from Realm on a background thread and observe on the main thread
    }

    @Override
    public void showPacmanProgress() {
        getView().showPacmanProgress();
    }

    @Override
    public void hidePacmanProgress() {
        getView().showPacmanProgress();
    }
}
