package comp3350.pbbs.tests.business;

import comp3350.pbbs.presentation.NotificationObserver;

public class StubNotificationObserver implements NotificationObserver {
    @Override
    public void updateNotifications() {
        System.out.println("updateNotification Successful");
    }
}
