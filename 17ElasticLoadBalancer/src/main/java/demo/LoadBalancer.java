package demo;

import java.util.ArrayList;
import java.util.HashMap;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class LoadBalancer extends UntypedAbstractActor {
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);


    private HashMap<ActorRef,ArrayList<String>> taskMap = new HashMap<ActorRef,ArrayList<String>>();
    private ArrayList<ActorRef> receiverList = new ArrayList<ActorRef>();

    private int max;

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
            
           if(getSender().path().name()=="a"){
                String message_string = ((MessageString)message).data;
                if(receiverList.size()<max){
                    ActorRef currentReceiver = this.getContext().actorOf(ReceiverActor.createActor(), "receiver" + String.valueOf(receiverList.size()));
                    taskMap.put(currentReceiver, new ArrayList<String>());
                    taskMap.get(currentReceiver).add(message_string);
                    receiverList.add(currentReceiver);
                    currentReceiver.tell(message, this.getSelf());
                    

                }
                else{
                    taskMap.get(receiverList.get(balancerCount)).add(message_string);
                    receiverList.get(balancerCount).tell(message, this.getSelf());
                    balancerCount = (balancerCount + 1)% receiverList.size();
                }

            }
        }

        if(message instanceof MessageFinished){
            String task = ((MessageFinished)message).task;
            
            taskMap.get(getSender()).remove(task);
            if(taskMap.get(getSender()).size()==0){
                this.getContext().stop(getSender());
                taskMap.remove(getSender());
                receiverList.remove(getSender());
            }
        }

        if(message instanceof MessageMax){
            max = ((MessageMax)message).max;
        }
        
    }
    


}
	

