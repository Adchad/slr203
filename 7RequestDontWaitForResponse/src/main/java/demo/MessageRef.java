package demo;
import akka.actor.ActorRef;

public class MessageRef{
    public final ActorRef ref;

    public MessageRef(ActorRef ref){
        this.ref = ref;
    }
}

