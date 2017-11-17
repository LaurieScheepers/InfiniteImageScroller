package laurcode.com.infiniteimagescroller.util;

import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * Contains helper methods about strings not found in other libraries.
 * <p>
 * Created by lauriescheepers on 2017/11/16.
 */

@SuppressWarnings("unused")
public class StringUtil {

    /**
     * Concatenates a bunch of strings to a string
     * @param originalString the string to concat to
     * @param stringsToConcat the list of strings to concat.
     * @return the concatenated string
     */
    public static String concatString(@Nullable String originalString, String... stringsToConcat) {

        if (stringsToConcat.length == 0) {
            CrashUtil.crashAppOnPurpose("No point in trying to concat no strings to a string, is there?");
        }

        if (!TextUtils.isEmpty(originalString)) {
            String concatenatedString = originalString;

            for (String string : stringsToConcat) {
                concatenatedString = concatenatedString.concat(string);
            }

            return concatenatedString;
        }

        return originalString;
    }
}
