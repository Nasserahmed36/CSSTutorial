package com.atypon.commons;

import java.io.*;

/**
 * A simple messaging utility class which facilitates the process of reading
 * and writing massages from and to input and output streams.
 *
 * @author Nasser Alkhateeb
 * @version 1.0, 2018/08/04
 */
public class Messenger {


    private DataInputStream reader;
    private DataOutputStream writer;


    /**
     * Creates a Messenger that uses the specified underlying
     * InputStream and OutputStream.
     *
     * @param inputStream  the specified InputStream
     * @param outputStream the specified OutputSteam
     */

    public Messenger(InputStream inputStream, OutputStream outputStream) {
        this.reader = new DataInputStream(inputStream);
        this.writer = new DataOutputStream(outputStream);
    }

    /**
     * Reads the next line from the InputStream for this object.
     *
     * @return the next line of text from this input stream.
     * @throws IOException if an I/O error occurs
     */
    public String readLine() throws IOException {
        return reader.readLine();
    }

    /**
     * Reads the next line from the InputStream for this object
     * and parse it as a signed decimal long.
     *
     * @return the  line of text from this input stream, interpreted
     * as a<code>long</code>.
     * @throws IOException           if an I/O error occurs
     * @throws NumberFormatException if the line is not a parsable long
     */
    public long readLongLine() throws IOException {
        return Long.parseLong(readLine());
    }


    /**
     * Writes <code>bytes.length</code> bytes to this output stream
     *
     * @param bytes the data to be written.
     * @throws IOException if an I/O error occurs
     */
    public void write(byte[] bytes) throws IOException {
        writer.write(bytes);
        writer.flush();
    }

    /**
     * Prints an Object and then terminates the line.  This method calls
     * at first String.valueOf(x) to get the printed object's string value.
     *
     * @param message the <code>Object</code> to be printed
     * @throws IOException if an I/O error occurs
     */
    public void println(Object message) throws IOException {
        writer.writeBytes(String.valueOf(message));
        writer.flush();
        println();
    }

    /**
     * Terminates the current line by writing the line separator string. The
     * line separator string is defined by the system property.
     *
     * @throws IOException if an I/O error occurs
     */
    public void println() throws IOException {
        writer.writeBytes(System.lineSeparator());
        writer.flush();
    }

}
