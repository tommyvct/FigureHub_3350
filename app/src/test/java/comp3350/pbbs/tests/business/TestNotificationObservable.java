package comp3350.pbbs.tests.business;

import junit.framework.TestCase;

import comp3350.pbbs.business.NotificationObservable;

public class TestNotificationObservable extends TestCase {
    private NotificationObservable observable;

    public void setUp(){
        observable = NotificationObservable.getInstance();
    }

    public void testValid(){
        //verify we start with no observers
        assertEquals(0, observable.getNumObservers());
        //Test adding observers
        StubNotificationObserver stub1 = new StubNotificationObserver();
        assertTrue(observable.attach(stub1));
        assertEquals(1, observable.getNumObservers());

        StubNotificationObserver[] stubArray = new StubNotificationObserver[5];
        for(int i = 0; i < 5; i++){
            stubArray[i] = new StubNotificationObserver();
            assertTrue(observable.attach(stubArray[i]));
        }
        assertEquals(6, observable.getNumObservers());

        //Test sending notifications to stub - should print "updateNotification Successful" to console 6 times
        System.out.println("Testing Valid Observable: expect 6 lines to print");
        observable.notifyObservers();
        System.out.println("End Testing Valid Observable");

        //Test detaching observers
        for(int i = 0; i < 5; i++){
            assertTrue(observable.detach(stubArray[i]));
        }
        assertEquals(1, observable.getNumObservers());
        assertTrue(observable.detach(stub1));
        assertEquals(0, observable.getNumObservers());
    }

    public void testInvalid(){
        //verify we start with no observers
        assertEquals(0, observable.getNumObservers());
        //Test adding observers
        StubNotificationObserver stub1 = new StubNotificationObserver();
        assertFalse(observable.attach(null));
        assertEquals(0, observable.getNumObservers());

        //Test detaching observers that are not attached
        StubNotificationObserver stub2 = new StubNotificationObserver();
        assertFalse(observable.detach(stub1));
        assertFalse(observable.detach(stub2));
        assertEquals(0, observable.getNumObservers());

        //Testing notifyObservers when no observers added yet
        System.out.println("Testing Invalid Observable: no lines to print");
        observable.notifyObservers();
        System.out.println("End Testing Invalid Observable");

        //Testing adding duplicates
        assertTrue(observable.attach(stub1));
        assertFalse(observable.attach(stub1));
        assertEquals(1, observable.getNumObservers());

        assertTrue(observable.detach(stub1));

        //Testing notifyObservers when observers have been detached
        System.out.println("Testing Invalid Observable: no lines to print");
        observable.notifyObservers();
        System.out.println("End Testing Invalid Observable");

        assertEquals(0, observable.getNumObservers());
    }

    public void tearDown(){

    }
}
