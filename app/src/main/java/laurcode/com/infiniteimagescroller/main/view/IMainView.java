package laurcode.com.infiniteimagescroller.main.view;

import laurcode.com.infiniteimagescroller.base.BaseView;

/**
 * Contains methods to with the main view - i.e. the MainFragment
 * <p>
 * Created by lauriescheepers on 2017/11/23.
 */

public interface IMainView extends BaseView {

    void onEndOfListReached();

    void showPacmanProgress();

    void hidePacmanProgress();
}
