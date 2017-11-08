package laurcode.com.infiniteimagescroller.sync;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import android.support.v4.app.JobIntentService;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import laurcode.com.infiniteimagescroller.BuildConfig;
import laurcode.com.infiniteimagescroller.api.helpers.PhotosApiHelper;
import laurcode.com.infiniteimagescroller.api.listeners.PhotosRetrievedListener;
import laurcode.com.infiniteimagescroller.events.PhotosRetrievedFailedEvent;
import laurcode.com.infiniteimagescroller.events.PhotosRetrievedSuccessEvent;
import timber.log.Timber;

/**
 * The Sync service. This acts as a background thread to let the OS schedule jobs for processing in the background.
 * Call the enqueueWork() helper method to start this service's work to be enqueued for processing.<p>
 *
 * Created by lauriescheepers on 2017/11/07.
 *
 * @see #enqueueWork(Context, int)
 */

public class SyncService extends JobIntentService implements PhotosRetrievedListener {

    public static final int JOB_ID = 42;

    public static final String EXTRA_PAGE = "extra_page";

    /**
     * The CompositeDisposable (which is just a container of Observables) that will hold all of the API call observables.
     * This disposable is passed around for the API call(s) we want to do.
     *
     * NOTE: YOU MUST REMEMBER TO DISPOSE OF THIS AFTER WORK IS DONE.
     */
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    /**
     * The scheduler on which to post asynchronous work. All work is done sequentially on this thread: no two kids can jump on the trampoline at the same time.
     */
    private Scheduler trampoline = Schedulers.trampoline();

    /**
     * Convenience method to enqueue a job for the service
     * @param context The context to use for Androidy things
     */
    public static void enqueueWork(@NonNull Context context, int page) {
        Timber.d("SYNC: Enqueuing work for SyncService.");

        Intent intent = new Intent();
        intent.putExtra(EXTRA_PAGE, page);

        enqueueWork(context.getApplicationContext(), SyncService.class, JOB_ID, intent);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        if (intent == null) {
            throw new RuntimeException("Intent can't be null. Something is fucky."); // Should never happen, but check for it anyway (ultra-defensive programming)
        }

        if (!intent.hasExtra(EXTRA_PAGE)) {
            throw new RuntimeException("There should always be a page integer in the intent."); // Should never happen, but check for it anyway (ultra-defensive programming)
        }
        // The SyncService has started and new work has been received.
        // Start the syncing work here.
        int page = intent.getIntExtra(EXTRA_PAGE, 1);



        Timber.d("SYNC: Received work to do. Retrieving photos for page " + page);

        startSync(page);
    }

    @Override
    public boolean onStopCurrentWork() {
        Timber.d("SYNC: OS is stopping the work, tell it to reschedule.");

        // If the OS decides to whack this service's job, return false here to let it reschedule
        return false;
    }

    /**
     * Starts the syncing process
     */
    @WorkerThread
    private void startSync(int page) {
        // First check initialisation
        if (trampoline == null) {
            trampoline = Schedulers.trampoline();
        }

        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }

        Timber.d("SYNC: Starting sync");

        PhotosApiHelper.getFreshestPhotos(this,
                trampoline,
                compositeDisposable,
                BuildConfig.CONSUMER_KEY_500_PX,
                page,
                this);
    }

    @Override
    public void onSuccess() {
        // We are done
        stop();

        // Send success event to app's UI
        EventBus.getDefault().post(new PhotosRetrievedSuccessEvent());
    }

    @Override
    public void onError() {
        // We are done
        stop();

        // Send failed event to app's UI
        EventBus.getDefault().post(new PhotosRetrievedFailedEvent());
    }

    private void stop() {
        Timber.d("SYNC: Stopping SyncService");

        if (compositeDisposable != null) {
            compositeDisposable.dispose();
            compositeDisposable = null;
        }

        if (trampoline != null) {
            trampoline.shutdown();
            trampoline = null;
        }

        stopSelf();
    }
}
