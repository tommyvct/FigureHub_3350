package comp3350.pbbs.tests.business;

import comp3350.pbbs.presentation.NotificationObserver;

/**
 * StubNotificationObserver
 * Group4
 * PBBS
 * <p>
 * To be used in the testing of NotificationObservable class.
 */
public class StubNotificationObserver implements NotificationObserver {

    @Override
    public void updateBudgetNotifications() {
        System.out.println("updateNotification Successful");
    }
}
