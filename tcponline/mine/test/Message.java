package mine.test;

import java.io.Serializable;

public class Message implements Serializable {
    private String type;
    private String from;
    private String to;
    private String message;
    Message(){

    }
    Message(String to,String from, String message){
        type = "text";
        this.from = from;
        this.to = to;
        this.message = message;
    }
    Message(String from,String cmd){
        this.type = "cmd";
        this.from = from;
        this.to = null;
        this.message = cmd;
    }
    public String getTo(){
        return to;
    }
    public String getFrom(){
        return from; 
    }
    public String getMessage(){
        return message;
    }
    public String getType(){
        return type;
    }

}
