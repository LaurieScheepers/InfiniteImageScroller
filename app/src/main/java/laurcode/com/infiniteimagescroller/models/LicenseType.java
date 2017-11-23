package laurcode.com.infiniteimagescroller.models;

import android.annotation.SuppressLint;

import java.util.HashMap;

/**
 * Enum describing the different types of licenses for pictures on 500px
 * <p>
 * Created by lauriescheepers on 2017/11/08.
 */

public enum LicenseType {

    STANDARD(0),
    CCL_NON_COMMERCIAL_ATTRIBUTION(1),
    CCL_NON_COMMERCIAL_NO_DERIVATIVES(2),
    CCL_NON_COMMERCIAL_SHARE_ALIKE(3),
    CCL_ATTRIBUTION(4),
    CCL_NO_DERIVATES(5),
    CCL_SHARE_ALIKE(6),
    CCL_PUBLIC_DOMAIN_MARK_1_0(7),
    CCL_PUBLIC_DOMAIN_DEDICATION(8);

    public int type;

    LicenseType(int type) {
        this.type = type;
    }

    // A map of the license types (key is the unique type, and value is the LicenseType object itself)
    @SuppressLint("UseSparseArrays")
    private static HashMap<Integer, LicenseType> map = new HashMap<>();

    @SuppressLint("UseSparseArrays")
    public static void addLicenseTypesToMap() {
        if (map == null) {
            map = new HashMap<>();
        }

        for (LicenseType licenseType : LicenseType.values()) {
            map.put(licenseType.type, licenseType);
        }
    }

    public static LicenseType licenseTypeFromNumber(int typeNumber) {
        for (LicenseType licenseType : LicenseType.values()) {
            if (licenseType.type == typeNumber) {
                return licenseType;
            }
        }

        return STANDARD;
    }

}
