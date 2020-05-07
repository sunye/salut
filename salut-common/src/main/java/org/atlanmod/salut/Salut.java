package org.atlanmod.salut;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.MulticastSocket;
import org.atlanmod.commons.log.Log;
import org.atlanmod.salut.domains.Host;
import org.atlanmod.salut.mdns.RecordFactory;
import org.atlanmod.salut.mdns.ServerSelectionRecord;
import org.atlanmod.salut.sd.ServiceDescription;
import org.atlanmod.salut.sd.ServicePublisher;

public class Salut implements ServicePublisher {

    private static final String MDNS_GROUP_ADDRESS_IPV4 = "224.0.0.251";
    private static final String MDNS_GROUP_ADDRESS_IPV6 = "FF02::FB";
    private static final int MDNS_PORT = 5353;
    private static final int SOCKET_TIMEOUT_MILLISECONDS = 750;
    private static final int SOCKET_TTL = 10;

    private MulticastSocket socket;
    private Host localHost;


    /**
     * Main class.
     * Singleton
     */
    private Salut() {}

    /**
     * Returns the instance of this class.
     *
     * @return the instance of this class
     */
    public static Salut getInstance() {
        return SalutHolder.INSTANCE;
    }

    public void run() throws IOException {
        Log.info("run()");

        this.localHost = Host.localHost();

        this.openSocket(InetAddress.getLocalHost());
        SocketReceiver receiver = new SocketReceiver(this.socket);
        SocketSender sender = new SocketSender(this.socket);
        IncomingPacketWorker parser = new IncomingPacketWorker(receiver);

        Thread thr1 = new Thread(receiver);
        Thread thr2 = new Thread(sender);
        Thread thr3 = new Thread(parser);

        thr1.start();
        thr2.start();
        thr3.start();

    }

    private void openSocket(InetAddress localhost) throws IOException {
        Log.info("Opening Socket {0}", localhost.getHostAddress());
        InetAddress groupAddress = (localhost instanceof Inet4Address) ?
                InetAddress.getByName(MDNS_GROUP_ADDRESS_IPV4) : InetAddress.getByName(MDNS_GROUP_ADDRESS_IPV6);

        socket = new MulticastSocket(MDNS_PORT);
        socket.setInterface(localhost);
        socket.joinGroup(groupAddress);
        socket.setTimeToLive(SOCKET_TTL);
        socket.setSoTimeout(SOCKET_TIMEOUT_MILLISECONDS);
    }

    /**
     * Creates a new instance of Builder to create and publish Queries and Services.
     *
     * @return A Builder
     */
    public  Builder builder() {
        return new Builder(this);
    }


    @Override
    public void publish(ServiceDescription service) {
        RecordFactory factory = new RecordFactory(this.localHost);
        ServerSelectionRecord srv = factory.createServerSelectionRecord(service);
    }

    public static void main(String[] args) throws IOException {
        Salut zero = new Salut();
        zero.run();
    }

    /**
     * The initialization-on-demand holder of the singleton of this class.
     */
    private static final class SalutHolder {

        /**
         * The instance of the outer class.
         */
        static final Salut INSTANCE = new Salut();
    }

}
