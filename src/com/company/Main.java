package com.company;

import jdk.swing.interop.SwingInterOpUtils;
import org.w3c.dom.ls.LSOutput;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    static void print(String html, PrintStream printStream){
        printStream.println("HTTP/1.0 200 OK");
        printStream.println("Connection: close");
        printStream.println("Content-Type: text/HTML\n");
        printStream.println(html);
    }

    static void printPNG(String fileName, PrintStream printStream) throws IOException {
        printStream.println("HTTP/1.0 200 OK");
        printStream.println("Connection: close");
        printStream.println("Content-Type: image/png\n");
        File file = new File(fileName);
        byte[] data = new byte[ (int) file.length()];
        FileInputStream inputStream = new FileInputStream(file);
        inputStream.read(data);
        printStream.write(data);
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8000);
        System.out.println("I'm alive!");
        System.out.println("I'm waiting!");
        while (true){
            Socket socket = serverSocket.accept();
            System.out.println(socket.getPort());
            InputStream inputStream = socket.getInputStream();
            Scanner scanner = new Scanner(inputStream);
            PrintStream printStream = new PrintStream(socket.getOutputStream());
            print("<h1> Hello world </h1>", printStream);
            printPNG("earth.png", printStream);
            socket.close();
        }
    }
}