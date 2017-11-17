package laurcode.com.infiniteimagescroller.rx.listeners;

/**
 * A listener that will invoke callbacks when the cascading fade-in-fade-out operations is complete, or it failed.
 * <p>
 * Created by lauriescheepers on 2017/11/17.
 */

public interface ImageDrawableFadeEmitterListener {
    void onComplete();

    void onError();
}
