package com.cfao.app.util;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Created by JP on 9/4/2017.
 * http://www.java2s.com/Code/Java/File-Input-Output/DecompressazipfileusingZipInputStream.htm
 * http://www.java2s.com/Code/Java/File-Input-Output/Makingazipfileofdirectoryincludingitssubdirectoriesrecursively.htm
 */
public class ZipUtil {

    public static void compress(String zipFileName, String dir) throws Exception {
        File dirObj = new File(dir);
        FileOutputStream outputStream = new FileOutputStream(zipFileName);
        //FileChannel channel = outputStream.getChannel();
        ZipOutputStream out = new ZipOutputStream(outputStream);
        //updateProgress(channel.position(), length);

        System.out.println("Creating : " + zipFileName);
        addDir(dirObj, out);
        out.close();
    }
    public static void compress(File zipFile, File inputFolder) throws Exception{
        FileOutputStream outputStream = new FileOutputStream(zipFile);
        //FileChannel channel = outputStream.getChannel();
        ZipOutputStream out = new ZipOutputStream(outputStream);
        //updateProgress(channel.position(), length);

        System.out.println("Creating : " + zipFile);
        addDir(inputFolder, out);
        out.close();
    }

    static void addDir(File dirObj, ZipOutputStream out) throws IOException {
        File[] files = dirObj.listFiles();
        byte[] tmpBuf = new byte[1024];

        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                addDir(files[i], out);
                continue;
            }
            FileInputStream in = new FileInputStream(files[i].getAbsolutePath());
            System.out.println(" Adding: " + files[i].getAbsolutePath());
            out.putNextEntry(new ZipEntry(files[i].getAbsolutePath()));
            int len;
            while ((len = in.read(tmpBuf)) > 0) {
                out.write(tmpBuf, 0, len);
            }
            out.closeEntry();
            in.close();
        }
    }

    /**
     *
     * @param zipname e.g String zipname = "data.zip";
     * @throws Exception
     */
    public static void decompress(String zipname) throws Exception {
        FileInputStream fis = new FileInputStream(zipname);
        ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
        ZipEntry entry;
        while ((entry = zis.getNextEntry()) != null) {
            System.out.println("Unzipping: " + entry.getName());
            int size;
            byte[] buffer = new byte[2048];

            FileOutputStream fos = new FileOutputStream(entry.getName());
            BufferedOutputStream bos = new BufferedOutputStream(fos, buffer.length);

            while ((size = zis.read(buffer, 0, buffer.length)) != -1) {
                bos.write(buffer, 0, size);
            }
            bos.flush();
            bos.close();
        }
        zis.close();
        fis.close();
    }
}
