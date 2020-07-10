package org.atlanmod.salut;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.atlanmod.commons.log.Log;
import org.atlanmod.salut.io.ByteArrayReader;
import org.atlanmod.salut.mdns.Additional;
import org.atlanmod.salut.mdns.Answer;
import org.atlanmod.salut.mdns.Authority;
import org.atlanmod.salut.mdns.Header;
import org.atlanmod.salut.mdns.Question;

public class PacketParser {

    private final ByteArrayReader buffer;
    //@formatter:off
    private Header header;
    private List<Question>      questions   = new ArrayList<>();
    private List<Answer>        answers     = new ArrayList<>();
    private List<Authority>     authorities = new ArrayList<>();
    private List<Additional>    additionals = new ArrayList<>();
    //@formatter:on

    public PacketParser(byte[] data) {
        this.buffer = ByteArrayReader.wrap(data);
    }

    public Package parse() {
        Log.info(" ------ Start Parsing ------");

        try {
            doParse();

        } catch (ParseException | IllegalArgumentException e) {
            String message = MessageFormat.format("Error when parsing packet at position {0}",
                buffer.position());
            Log.error(e, message);
            try {
                Path path = Files.createTempFile("packet_error_", ".hex");
                Files.write(path, buffer.array());
                Log.error("Created package dump at {0} ", path);
            } catch (IOException ex) {
                Log.error(ex);
            }
        } finally {
            Log.info("Found {0} questions, {1} answers, {2} authorities, and {3} additionals.",
                questions.size(), answers.size(), authorities.size(), additionals.size());
        }

        return new Package(header, questions, answers, authorities, additionals);
    }

    public void doParse() throws ParseException {
        header = Header.fromByteBuffer(buffer);
        this.parseQuestions(header.questionRecordCount());
        this.parseAnswers(header.answerRecordCount());
        this.parseAuthorities(header.authorityRecordCount());
        this.parseAdditional(header.additionalRecordCount());

        Log.info("Packet parsed successfully !");
    }

    public void parseQuestions(int numberOfQuestions) throws ParseException {
        this.questions = new ArrayList<>(numberOfQuestions);
        for (int i = 0; i < numberOfQuestions; i++) {
            questions.add(Question.fromByteBuffer(buffer));
        }
    }

    public void parseAnswers(int numberOfAnswers) throws ParseException {
        this.answers = new ArrayList<>(numberOfAnswers);
        for (int i = 0; i < numberOfAnswers; i++) {
            answers.add(Answer.fromByteBuffer(buffer));
        }
    }

    public void parseAuthorities(int numberOfAuthorities) throws ParseException {
        this.authorities = new ArrayList<>(numberOfAuthorities);
        for (int i = 0; i < numberOfAuthorities; i++) {
            authorities.add(Authority.fromByteBuffer(buffer));
        }
    }

    public void parseAdditional(int numberOfAdditionals) throws ParseException {
        this.additionals = new ArrayList<>(numberOfAdditionals);
        for (int i = 0; i < numberOfAdditionals; i++) {
            additionals.add(Additional.fromByteBuffer(buffer));
        }
    }

}
