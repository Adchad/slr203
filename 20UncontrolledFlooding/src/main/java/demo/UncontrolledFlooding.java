package demo;

import java.util.ArrayList;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;


public class UncontrolledFlooding {

	private static ArrayList<ActorRef> actorList = new ArrayList<ActorRef>();

    public static void main(String[] args) {
		// Instantiate an actor system
		final ActorSystem system = ActorSystem.create("system");
		
		// Instantiate first and second actor
		
		

		actorList.add(system.actorOf(Actor.createActor(), "a"));
		actorList.add(system.actorOf(Actor.createActor(), "b"));
		actorList.add(system.actorOf(Actor.createActor(), "c"));
		actorList.add(system.actorOf(Actor.createActor(), "d"));
		actorList.add(system.actorOf(Actor.createActor(), "e"));


		ArrayList<ArrayList<Integer>> matrix = generateMatrix();

		sendRefTopology(matrix);

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

	public static void sendRefTopology(ArrayList<ArrayList<Integer>> matrix){
		for(int i =0; i<5; ++i){
			MessageRefList message = new MessageRefList(new ArrayList<ActorRef>());
			for(int j=0; j<5; ++j){
				w.get(j));
				}
			}

			actorList.get(i).tell(message, ActorRef.noSender());
			try{Thread.sleep(200);}catch(Exception e){}
		}
	}


	public static ArrayList<ArrayList<Integer>> generateMatrix(){
		ArrayList<ArrayList<Integer>> matrix = new ArrayList<ArrayList<Integer>>();

		matrix.add(new ArrayList<Integer>());
		matrix.add(new ArrayList<Integer>());
		matrix.add(new ArrayList<Integer>());
		matrix.add(new ArrayList<Integer>());
		matrix.add(new ArrayList<Integer>());

		for(int i =0; i<matrix.size(); ++i){
			matrix.get(i).add(0);
			matrix.get(i).add(0);
			matrix.get(i).add(0);
			matrix.get(i).add(0);
			matrix.get(i).add(0);

		}

		matrix.get(0).set(1, 1);
		matrix.get(0).set(2, 1);
		matrix.get(1).set(3, 1);
		matrix.get(2).set(3, 1);
		matrix.get(3).set(4, 1);
	
		return matrix;
	}
}