package demo;

import java.util.ArrayList;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Merger extends UntypedAbstractActor {
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    private ArrayList<String> messageHistory = new ArrayList<String>();
    private ArrayList<ActorRef> senderList = new ArrayList<ActorRef>();
    private ActorRef receiver;

    // Empty Constructor
	public Merger() {}

	// Static function that creates actor
	public static Props createActor() {
		return Props.create(Merger.class, () -> {
			return new Merger();
		});
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		if(message instanceof MessageString){
            if(senderList.indexOf(getSender())!=-1){
                messageHistory.set(senderList.indexOf(getSender()), ((MessageString)message).data);
                if(checkSending()){
                    log.info("Message merged");
                    receiver.tell(new MessageString(messageHistory.get(0)), this.getSelf() );
                }
            }


        }
        
        if(message instanceof MessageJoin){
            log.info(getSender().path().name()+ " Joined ");
            senderList.add(getSender());
            messageHistory.add("");
        }
        if(message instanceof MessageUnjoin){
            log.info(getSender().path().name()+ " Unjoined ");
            messageHistory.remove(senderList.indexOf(getSender()));
            senderList.remove(getSender());
        }

        if(message instanceof MessageRef){
            receiver = ((MessageRef)message).ref;
        }
    }

    private boolean checkSending(){
        String firstMessage = messageHistory.get(0);
        for(String message : messageHistory){
            if(message!=firstMessage){
                return false;
            }
        }
        return true;
    }
    


}
	

