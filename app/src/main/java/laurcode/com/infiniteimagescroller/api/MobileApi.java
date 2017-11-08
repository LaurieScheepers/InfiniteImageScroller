package laurcode.com.infiniteimagescroller.api;

import android.support.annotation.NonNull;

import io.reactivex.Observable;
import laurcode.com.infiniteimagescroller.models.FreshestPhotos;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * The API used in the app. Contains method definitions for the API's used for the 500px image retrieval. <p>
 * Info on the API can be found <a href="https://github.com/500px/api-documentation">here</a>.
 * <p>
 * Created by lauriescheepers on 2017/11/07.
 */

public interface MobileApi {

    /**
     * GETS todays freshest photos from the 500px API
     * @param consumerKey the consumer key as registered on the 500px dashboard for this app
     * @return the observable containing the FreshestPhotos DB model object
     */
    @GET("photos")
    Observable<Response<FreshestPhotos>> getFreshestPhotos(@Query("consumer_key") @NonNull String consumerKey, @Query("feature") String feature, @Query("page") Integer page);
}
