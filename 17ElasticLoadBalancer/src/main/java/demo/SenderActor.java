package demo;

import java.time.Duration;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class SenderActor extends UntypedAbstractActor{

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	// Actor reference
	private ActorRef loadbalancer;
	
	private int messageCount = 0;
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
		this.loadbalancer = ((MessageRef) message).ref;

		log.info("Received from " +getSender().path().name()+ " : references to LoadBalancer");

		getContext().system().scheduler().scheduleOnce(Duration.ofMillis(1000), getSelf(), "go", getContext().system().dispatcher(), ActorRef.noSender());

		}

		if(message instanceof String){
			if((String)message=="go"){
				switch (messageCount) {
					case 0:
						loadbalancer.tell( new MessageString("t1"), this.getSelf());
						log.info("Sent t1 to loadBalancer");
						messageCount++;
						break;
					case 1:
						loadbalancer.tell( new MessageString("t2"), this.getSelf());
						log.info("Sent t2 to loadBalancer");
						messageCount++;
						break;
					case 2:
						loadbalancer.tell( new MessageString("t3"), this.getSelf());
						log.info("Sent t3 to loadBalancer");
						messageCount++;
						break;
					
				}
				getContext().system().scheduler().scheduleOnce(Duration.ofMillis(1000), getSelf(), "go", getContext().system().dispatcher(), ActorRef.noSender());
			}
		}
	}
}
