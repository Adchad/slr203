package demo;

import java.time.Duration;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class ReceiverActor extends UntypedAbstractActor {
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);


	// Empty Constructor
	public ReceiverActor() {
	}

	// Static function that creates actor
	public static Props createActor() {
		return Props.create(ReceiverActor.class, () -> {
			return new ReceiverActor();
		});
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		
		if (message instanceof MessageString){
			log.info("Received from " +getSender().path().name()+ " :" + ((MessageString)message).data);
			getContext().system().scheduler().scheduleOnce(Duration.ofMillis(3000), getSelf(), ((MessageString)message).data, getContext().system().dispatcher(), ActorRef.noSender());
		}

		if(message instanceof String){
			log.info((String)message + " finished");
			this.getContext().getParent().tell(new MessageFinished((String)message), this.getSelf());
		}
	}

	@Override
	public void postStop(){
		log.info("This receiver was stopped");
	}
	
	
}
