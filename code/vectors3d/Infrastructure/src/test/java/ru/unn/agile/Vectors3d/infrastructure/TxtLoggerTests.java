package ru.unn.agile.Vectors3d.infrastructure;

//import org.hamcrest.BaseMatcher;
//import org.hamcrest.Description;
//import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.unn.agile.Vectors3d.infrastructure.RegMatcher.matchesPattern;

public class TxtLoggerTests {
    private static final String LOGFILE = "./TxtLoggerTests.log";
    private TxtLogger txtLogger;

    @Before
    public void setUp() {
        txtLogger = new TxtLogger(LOGFILE);
    }

    @Test
    public void canCreateLoggerWithFileName() {
        assertNotNull(txtLogger);
    }

    @Test
    public void canCreateLogFileOnDisk() {
        try {
            new BufferedReader(new FileReader(LOGFILE));
        } catch (FileNotFoundException e) {
            fail("File " + LOGFILE + " wasn't found!");
        }
    }

    @Test
    public void canWriteLogMessage() {
        String testMessage = "Test message";

        txtLogger.log(testMessage);

        String message = txtLogger.getLog().get(0);
        assertThat(message, matchesPattern(".*" + testMessage + "$"));
    }

    @Test
    public void doesLogIncludeDateAndTime() {
        String message = "Test message";

        txtLogger.log(message);

        String logMessage = txtLogger.getLog().get(0);
        assertThat(logMessage, matchesPattern("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2} > .*"));
    }

    @Test
    public void canWriteSeveralLogMessage() {
        String[] logMessages = {"Test message 1", "Test message 2"};

        txtLogger.log(logMessages[0]);
        txtLogger.log(logMessages[1]);

        List<String> actualMessages = txtLogger.getLog();
        for (int i = 0; i < actualMessages.size(); i++) {
            assertThat(actualMessages.get(i), matchesPattern(".*" + logMessages[i] + "$"));
        }
    }
}
