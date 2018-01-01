package laurcode.com.retire.exceptions;

/**
 * A custom exception indicating that it was impossible finding the stacktrace of an exception or thread.
 * <br><br>
 * Created by lauriescheepers on 2017/10/18.
 */

public class NoStackTraceException extends Exception {

    public NoStackTraceException() {
        super("It was impossible to find a stacktrace. Sorry about that, nothing we can do about it.");
    }
}
