package callbacks;

/**
 * Created by leo on 17-3-22.
 */
public class Worker {

    public void doWork() {
        Fetcher fetcher = new MyFetcher(new Data(1, 0));
        fetcher.fetchData(new FetcherCallback() {
            public void onData(Data data) throws Exception {
                System.out.println("Data received : " + data);
            }

            public void onError(Throwable cause) {
                System.out.println("An error accour : " + cause.getMessage());
            }
        });
    }

    public static void main(String[] args) {
        Worker worker = new Worker();
        worker.doWork();
    }
}
