package org.atlanmod.salut.data;

import static com.google.common.truth.Truth.assertThat;

import java.text.ParseException;
import org.atlanmod.salut.mdns.LabelArray;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ServiceInstanceNameIT {


  @DisplayName("Check methods parseString() and fromList() are equivalent")
  @Test
  void testParseString() throws ParseException {
    ServiceInstanceName one = ServiceInstanceName
        .parseString("PrintsAlot.airplay.tcp.MacBook.local");

    ServiceInstanceName other =  ServiceInstanceName
        .fromNameArray(LabelArray.fromList("PrintsAlot", "airplay", "tcp", "MacBook", "local"));

    assertThat(one).isEqualTo(other);
  }
}
