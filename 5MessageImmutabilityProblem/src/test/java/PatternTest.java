

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import demo.*;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.testkit.javadsl.TestKit;

public class PatternTest {
    static ActorSystem system;

    @BeforeClass
    public static void setup() {
        system = ActorSystem.create();
    }

    @AfterClass
    public static void teardown() {
        TestKit.shutdownActorSystem(system);
        system = null;
    }

    @Test
    public void testFireForget() {
        final ActorRef a = system.actorOf(MyActor.createActor(), "a");
        MyBrokenMessage m = new MyBrokenMessage(null,0,null,null);
        a.tell(m, ActorRef.noSender());
    }
}
