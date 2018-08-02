import java.util.Arrays;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;

import javax.sound.midi.Receiver;
import javax.sound.midi.ControllerEventListener;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

/**
 * Dailyprogrammer: 7 - easy
 * https://www.reddit.com/r/dailyprogrammer/comments/pr2xr/2152012_challenge_7_easy/
 */
public class Easy {

    private enum Token {
        DOT,
        DASH,
        LETTER_PAUSE,
        WORD_PAUSE
    }

    private static final String ENCODE_CMD = "encode";
    private static final String DECODE_CMD = "decode";
    private static final String BEEP_CMD = "beep";

    private static final String DOT = ".";
    private static final String DASH = "-";
    private static final String WORD_SEPARATOR = " / ";
    private static final String LETTER_SEPARATOR = " ";
    
    private static final int DOT_DURATION = 1;
    private static final int DASH_DURATION = DOT_DURATION * 3;
    private static final int LETTER_PAUSE_DURATION = DOT_DURATION * 3;
    private static final int WORD_PAUSE_DURATION = DOT_DURATION * 7;

    private static final Map<Character, List<Token>> ALPHABET;
    static {
        ALPHABET = new LinkedHashMap<Character, List<Token>>();
        ALPHABET.put('A', Arrays.asList(Token.DOT, Token.DASH));
        ALPHABET.put('B', Arrays.asList(Token.DASH, Token.DOT, Token.DOT, Token.DOT));
        ALPHABET.put('C', Arrays.asList(Token.DASH, Token.DOT, Token.DASH, Token.DOT));
        ALPHABET.put('D', Arrays.asList(Token.DASH, Token.DOT, Token.DOT));
        ALPHABET.put('E', Arrays.asList(Token.DOT));
        ALPHABET.put('F', Arrays.asList(Token.DOT, Token.DOT, Token.DASH, Token.DOT));
        ALPHABET.put('G', Arrays.asList(Token.DASH, Token.DASH, Token.DOT));
        ALPHABET.put('H', Arrays.asList(Token.DOT, Token.DOT, Token.DOT, Token.DOT));
        ALPHABET.put('I', Arrays.asList(Token.DOT, Token.DOT));
        ALPHABET.put('J', Arrays.asList(Token.DOT, Token.DASH, Token.DASH, Token.DASH));
        ALPHABET.put('K', Arrays.asList(Token.DASH, Token.DOT, Token.DASH));
        ALPHABET.put('L', Arrays.asList(Token.DOT, Token.DASH, Token.DOT, Token.DOT));
        ALPHABET.put('M', Arrays.asList(Token.DASH, Token.DASH));
        ALPHABET.put('N', Arrays.asList(Token.DASH, Token.DOT));
        ALPHABET.put('O', Arrays.asList(Token.DASH, Token.DASH, Token.DASH));
        ALPHABET.put('P', Arrays.asList(Token.DOT, Token.DASH, Token.DASH, Token.DOT));
        ALPHABET.put('Q', Arrays.asList(Token.DASH, Token.DASH, Token.DOT, Token.DASH));
        ALPHABET.put('R', Arrays.asList(Token.DOT, Token.DASH, Token.DOT));
        ALPHABET.put('S', Arrays.asList(Token.DOT, Token.DOT, Token.DOT));
        ALPHABET.put('T', Arrays.asList(Token.DASH));
        ALPHABET.put('U', Arrays.asList(Token.DOT, Token.DOT, Token.DASH));
        ALPHABET.put('V', Arrays.asList(Token.DOT, Token.DOT, Token.DOT, Token.DASH));
        ALPHABET.put('W', Arrays.asList(Token.DOT, Token.DASH, Token.DASH));
        ALPHABET.put('X', Arrays.asList(Token.DASH, Token.DOT, Token.DOT, Token.DASH));
        ALPHABET.put('Y', Arrays.asList(Token.DASH, Token.DOT, Token.DASH, Token.DASH));
        ALPHABET.put('Z', Arrays.asList(Token.DASH, Token.DASH, Token.DOT, Token.DOT));

        ALPHABET.put('1', Arrays.asList(Token.DOT, Token.DASH, Token.DASH, Token.DASH, Token.DASH));
        ALPHABET.put('2', Arrays.asList(Token.DOT, Token.DOT, Token.DASH, Token.DASH, Token.DASH));
        ALPHABET.put('3', Arrays.asList(Token.DOT, Token.DOT, Token.DOT, Token.DASH, Token.DASH));
        ALPHABET.put('4', Arrays.asList(Token.DOT, Token.DOT, Token.DOT, Token.DOT, Token.DASH));
        ALPHABET.put('5', Arrays.asList(Token.DOT, Token.DOT, Token.DOT, Token.DOT, Token.DOT));
        ALPHABET.put('6', Arrays.asList(Token.DASH, Token.DOT, Token.DOT, Token.DOT, Token.DOT));
        ALPHABET.put('7', Arrays.asList(Token.DASH, Token.DASH, Token.DOT, Token.DOT, Token.DOT));
        ALPHABET.put('8', Arrays.asList(Token.DASH, Token.DASH, Token.DASH, Token.DOT, Token.DOT));
        ALPHABET.put('9', Arrays.asList(Token.DASH, Token.DASH, Token.DASH, Token.DASH, Token.DOT));
        ALPHABET.put('0', Arrays.asList(Token.DASH, Token.DASH, Token.DASH, Token.DASH, Token.DASH));
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            usage();
            return;
        }

