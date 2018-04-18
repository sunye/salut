package org.atlanmod.salut;

import fr.inria.atlanmod.commons.log.Log;
import org.atlanmod.salut.io.ByteArrayBuffer;
import org.atlanmod.salut.mdns.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PacketParser {

    private final ByteArrayBuffer buffer;

    private Header header;
    private List<Question> questions = Collections.emptyList();
    private List<Answer> answers = Collections.emptyList();
    private List<Authority> authorities = Collections.emptyList();
    private List<Additional> additionals = Collections.emptyList();

    public PacketParser(byte[] data) {
        this.buffer = ByteArrayBuffer.wrap(data);
    }

    public void parse () {

        try {
            header = Header.fromByteBuffer(buffer);
            this.parseQuestions(header.questionRecordCount());
            this.parseAnswers(header.answerRecordCount());
            this.parseAuthorities(header.authorityRecordCount());
            this.parseAdditional(header.additionalRecordCount());

            Log.info("Packet parsed successfully !");

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
