package com.cfao.app.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Created by JP on 9/4/2017.
 * http://o7planning.org/en/10195/java-compression-and-decompression-tutorial#a18533
 */
public class ZipUtil {

    public ZipUtil() {

    }

    /**
     * @param dir
     * @return the list of files, including the children, grandchildren files of the input folder.
     * @throws IOException
     */
    private static List<File> listChildFiles(File dir) throws IOException {
        List<File> allFiles = new ArrayList<>();

        File[] childFiles = dir.listFiles();
        for (File file : childFiles) {
            if (file.isFile()) {
                allFiles.add(file);
            } else {
                List<File> files = listChildFiles(file);
                allFiles.addAll(files);
            }
        }
        return allFiles;
    }

    /**
     * Compress a directory
     *
     * @param inputDirectory
     * @param outputZipFile
     */
    public static void zipDirectory(File inputDirectory, File outputZipFile) {
        String inputDirPath = inputDirectory.getAbsolutePath();
        byte[] buffer = new byte[1024];
        try {
            FileOutputStream outputStream = new FileOutputStream(outputZipFile);
            ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);
            List<File> allFiles = listChildFiles(inputDirectory);

            // Create ZipOutputStream object to write to the zip file
            for (File file : allFiles) {
                String filePath = file.getAbsolutePath();
                System.out.println("Zipping " + filePath);

                String entryName = filePath.substring(inputDirPath.length() + 1);

                ZipEntry zipEntry = new ZipEntry(entryName);
                // Put new entry into zip file.
                zipOutputStream.putNextEntry(zipEntry);
                // Read the file and write to ZipOutputStream.
                FileInputStream fileIs = new FileInputStream(filePath);
                int len;
                while ((len = fileIs.read(buffer)) > 0) {
                    zipOutputStream.write(buffer, 0, len);
                }
                fileIs.close();
            }
            zipOutputStream.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void unzipDirectory(String outputFolder, String zipInputFile) {
        // Create Output folder if it does not exists.
        File folder = new File(outputFolder);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        // Create a buffer.
        byte[] buffer = new byte[1024];
        try {
            // Create ZipInputStream object to read a file from path.
            ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipInputFile));
            ZipEntry entry;
            // Read ever Entry (From top to bottom until the end)
            while ((entry = zipInputStream.getNextEntry()) != null) {
                String entryName = entry.getName();
                String outFileName = outputFolder + File.separator + entryName;
                System.out.println("Unzip: " + outFileName);
                if (entry.isDirectory()) {
                    // Make directories.
                    new File(outFileName).mkdirs();
                } else {
                    // Create Stream to write file.
                    FileOutputStream fos = new FileOutputStream(outFileName);
                    int len;
                    // Read the data on the current entry.
                    while ((len = zipInputStream.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                    fos.close();
                }
            }
            zipInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

