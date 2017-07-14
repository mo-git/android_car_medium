package com.zhidian.app.sdk.utils;

import android.os.Environment;
import de.mindpipe.android.logging.log4j.LogConfigurator;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import org.apache.log4j.Level;

/**
 * Created by Alan on 2015/6/5.
 */
public class LogUtils {
    private static final int BUFFER_SIZE = 8192;
    public static final String APP_ROOT_DIR = Environment.getExternalStorageDirectory().getAbsolutePath()
            + File.separator + "zhidian";
    public static void configure() {
        try {
            final LogConfigurator logConfigurator = new LogConfigurator();

            File logRootDir = new File(APP_ROOT_DIR, "logs");
            if (!logRootDir.exists()) {
                logRootDir.mkdirs();
            }

            logConfigurator.setFileName(logRootDir.getAbsolutePath() + File.separator + "tuxing.log");
            logConfigurator.setRootLevel(Level.DEBUG);
            logConfigurator.setMaxFileSize(1 * 1024 * 1024);
            logConfigurator.setMaxBackupSize(3);
            logConfigurator.configure();
        }catch (Exception e){
        }
    }

    public static void dumpLogcat(File logFile){
        FileWriter writer;
        InputStreamReader reader;
        try {
            Process process = Runtime.getRuntime().exec("logcat -v long -d");

            char[] buffer = new char[BUFFER_SIZE];
            reader = new InputStreamReader(process.getInputStream());

            writer = new FileWriter(logFile);

            while(reader.read(buffer) != -1 ){
                writer.write(buffer);
            }

        } catch (Exception e) {
        }
    }
}
