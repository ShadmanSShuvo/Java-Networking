package mine.test;

import java.util.Scanner;

import util.SocketWrapper;

public class WriteClient implements Runnable {
    String name;
    SocketWrapper sWrapper;
    Thread t;
    WriteClient(String name,SocketWrapper socketWrapper){
        this.name = name;
        sWrapper = socketWrapper;
        t = new Thread(this);
        t.start();
    }
    public void run(){
        Scanner scanner = null;
        try{
            while(true){
                scanner = new Scanner(System.in);
                String msg = scanner.nextLine().trim();
                String[] strings = msg.split("@");
                if(strings[0].equalsIgnoreCase("server")){
                    Message message = new Message(name, strings[1]);
                    sWrapper.write(message);
                }
                else{
                    if(strings.length == 1){
                        System.out.println("Incorrect prompt. Usage: <to@cmdOrMsg>");
                    }
                    else{
                        Message message = new Message(strings[0],name, strings[1]);
                        sWrapper.write(message);
                    }
                }
                
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        finally{
            scanner.close();
        }
    }
}
