package demo;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class FirstActor extends UntypedAbstractActor{

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	// Actor reference
	private ActorRef actorRef;
	private ActorRef transmitterRef;

	public FirstActor() {}

	// Static function creating actor
	public static Props createActor() {
		return Props.create(FirstActor.class, () -> {
			return new FirstActor();
		});
	}


	@Override
	public void onReceive(Object message) throws Throwable {
		if(message instanceof MessageTwoRefs){
		this.actorRef = ((MessageTwoRefs) message).ref2;
		this.transmitterRef = ((MessageTwoRefs) message).ref1;
		log.info("Received from" +getSender()+ " : references to B and Transmitter");

		}
		else if(message instanceof MessageString){
			if(((MessageString)message).data=="start"){
				MessageStringRef m = new MessageStringRef("hello", this.actorRef);
				this.transmitterRef.tell(m, this.getSelf());
				log.info("Received from" +getSender()+ " : start");
			}
		}
	}


	/**
	 * alternative for AbstractActor
	 * @Override
	public Receive createReceive() {
		return receiveBuilder()
				// When receiving a new message containing a reference to an actor,
				// Actor updates his reference (attribute).
				.match(ActorRef.class, ref -> {
					this.actorRef = ref;
					log.info("Actor reference updated ! New reference is: {}", this.actorRef);
				})
				.build();
	}
	 */
}
