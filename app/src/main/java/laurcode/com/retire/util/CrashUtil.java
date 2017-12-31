package laurcode.com.retire.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.crashlytics.android.Crashlytics;

import laurcode.com.retire.BuildConfig;
import laurcode.com.retire.exceptions.NoStackTraceException;
import timber.log.Timber;

/**
 * Contains helper methods for things related to crashes.
 * <p>
 * Created by lauriescheepers on 2017/11/16.
 */

@SuppressWarnings("unused")
public class CrashUtil {

    /**
     * Helper method to log a crash. If in release builds this will log the crash to Crashlytics.
     * @param e the exception causing the crash
     */
    public static void logCrash(@NonNull Exception e) {
        logCrash(null, e);
    }

    /**
     * Helper method to log a crash. If in release builds this will log the crash to Crashlytics.
     * @param customMessage a message containing extra details about the crash
     * @param t the throwable causing the error/crash
     */
    @SuppressWarnings("SameParameterValue")
    public static void logCrash(String customMessage, @NonNull Throwable t) {
        String messageLog = "FATAL ERROR";

        // Add stacktrace details
        try {
            messageLog = getStackTraceLogMessage();
        } catch (SecurityException se) {
            handleMissingStacktraceError(t, se);
        }

        // Add custom message
        if (!TextUtils.isEmpty(customMessage)) {
            messageLog = StringUtil.concatString(messageLog, "\nCustom message = {", customMessage, "}");
        }

        Timber.e(messageLog, t);

        if (BuildConfig.RELEASE) {
            Crashlytics.log(messageLog);
            Crashlytics.logException(t);
        }
    }

    /**
     * Helper method to crash because of code being used incorrectly.
     */
    public static void crashAppOnPurpose() {
        crashAppOnPurpose("There was an issue caused by incorrect code in the app. Check stacktrace for details.");
    }

    /**
     * Crashes the app on purpose to signal a programming error.
     * @param message the message to include
     */
    public static void crashAppOnPurpose(String message) {
        RuntimeException crash = new RuntimeException(message);

        // Let us know in Fabric Crashlytics.
        if (BuildConfig.RELEASE) {
            logCrash(message, crash);
        }

        Timber.e(crash);

        // Aaaaand we crash the app. BOOM :)
        throw new RuntimeException(message);
    }

    /**
     * Builds a pretty log statement based on the stack trace.
     */
    private static String getStackTraceLogMessage() {
        String messageLog = "";

        // Get the stack trace and add it to the logs.
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();

        for (StackTraceElement stackTraceElement : stackTraceElements) {
            if (stackTraceElement != null) {
                messageLog = StringUtil.concatString(messageLog, ":\n stacktrace element = {", stackTraceElement.toString(), "}");
            }
        }

        return messageLog;
    }

    /**
     * Handles an error we can't get the stack trace (probably due to a SecurityManager not allowing it to be retrieved).
     * @param originalThrowable The original throwable causing the error (before we tried to get the stack trace).
     */
    private static void handleMissingStacktraceError(@Nullable Throwable originalThrowable, @NonNull SecurityException securityException) {
        // Damn we couldn't get the stack trace because of security reasons.
        // For more info see here (https://developer.android.com/reference/java/lang/SecurityException.html)
        // In this case we log the security exception as well
        logCrash(securityException);

        // And we try and get the stacktrace using a different way and that is to get it from the original exception.
        if (originalThrowable != null) {
            StackTraceElement[] stackTraceElements = originalThrowable.getStackTrace();

            if (stackTraceElements == null || stackTraceElements.length == 0) {
                // Out of luck... Let's log this as well just for extra information
                logCrash(new NoStackTraceException());
            }
        }
    }
}
