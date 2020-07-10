package org.atlanmod.salut;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.atlanmod.salut.mdns.Additional;
import org.atlanmod.salut.mdns.Answer;
import org.atlanmod.salut.mdns.Authority;
import org.atlanmod.salut.mdns.Header;
import org.atlanmod.salut.mdns.Question;

public class Package {
    //@formatter:off
    private Header              header;
    private List<Question>      questions;
    private List<Answer>        answers;
    private List<Authority>     authorities;
    private List<Additional>    additionals;
    //@formatter:on


    public Package(Header header, List<Question> questions,
        List<Answer> answers, List<Authority> authorities,
        List<Additional> additionals) {
        this.header = header;
        this.questions = questions;
        this.answers = answers;
        this.authorities = authorities;
        this.additionals = additionals;
    }

}
