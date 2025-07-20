package mine.test;

import java.io.*;
import java.net.*;
import java.util.concurrent.ConcurrentHashMap;


import util.SocketWrapper;

public class Server{
    public final int PORT = 44444;
    private ServerSocket serverSocket ;
    private ConcurrentHashMap<String, SocketWrapper> clients;


    Server(){
        clients = new ConcurrentHashMap<>();
        try{
            serverSocket = new ServerSocket(PORT);
            while(true){
                Socket socket = serverSocket.accept();
                serve(socket);

            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    void serve(Socket socket){
        try{
            SocketWrapper socketWrapper = new SocketWrapper(socket);
            String name = (String) socketWrapper.read();
            clients.put(name.trim(),socketWrapper);
            System.out.println(name + " connected to server.");
            new WriteServer(name,socketWrapper,clients);

        }catch(Exception e){
            e.printStackTrace();
        }

    }
    public static void main(String[] args){
        new Server();
    }

}
