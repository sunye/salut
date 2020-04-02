package org.atlanmod.salut;

import org.atlanmod.commons.log.Log;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

class PacketParserTest {

    /*
     In addition to compressing the *data* of resource records, data
   that appear within the *rdata* of the following rrtypes SHOULD also
   be compressed in all Multicast DNS messages:

     NS, CNAME, PTR, DNAME, SOA, MX, AFSDB, RT, KX, RP, PX, SRV, NSEC
     */

    private static String[] dirs = {"./appa-org.atlanmod.salut/src/test/resources/", "./src/test/resources/"};

    private String packetStr = "0000" + // Transaction ID
            "0000" + // Flags
            "0003" + // #Questions
            "0000" + // #Answers
            "0000" + // #Authority resource records
            "0001" + // #Additionals
            "085F686F6D656B6974" + // _homekit
            "045F746370" + // _tcp
            "056C6F63616C" + // local
            "00" + // Terminator
            "000C" + // Type PTR
            "8001" + // Class IN
            "055F72616F70" + // _raop
            "C015" + // Pointer to 0x15 (_tcp,local)
            "000C" + // Type PTR
            "8001" + // Class IN
            "085F616972706C6179" + // _airplay
            "C015" + // Pointer to Ox15 (_tcp,local)
            "000C" + // Type PTR
            "8001" + // Class IN
            //------------ RR
            "00" + // RR Label (empty)
            "0029" + // RRType x41 (OPT - Pseudo NormalRecord)
            "05A0" + // sender's UDP payload size
            "00001194" + // extended RCODE and flags (not TTL)
            "0012" + // RDLEN Length (18)
            "0004" + // OPT-CODE
            "000E" + // OPT-LENGTH
            "00E46E19C05D3A476C19C05D3A4700" + // RData
            "000000";


    private String iPhoneDeCeline = "0000" +
            "8400" + // Flags (1000 0100 0000 0000): Response, OPCODE=Query(0), AA.
            "0000" + // Questions
            "0001" + // Answers
            "0000" + // Authority RR
            "0003" + // Additionals
            "0135" +
            "0134" +
            "0165" +
            "0163" +
            "0137" +
            "0138" +
            "0131" +
            "0135" +
            "0163" +
            "0139" +
            "0132" +
            "0162" +
            "0133" +
            "0161" +
            "0163" +
            "0130" +
            "0130" +
            "0130" +
            "0130" +
            "0130" +
            "0130" +
            "0130" +
            "013001300130013001300130013001380165016603697036046172706100000C0001000000780018106950686F6E652D64652D43656C696E65056C6F63616C00C060000180010000007800040A2CC088C060001C8001000000780010FE800000000000000CA3B29C5187CE4500002905A000000000000CFDEA0008A8261D1E42490C34000000";

    @BeforeAll
    static void setup() throws IOException {

    }

    @Test
    @Tag("slow")
    void testParseAll() throws IOException {

        List<File> files = new LinkedList<>();
        Arrays.stream(dirs).map(each -> new File(each))
                .filter(each -> each.exists())
                .map(each -> each.listFiles())
                .flatMap(each -> Arrays.stream(each))
                .filter(each -> each.getName().endsWith(".hex"))
                .forEach(files::add);

        for (File each : files) {
            byte[] buffer = Files.readAllBytes(each.toPath());
            PacketParser parser = new PacketParser(buffer);
            try {
                parser.parse();
            } catch (BufferUnderflowException e) {
                Log.error(e);
                Log.error("##### - {0}", each.toPath());
            }
        }
    }


    @Test
    void testParseAdditionalRecord() {

        PacketParser parser = new PacketParser(toByteArray(packetStr));
        parser.parse();
    }

    @Test
    void parseQuestions() {
    }

    @Test
    void parseAnswers() {
    }

    @Test
    void parseAuthorities() {
    }

    @Test
    void parseAdditional() {
    }


    public static byte[] toByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }
}
