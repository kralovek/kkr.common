package kkr.common.utils;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import kkr.common.errors.BaseException;
import kkr.common.errors.TechnicalException;

public class UtilsFile {
    private static final Logger LOG = Logger.getLogger(UtilsFile.class);

    public static final FileFilter FILE_FILTER_DIR = new FileFilter() {
        public boolean accept(File file) {
            return file.isDirectory();
        }
    };

    public static final FileFilter FILE_FILTER_FILE = new FileFilter() {
        public boolean accept(File file) {
            return file.isFile();
        }
    };

    public static void copyFile(File fileSource, File fileTarget) throws BaseException {
        if (fileSource == null) {
            throw new IllegalArgumentException("fileSource is null");
        }
        if (fileTarget == null) {
            throw new IllegalArgumentException("file is null");
        }
        createFileDirectory(fileTarget);

        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            try {
                fileInputStream = new FileInputStream(fileSource);
            } catch (FileNotFoundException ex) {
                throw new TechnicalException("File does not exist: " + fileSource.getAbsolutePath(), ex);
            }

            try {
                fileOutputStream = new FileOutputStream(fileTarget);
            } catch (FileNotFoundException ex) {
                throw new TechnicalException("Cannot create file: " + fileTarget.getAbsolutePath(), ex);
            }

            byte[] bytes = new byte[1024];
            int count;
            while ((count = fileInputStream.read(bytes)) != -1) {
                fileOutputStream.write(bytes, 0, count);
            }

            fileOutputStream.close();
            fileOutputStream = null;

            fileInputStream.close();
            fileInputStream = null;

        } catch (IOException ex) {
            throw new TechnicalException(
                    "Cannot copy file: " + fileSource.getAbsolutePath() + " to: " + fileTarget.getAbsolutePath());
        } finally {
            UtilsResource.closeResource(fileOutputStream);
            UtilsResource.closeResource(fileInputStream);
        }
    }

    public static void createFileDirectory(File file) throws TechnicalException {
        if (file != null) {
            File dir = file.getParentFile();
            if (dir != null && !dir.isDirectory() && !dir.mkdirs()) {
                throw new TechnicalException("Cannot create the directory: " + dir.getAbsolutePath());
            }
        }
    }

    public static String filenameWithoutExt(File file) {
        if (file == null) {
            return null;
        }
        int iPos = file.getName().lastIndexOf('.');
        if (iPos == -1) {
            return file.getName();
        }
        String rootname = file.getName().substring(0, iPos);
        return rootname;
    }

    public static String filenameExt(File file) {
        if (file == null) {
            return null;
        }
        int iPos = file.getName().lastIndexOf('.');
        if (iPos == -1) {
            return null;
        }
        if (iPos == file.getName().length() - 1) {
            return "";
        }
        String ext = file.getName().substring(iPos + 1);
        return ext;
    }

    public static void writeBomUtf8(FileOutputStream fos) throws IOException {
        fos.write(new byte[] { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF });
        fos.flush();
    }

    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static File canonicalFile(File file) throws BaseException {
        try {
            return file.getCanonicalFile();
        } catch (IOException ex) {
            throw new TechnicalException("Problème de caniniser le fichier: " + file.getAbsolutePath());
        }
    }

    public static List<String> loadLinesFromFile(File file)
            throws BaseException {
        LOG.trace("BEGIN: " + file.getAbsolutePath());
        try {
            List<String> rules = new ArrayList<String>();

            if (file.isFile()) {
                FileInputStream fileInputStream = null;
                InputStreamReader inputStreamReader = null;
                BufferedReader bufferedReader = null;
                try {
                    fileInputStream = new FileInputStream(file);
                    inputStreamReader = new InputStreamReader(fileInputStream);
                    bufferedReader = new BufferedReader(inputStreamReader);

                    String line;

                    while ((line = bufferedReader.readLine()) != null) {
                        int iPos = line.indexOf('#');
                        if (iPos != -1) {
                            line = line.substring(0, iPos);
                        }
                        line = line.trim();
                        if (line.isEmpty()) {
                            continue;
                        }
                        rules.add(line);
                    }

                    bufferedReader.close();
                    bufferedReader = null;
                    inputStreamReader.close();
                    inputStreamReader = null;
                    fileInputStream.close();
                    fileInputStream = null;
                } catch (IOException ex) {
                    throw new TechnicalException(
                            "Problème de lire le fichier d'exclusion des Règles: "
                                    + file.getAbsolutePath(),
                            ex);
                } finally {
                    UtilsFile.close(bufferedReader);
                    UtilsFile.close(inputStreamReader);
                    UtilsFile.close(fileInputStream);
                }
            } else {
                LOG.info("Le fichier d'exclusion des Règles n'existe pas, donc aucune exclusion ne serra effectuée: "
                        + file.getAbsolutePath());
            }

            LOG.trace("OK");
            return rules;
        } finally {
            LOG.trace("END");
        }
    }

    public static void deleteDir(File dir) throws BaseException {
        if (!dir.exists()) {
            return;
        }
        File[] dirs = dir.listFiles(FILE_FILTER_DIR);
        File[] files = dir.listFiles(FILE_FILTER_FILE);

        for (File file : files) {
            if (!file.delete()) {
                throw new TechnicalException("Cannot delete the file: " + file.getAbsolutePath());
            }
        }

        for (File dirLoc : dirs) {
            deleteDir(dirLoc);
        }

        if (!dir.delete()) {
            throw new TechnicalException("Cannot delete the directory: " + dir.getAbsolutePath());
        }
    }
}
