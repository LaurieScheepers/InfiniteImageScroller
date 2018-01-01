package laurcode.com.retire.api.listeners;

/**
 * A listener that will notify when photos have been retrieved successfully, or if an error occurred.
 * <p>
 * Created by lauriescheepers on 2017/11/07.
 */

public interface PhotosRetrievedListener {

    void onSuccess();

    void onError(Throwable error);
}
