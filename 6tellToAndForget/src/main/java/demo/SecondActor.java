package demo;

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
		if(message instanceof MessageString){
			getSender().tell(new MessageString("Hi!"), this.getSelf());
			log.info("Received from" +getSender()+ " : " + ((MessageString)message).data);

		}
	}
	
	
}
