package laurcode.com.infiniteimagescroller.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Model object class representing the user avatars object contained in the base FreshestPhotosResponse
 * <p>
 * Created by lauriescheepers on 2017/11/08.
 */

public class Avatars extends RealmObject {

    @SerializedName("default")
    @Expose
    private DefaultAvatar _default;
    @SerializedName("large")
    @Expose
    private LargeAvatar large;
    @SerializedName("small")
    @Expose
    private SmallAvatar small;
    @SerializedName("tiny")
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
}
