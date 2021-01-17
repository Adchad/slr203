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
	private ActorRef merger;

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
		this.merger = ((MessageRef) message).ref;

		log.info("Received from " +getSender().path().name()+ " : references to Merger");
		join();
		log.info("Joined Merger");

		getContext().system().scheduler().scheduleOnce(Duration.ofMillis(1000), getSelf(), "go", getContext().system().dispatcher(), ActorRef.noSender());
		}

		if(message instanceof String){
			log.info("Received from " +getSender().path().name()+ " : " + (String)message);
			if((String)message=="go"){
				log.info("Sent hi to Merger");
				merger.tell( new MessageString("hi"), this.getSelf());
				if(this.getSelf().path().name()=="c"){
					unjoin();
					log.info("Unjoined  Merger");
				}
				else{
					getContext().system().scheduler().scheduleOnce(Duration.ofMillis(1000), getSelf(), "go2", getContext().system().dispatcher(), ActorRef.noSender());
				}

			}
			if((String)message=="go2" && this.getSelf().path().name()!="c"){
				log.info("Sent hi2 to Merger");
				merger.tell( new MessageString("hi2"), this.getSelf());

			}
		}
	}


	public void join(){
		this.merger.tell(new MessageJoin(), this.getSelf());
	}

	public void unjoin(){
		this.merger.tell(new MessageUnjoin(), this.getSelf());
	}
}

//		getContext().system().scheduler().scheduleOnce(Duration.ofMillis(1000), getSelf(), "go", getContext().system().dispatcher(), ActorRef.noSender());
