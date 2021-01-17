package demo;


import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import java.util.HashMap;

public class SessionManager extends UntypedAbstractActor {
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    private HashMap<ActorRef,ActorRef> clientSessionMap = new HashMap<ActorRef,ActorRef>();
    // Empty Constructor
	public SessionManager() {}

	// Static function that creates actor
	public static Props createActor() {
		return Props.create(SessionManager.class, () -> {
			return new SessionManager();
		});
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		if(message instanceof MessageCreate){
            if(((MessageCreate)message).status){
                ActorRef session = this.getContext().actorOf(SessionActor.createActor(), getSender().path().name()+"_session");
                clientSessionMap.put(getSender(), session);
                session.tell(new MessageRef(getSender()), this.getSelf());
                getSender().tell(new MessageStringRef("session",session), this.getSelf());
                log.info("Session Created for " + getSender().path().name());
            }

            if(!((MessageCreate)message).status){
                //log.info("Received from " +getSender().path().name()+ " :" + ((MessageCreate)message).status);
                this.getContext().stop(clientSessionMap.get(getSender()));
                clientSessionMap.remove(getSender());
                log.info("Session Stopped for " + getSender().path().name());
            }
        }
            
	
    }
        
}
    



	

