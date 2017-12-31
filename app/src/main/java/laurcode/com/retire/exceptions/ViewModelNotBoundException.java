package laurcode.com.retire.exceptions;

/**
 * Exception that will be thrown when a view model is not bound to its presenter
 *
 * <p/>
 * Created by laurie on 2017/09/13.
 */

public class ViewModelNotBoundException extends RuntimeException {
    public ViewModelNotBoundException() {
        super("Please call Presenter.bindViewModel(BaseViewModel) before" +
                " requesting data to the Presenter");
    }
}
