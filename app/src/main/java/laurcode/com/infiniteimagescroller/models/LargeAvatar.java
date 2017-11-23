package laurcode.com.infiniteimagescroller.models;

import com.google.gson.annotations.Expose;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Model class representing the "large" object contained in the base FreshestPhotos response
 * <p>
 * Created by lauriescheepers on 2017/11/08.
 */

@SuppressWarnings("WeakerAccess")
public class LargeAvatar extends RealmObject {

    @PrimaryKey
    private int id;

    @Expose
    private String https;

    public String getHttps() {
        return https;
    }

    public void setHttps(String https) {
        this.https = https;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
