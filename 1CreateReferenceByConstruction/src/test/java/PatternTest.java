

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
    public void testSendReferenceByConstruction() {
        final ActorRef a2 = system.actorOf(SecondActor.createActor(), "a2");
        final ActorRef a1 = system.actorOf(FirstActor.createActor(a2), "a1");
        assertNotEquals(a1, a2);
    }
}
