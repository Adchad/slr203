package demo;

import java.time.Duration;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import scala.concurrent.ExecutionContext;

public class SenderActor extends UntypedAbstractActor{

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	// Actor reference
	private ActorRef broadcaster;

	public SenderActor() {}

	// Static function creating actor
	public static Props createActor() {
		return Props.create(SenderActor.class, () -> {
			return new SenderActor();
		});
	}


	@Override
	public void onReceive(Object message) throws Throwable {
		if(message instanceof MessageRef){
		this.broadcaster = ((MessageRef) message).ref;

		log.info("Received from " +getSender().path().name()+ " : references to Broadcaster");

		getContext().system().scheduler().scheduleOnce(Duration.ofMillis(1000), getSelf(), "go", getContext().system().dispatcher(), ActorRef.noSender());

		}

		if(message instanceof String){
			log.info("Received from " +getSender().path().name()+ " : " + (String)message);
			if((String)message=="go"){
				broadcaster.tell( new MessageString("Hello World"), this.getSelf());
			}
		}
	}
}