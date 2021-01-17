package demo;



import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class SessionActor extends UntypedAbstractActor{

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	// Actor reference
	private ActorRef client;



	public SessionActor() {

	}

	// Static function creating actor
	public static Props createActor() {
		return Props.create(SessionActor.class, () -> {
			return new SessionActor();
		});
	}


	@Override
	public void onReceive(Object message) throws Throwable {
		if(message instanceof MessageRef){
		this.client = ((MessageRef) message).ref;
		log.info("Received from " +getSender().path().name()+ " : references to Client");

		}

		if(message instanceof MessageString){
			log.info("Received from " +getSender().path().name()+ " : " + ((MessageString)message).data);

			if(((MessageString)message).data=="m1"){
				client.tell(new MessageString("m2"), this.getSelf());
			}
			

		}
	}


	@Override 
    public void postStop() {
		log.info("This session was stopped");
	}
}