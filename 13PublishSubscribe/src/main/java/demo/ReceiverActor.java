package demo;

import java.time.Duration;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class ReceiverActor extends UntypedAbstractActor {
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

	private ActorRef topic1;
	private ActorRef topic2;


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
		if (message instanceof MessageTwoRefs) {
			topic1 = ((MessageTwoRefs)message).ref1;
			topic2 = ((MessageTwoRefs)message).ref2;
			subscribe();
			

		}
		if (message instanceof MessageString){
			log.info("Received from " +getSender().path().name()+ " :" + ((MessageString)message).data);
		}

		if(message instanceof String){
			if((String)message == "unsubscribe"){
				unsubscribe();
			}
		}
	}

	//The subscribe and unsubscribe patterns are hard-coded in this implementation, but we imagine that in a real situation they happen in the main
	public void subscribe(){
		
		switch (this.getSelf().path().name()) {
			case "a":
				topic1.tell(new MessageSubscribe("subscribe"), this.getSelf());
				//a waits 2 seconds before unsubscribing from topic1
				getContext().system().scheduler().scheduleOnce(Duration.ofMillis(3000), getSelf(), "unsubscribe", getContext().system().dispatcher(), ActorRef.noSender());
				break;
			case "b":
				topic1.tell(new MessageSubscribe("subscribe"), this.getSelf());
				topic2.tell(new MessageSubscribe("subscribe"), this.getSelf());
				break;
			case "c":
				topic2.tell(new MessageSubscribe("subscribe"), this.getSelf());
				break;
		}
	}

	public void unsubscribe(){
		if (this.getSelf().path().name()=="a") {
			topic1.tell(new MessageSubscribe("unsubscribe"), this.getSelf());

		}
	}
	
	
}
