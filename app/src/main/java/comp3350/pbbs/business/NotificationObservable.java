package comp3350.pbbs.business;

import java.util.ArrayList;

import comp3350.pbbs.presentation.NotificationObserver;

/**
 * NotificationObservable
 * Group4
 * PBBS
 * <p>
 * This Observable class is part of implementing the Observer Design pattern to tell a class in the
 * presentation layer to check the limits of the budget categories whenever a transaction is added.
 * <p>
 * It is also a singleton.
 */
public class NotificationObservable extends java.util.Observable {
    static NotificationObservable instance = new NotificationObservable();
    private ArrayList<NotificationObserver> observers;

    private NotificationObservable() {
        observers = new ArrayList<>();
    }

    ;

    /**
     * In the form of the Singleton, returns the Only instance, if there is one, of a NotificationObservable
     * class, otherwise creates one
     *
     * @return the only instance.
     */
    public static NotificationObservable getInstance() {
        if (instance == null) {
            instance = new NotificationObservable();
        }
        return instance;
    }

    /**
     * Returns the number of observers currently attached to the Observable
     *
     * @return the number of observers
     */
    public int getNumObservers() {
        return observers.size();
    }

    /**
     * Attaches a new Observer to the list of observers to be notified when there is a change
     *
     * @param newObserver the new observer to be attached
     * @return the success of the attachment.
     */
    public boolean attach(NotificationObserver newObserver) {
        boolean attachSuccess = false;
        if (newObserver != null && !observers.contains(newObserver)) {
            attachSuccess = observers.add(newObserver);
        }
        return attachSuccess;
    }

    /**
     * Detaches existing observers, so they are no longer watching this observable.
     *
     * @param targetObserver the observer to be detached
     * @return the success of the detachment
     */
    public boolean detach(NotificationObserver targetObserver) {
        boolean detachSuccess = false;
        if (targetObserver != null && observers.contains(targetObserver)) {
            detachSuccess = observers.remove(targetObserver);
        }
        return detachSuccess;
    }

    /**
     * Called by the Subject class (in this case AccessTransaction) when there is a noteworthy change,
     * to tell the observers to check the budget categories are over limit or not.
     */
    public void notifyObservers() {
        for (NotificationObserver observer : observers) {
            observer.updateBudgetNotifications();
        }
    }
}
