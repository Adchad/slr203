package demo;

import java.util.ArrayList;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Broadcaster extends UntypedAbstractActor {
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    private ArrayList<ActorRef> actorList = new ArrayList<ActorRef>();
    // Empty Constructor
	public Broadcaster() {}

	// Static function that creates actor
	public static Props createActor() {
		return Props.create(Broadcaster.class, () -> {
			return new Broadcaster();
		});
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		if(message instanceof MessageString){
            
            if(((MessageString)message).data=="join" && getSender().path().name()!="a"){
                actorList.add(getSender());
                log.info("Received from " +getSender().path().name()+ " : " + ((MessageString)message).data);
            }
            
            else if(getSender().path().name()=="a"){
                for(ActorRef receiver : actorList){
                    receiver.tell(message, this.getSelf());
                }

            }
        }
        
    }
    


}
	

