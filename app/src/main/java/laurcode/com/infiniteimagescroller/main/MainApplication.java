package laurcode.com.infiniteimagescroller.main;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import laurcode.com.infiniteimagescroller.BuildConfig;

/**
 * The Main Application class of this app. This is mainly used for initialisation of various libraries
 * <br><br>
 * Created by lauriescheepers on 2017/11/06.
 */

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // To enable backwards-compatible vector resources
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        // Init Realm
        Realm.init(this);

        // Create and set the default configuration
        // NOTE: For this demo app I delete the DB if migration is needed. On app upgrade, the db is populated from scratch.
        Realm.setDefaultConfiguration(new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build());

        // Lib initialisation only for release builds
        if (BuildConfig.RELEASE) {

            // Init Crashlytics
            Fabric.with(this, new Crashlytics());
        }
    }
}
