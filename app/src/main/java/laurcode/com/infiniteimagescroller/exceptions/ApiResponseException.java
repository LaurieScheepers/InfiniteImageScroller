package laurcode.com.infiniteimagescroller.exceptions;

import laurcode.com.infiniteimagescroller.util.CrashUtil;

/**
 * INSERT DESCRIPTION OF CLASS HERE
 * <p>
 * Created by lauriescheepers on 2017/11/23.
 */

public class ApiResponseException extends Exception {

    public ApiResponseException() {
        super("We didn't receive a proper response from the API.");
    }

    public ApiResponseException(int statusCode) {
        super("We didn't receive a proper response from the API. Status code = {" + statusCode + "}");
    }

    public ApiResponseException(String error) {
        super("We didn't receive a proper response from the API. Error string = {\"" + error + "\"}");
    }

    public ApiResponseException(Exception e) {
        super("ApiResponseException: Yo dawg, I heard you liked exceptions so we put an exception in your exception - exception details = {\"" + e.toString() + "\"}");

        CrashUtil.logCrash("The ApiResponseException was caused by another exception: Check logs", e);
    }
}
