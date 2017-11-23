package laurcode.com.infiniteimagescroller.exceptions;

/**
 * An exception that will be thrown when an Mvp view is not attached
 *
 * <p/>
 * Created by laurie on 2017/09/13.
 */

public class ViewNotAttachedException extends RuntimeException {
    public ViewNotAttachedException() {
        super("Please call Presenter.attachView(BaseView) before" +
                " requesting data to the Presenter");
    }
}
