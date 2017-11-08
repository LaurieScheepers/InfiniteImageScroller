package laurcode.com.infiniteimagescroller.models;

import com.google.gson.annotations.Expose;

/**
 * Model object class representing the "default" object contained in the base freshest photos response
 * <p>
 * Created by lauriescheepers on 2017/11/08.
 */

@SuppressWarnings("WeakerAccess")
public class DefaultAvatar {

    @Expose
    public String https;

    public String getHttps() {
        return https;
    }

    public void setHttps(String https) {
        this.https = https;
    }
}
