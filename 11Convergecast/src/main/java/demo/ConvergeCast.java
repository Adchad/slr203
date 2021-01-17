package demo;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
/**
 * @author Remi SHARROCK and Axel Mathieu
 * @description Create an actor and passing his reference to
 *				another actor by message.
 */
public class ConvergeCast {

	public static void main(String[] args) {
		// Instantiate an actor system
		final ActorSystem system = ActorSystem.create("system");
		
		// Instantiate first and second actor
		final ActorRef a = system.actorOf(SenderActor.createActor(), "a");
		
		final ActorRef b = system.actorOf(SenderActor.createActor(), "b");
		final ActorRef c = system.actorOf(SenderActor.createActor(), "c");

		final ActorRef d = system.actorOf(ReceiverActor.createActor(), "d");


		final ActorRef merger = system.actorOf(Merger.createActor(), "merger");

		MessageRef mergerRefMsg = new MessageRef(merger);

		a.tell(mergerRefMsg, ActorRef.noSender());
		b.tell(mergerRefMsg, ActorRef.noSender());
		c.tell(mergerRefMsg, ActorRef.noSender());
		
		merger.tell(new MessageRef(d),ActorRef.noSender());


	    try {
			waitBeforeTerminate();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			system.terminate();
		}
	}

	public static void waitBeforeTerminate() throws InterruptedException {
		Thread.sleep(5000);
	}

}
