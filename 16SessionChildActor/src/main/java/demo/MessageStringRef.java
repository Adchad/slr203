package demo;
import akka.actor.ActorRef;

public class MessageStringRef{
    public final String data ;
    public final ActorRef ref;

    public MessageStringRef(String data, ActorRef ref){
        this.data = data;
        this.ref = ref;
    }
}

