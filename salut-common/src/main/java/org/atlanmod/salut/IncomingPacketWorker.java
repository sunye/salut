package org.atlanmod.salut;

import java.net.DatagramPacket;
import org.atlanmod.commons.log.Log;

public class IncomingPacketWorker implements Runnable {
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
