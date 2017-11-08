package laurcode.com.infiniteimagescroller.models;

/**
 * The upgrade status of a user - denotes whether the user is a premium user. <p>
 * Non-zero values identify premium users; a value of 2 identifies an Awesome user while a value of 1 identifies a Plus user.
 * Other states may be added in the future, so write your parsers accordingly.
 * <p>
 * Created by lauriescheepers on 2017/11/08.
 */

@SuppressWarnings("WeakerAccess")
public class UpgradeStatus {

    private int status; // Int denoting status of user

    public static final int FREE = 0;
    public static final int PLUS = 1;
    public static final int AWESOME = 2;

    public UpgradeStatus(int status) {
        this.status = status;
    }

    public boolean isPremium() {
        return status > FREE;
    }

    public boolean isPlus() {
        return status == PLUS;
    }

    public boolean isAwesome() {
        return status == AWESOME;
    }

    public boolean isNextLevel() {
        return status > AWESOME;
    }
}
