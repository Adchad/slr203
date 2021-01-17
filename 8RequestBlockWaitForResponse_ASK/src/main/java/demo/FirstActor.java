package demo;

import java.time.Duration;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import scala.concurrent.Await;
import scala.concurrent.Future;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.pattern.Patterns;
import akka.util.Timeout;

public class FirstActor extends UntypedAbstractActor {

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	// Actor reference
	private ActorRef bRef;

	public FirstActor() {
	}

	// Static function creating actor
	public static Props createActor() {
		return Props.create(FirstActor.class, () -> {
			return new FirstActor();
		});
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		if (message instanceof MessageRef) {
			this.bRef = ((MessageRef) message).ref;
			log.info("Received from" + getSender() + " : reference to B");
			loopMessage(30);

		} //else if (message instanceof MessageString) {
		//	log.info("Received from" + getSender() + " : " + ((MessageString) message).data);
		//}
	}

	public void loopMessage(int n) {
		MessageString m;
		for(int i =0; i<n ; ++i){
			Timeout timeout = Timeout.create(Duration.ofSeconds(5));
			Future<Object> future = Patterns.ask(bRef, new MessageString("req"+String.valueOf(i)), timeout);
			try {
				MessageString res = (MessageString) Await.result(future, timeout.duration());
				log.info("Received from" +getSender()+ " : " + res.data);
			} catch (Exception e) {
				e.printStackTrace();
			}	
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

