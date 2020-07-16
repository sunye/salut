package org.atlanmod.salut.builders;

import org.atlanmod.salut.Salut;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class QueryBuilderIT {


    @Disabled
    @Test
    void queryTest() {
        Salut.getInstance()
            .builder()
                .newQuery()
                .tcp()
                .airplay()
                .run();
    }
}
