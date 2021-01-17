package demo;

import java.time.Duration;
import java.util.ArrayList;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Actor extends UntypedAbstractActor{

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	private MessageNumberPacket message_old = new MessageNumberPacket(-1, "");
	// Actor reference
	ArrayList<ActorRef> list ;


	public Actor() {}

	// Static function creating actor
	public static Props createActor() {
		return Props.create(Actor.class, () -> {
			return new Actor();
		});
	}


	@Override
	public void onReceive(Object message) throws Throwable {
		if(message instanceof MessageRefList){
			list = ((MessageRefList)message).refList;
			for(ActorRef ref : list){
				log.info("[" + getSelf().path().name() + "] This Actor has received a ref to: "+ ref.path().name());
			}
           
		}
		
		if(message instanceof String){
			log.info("[" + getSelf().path().name() + "] "+ (String)message + " was received from "+ getSender().path().name());
			for(ActorRef actor : list){
				actor.tell((String)message, this.getSelf());
			}
		}
		
		if(message instanceof MessageNumberPacket){
			MessageNumberPacket message_new = (MessageNumberPacket)message;
			
			if(message_new.number == message_old.number){
				log.info("[" + getSelf().path().name() + "] "+"Packet from "+getSender().path().name()+" was dropped");
				return;
			}
			else{
				message_old = message_new;
				
				log.info("[" + getSelf().path().name() + "] "+ message_new.packet +" with number "+ message_new.number + " was received from "+ getSender().path().name());
				
				for(ActorRef actor : list){
					actor.tell(message_new, this.getSelf());
				}


			}

		}

		
	}
}
//		getContext().system().scheduler().scheduleOnce(Duration.ofMillis(1000), getSelf(), "go", getContext().system().dispatcher(), ActorRef.noSender());
