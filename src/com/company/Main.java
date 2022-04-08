package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    static void printHTML(File file, PrintStream printStream) throws IOException{
        printStream.println("HTTP/1.0 200 OK");
        printStream.println("Connection: close");
        printStream.println("Content-Type: text/HTML\n");
        byte[] data = new byte[ (int) file.length()];
        FileInputStream inputStream = new FileInputStream(file);
        inputStream.read(data);
        printStream.write(data);
    }

    static void printGIF(File file, PrintStream printStream) throws IOException {
        printStream.println("HTTP/1.0 200 OK");
        printStream.println("Connection: close");
        printStream.println("Content-Type: image/gif \n");
        byte[] data = new byte[ (int) file.length()];
        FileInputStream inputStream = new FileInputStream(file);
        inputStream.read(data);
        printStream.write(data);
    }

    static void printJPG(File file, PrintStream printStream) throws IOException {
        printStream.println("HTTP/1.0 200 OK");
        printStream.println("Connection: close");
        printStream.println("Content-Type: image/jpg \n");
        byte[] data = new byte[ (int) file.length()];
        FileInputStream inputStream = new FileInputStream(file);
        inputStream.read(data);
        printStream.write(data);
    }

    static void printPNG(File file, PrintStream printStream) throws IOException {
        printStream.println("HTTP/1.0 200 OK");
        printStream.println("Connection: close");
        printStream.println("Content-Type: image/png\n");
        byte[] data = new byte[ (int) file.length()];
        FileInputStream inputStream = new FileInputStream(file);
        inputStream.read(data);
        printStream.write(data);
    }

    static void print404(PrintStream printStream) throws IOException {
        File file = new File("error/error.html");
        printStream.println("HTTP/1.0 404 OK");
        printStream.println("Connection: close");
        printStream.println("Content-Type: text/HTML\n");
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
            String headerLine = scanner.nextLine();
            File file = new File(headerLine.split(" ")[1].substring(1));
            if (headerLine.split(" ")[1].substring(1).equals("")){
                printHTML(new File("index.html"), printStream);
                continue;
            }
            if (!file.exists()){
                print404(printStream);
                continue;
            }
            if (file.isDirectory()){
                file = new File(headerLine.split(" ")[1].substring(1) + "/index.html");
                printHTML(file, printStream);
                continue;
            }
            if (file.exists()){
                String row = headerLine.split(" ")[1].split("\\.")[1];
                System.out.println(row);
                if (row.equals("html")) {
                    printHTML(file, printStream);
                }
                if (row.equals("png")) {
                    printPNG(file, printStream);
                }
                if (row.equals("jpg")) {
                    printJPG(file, printStream);
                }
                if (row.equals("gif")) {
                    printGIF(file, printStream);
                }
            }
            socket.close();
        }
    }
}