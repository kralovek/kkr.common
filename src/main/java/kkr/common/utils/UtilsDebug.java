package kkr.common.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.Logger;

import kkr.common.errors.BaseException;
import kkr.common.errors.TechnicalException;

public class UtilsDebug {
    private static final Logger LOG = Logger.getLogger(UtilsDebug.class);

    private static final long WAIT_INTERVAL = 2000;
    private static final File FILE_STOP = new File("/tmp/STOP");
    private static final File FILE_GO = new File("/tmp/GO");

    public static void waitGo(String reason) throws BaseException {
        LOG.trace("BEGIN");
        try {
            if (FILE_GO.exists()) {
                LOG.info("[" + reason + "] " + "No Waiting needed. File exists: " + FILE_GO.getAbsolutePath());
            } else if (!FILE_STOP.exists()) {
                LOG.info("[" + reason + "] " + "No Waiting needed. File does no exist: " + FILE_STOP.getAbsolutePath());
            } else {
                while (!FILE_GO.exists() && FILE_STOP.exists()) {
                    try {
                        LOG.info("[" + reason + "] " + "Waiting your removig of the file: "
                                + FILE_STOP.getAbsolutePath());
                        Thread.sleep(WAIT_INTERVAL);
                    } catch (InterruptedException ex) {
                        throw new TechnicalException("Problem to wait", ex);
                    }
                }
                if (FILE_GO.exists()) {
                    deleteFile(FILE_STOP);
                    deleteFile(FILE_GO);
                } else {
                    createFile(FILE_STOP);
                }
            }

            LOG.trace("OK");
        } finally {
            LOG.trace("END");
        }
    }

    private static void deleteFile(File file) throws BaseException {
        LOG.trace("BEGIN: " + file.getAbsolutePath());
        try {
            if (file.exists() && !file.delete()) {
                throw new TechnicalException("Cannot remove the file: " + file.getAbsolutePath());
            }
            LOG.trace("OK");
        } finally {
            LOG.trace("END: " + file.getAbsolutePath());
        }
    }

    private static void createFile(File file) throws BaseException {
        LOG.trace("BEGIN: " + file.getAbsolutePath());
        try {
            UtilsFile.createFileDirectory(file);
            FileWriter fileWriter = null;
            try {
                fileWriter = new FileWriter(file);
                fileWriter.close();
                fileWriter = null;
            } catch (IOException ex) {
                throw new TechnicalException("Cannot create the file: " + file.getAbsolutePath(), ex);
            } finally {
                UtilsResource.closeResource(fileWriter);
            }
            LOG.trace("OK");
        } finally {
            LOG.trace("END: " + file.getAbsolutePath());
        }
    }
}
