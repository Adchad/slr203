package demo;

import java.time.Duration;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class MainActor extends UntypedAbstractActor{

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	// Actor reference
	private int numberChild=0;
	

	public MainActor() {}

	// Static function creating actor
	public static Props createActor() {
		return Props.create(MainActor.class, () -> {
			return new MainActor();
		});
	}


	@Override
	public void onReceive(Object message) throws Throwable {
        if(message instanceof String){
            if((String)message=="CREATE"){
				this.getContext().actorOf(ChildActor.createActor(), "actor"+numberChild);
				log.info("actor"+numberChild+ " was created");
				numberChild++;
            }
        }
		

		
	}
}
//		getContext().system().scheduler().scheduleOnce(Duration.ofMillis(1000), getSelf(), "go", getContext().system().dispatcher(), ActorRef.noSender());
