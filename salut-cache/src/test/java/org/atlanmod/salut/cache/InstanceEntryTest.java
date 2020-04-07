package org.atlanmod.salut.cache;

import org.atlanmod.salut.labels.Labels;

class InstanceEntryTest {
    private Labels names = Labels.fromList("MacBook", "local");
    private ServiceEntry entry;

    /*
    @BeforeEach
    void setup() throws ParseException {
        Domain name = DomainBuilder.fromLabels(names);
        this.entry = new ServiceEntry(TimeToLive.fromSeconds(0), name);
    }


    @Test
    void name() throws ParseException {
        Domain name = DomainBuilder.fromLabels(names);

        assertEquals(name, entry.name());
    }

    @Test
    void getAddresses() throws UnknownHostException {
        Inet4Address inet4address = (Inet4Address) InetAddress.getByAddress(new byte[]{72, 16, 8, 4});
        AddressEntry ae = new Inet4AddressEntry(TimeToLive.fromSeconds(0), inet4address);
        entry.getAddresses().add(ae);

        assertTrue(ae.getLabels().contains(entry));
        assertTrue(entry.getAddresses().contains(ae));
        assertTrue(entry.getAddresses()
                .references()
                .stream()
                .anyMatch(each -> each.address().equals(inet4address)));
    }

    @Test
    void getService() {
    }
    */
}
