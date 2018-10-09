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
 * Dailyprogrammer: 12 - difficult
 * https://www.reddit.com/r/dailyprogrammer/comments/pxsew/2202012_challenge_12_difficult/
 */
public class Difficult {

    public static void main(String[] args) {
        play(args[0]);
    }

    public static void play(String notes) {
         try {
            final Sequencer sequencer = MidiSystem.getSequencer();
            Sequence sequence = new Sequence(Sequence.PPQ, 4);
            Track track = sequence.createTrack();

            int time = 0;
            int channel = 0;
            int velocity = 127;
            int pause = 3;

            track.add(new MidiEvent(new ShortMessage(ShortMessage.PROGRAM_CHANGE, channel, 41, 0), time));
            time += pause;

            for (int i = 0; i < notes.length(); i++) {
                char note = notes.charAt(i);
                int noteNumber;
                if (note == 'A') {
                    noteNumber = 69;
                } else if (note == 'B') {
                    noteNumber = 71;
                } else if (note == 'C') {
                    noteNumber = 60;
                } else if (note == 'D') {
                    noteNumber = 62;
                } else if (note == 'E') {
                    noteNumber = 64;
                } else if (note == 'F') {
                    noteNumber = 65;
                } else if (note == 'G') {
                    noteNumber = 67;
                } else {
                    throw new IllegalArgumentException("unknown note: " + note);
                }

                track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, channel, noteNumber, velocity), time));
                time += pause;

                track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, channel, noteNumber, velocity), time));
                time += pause;
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
            System.out.println("Can't play notes: " + e.getMessage());
        } catch (InvalidMidiDataException e) {
            System.out.println("Invalid midi data: " + e.getMessage());
        }
    }
}
