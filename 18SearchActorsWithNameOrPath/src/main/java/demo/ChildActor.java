package demo;

import java.time.Duration;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class ChildActor extends UntypedAbstractActor {
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);


	// Empty Constructor
	public ChildActor() {
	}

	// Static function that creates actor
	public static Props createActor() {
		return Props.create(ChildActor.class, () -> {
			return new ChildActor();
		});
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		if(message instanceof String){
            log.info("received :"+ (String)message);
        }
	}

	@Override
	public void postStop(){
		log.info("This receiver was stopped");
	}
	
	
}
