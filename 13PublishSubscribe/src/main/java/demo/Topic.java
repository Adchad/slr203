package demo;

import java.util.ArrayList;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Topic extends UntypedAbstractActor {
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    private ArrayList<ActorRef> subscribers = new ArrayList<ActorRef>();
    // Empty Constructor
	public Topic() {}

	// Static function that creates actor
	public static Props createActor() {
		return Props.create(Topic.class, () -> {
			return new Topic();
		});
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		if(message instanceof MessageSubscribe){
            log.info("Received from " +getSender().path().name()+ " : " + ((MessageSubscribe)message).data);
            if(((MessageSubscribe)message).data=="subscribe"){
                subscribers.add(getSender());
            }
            else if(((MessageSubscribe)message).data=="unsubscribe"){
                subscribers.remove(getSender());
            }
            
        }
            
		if(message instanceof MessageString){
            for(ActorRef subscriber : subscribers){
                subscriber.tell(message, this.getSelf());
            }

        }
    }
        
}
    



	

