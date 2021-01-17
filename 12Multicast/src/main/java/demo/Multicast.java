package demo;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
/**
 * @author Remi SHARROCK and Axel Mathieu
 * @description Create an actor and passing his reference to
 *				another actor by message.
 */
public class Multicast {

	public static void main(String[] args) {
		// Instantiate an actor system
		final ActorSystem system = ActorSystem.create("system");
		
		// Instantiate first and second actor
		final ActorRef sender = system.actorOf(SenderActor.createActor(), "a");
		
		final ActorRef receiver1 = system.actorOf(ReceiverActor.createActor(), "receiver1");
		final ActorRef receiver2 = system.actorOf(ReceiverActor.createActor(), "receiver2");
		final ActorRef receiver3 = system.actorOf(ReceiverActor.createActor(), "receiver3");

		final ActorRef multicaster = system.actorOf(Multicaster.createActor(), "multicaster");

		MessageInitRef initMessage = new MessageInitRef(multicaster, receiver1, receiver2, receiver3);

		sender.tell(initMessage, ActorRef.noSender());
		

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
