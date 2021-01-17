package demo;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class ReceiverActor extends UntypedAbstractActor {
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

	private ActorRef loadbalancer;

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
		if (message instanceof MessageRef) {
			this.loadbalancer = ((MessageRef) message).ref;
			loadbalancer.tell(new MessageString("join"), this.getSelf());

		}
		if (message instanceof MessageString){
			log.info("Received from " +getSender().path().name()+ " :" + ((MessageString)message).data);
			if(((MessageString)message).data=="m2"){
				loadbalancer.tell(new MessageString("unjoin"), this.getSelf());
			}
		}
	}
	
	
}
