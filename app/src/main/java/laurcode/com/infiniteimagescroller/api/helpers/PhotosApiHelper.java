package laurcode.com.infiniteimagescroller.api.helpers;

import android.content.Context;

import io.reactivex.observers.DisposableObserver;
import laurcode.com.infiniteimagescroller.api.listeners.PhotosRetrievedListener;
import laurcode.com.infiniteimagescroller.models.FreshestPhotos;
import retrofit2.Response;

/**
 * Helper class for API calls related to retrieving photos used in the app
 * <p>
 * Created by lauriescheepers on 2017/11/07.
 */

public class PhotosApiHelper {
    public static DisposableObserver<Response<FreshestPhotos>> observe(Context context, PhotosRetrievedListener listener) {
        return new DisposableObserver<Response<FreshestPhotos>>() {
            @SuppressWarnings("UnnecessaryReturnStatement")
            @Override
            public void onNext(Response<FreshestPhotos> freshestPhotosResponse) {
                if (freshestPhotosResponse == null) {
                    // Obviously something is wrong here. TODO add error handling
                    return;
                }

                if (!freshestPhotosResponse.isSuccessful()) {
                    // TODO add error handling
                    return;
                }

                // TODO handle success
            }

            @Override
            public void onError(Throwable e) {
                // TODO add error handling
            }

            @Override
            public void onComplete() {
                // Ignore
            }
        };


    }
}
