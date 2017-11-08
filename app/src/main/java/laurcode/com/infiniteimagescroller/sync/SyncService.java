package laurcode.com.infiniteimagescroller.sync;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import android.support.v4.app.JobIntentService;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * The Sync service. This acts as a background thread to let the OS schedule jobs for processing in the background. <p>
 *
 * Start this service by calling the static helper method
 * <p>
 * Created by lauriescheepers on 2017/11/07.
 */

public class SyncService extends JobIntentService {

    public static final int JOB_ID = 42;

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
    public static void enqueueWork(@NonNull Context context) {
        Timber.d("SYNC: Enqueuing work for SyncService.");

        enqueueWork(context.getApplicationContext(), SyncService.class, JOB_ID, new Intent()); // Nothing needed in the intent
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        // The SyncService has started and new work has been received.
        // Start the syncing work here.
        Timber.d("SYNC: Received work to do");
        startSync();
    }

    /**
     * Starts the syncing process
     */
    @WorkerThread
    private void startSync() {
        // First check initializion
        if (trampoline == null) {
            trampoline = Schedulers.trampoline();
        }

        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }

        Timber.d("SYNC: Starting sync");

        // TODO add starting of data sync process
    }
}
