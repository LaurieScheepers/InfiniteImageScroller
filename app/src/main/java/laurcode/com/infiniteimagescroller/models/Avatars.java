package laurcode.com.infiniteimagescroller.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Model object class representing the user avatars object contained in the base FreshestPhotosResponse
 * <p>
 * Created by lauriescheepers on 2017/11/08.
 */

public class Avatars extends RealmObject {

    @PrimaryKey
    private int id;

    @SerializedName("default")
    @Expose
    private DefaultAvatar _default; // default is reserved keyword in java 8, so cannot call it just that, hence the "serializedName" annotation

    @Expose
    private LargeAvatar large;

    @Expose
    private SmallAvatar small;

    @Expose
    private TinyAvatar tiny;

    public DefaultAvatar getDefault() {
        return _default;
    }

    public void setDefault(DefaultAvatar _default) {
        this._default = _default;
    }

    public LargeAvatar getLarge() {
        return large;
    }

    public void setLarge(LargeAvatar large) {
        this.large = large;
    }

    public SmallAvatar getSmall() {
        return small;
    }

    public void setSmall(SmallAvatar small) {
        this.small = small;
    }

    public TinyAvatar getTiny() {
        return tiny;
    }

    public void setTiny(TinyAvatar tiny) {
        this.tiny = tiny;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
