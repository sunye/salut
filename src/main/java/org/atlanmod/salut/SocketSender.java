package org.atlanmod.salut;

import org.atlanmod.commons.log.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class SocketSender implements Runnable {

    private final MulticastSocket socket;
    private BlockingQueue<DatagramPacket> outgoing = new ArrayBlockingQueue<DatagramPacket>(10);

    public SocketSender(MulticastSocket socket) {
        this.socket = socket;
    }

    public void send(DatagramPacket packet) {
        this.outgoing.offer(packet);
    }

    @Override
    public void run() {
        Log.info("Starting socket sender thread");
        Thread thread =  Thread.currentThread();
        while (!thread.isInterrupted()) {
            try {
                DatagramPacket packet = outgoing.take();
                socket.send(packet);
                Log.info("Packet sent");
            } catch (InterruptedException e) {
                Log.debug(e, "Thread interrupted when sending packet");
            } catch (IOException e) {
                Log.debug(e, "IO error when sending packet");
            }
        }
    }
}
