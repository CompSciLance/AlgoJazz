import java.util.ArrayList;


public class StatisticalChordGenerator 
{
	public StatisticalChordGenerator() throws Exception
	{
		Initialize();
	}
	
	private void Initialize() throws Exception
	{
		SimpleMidiReader smr= null;
		ArrayList<Note> notes = new ArrayList<Note>();
		for(int i=1;i<=Settings.TOTAL_INPUT_FILES;i++)
		{
			smr = new SimpleMidiReader(Settings.INPUT_FILENAME_BASE+String.valueOf(i)+".mid");
			notes.addAll(smr.getNotes());
		}
		ChordsToArff c = new ChordsToArff();
		originalChords = c.outputToArff(notes);
		classifier = new StatisticalChords(Settings.ARFF_OUTPUT_FILENAME);
	}

	public ArrayList<Note> predictedChordFromChord(ArrayList<Note> pChord) throws Exception 
	{
		ArrayList<Note> chord = new ArrayList<Note>();
		int prediction = classifier.getPredictedIndexFromChord(pChord);
		ArrayList<Note> oldChord = originalChords.get(prediction);
		for(Note oldNote : oldChord)
		{
			Note newNote = new Note(Settings.TIME_RESOLUTION,4);
			newNote.velocity = Settings.VELOCITY;
			newNote.startTickSource = 0;
			newNote.endTickSource = (int) Settings.getDurationFromNoteType(Settings.NoteType.HALF);
			newNote.durationInBeats = oldNote.durationInBeats;
			newNote.ticksPerBeatSource = oldNote.ticksPerBeatSource;
			
			newNote.pitch = oldNote.pitch;
			if(newNote.pitch < 74)
				chord.add(newNote);
		}
		
		return chord;
	}
	
	public ArrayList<Note> predictedChordFromRandom() throws Exception 
	{
		ArrayList<Note> chord = new ArrayList<Note>();
		int prediction = classifier.getPredictedIndexFromRandom();
		ArrayList<Note> oldChord = originalChords.get(prediction);
		for(Note oldNote : oldChord)
		{
			Note newNote = new Note(Settings.TIME_RESOLUTION,4);
			newNote.velocity = Settings.VELOCITY;
			newNote.startTickSource = 0;
			newNote.endTickSource = (int) Settings.getDurationFromNoteType(Settings.NoteType.HALF);
			newNote.durationInBeats = oldNote.durationInBeats;
			newNote.ticksPerBeatSource = oldNote.ticksPerBeatSource;
			
			newNote.pitch = oldNote.pitch;
			chord.add(newNote);
		}
		
		return chord;
	}
	
	StatisticalChords classifier;
	ArrayList<ArrayList<Note>> originalChords;
	
	
	
	public static ArrayList<ArrayList<Note>> getChords() 
	{
		ArrayList<ArrayList<Note>> predictedChords = new ArrayList<ArrayList<Note>>();
		SimpleMidiReader smr = new SimpleMidiReader("input.mid");
		ArrayList<Note> notes = smr.getNotes();
		ChordsToArff c = new ChordsToArff();
		ArrayList<ArrayList<Note>> chords = c.outputToArff(notes);
		StatisticalChords sc = new StatisticalChords();
		try
		{
			ArrayList<Integer> predictions = sc.start();
			
			int velocity = Settings.VELOCITY;
			int duration = Settings.DUARTION;
			int currentTime=0;
			for(Integer p : predictions)
			{
				ArrayList<Note> oldChord = chords.get(p);
				ArrayList<Note> newChord = new ArrayList<Note>();
				for(Note oldNote : oldChord)
				{
					Note newNote = new Note(4,4);
					newNote.velocity = velocity;
					newNote.startTickSource = currentTime;
					newNote.endTickSource = currentTime + duration;
					newNote.durationInBeats = oldNote.durationInBeats;
					newNote.ticksPerBeatSource = oldNote.ticksPerBeatSource;
					
					newNote.pitch = oldNote.pitch;
					newChord.add(newNote);
				}
				predictedChords.add(newChord);
				currentTime += duration;
			}
			System.out.println("number of chords: " + predictedChords.size());
			System.out.println(ChordWriter.writeChords(predictedChords));
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return predictedChords;
	}

}
