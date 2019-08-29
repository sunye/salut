package org.atlanmod.salut;

import org.junit.jupiter.api.Test;

public class QueryBuilderTest {


    @Test
    void queryTest() {
        Salut salut = new Salut();
        salut.builder()
                .query()
                .tcp()
                .airplay()
                .run();
    }
}
