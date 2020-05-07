package org.atlanmod.salut;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import org.atlanmod.commons.log.Log;

public class SocketReceiver implements Runnable {

    private static final short MESSAGE_LENGTH = 8972;

    private final MulticastSocket socket;
    private BlockingQueue<DatagramPacket> incoming = new ArrayBlockingQueue<>(10);

    public SocketReceiver(MulticastSocket socket) {
        this.socket = socket;
    }

    public DatagramPacket receive() throws InterruptedException {
        return this.incoming.take();
    }

    @Override
    public void run() {
        Log.info("Starting socket receiver thread");
        Thread thread = Thread.currentThread();
        while (!thread.isInterrupted()) {
            DatagramPacket packet = this.createDatagramPacket();
            try {
                socket.receive(packet);
                Log.info("Received packed from {0}", packet.getAddress());
                boolean isOffered = incoming.offer(packet);
                assert isOffered : "Offering a packet should always be possible";
            } catch (SocketTimeoutException e) {
                Log.debug(e, "Socket timeout when waiting for packet");
            } catch (IOException e) {
                Log.debug(e, "IO error when receiving packet");
            }
        }
    }

    private DatagramPacket createDatagramPacket() {
        byte[] buf = new byte[MESSAGE_LENGTH];
        return new DatagramPacket(buf, buf.length);
    }
}
