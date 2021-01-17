package demo;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
/**
 * @author Remi SHARROCK and Axel Mathieu
 * @description Create an actor and passing his reference to
 *				another actor by message.
 */
public class BroadcastRoundRobin {

	public static void main(String[] args) {
		// Instantiate an actor system
		final ActorSystem system = ActorSystem.create("system");
		
		// Instantiate first and second actor
		final ActorRef a = system.actorOf(SenderActor.createActor(), "a");
		
		final ActorRef b = system.actorOf(ReceiverActor.createActor(), "b");
		final ActorRef c = system.actorOf(ReceiverActor.createActor(), "c");

		final ActorRef broadcaster = system.actorOf(Broadcaster.createActor(), "broadcaster");

		MessageRef broadcastRefMsg = new MessageRef(broadcaster);

		a.tell(broadcastRefMsg, ActorRef.noSender());
		b.tell(broadcastRefMsg, ActorRef.noSender());
		c.tell(broadcastRefMsg, ActorRef.noSender());
		


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
