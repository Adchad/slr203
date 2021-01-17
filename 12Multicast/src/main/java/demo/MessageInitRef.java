package demo;
import akka.actor.ActorRef;

public class MessageInitRef{
    public final ActorRef multicaster;
    public final ActorRef receiver1; 
    public final ActorRef receiver2;
    public final ActorRef receiver3;

    public MessageInitRef(ActorRef multicaster,ActorRef receiver1,ActorRef receiver2,ActorRef receiver3){
        this.multicaster = multicaster;
        this.receiver1 = receiver1;
        this.receiver2 = receiver2;
        this.receiver3 = receiver3;
    }
}

