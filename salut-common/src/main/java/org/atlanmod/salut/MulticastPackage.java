package org.atlanmod.salut;

import java.util.List;
import org.atlanmod.salut.record.Additional;
import org.atlanmod.salut.record.Answer;
import org.atlanmod.salut.record.Authority;
import org.atlanmod.salut.record.Header;
import org.atlanmod.salut.record.Question;

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

    /**
     * Reads the Header and returns true if it is a Query and false otherwise.
     *
     * @return true, if this package is a query.
     */
    public boolean isQuery() {
        return header.isQuery();
    }
}
