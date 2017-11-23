package laurcode.com.infiniteimagescroller.db.realm;

import com.crashlytics.android.Crashlytics;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmSchema;
import timber.log.Timber;

/**
 * Responsible for handling Realm migrations correctly
 *
 * <p/>
 * Created by Laurie on 2017/05/10.
 */

public class RealmMigrationHandler implements RealmMigration {
    /**
     * The version of the our DB. Higher it by one if the DB schema changes (if you are adding a new field or something
     * on a new version). Values 0..n
     */
    public final static int LIVEAMP_DB_VERSION = 0;

    /**
     * Checks if we need to update the DB (on new app version), if we need to than it updates it.
     */
    public static void init() {

        RealmConfiguration config = new RealmConfiguration.Builder()
                .schemaVersion(LIVEAMP_DB_VERSION) // Must be bumped when the schema changes
                .migration(new RealmMigrationHandler()) // Migration to run instead of throwing an exception
                .build();

        Realm.setDefaultConfiguration(config);

        Realm realm = Realm.getInstance(config);
        realm.close();
    }

    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {

        try {

            Timber.d("Migrating DB from version %d to version %d", oldVersion, newVersion);

            // DynamicRealm exposes an editable schema
            RealmSchema schema = realm.getSchema();

            // Migrate to version 1:
            if (oldVersion == 0) {

                oldVersion++;
            }

        } catch (Exception e) {

            Crashlytics.logException(new Exception("Migration failed: old version = " + oldVersion + ", new version " + newVersion));
        }
    }
}
