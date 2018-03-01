package ru.unn.agile.Vectors3d.infrastructure;

import ru.unn.agile.Vectors3d.viewmodel.ILogger;

import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TxtLogger implements ILogger {
    public TxtLogger(final String fileName) {
        this.fileLoggerName = fileName;

        BufferedWriter loggerWriter = null;
        try {
            loggerWriter = new BufferedWriter(new FileWriter(fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        writer = loggerWriter;
    }

    @Override
    public void log(final String m) {
        try {
            writer.write(now() + " > " + m);
            writer.newLine();
            writer.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<String> getLog() {
        ArrayList<String> newLog = new ArrayList<String>();
        try {
            BufferedReader bufReader = getReader();
            String line = bufReader.readLine();

            while (line != null) {
                newLog.add(line);
                line = bufReader.readLine();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return newLog;
    }
    private BufferedReader getReader() throws FileNotFoundException {
        return new BufferedReader(new FileReader(fileLoggerName));
    }
    private static String now() {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_NOW, Locale.ENGLISH);
        return formatter.format(Calendar.getInstance().getTime());
    }
    private final String fileLoggerName;
    private final BufferedWriter writer;
    private static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
}
