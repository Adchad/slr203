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
	private ActorRef bRef;

	public FirstActor() {}

	// Static function creating actor
	public static Props createActor() {
		return Props.create(FirstActor.class, () -> {
			return new FirstActor();
		});
	}


	@Override
	public void onReceive(Object message) throws Throwable {
		if(message instanceof MessageRef){
			this.bRef = ((MessageRef) message).ref;
			log.info("Received from" +getSender()+ " : reference to B");
			loopMessage(30);


		}
		else if(message instanceof MessageString){
			log.info("Received from" +getSender()+ " : " + ((MessageString)message).data);
		}
	}


	public void loopMessage(int n){
		MessageString m ;
		for(int i =0; i<n ; ++i){
			m = new MessageString("req" + String.valueOf(i));
			bRef.tell(m, this.getSelf());
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

