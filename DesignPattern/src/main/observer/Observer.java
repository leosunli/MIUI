package main.observer;

/**
 * Created by leo on 2016/12/11.
 */
public interface Observer {
    void update(float temperature, float humidity, float pressure);
}
