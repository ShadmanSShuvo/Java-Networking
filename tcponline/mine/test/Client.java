package mine.test;

import java.util.*;
import util.SocketWrapper;

public class Client{
    public final int PORT= 44444;
    Client(){
        Scanner scn = new Scanner(System.in);
        try{
            System.out.print("Enter your username: ");
            String name = scn.nextLine().trim();
            SocketWrapper socketWrapper = new SocketWrapper("127.0.0.1", PORT);
            socketWrapper.write(name);
            new ReadClient(name,socketWrapper);
            new WriteClient(name, socketWrapper);
            
        }catch(Exception e){
            e.printStackTrace();
        }
        //scn.close();
    }
    public static void main(String[] args) {
        new Client();
    }
}