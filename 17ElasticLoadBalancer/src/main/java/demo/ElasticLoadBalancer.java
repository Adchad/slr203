package demo;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
/**
 * @author Remi SHARROCK and Axel Mathieu
 * @description Create an actor and passing his reference to
 *				another actor by message.
 */
public class ElasticLoadBalancer {

	public static void main(String[] args) {
		// Instantiate an actor system
		final ActorSystem system = ActorSystem.create("system");
		
		// Instantiate first and second actor
		final ActorRef a = system.actorOf(SenderActor.createActor(), "a");
		

		final ActorRef loadbalancer = system.actorOf(LoadBalancer.createActor(), "loadbalancer");

		MessageRef loadbalancerRef = new MessageRef(loadbalancer);

		a.tell(loadbalancerRef, ActorRef.noSender());

		loadbalancer.tell(new MessageMax(2), ActorRef.noSender());


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
