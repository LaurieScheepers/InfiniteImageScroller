package laurcode.com.retire.api.helpers;

import android.content.Context;
import android.support.annotation.NonNull;

import java.io.IOException;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import laurcode.com.retire.api.MobileApi;
import laurcode.com.retire.api.RestClient;
import laurcode.com.retire.api.listeners.PhotosRetrievedListener;
import laurcode.com.retire.db.photo.dao.PhotosDao;
import laurcode.com.retire.exceptions.ApiResponseException;
import laurcode.com.retire.models.FreshestPhotos;
import laurcode.com.retire.util.CrashUtil;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Helper class for API calls related to retrieving photos used in the app
 * <p>
 * Created by lauriescheepers on 2017/11/07.
 */

@SuppressWarnings("ConstantConditions")
public class PhotosApiHelper {

    /**
     * Observes the response
     * @param listener the listener that will invoke callbacks of success/error
     * @return a disposable observer, for chaining of calls
     */
    public static DisposableObserver<Response<FreshestPhotos>> observe(@NonNull PhotosRetrievedListener listener) {
        return new DisposableObserver<Response<FreshestPhotos>>() {
            @SuppressWarnings("UnnecessaryReturnStatement")
            @Override
            public void onNext(Response<FreshestPhotos> freshestPhotosResponse) {
                if (freshestPhotosResponse == null) {
                    // Obviously something is wrong here.

                    listener.onError(new ApiResponseException());

                    return;
                }

                if (!freshestPhotosResponse.isSuccessful()) {

                    // Means it's 400/500 error - I'm not adding real specific error handling in this demo app. It's either success or failure
                    listener.onError(new ApiResponseException(freshestPhotosResponse.code()));

                    return;
                }

                if (freshestPhotosResponse.errorBody() != null) {
                    // There is an error body in the API response - so notify the listener
                    try {
                        listener.onError(new ApiResponseException(freshestPhotosResponse.errorBody().string()));
                    } catch (IOException e) {
                        listener.onError(new ApiResponseException());
                    }

                    return;
                }

                FreshestPhotos freshestPhotos = freshestPhotosResponse.body();

                if (freshestPhotos != null && freshestPhotos.getPhotos() != null && !freshestPhotos.getPhotos().isEmpty()) {

                    Timber.d("We got a successful response from the server with data, saving to Realm now and posting event to update the UI");

                    try (Realm realm = Realm.getDefaultInstance()) {
                        PhotosDao.saveFreshestPhotosSync(realm, freshestPhotos);
                    }

                    // Success! Notify the listener
                    listener.onSuccess();
                } else {
                    // We got a successful response from the server but with no actual data - this shouldn't happen
                    listener.onError(new ApiResponseException("No data received from server"));
                }
            }

            @Override
            public void onError(Throwable t) {
                CrashUtil.logCrash("Error in syncing data!", t);

                listener.onError(new ApiResponseException(t.toString()));
            }

            @Override
            public void onComplete() {
                // Ignore
            }
        };
    }

    /**
     * Helper method to get the freshest photos from 500px API
     * @param context the context to use for the Rest Client
     * @param scheduler the scheduler on which to schedule the API call
     * @param compositeDisposable the disposable that will contain the call
     * @param consumerKey the consumer key used in the API call
     * @param page the current page used in the API call
     * @param listener the listener that will invoke callbacks for success/error
     */
    @SuppressWarnings("ConstantConditions")
    public static void getFreshestPhotos(@NonNull Context context,
                                         @NonNull Scheduler scheduler,
                                         @NonNull CompositeDisposable compositeDisposable,
                                         @NonNull String consumerKey,
                                         @NonNull Integer page,
                                         @NonNull PhotosRetrievedListener listener) {

        MobileApi mobileApi = RestClient.getClient(context).getMobileApi();

        if (scheduler == null) {
            throw new RuntimeException("Thread destroyed!"); // This should not happen. If it does, something else is wrong.
        }

        // Add the API call to the disposable
        compositeDisposable.add(mobileApi.getFreshestPhotos(consumerKey, "fresh_today", page)   // We want the feature parameter to be the freshest photos of the day
                .subscribeOn(Schedulers.io())
                .observeOn(scheduler)
                .subscribeWith(observe(listener)));
    }
}
