package demo;

import java.util.ArrayList;

import akka.actor.ActorRef;

public class MessageNumberPacket{
    public final String packet;
    public final int number;


    public MessageNumberPacket(int number, String packet){
        this.number = number;
        this.packet = packet;
    }
}

