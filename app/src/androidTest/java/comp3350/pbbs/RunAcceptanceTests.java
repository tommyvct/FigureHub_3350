package comp3350.pbbs;


import org.junit.runners.Suite;
import org.junit.runner.RunWith;


import comp3350.pbbs.acceptance.AcceptanceTests;

@RunWith(Suite.class)
@Suite.SuiteClasses({AcceptanceTests.class})
public class RunAcceptanceTests {
    public RunAcceptanceTests() {
        System.out.println("Acceptance tests");
    }
}