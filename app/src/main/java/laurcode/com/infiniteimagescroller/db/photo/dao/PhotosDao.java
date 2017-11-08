package laurcode.com.infiniteimagescroller.db.photo.dao;

import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;

import io.realm.Realm;
import laurcode.com.infiniteimagescroller.models.FreshestPhotos;

/**
 * DAO (data access object) class for handling saving/loading of photos from the DB
 * <p>
 * Created by lauriescheepers on 2017/11/07.
 */

public class PhotosDao {

    /**
     * Saves the freshest photos to the DB
     * @param freshestPhotos the freshest photos DB model object
     */
    @WorkerThread
    public void saveFreshestPhotosSync(@NonNull FreshestPhotos freshestPhotos) {
        // TODO implement
    }

    /**
     * Loads the freshest photos from the DB
     * @return the freshest photos DB model object
     */
    @WorkerThread
    public FreshestPhotos loadFreshestPhotosSync() {
        Realm realm = Realm.getDefaultInstance();
        // TODO implement
        return null;
    }
}
