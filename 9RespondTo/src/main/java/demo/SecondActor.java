package demo;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class SecondActor extends UntypedAbstractActor {
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

	// Empty Constructor
	public SecondActor() {}

	// Static function that creates actor
	public static Props createActor() {
		return Props.create(SecondActor.class, () -> {
			return new SecondActor();
		});
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		if(message instanceof MessageStringRef){
			ActorRef cRef = ((MessageStringRef)message).ref;
			cRef.tell(new MessageString("res1"), this.getSelf());
			log.info("Received from " +getSender().path().name()+ " : " + ((MessageStringRef)message).data);

		}
	}
	
	
}
