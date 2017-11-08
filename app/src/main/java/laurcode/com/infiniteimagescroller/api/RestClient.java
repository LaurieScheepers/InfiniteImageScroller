package laurcode.com.infiniteimagescroller.api;

import android.content.Context;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * The main class responsible for communication with the API
 *
 * <p/>
 * Created by Laurie on 2017/08/13.
 */

@SuppressWarnings("WeakerAccess")
public class RestClient {

    /**
     * The base url used for API comms
     */
    public static final String BASE_URL = "https://500px.com/"; // NOTE: Must end with "/" - this is the standard way of defining a base url

    private static RestClient restClient;
    private static Gson gson;
    private static OkHttpClient client;
    private static Retrofit retrofit;
    private static MobileApi mobileApi;

    public static RestClient getClient(Context context) {
        if (restClient == null) {
            restClient = new RestClient(context);
        }

        return restClient;
    }

    private RestClient(Context context) {

        // Create the things we need for networking
        client = createOkHttpClient();

        gson = createGson();

        retrofit = createRetrofit();
    }

    @SuppressWarnings("UnnecessaryLocalVariable")
    private static OkHttpClient createOkHttpClient() {
        // Create a logging interceptor (to show detailed logs about the request and response).
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        return client;
    }

    private static Gson createGson() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .excludeFieldsWithoutExposeAnnotation()
                .create();
    }

    private static Retrofit createRetrofit() {
        return new Retrofit.Builder()
                .client(client)                                             // Add OkHttp client
                .baseUrl(BASE_URL)                                          // Set the base url on retrofit
                .addConverterFactory(GsonConverterFactory.create(gson))     // Add GSON converter
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())  // Add RxJava call adapter
                .build();
    }

    public MobileApi getMobileApi() {
        if (mobileApi == null) {
            return retrofit.create(MobileApi.class);
        }

        return mobileApi;
    }

    public static Retrofit getRetrofit(Context context) {
        if (retrofit == null) {

            if (gson == null) {
                gson = createGson();
            }

            if (client == null) {
                client = createOkHttpClient();
            }

            retrofit = createRetrofit();
        }

        return retrofit;
    }

    public static Gson getGson() {
        if (gson == null) {
            gson = createGson();
        }
        return gson;
    }
}
