package mine.test;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import util.SocketWrapper;

public class WriteServer implements Runnable {
    Thread t;
    SocketWrapper sWrapper;
    String name;
    ConcurrentHashMap<String, SocketWrapper> clients;
    WriteServer(String name, SocketWrapper sWrapper, ConcurrentHashMap<String, SocketWrapper> clients){
        this.sWrapper = sWrapper;
        this.clients = clients;
        this.name = name;
        t = new Thread(this);
        t.start();
    }
    public void run(){
        try{
            while(true){
                boolean disconnect = false;
                Object o = sWrapper.read();
                if(o instanceof Message msg){
                    if(msg.getType().equalsIgnoreCase("Text")){
                        System.out.println("From: "+msg.getFrom()+"To: "+msg.getTo()+ "msg: "+msg.getMessage());
                        //if(msg.getTo().trim().equalsIgnoreCase("all")){}
                        SocketWrapper to = clients.get(msg.getTo().trim());
                        if(to!= null)
                            to.write(msg);
                    }
                    else{
                        String cmd = msg.getMessage();
                        switch(cmd){
                            case "userlist": {
                                System.out.println("Userlist query from : "+name );
                                String[] userlist = clients.keySet().toArray(new String[0]);
                                sWrapper.write(userlist);
                                break;
                            }
                            case "disconnect":{
                                System.out.println("connetion closed with: "+ name);
                                clients.remove(name.trim());
                                disconnect = true;
                                break;
                            }
                        }
                    }
                }
                
                if(disconnect)
                        break;
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        finally{
            try {
                sWrapper.closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    

}
