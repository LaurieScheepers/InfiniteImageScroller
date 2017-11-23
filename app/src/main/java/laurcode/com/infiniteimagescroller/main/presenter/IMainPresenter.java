package laurcode.com.infiniteimagescroller.main.presenter;

import laurcode.com.infiniteimagescroller.base.Presenter;
import laurcode.com.infiniteimagescroller.main.view.IMainView;
import laurcode.com.infiniteimagescroller.main.viewmodel.MainViewModel;

/**
 * Method definitions for the main presenter
 * <p>
 * Created by lauriescheepers on 2017/11/23.
 */

public interface IMainPresenter extends Presenter<IMainView, MainViewModel> {
    void showPacmanProgress();

    void hidePacmanProgress();
}
