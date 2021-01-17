package demo;

import java.time.Duration;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Publisher extends UntypedAbstractActor{

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	// Actor reference
	private ActorRef topic;

	private int timer;
	private String message;
	private String message2;

	public Publisher(int timer, String message, String message2) {
		this.timer = timer;
		this.message=message;
		this.message2=message2;

	}

	// Static function creating actor
	public static Props createActor(int timer, String message, String message2) {
		return Props.create(Publisher.class, () -> {
			return new Publisher(timer, message, message2);
		});
	}


	@Override
	public void onReceive(Object message) throws Throwable {
		if(message instanceof MessageRef){
		this.topic = ((MessageRef) message).ref;

		log.info("Received from " +getSender().path().name()+ " : references to Topic");

		getContext().system().scheduler().scheduleOnce(Duration.ofMillis(this.timer), getSelf(), "go", getContext().system().dispatcher(), ActorRef.noSender());
		
		}

		if(message instanceof String){
			if((String)message=="go"){

				topic.tell( new MessageString(this.message), this.getSelf());
				getContext().system().scheduler().scheduleOnce(Duration.ofMillis(5000), getSelf(), "go2", getContext().system().dispatcher(), ActorRef.noSender());

			}
			if((String)message=="go2" && this.getSelf().path().name()=="publisher1"){

				topic.tell( new MessageString(this.message2), this.getSelf());

			}

		}
	}
}