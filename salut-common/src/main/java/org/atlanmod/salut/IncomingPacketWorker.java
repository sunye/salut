package org.atlanmod.salut;

import java.net.DatagramPacket;
import org.atlanmod.commons.log.Log;
import org.atlanmod.salut.mdns.Querier;
import org.atlanmod.salut.mdns.Responder;

public class IncomingPacketWorker implements Runnable {

    private final SocketReceiver receiver;
    private final Responder responder;
    private final Querier querier;

    public IncomingPacketWorker(SocketReceiver receiver, Responder responder, Querier querier) {
        this.receiver = receiver;
        this.responder = responder;
        this.querier = querier;
    }

    @Override
    public void run() {
        Log.info("Starting oncoming packet worker thread");
        Thread thread = Thread.currentThread();
        byte[] data = new byte[]{};
        while (!thread.isInterrupted()) {
            try {
                DatagramPacket packet = receiver.receive();
                Log.info("Received packet of {0} bytes from {1}", packet.getLength(),
                    packet.getSocketAddress());
                data = packet.getData();

                PacketParser parser = new PacketParser(data);
                MulticastPackage pack = parser.parse();
                this.handleIncomingMessage(pack);

            } catch (InterruptedException e) {
                Log.error(e, "Worker interrupted when waiting for a packet");
                Thread.currentThread().interrupt();
            } catch (NullPointerException e) {
                Log.error(e, "Null pointer error when waiting for a packet");

            }
        }
    }

    /**
     * Dispatches incoming MDNS messages to the Responder or the Querier, according to message
     * Query/Response flag.
     *
     * @param receivedMessage the incoming message.
     */
    private void handleIncomingMessage(MulticastPackage receivedMessage) {
        if (shouldBeIgnored(receivedMessage)) {
            Log.warn("Silently ignoring incoming message {0}.", receivedMessage);
            return;
        }

        if (receivedMessage.isQuery()) {
            responder.accept(receivedMessage);
        } else {
            querier.accept(receivedMessage);
        }
    }

    /**
     * From [RFC6762], Section 18.3 OPCODE: Multicast DNS messages received with an OPCODE other
     * than zero MUST be silently ignored.
     *
     * @param receivedMessage
     * @return
     */
    private boolean shouldBeIgnored(MulticastPackage receivedMessage) {
        return receivedMessage.opCode() != 0;
    }

}
