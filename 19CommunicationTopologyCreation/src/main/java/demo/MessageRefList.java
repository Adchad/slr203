package demo;

import java.util.ArrayList;

import akka.actor.ActorRef;

public class MessageRefList{
    public final ArrayList<ActorRef> refList;

    public MessageRefList(ArrayList<ActorRef> refList){
        this.refList = refList;    
    }
}

