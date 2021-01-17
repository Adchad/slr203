package demo;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
/**
 * @author Remi SHARROCK and Axel Mathieu
 * @description Create an actor and passing his reference to
 *				another actor by message.
 */
public class SessionChildActor {

	public static void main(String[] args) {
		// Instantiate an actor system
		final ActorSystem system = ActorSystem.create("system");
		
		// Instantiate first and second actor
		final ActorRef sessionManager = system.actorOf(SessionManager.createActor(), "sessionManager");

		final ActorRef client1 = system.actorOf(ClientActor.createActor(), "client1");



		MessageStringRef sessionManagerRef = new MessageStringRef("sessionManager",sessionManager);
		
		client1.tell(sessionManagerRef, ActorRef.noSender());

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
