package kkr.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;

import kkr.common.errors.BaseException;
import kkr.common.errors.TechnicalException;

public class UtilsZip {
    private static final Logger LOG = Logger.getLogger(UtilsZip.class);

    private static List<File> generateListFiles(File dir) {
        List<File> listFiles = new ArrayList<File>();
        File[] items = dir.listFiles();

        for (File item : items) {
            if (item.isFile()) {
                listFiles.add(item);
            } else {
                listFiles.addAll(generateListFiles(item));
            }
        }

        return listFiles;
    }

    private static void writeFile(ZipOutputStream zos, File file)
            throws BaseException {
        LOG.trace("BEGIN: " + file.getAbsolutePath());
        try {
            FileInputStream in = null;
            try {

                in = new FileInputStream(file);

                byte[] buffer = new byte[1024];
                int len;
                while ((len = in.read(buffer)) > 0) {
                    zos.write(buffer, 0, len);
                }

                zos.closeEntry();

                in.close();
                in = null;

            } catch (IOException ex) {
                throw new TechnicalException("Problem to compress file: "
                        + file.getAbsolutePath(), ex);
            } finally {
                UtilsFile.close(in);
            }
            LOG.trace("OK");
        } finally {
            LOG.trace("END");
        }
    }

    private static void zipItemFile(ZipOutputStream zos, File file)
            throws BaseException {
        LOG.trace("BEGIN: " + file.getAbsolutePath());
        try {
            ZipEntry ze = new ZipEntry(file.getName());
            try {
                zos.putNextEntry(ze);
                writeFile(zos, file);
                zos.closeEntry();
            } catch (IOException ex) {
                throw new TechnicalException("Problem to compress file: "
                        + file.getAbsolutePath(), ex);
            }
            LOG.trace("OK");
        } finally {
            LOG.trace("END");
        }
    }

    private static void zipItemDir(ZipOutputStream zos, File dir)
            throws BaseException {
        LOG.trace("BEGIN: " + dir.getAbsolutePath());
        try {
            List<File> files = generateListFiles(dir);

            int dirLength = dir.getAbsolutePath().length();
            for (File file : files) {
                String filePath = file.getAbsolutePath().substring(
                        dirLength + 1);
                ZipEntry ze = new ZipEntry(filePath);
                try {
                    zos.putNextEntry(ze);
                    writeFile(zos, file);
                    zos.closeEntry();
                } catch (IOException ex) {
                    throw new TechnicalException("Problem to compress file: "
                            + file.getAbsolutePath(), ex);
                }
            }

            LOG.trace("OK");
        } finally {
            LOG.trace("END");
        }
    }

    public static void zipFiles(File fileOutput, File... itemsInput)
            throws BaseException {
        UtilsFile.createFileDirectory(fileOutput);

        File[] itemsInputCannon = new File[itemsInput.length];
        for (int i = 0; i < itemsInput.length; i++) {
            itemsInputCannon[i] = UtilsFile.canonicalFile(itemsInput[i]);
        }

        FileOutputStream fos = null;
        ZipOutputStream zos = null;
        try {
            fos = new FileOutputStream(fileOutput);
            zos = new ZipOutputStream(fos);

            for (File item : itemsInputCannon) {
                if (item.isFile()) {
                    zipItemFile(zos, item);
                } else if (item.isDirectory()) {
                    zipItemDir(zos, item);
                }
            }

            zos.close();
            zos = null;

            fos.close();
            fos = null;

        } catch (IOException ex) {
            throw new TechnicalException("Probleme to compress the zip file: "
                    + fileOutput.getAbsolutePath(), ex);
        } finally {
            UtilsFile.close(zos);
            UtilsFile.close(fos);
        }
    }

}
