package laurcode.com.retire.db.photo.dao;

import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;

import io.realm.Realm;
import io.realm.RealmResults;
import laurcode.com.retire.models.FreshestPhotos;

/**
 * DAO (data access object) class for handling saving/loading of photos from the DB
 * <p>
 * Created by lauriescheepers on 2017/11/07.
 */

public class PhotosDao {

    /**
     * Saves the freshest photos to the DB using the supplied instance of Realm. This method blocks so please use it on a background thread.
     * NOTE: This method does not close Realm after use, so please take care to close it when done.
     * @param realm the Realm instance supplied
     * @param freshestPhotos the freshest photos DB model object
     */
    @WorkerThread
    public static void saveFreshestPhotosSync(@NonNull Realm realm, @NonNull FreshestPhotos freshestPhotos) {
        realm.executeTransaction(realm1 -> realm1.copyToRealmOrUpdate(freshestPhotos));
    }

    /**
     * Loads the freshest photos from the DB using the supplied instance of Realm. This method blocks so please use it on a background thread.
     * NOTE: Realm is not closed in this method, instead it is the consumer's responsibility to close it after use.
     * @return a RealmResults object containing the freshest photos DB model objects
     */
    @WorkerThread
    public static RealmResults<FreshestPhotos> loadFreshestPhotosSync(@NonNull Realm realm) {
        return realm.where(FreshestPhotos.class).findAll();
    }
}
