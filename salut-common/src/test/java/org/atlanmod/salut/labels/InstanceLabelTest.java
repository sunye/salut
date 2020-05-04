package org.atlanmod.salut.labels;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class InstanceLabelTest {


    @ValueSource(strings = {"macbook", "a more complicate label", "a.label.with.points",
        "A LABEL WITH UPPER CASES"})
    @ParameterizedTest
    public void validLabels(String value) {
        InstanceLabel label = new InstanceLabel(value);
        assertThat(label.isValid()).isTrue();
    }

    @ValueSource(strings = {"macbook \0x01", "\0x1F DB Server"})
    @ParameterizedTest
    public void invalidLabels(String value) {
        InstanceLabel label = new InstanceLabel(value);
        assertThat(label.isValid()).isFalse();
    }

}