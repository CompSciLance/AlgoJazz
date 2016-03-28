import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;


public class ChordWriter 
{

	public static String writeChords(ArrayList<ArrayList<Note>> chordList)
	{
		Sequence s = null;
		try
		{
			s = new Sequence(Sequence.PPQ, 120); // create a new sequence
		}
		catch (InvalidMidiDataException e)
		{
			return e.getMessage();
		}
		Track track = s.createTrack();
		int currentTick = 0;
		for (ArrayList<Note> chord : chordList)
		{
			for (Note n : chord)
			{
				MidiEvent event = MidiUtil.CreateNoteEvent(ShortMessage.NOTE_ON, n.pitch, n.velocity, currentTick);
				MidiEvent event2 = MidiUtil.CreateNoteEvent(ShortMessage.NOTE_OFF, n.pitch, 0, currentTick + Settings.getDurationFromNoteType(Settings.NoteType.HALF));
				track.add(event);
				track.add(event2);
			}
			currentTick += Settings.getDurationFromNoteType(Settings.NoteType.HALF);
		}
		
	    try
	    {
	    	File OutputFile = new File(Settings.CHORDS_OUTPUT_FILENAME);
	    	MidiSystem.write(s, 1, OutputFile);
	    }
	    catch (IOException e)
	    {
	    	return e.getMessage();
	    }
	    return "";
	}
}
