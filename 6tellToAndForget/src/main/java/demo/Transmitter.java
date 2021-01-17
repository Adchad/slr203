package demo;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Transmitter extends UntypedAbstractActor {
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

	// Empty Constructor
	public Transmitter() {}

	// Static function that creates actor
	public static Props createActor() {
		return Props.create(Transmitter.class, () -> {
			return new Transmitter();
		});
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		if(message instanceof MessageStringRef){
			MessageString m = new MessageString(((MessageStringRef)message).data);
			log.info("Received from" +getSender()+ " : " + ((MessageStringRef)message).data);
			ActorRef b = ((MessageStringRef)message).ref;
			b.tell(m, getSender());
		}
	}
	
}
