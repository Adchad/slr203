package demo;
import java.util.ArrayList;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Multicaster extends UntypedAbstractActor {
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    private ArrayList<MessageGroup> groupList = new ArrayList<MessageGroup>();
    // Empty Constructor
	public Multicaster() {}

	// Static function that creates actor
	public static Props createActor() {
		return Props.create(Multicaster.class, () -> {
			return new Multicaster();
		});
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		if(message instanceof MessageGroup){
            groupList.add((MessageGroup)message);
            log.info("Received from " +getSender().path().name()+ " : references to " + ((MessageGroup)message).name);
        
        }

        if(message instanceof MessageToGroup){

            MessageToGroup message_parsed = (MessageToGroup) message;
            sendGroup(message_parsed.groupName, message_parsed.message);
        }
        
    }

    public void sendGroup(String groupName, String message){
        for(MessageGroup group : groupList){
            if(group.name==groupName){

                for(ActorRef receiver : group.refs){
                    receiver.tell(new MessageString(message),this.getSelf());
                }
            }
        }
    }
    


}
	

