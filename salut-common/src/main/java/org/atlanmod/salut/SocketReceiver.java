package org.atlanmod.salut;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import org.atlanmod.commons.log.Log;

public class SocketReceiver implements Runnable {

    private final static short MESSAGE_LENGTH =  8972;

    private final MulticastSocket socket;
    private BlockingQueue<DatagramPacket> incoming = new ArrayBlockingQueue<DatagramPacket>(10);

    public SocketReceiver(MulticastSocket socket) {
        this.socket = socket;
    }

    public DatagramPacket receive() throws InterruptedException {
        return this.incoming.take();
    }

    @Override
    public void run() {
        Log.info("Starting socket receiver thread");
        Thread thread =  Thread.currentThread();
        while (!thread.isInterrupted()) {
            DatagramPacket packet = this.createDatagramPacket();
            try {
                socket.receive(packet);
                Log.info("Received packed from {0}", packet.getAddress());
                incoming.offer(packet);
            } catch (SocketTimeoutException e) {
                Log.debug(e, "Socket timeout when waiting for packet");

            } catch (IOException e) {
                Log.debug(e, "IO error when receiving packet");
                e.printStackTrace();
            }
        }
    }

    private DatagramPacket createDatagramPacket() {
        byte buf[] = new byte[MESSAGE_LENGTH];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        return packet;
    }
}
