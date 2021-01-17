package demo;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class ClientActor extends UntypedAbstractActor {
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

	private ActorRef sessionManager;
	private ActorRef session;
	


	// Empty Constructor
	public ClientActor() {
	}

	// Static function that creates actor
	public static Props createActor() {
		return Props.create(ClientActor.class, () -> {
			return new ClientActor();
		});
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		if (message instanceof MessageStringRef) {
			

			MessageStringRef message_parsed = (MessageStringRef)message;

			if(message_parsed.data == "sessionManager"){
				this.sessionManager = message_parsed.ref;
				createSession();
			}
			if(message_parsed.data == "session"){
				this.session = message_parsed.ref;
				session.tell(new MessageString("m1"), this.getSelf());
			}

		}
		if (message instanceof MessageString){
			log.info("Received from " +getSender().path().name()+ " :" + ((MessageString)message).data);
			session.tell(new MessageString("m3"), this.getSelf());
			endSession();
		}

	}


	public void createSession(){
		sessionManager.tell(new MessageCreate(true), this.getSelf());
	}

	public void endSession(){
		sessionManager.tell(new MessageCreate(false), this.getSelf());
	}


	
	
}
