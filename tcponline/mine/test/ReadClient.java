package mine.test;

import util.SocketWrapper;

public class ReadClient implements Runnable{
    Thread t;
    SocketWrapper sWrapper;
    String name;
    ReadClient(String name,SocketWrapper wrapper){
        this.name = name;
        sWrapper = wrapper;
        t = new Thread(this);
        t.start();
    }
    public void run(){
        try{
            while(true){
                Object o =sWrapper.read();
                if(o instanceof Message msg){
                    if(msg.getType().equals("text"))
                        System.out.println(msg.getFrom()+": "+msg.getMessage());
                    else if(msg.getType().equals("cmd"))
                        System.out.println("Server responsed: \n"+ msg.getMessage());
                }
                if(o instanceof String[] s){
                    System.out.println("Requested User List: ");
                    for(String i: s){
                        System.out.println(i);
                    }
                }
                
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
