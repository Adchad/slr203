package demo;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;


public class SearchActorMain {

    public static void main(String[] args) {
		// Instantiate an actor system
		final ActorSystem system = ActorSystem.create("system");
		
		// Instantiate first and second actor
		final ActorRef a = system.actorOf(MainActor.createActor(), "a");
		
		a.tell("CREATE", ActorRef.noSender());
		a.tell("CREATE", ActorRef.noSender());

		try{Thread.sleep(200);}catch(Exception e){}
		system.actorSelection("/user/a/actor0").tell("This is a message sent from main", ActorRef.noSender());

	    try {
			waitBeforeTerminate();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			system.terminate();
		}
	}

	public static void waitBeforeTerminate() throws InterruptedException {
		Thread.sleep(10000);
	}
}