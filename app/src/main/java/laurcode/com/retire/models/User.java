package laurcode.com.retire.models;

import com.google.gson.annotations.Expose;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Model object class representing the "user" object contained in the base FreshestPhotos response
 * <p>
 *
 * @see <a href="https://github.com/500px/api-documentation/blob/master/basics/formats_and_terms.md#short-format-1">User Short Format</a>
 * Created by lauriescheepers on 2017/11/08.
 */

public class User extends RealmObject {

    @PrimaryKey
    @Expose
    private int id;

    @Expose
    private String username;

    @Expose
    private String firstname;

    @Expose
    private String lastname;

    @Expose
    private String city;

    @Expose
    private String country;

    @Expose
    private String fullname;

    @Expose
    private String userpicUrl;

    @Expose
    private int upgradeStatus;

    @Expose
    private boolean storeOn;

    @Expose
    private int affection;

    @Expose
    private Avatars avatars;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUserpicUrl() {
        return userpicUrl;
    }

    public void setUserpicUrl(String userpicUrl) {
        this.userpicUrl = userpicUrl;
    }

    public int getUpgradeStatus() {
        return upgradeStatus;
    }

    public void setUpgradeStatus(int upgradeStatus) {
        this.upgradeStatus = upgradeStatus;
    }

    public boolean isStoreOn() {
        return storeOn;
    }

    public void setStoreOn(boolean storeOn) {
        this.storeOn = storeOn;
    }

    public int getAffection() {
        return affection;
    }

    public void setAffection(int affection) {
        this.affection = affection;
    }

    public Avatars getAvatars() {
        return avatars;
    }

    public void setAvatars(Avatars avatars) {
        this.avatars = avatars;
    }
}
