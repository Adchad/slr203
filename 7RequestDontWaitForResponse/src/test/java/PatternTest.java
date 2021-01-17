

import static org.junit.Assert.assertNotEquals;

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
    public void testSendReferenceByMessage() {
        final ActorRef a2 = system.actorOf(SecondActor.createActor(), "a2");
        final ActorRef a1 = system.actorOf(FirstActor.createActor(), "a1");
        a1.tell(a2, ActorRef.noSender());
        assertNotEquals(a1, a2);
    }
}
