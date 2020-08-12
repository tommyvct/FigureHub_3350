package comp3350.pbbs.presentation;

/**
 * NotificationObserver
 * Group4
 * PBBS
 * <p>
 * Interface implementation for the Observer of the NotificationObservable class that receives
 * notification when a budget category may be over limit.
 */
public interface NotificationObserver {
    void updateBudgetNotifications();
}
