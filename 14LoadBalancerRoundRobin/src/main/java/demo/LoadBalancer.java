package demo;

import java.util.ArrayList;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class LoadBalancer extends UntypedAbstractActor {
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    private ArrayList<ActorRef> receiverList = new ArrayList<ActorRef>();

    private int balancerCount = 0;
    // Empty Constructor
	public LoadBalancer() {}

	// Static function that creates actor
	public static Props createActor() {
		return Props.create(LoadBalancer.class, () -> {
			return new LoadBalancer();
		});
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		if(message instanceof MessageString){
            
            if(((MessageString)message).data=="join" && getSender().path().name()!="a"){
                receiverList.add(getSender());
                log.info("Received from " +getSender().path().name()+ " : " + ((MessageString)message).data);
            }
            
            else if(getSender().path().name()=="a"){
                receiverList.get(balancerCount).tell(message, this.getSelf());
                balancerCount = (balancerCount + 1)% receiverList.size();

            }

            else if(((MessageString)message).data=="unjoin" && getSender().path().name()!="a"){
                receiverList.remove(getSender());
                log.info("Received from " +getSender().path().name()+ " : " + ((MessageString)message).data);
                balancerCount = 0;
            }
        }
        
    }
    


}
	

