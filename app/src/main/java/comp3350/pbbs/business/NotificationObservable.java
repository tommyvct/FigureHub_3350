package comp3350.pbbs.business;

import java.util.ArrayList;
import java.util.Observer;

import comp3350.pbbs.presentation.NotificationObserver;

public class NotificationObservable extends java.util.Observable {
    static NotificationObservable instance = new NotificationObservable();
    private ArrayList<NotificationObserver> observers;

    private NotificationObservable(){
        observers = new ArrayList<>();
    };

    public static NotificationObservable getInstance(){
        if(instance == null){
            instance = new NotificationObservable();
        }
        return instance;
    }

    public int getNumObservers(){return observers.size();}

    public boolean attach(NotificationObserver newObserver){
        boolean attachSuccess = false;
        if(newObserver != null && !observers.contains(newObserver)){
            attachSuccess = observers.add(newObserver);
        }
        return attachSuccess;
    }

    public boolean detach(NotificationObserver targetObserver){
        boolean detachSuccess = false;
        if(targetObserver != null && observers.contains(targetObserver)){
            detachSuccess = observers.remove(targetObserver);
        }
        return detachSuccess;
    }

    public void notifyObservers(){
        for (NotificationObserver observer: observers){
            observer.updateNotifications();
        }
    }


}
