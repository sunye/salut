package org.atlanmod.salut.data;

import org.atlanmod.salut.mdns.LabelArray;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

class DomainBuilderTest {

    @Test
    void test_fromNameArray() throws ParseException {

        LabelArray names = LabelArray.fromList("220", "192", "44", "10", "in-addr", "arpa");
        Domain domain = DomainBuilder.fromLabels(names);
    }
}
