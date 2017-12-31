package laurcode.com.retire.exceptions;

/**
 * An exception that happens when the root view of an activity or fragment can't be found
 *
 * <p/>
 * Created by lauriescheepers on 2017/09/16.
 */

public class RootViewNotFoundException extends RuntimeException {
    public RootViewNotFoundException() {
        super("Root view can't be found. Check if the layout is attached.");
    }
}
