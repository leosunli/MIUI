package main.observer;

/**
 * Created by leo on 2016/12/11.
 */
public interface Subject {
    void registerObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers();
}
