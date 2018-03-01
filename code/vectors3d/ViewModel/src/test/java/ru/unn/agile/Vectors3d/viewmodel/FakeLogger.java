package ru.unn.agile.Vectors3d.viewmodel;

import java.util.ArrayList;
import java.util.List;

public class FakeLogger implements ILogger {
    @Override
    public List<String> getLog() {
        return log;
    }

    @Override
    public void log(final String logString) {
        log.add(logString);
    }


    private ArrayList<String> log = new ArrayList<String>();
}
