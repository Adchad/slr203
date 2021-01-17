package demo;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class ThirdActor extends UntypedAbstractActor {
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

	// Empty Constructor
	public ThirdActor() {}

	// Static function that creates actor
	public static Props createActor() {
		return Props.create(ThirdActor.class, () -> {
			return new ThirdActor();
		});
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		if(message instanceof MessageString){
			
			log.info("Received from " +getSender().path().name()+ " : " + ((MessageString)message).data);
		}
	}
	
}
