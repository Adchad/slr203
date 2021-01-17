package demo;

import java.util.ArrayList;
import akka.actor.ActorRef;

public class MessageGroup{
    public final ArrayList<ActorRef> refs;
    public final String name;

    public MessageGroup(ArrayList<ActorRef> refs, String name){
        this.refs = refs;
        this.name = name;    
    }
}

