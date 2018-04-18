package org.atlanmod.salut;

import fr.inria.atlanmod.commons.log.Log;

import java.net.DatagramPacket;

public class IncomingPacketWorker implements Runnable {

    private static final int HEADER_SIZE = 12;

    private final SocketReceiver receiver;

    public IncomingPacketWorker(SocketReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void run() {
        byte[] data = new byte[]{};
        while (true) {
            try {
                DatagramPacket packet = receiver.receive();
                Log.info("Received packet of {0} bytes", data.length);

                data = packet.getData();
                PacketParser parser = new PacketParser(data);
                parser.parse();

            } catch (InterruptedException e) {
                Log.error(e, "Worker interrupted when waiting for a packet");
            } catch (NullPointerException e) {

            }
        }
    }

}
