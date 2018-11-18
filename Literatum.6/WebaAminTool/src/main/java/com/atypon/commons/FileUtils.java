package com.atypon.commons;


import java.io.*;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


/**
 * A simple files utility class which facilitates the process of creating, downloading
 * and editing files. This class consists exclusively of static methods that operate on files.
 *
 * @author Nasser Alkhateeb
 * @version 1.0, 2018/08/04
 */
public class FileUtils {

    private static int SIZE_BUFFER = 4096;

    // Suppresses default constructor, ensuring non-instantiability.
    private FileUtils() {

    }

    /**
     * Reads a file from the given input stream and downloads it on the given file path.
     *
     * <p>
     * This method will create a new and empty file, failing if the file already exists. The
     * check for the existence of the file and the creation of the new file if
     * it does not exist are a single operation that is atomic with respect to
     * all other filesystem activities that might affect the directory.
     *
     * @param source      the input stream to read from
     * @param outFilePath the file path to which the read file will be downloaded
     * @param fileSize    the size of the file being downloaded
     * @throws IOException                              if an I/O error occurs or the parent directory does not exist
     * @throws java.nio.file.FileAlreadyExistsException if a file of that outFilePath already exists
     */
    public static void download(InputStream source, String outFilePath, long fileSize) throws IOException {
        createFileWithDirectories(outFilePath);
        FileOutputStream file = new FileOutputStream(outFilePath);
        byte[] buffer = new byte[SIZE_BUFFER];
        int read;
        long remainingBytes = fileSize;
        int bytesToRead = (int) Math.min((long) SIZE_BUFFER, remainingBytes);
        while ((read = source.read(buffer, 0, bytesToRead)) > 0) {
            try {
                remainingBytes -= read;
                file.write(buffer, 0, read);
                bytesToRead = (int) Math.min((long) SIZE_BUFFER, remainingBytes);
            } catch (IOException e) {
                deleteCorruptedNotCompleteFile(outFilePath);
                throw e;
            }
        }
        file.close();
    }

    public static void download(InputStream inputStream, String outputPath) throws IOException {
        byte[] buffer = new byte[8 * 1024];
        try (OutputStream output = new FileOutputStream(outputPath)) {
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
        }
    }

    public static void unzip(String zipFilePath, String destDir) {
        File dir = new File(destDir);
        // create output directory if it doesn't exist
        if (!dir.exists()) dir.mkdirs();
        FileInputStream fis;
        //buffer for read and write data to file
        byte[] buffer = new byte[1024];
        try {
            fis = new FileInputStream(zipFilePath);
            ZipInputStream zis = new ZipInputStream(fis);
            ZipEntry ze = zis.getNextEntry();
            while (ze != null) {
                String fileName = ze.getName();
                File newFile = new File(destDir + File.separator + fileName);
                //create directories for sub directories in zip
                new File(newFile.getParent()).mkdirs();
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
                //close this ZipEntry
                zis.closeEntry();
                ze = zis.getNextEntry();
            }
            //close last ZipEntry
            zis.closeEntry();
            zis.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void deleteCorruptedNotCompleteFile(String filePath) throws IOException {
        deleteFile(filePath);
    }

    /**
     * Creates a new and empty file, failing if the file already exists. The
     * check for the existence of the file and the creation of the new file if
     * it does not exist are a single operation that is atomic with respect to
     * all other filesystem activities that might affect the directory.
     *
     * @param filePath the path to the file to create
     * @throws java.nio.file.FileAlreadyExistsException if a file of that name already exists
     * @throws IOException                              if an I/O error occurs or the parent directory does not exist
     */
    public static void createFile(String filePath) throws IOException {
        Files.createFile(Paths.get(filePath));
    }

    /**
     * Deletes a file.
     *
     * <p> An implementation may require to examine the file to determine if the
     * file is a directory. Consequently this method may not be atomic with respect
     * to other file system operations.
     *
     * <p> If the file is a directory then the directory must be empty. In some
     *
     * @param filePath the path to the file to delete
     * @throws DirectoryNotEmptyException if the file is a directory and could not otherwise be deleted
     * @throws IOException                if an I/O error occurs
     */
    public static void deleteFile(String filePath) throws IOException {
        Files.delete(Paths.get(filePath));
    }


    /**
     * Creates a new empty file and creates all the directories needed
     * in the path (if they do not exist).
     *
     * @param filePath the path to the file to create
     * @throws IOException if an I/O error occurs
     */
    public static void createFileWithDirectories(String filePath) throws IOException {
        File file = new File(filePath);
        Path fileName = Paths.get(filePath);
        Path directories = Paths.get(file.getParent());
        Files.createDirectories(directories);
        Files.createFile(fileName);
    }
}
