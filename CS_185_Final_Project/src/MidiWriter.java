import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;


public class MidiWriter 
{
	public static String writeMusicFromNotes(ArrayList<ArrayList<Note>> chordList, ArrayList<Note> melodyList)
	{
		Sequence seq = null;
		try
		{
			seq = new Sequence(Sequence.PPQ, Settings.TIME_RESOLUTION); // create a new sequence
		}
		catch (InvalidMidiDataException e)
		{
			return e.getMessage();
		}
		Track chordTtrack = seq.createTrack();
		Track melodyTrack = seq.createTrack();
		int currentMelodyTick = 0;
		int currentChordTick = 0;
		for (ArrayList<Note> chord : chordList)
		{
			for (Note n : chord)
			{
				MidiEvent event = MidiUtil.CreateNoteEvent(ShortMessage.NOTE_ON, (n.pitch), Settings.VELOCITY, currentChordTick);
				MidiEvent event2 = MidiUtil.CreateNoteEvent(ShortMessage.NOTE_OFF, (n.pitch), 0,currentChordTick + Settings.getDurationFromNoteType(Settings.NoteType.HALF));
				chordTtrack.add(event);
				chordTtrack.add(event2);
			}
			currentChordTick += Settings.getDurationFromNoteType(Settings.NoteType.HALF);
		}
		
		
		for( Note n : melodyList )
		{
			
			
			if( n.pitch > -1 ) // -1 = rest
			{
				melodyTrack.add(SimpleMidiWriter.CreateNoteEvent(ShortMessage.NOTE_ON, n.pitch, Settings.VELOCITY, currentMelodyTick));
				currentMelodyTick += n.endTickSource;
				melodyTrack.add(SimpleMidiWriter.CreateNoteEvent(ShortMessage.NOTE_OFF, n.pitch, 0, currentMelodyTick));
			}
			else
				currentMelodyTick += n.endTickSource;
		}
		
	    try
	    {
	    	
	    	File OutputFile = new File(Settings.FINAL_OUTPUT_FILENAME);
	    	MidiSystem.write(seq, 1, OutputFile);
	    	
	    }
	    catch (IOException e)
	    {
	    	return e.getMessage();
	    }
	    return "";
	}
	
	public static String writeMusic(ArrayList<ArrayList<Note>> chordList, ArrayList<Integer> melodyPitches)
	{
		Sequence seq = null;
		try
		{
			seq = new Sequence(Sequence.PPQ, Settings.TIME_RESOLUTION); // create a new sequence
		}
		catch (InvalidMidiDataException e)
		{
			return e.getMessage();
		}
		Track chordTtrack = seq.createTrack();
		Track melodyTrack = seq.createTrack();
		int currentMelodyTick = 0;
		int currentChordTick = 0;
		for (ArrayList<Note> chord : chordList)
		{
			for (Note n : chord)
			{
				MidiEvent event = MidiUtil.CreateNoteEvent(ShortMessage.NOTE_ON, (n.pitch), Settings.VELOCITY, currentChordTick);
				MidiEvent event2 = MidiUtil.CreateNoteEvent(ShortMessage.NOTE_OFF, (n.pitch), 0,currentChordTick + Settings.getDurationFromNoteType(Settings.NoteType.HALF));
				chordTtrack.add(event);
				chordTtrack.add(event2);
			}
			currentChordTick += Settings.getDurationFromNoteType(Settings.NoteType.HALF);
		}
		
		
		for( Integer i : melodyPitches )
		{
			
			
			if( i > -1 ) // -1 = rest
			{
				melodyTrack.add(SimpleMidiWriter.CreateNoteEvent(ShortMessage.NOTE_ON, i, Settings.VELOCITY, currentMelodyTick));
				currentMelodyTick += Settings.getDurationFromNoteType(Settings.NoteType.QUARTER);
				melodyTrack.add(SimpleMidiWriter.CreateNoteEvent(ShortMessage.NOTE_OFF, i, 0, currentMelodyTick));
			}	
		}
		
	    try
	    {
	    	
	    	File OutputFile = new File(Settings.FINAL_OUTPUT_FILENAME);
	    	MidiSystem.write(seq, 1, OutputFile);
	    	
	    }
	    catch (IOException e)
	    {
	    	return e.getMessage();
	    }
	    return "";
	}
}
