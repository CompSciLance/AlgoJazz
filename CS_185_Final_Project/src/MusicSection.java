import java.util.ArrayList;


public class MusicSection 
{
	public ArrayList<ArrayList<Note>> getChords()
	{
		return chordList;
	}
	
	public void addChord(ArrayList<Note> chord)
	{
		chordList.add(chord);
	}
	
	public ArrayList<Note> getMelody()
	{
		return melodyList;
	}
	
	public void addMelodySingleNote(Note note)
	{
		melodyList.add(note);
	}
	
	public void addMelody(ArrayList<Note> notes)
	{
		melodyList.addAll(notes);
	}
	
	private ArrayList<ArrayList<Note>> chordList = new ArrayList<ArrayList<Note>>();
	private ArrayList<Note> melodyList = new ArrayList<Note>();
}
