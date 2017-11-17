package laurcode.com.infiniteimagescroller.main;

import android.app.Application;
import android.databinding.BaseObservable;
import android.support.v7.app.AppCompatDelegate;

import com.crashlytics.android.Crashlytics;
import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import java.util.HashMap;

import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import laurcode.com.infiniteimagescroller.BuildConfig;
import laurcode.com.infiniteimagescroller.models.Category;
import laurcode.com.infiniteimagescroller.models.LicenseType;
import timber.log.Timber;

/**
 * The Main Application class of this app. This is mainly used for initialisation of various libraries
 * <br><br>
 * Created by lauriescheepers on 2017/11/06.
 */

public class MainApplication extends Application {

    /**
     * An in-memory cache of view model objects. This will persist app-wide
     */
    private static HashMap<Class, BaseObservable> viewModels;

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

        // Lib initialisation only for debug builds
        if (BuildConfig.DEBUG) {
            // Init Timber, for logging.
            // NOTE: The tree is only planted in debug mode. The Timber.log() statements in the code will be a no-op in production.
            // Take a look at (https://github.com/JakeWharton/timber/issues/210)
            Timber.plant(new Timber.DebugTree());

            // Init Stetho, for debugging
            Stetho.initialize(
                    Stetho.newInitializerBuilder(this)
                            .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                            .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                            .build());

            // Init Leakcanary, let's find those memory leaks and eliminate them
            //noinspection ConstantConditions
            if (LeakCanary.isInAnalyzerProcess(this)) {
                // This process is dedicated to LeakCanary for heap analysis.
                // You should not init your app in this process.
                return;
            }

            LeakCanary.install(this);
        }

        // Enum initialisation
        LicenseType.addLicenseTypesToMap();
        Category.addCategoriesToMap();

        // ViewModels initialisation
        viewModels = new HashMap<>();
    }

    public static void registerViewModel(Class clazz, BaseObservable viewModel) {
        viewModels.put(clazz, viewModel);
    }
}
