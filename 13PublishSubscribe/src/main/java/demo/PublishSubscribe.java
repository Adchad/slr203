package demo;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
/**
 * @author Remi SHARROCK and Axel Mathieu
 * @description Create an actor and passing his reference to
 *				another actor by message.
 */
public class PublishSubscribe {

	public static void main(String[] args) {
		// Instantiate an actor system
		final ActorSystem system = ActorSystem.create("system");
		
		// Instantiate first and second actor
		final ActorRef publisher1 = system.actorOf(Publisher.createActor(1000, "hello","hello2"), "publisher1");
		final ActorRef publisher2 = system.actorOf(Publisher.createActor(2000, "world",""), "publisher2");

		final ActorRef a = system.actorOf(ReceiverActor.createActor(), "a");
		final ActorRef b = system.actorOf(ReceiverActor.createActor(), "b");
		final ActorRef c = system.actorOf(ReceiverActor.createActor(), "c");

		final ActorRef topic1 = system.actorOf(Topic.createActor(), "topic1");
		final ActorRef topic2 = system.actorOf(Topic.createActor(), "topic2");



		MessageTwoRefs topicRef = new MessageTwoRefs(topic1, topic2);
		MessageRef topic1Ref = new MessageRef(topic1);
		MessageRef topic2Ref = new MessageRef(topic2);

		a.tell(topicRef, ActorRef.noSender());
		b.tell(topicRef, ActorRef.noSender());
		c.tell(topicRef, ActorRef.noSender());

		publisher1.tell(topic1Ref, ActorRef.noSender());
		publisher2.tell(topic2Ref, ActorRef.noSender());

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
