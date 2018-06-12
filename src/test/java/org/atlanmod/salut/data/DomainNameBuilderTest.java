package org.atlanmod.salut.data;

import org.atlanmod.salut.mdns.NameArray;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

class DomainNameBuilderTest {

    @Test
    void test_fromNameArray() throws ParseException {

        NameArray names = NameArray.fromList("220", "192", "44", "10", "in-addr", "arpa");
        DomainName domain = DomainNameBuilder.fromNameArray(names);
    }
}