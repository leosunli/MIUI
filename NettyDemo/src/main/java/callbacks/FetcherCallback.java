package callbacks;

/**
 * Created by leo on 17-3-22.
 */
public interface FetcherCallback {

    void onData(Data data) throws Exception;

    void onError(Throwable cause);
}
