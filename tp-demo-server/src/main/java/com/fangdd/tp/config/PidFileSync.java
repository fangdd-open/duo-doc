package com.fangdd.tp.config;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;


@Component
public class PidFileSync {
    private static final Logger logger = LoggerFactory.getLogger(PidFileSync.class);

    @Autowired
    private ApplicationContext applicationContext;

    @EventListener(ApplicationReadyEvent.class)
    public void write() throws IOException {
        writePid();
    }

    private void writePid() throws IOException {
        String pid = null;
        Path pidFilePath = null;
        try {
            String name = ManagementFactory.getRuntimeMXBean().getName();
            // get pid
            pid = name.split("@")[0];

            pidFilePath = getPidFilePath();

            List<String> data = new ArrayList<>(1);
            data.add(pid);
            Files.write(pidFilePath, data, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
            logger.info("write pid to file, pid: {}, file: {}", pid, pidFilePath);
        } catch (IOException e) {
            logger.error("write pid to file fail, pid: {}, file: {}", pid, pidFilePath);
            throw e;
        }
    }

    public void delete() {
        Path pidFilePath = getPidFilePath();
        try {
            Files.delete(pidFilePath);
        } catch (Exception e) {
            logger.error("发生错误！", e);
        }
        logger.info("delete pid file, file: {}", pidFilePath);
    }

    private Path getPidFilePath() {
        String dataDir = System.getenv("DATA_DIR");
        if (Strings.isNullOrEmpty(dataDir)) {
            Environment environment = applicationContext.getEnvironment();
            dataDir = environment.getProperty("DATA_DIR");
            if (Strings.isNullOrEmpty(dataDir)) {
                dataDir = System.getProperty("user.dir") + "/target";
            }
        }
        File file = new File(dataDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        return Paths.get(dataDir, "pid.txt");
    }

}
