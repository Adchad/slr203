package demo;

import java.time.Duration;
import java.util.ArrayList;

import akka.actor.ActorRef; 
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class SenderActor extends UntypedAbstractActor{

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	// Actor reference
	private ActorRef multicaster;
	private ActorRef receiver1;
	private ActorRef receiver2;
	private ActorRef receiver3;

	private ArrayList<ActorRef> group1 = new ArrayList<ActorRef>();
	private ArrayList<ActorRef> group2 = new ArrayList<ActorRef>();

	public SenderActor() {}

	// Static function creating actor
	public static Props createActor() {
		return Props.create(SenderActor.class, () -> {
			return new SenderActor();
		});
	}


	@Override
	public void onReceive(Object message) throws Throwable {
		if(message instanceof MessageInitRef){
			
			MessageInitRef message_parsed = (MessageInitRef) message;
		
			this.multicaster = message_parsed.multicaster;
			this.receiver1 = message_parsed.receiver1;
			this.receiver2 = message_parsed.receiver2;
			this.receiver3 = message_parsed.receiver3;
			
			log.info("Received from " +getSender().path().name()+ " : references to Receivers and Multicaster");

			group1.add(receiver1);			
			group1.add(receiver2);		
			
			group2.add(receiver2);			
			group2.add(receiver3);	

			multicaster.tell(new MessageGroup(group1, "group1"), this.getSelf());
			multicaster.tell(new MessageGroup(group2, "group2"), this.getSelf());

			multicaster.tell(new MessageToGroup("group1","hello"), this.getSelf());
			log.info("Sent hello to group1");

			getContext().system().scheduler().scheduleOnce(Duration.ofMillis(1000), getSelf(), "go", getContext().system().dispatcher(), ActorRef.noSender());

		}

		if(message instanceof String){
			if((String)message == "go"){
				multicaster.tell(new MessageToGroup("group2","world"), this.getSelf());
				log.info("Sent world to group2");
			}
		}

	}
}

//getContext().system().scheduler().scheduleOnce(Duration.ofMillis(1000), getSelf(), "go", getContext().system().dispatcher(), ActorRef.noSender());
