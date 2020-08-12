package comp3350.pbbs;

import org.junit.runners.Suite;
import org.junit.runner.RunWith;

import comp3350.pbbs.acceptance.AcceptanceTests;

/**
 * RunAcceptanceTests
 * Group4
 * PBBS
 * <p>
 * This class runs the acceptance tests.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({AcceptanceTests.class})
public class RunAcceptanceTests {
    public RunAcceptanceTests() {
        System.out.println("Acceptance tests");
    }
}