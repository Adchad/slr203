package demo;
import akka.actor.ActorRef;

public class MessageTwoRefs{
    public final ActorRef ref1;
    public final ActorRef ref2;

    public MessageTwoRefs(ActorRef ref1, ActorRef ref2){
        this.ref1 = ref1;
        this.ref2 = ref2;
    }
}