        String cmd = args[0];
        if (ENCODE_CMD.equals(cmd)) {
            System.out.println(encode(args[1]));
        } else if (DECODE_CMD.equals(cmd)) {
            System.out.println(decode(args[1]));
        } else if (BEEP_CMD.equals(cmd)) {
            beep(args[1]);
        } else {
            usage();
        }
    }

    private static void usage() {
        System.out.println("Usage: java Easy <decode|encode|beep> <msg>");
        System.exit(-1);
    }

    private static String decode(String code) {
        if (code == null) 
            throw new IllegalArgumentException("Code is null");

        StringBuilder msg = new StringBuilder();
        // max sequence length for a single letter is 5 dashes/dots
        List<Token> letter = new ArrayList<>(5);
        List<Token> tokens = tokenize(code);
        for (Token t : tokens) {
            if (t == Token.DOT) {
                letter.add(t);
            } else if (t == Token.DASH) {
                letter.add(t);
            } else if (t == Token.WORD_PAUSE) {
                msg.append(match(letter));
                msg.append(" ");
                letter.clear();
            } else if (t == Token.LETTER_PAUSE) {
                msg.append(match(letter));
                letter.clear();
            } else {
                throw new IllegalStateException("Unknown token: " + t);
            }
        }

        if (letter.size() > 0) {
            msg.append(match(letter));
        }

        return msg.toString();
    }

    private static Character match(List<Token> tokens) {
        assert tokens != null && tokens.size() > 0;
        for (Map.Entry<Character, List<Token>> entry : ALPHABET.entrySet()) {
            if (tokens.equals(entry.getValue())) {
                return entry.getKey();
            }
        }

        throw new IllegalArgumentException("Unknown code: " + tokens);
    }

    private static String encode(String msg) {
        if (msg == null) 
            throw new IllegalArgumentException("Message is null");

        msg = msg.trim();
        StringBuilder sb = new StringBuilder();
        char ch;
        for (int i = 0; i < msg.length();) {
            ch = msg.charAt(i);
            if (Character.isWhitespace(ch)) {
                // replace all subsequent whitespaces with a single word separator
                sb.append(WORD_SEPARATOR);
                while (i < msg.length() && Character.isWhitespace(msg.charAt(i))) i++;
                continue;
            }

            List<Token> tokens = ALPHABET.get(ch);
            //System.out.println(ch + " -> " + tokens);
            if (tokens != null) {
                for (Token t : tokens) {
                    if (t == Token.DOT) {
                        sb.append(DOT);
                    } else if (t == Token.DASH) {
                        sb.append(DASH);
                    } else {
                        throw new IllegalStateException(tokens + ", for char=" + ch);
                    }
                }

                // don't append letter separator at the end of the message
                // don't append letter separator before a word separator
                if (i + 1 < msg.length() && !Character.isWhitespace(msg.charAt(i + 1))) {
                    sb.append(LETTER_SEPARATOR);
                }

                i++;
            } else {
                throw new IllegalArgumentException(String.format(
                    "Message must contain only uppercase latin letters separated by whitespaces, illegal char: '%s'", ch));
            }
        }

        return sb.toString();
    }

    private static void beep(String msg) {
        System.out.println(encode(msg));
        List<Token> tokens = tokenize(encode(msg));
        try {
            final Sequencer sequencer = MidiSystem.getSequencer();
            Sequence sequence = new Sequence(Sequence.PPQ, 4);
            Track track = sequence.createTrack();

            int note = 52;
            int time = 0;
            int channel = 0;
            int velocity = 127;

            track.add(new MidiEvent(new ShortMessage(ShortMessage.PROGRAM_CHANGE, channel, 41, 0), time));
            track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, channel, note, velocity), time));
            time += DASH_DURATION;

            for (int i = 0; i < tokens.size(); i++) {
                Token t = tokens.get(i);
                if (t == Token.DOT) {
                    track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, channel, note, velocity), time));
                    time += DOT_DURATION;
                } else if (t == Token.DASH) {
                    track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, channel, note, velocity), time));
                    time += DASH_DURATION;
                } else if (t == Token.LETTER_PAUSE) {
                    track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, channel, note, velocity), time));
                    time += LETTER_PAUSE_DURATION;
                } else if (t == Token.WORD_PAUSE) {
                    track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, channel, note, velocity), time));
                    time += WORD_PAUSE_DURATION;
                }

                // pause between code symbols within a letter
                // also add a pause after the final code symbol, so it doesn't stop so abruptly
                if (i == tokens.size() - 1) {
                    track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, channel, note, velocity), time));
                    time += WORD_PAUSE_DURATION;
                } else if (tokens.get(i + 1) != Token.LETTER_PAUSE && tokens.get(i + 1) != Token.WORD_PAUSE) {
                    track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, channel, note, velocity), time));
                    time += DOT_DURATION;
                }
            }

            track.add(new MidiEvent(new ShortMessage(ShortMessage.STOP), time));

            sequencer.addMetaEventListener(new MetaEventListener() {
                public void meta(MetaMessage message) {
                    if (message.getType() == 0x2F) {
                        sequencer.close();
                    }
                }
            });
            sequencer.open();
            sequencer.setSequence(sequence);
            sequencer.setTickPosition(0);
            sequencer.start();
        } catch (MidiUnavailableException e) {
            System.out.println("Can't beep code, midi is not available: " + e.getMessage());
        } catch (InvalidMidiDataException e) {
            System.out.println("Invalid midi data: " + e.getMessage());
        }
    }

    private static List<Token> tokenize(String code) {
        assert code != null && code.length() > 0;
        List<Token> tokens = new ArrayList<>();

        String ch;
        for (int i = 0; i < code.length(); ) {
            ch = code.charAt(i) + "";
            if (DOT.equals(ch)) {
                tokens.add(Token.DOT);
                i += DOT.length();
            } else if (DASH.equals(ch)) {
                tokens.add(Token.DASH);
                i += DASH.length();
            } else if (code.startsWith(WORD_SEPARATOR, i)) {
                tokens.add(Token.WORD_PAUSE);
                i += WORD_SEPARATOR.length();
            } else if (code.startsWith(LETTER_SEPARATOR, i)) {
                tokens.add(Token.LETTER_PAUSE);
                i += LETTER_SEPARATOR.length();
            } else {
                throw new IllegalArgumentException(String.format("Unknown code sumbols: '%s'", ch));
            }
        }

        return tokens;
    }
}
