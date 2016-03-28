import java.util.ArrayList;


public class AlgoJazz {

	public static void main(String[] args) {
		AlgoJazz jazz = new AlgoJazz();
		jazz.run();

	}

	public void run()
	{
		try
		{
			//read midi files
			//convert to arff
			//classify with weka
			//create chord sequence
			//output chord sequence
			//ArrayList<ArrayList<Note>> predictedChords = StatisticalChordGenerator.getChords();
			//create melody sequence
			//ArrayList<Integer> melody = RandomMelodyGenerator.getRandomMeasure();
			generator = new StatisticalChordGenerator();
			ArrayList<MusicSection> sections = new ArrayList<MusicSection>();
			ArrayList<ArrayList<Note>> predictedChords = new ArrayList<ArrayList<Note>>();
			ArrayList<Note> melody = new ArrayList<Note>();
		
			for(int i=0;i<Settings.TOTAL_SECTIONS; i++)
				sections.add(buildSection());
		
			for(MusicSection ms : sections)
			{
				predictedChords.addAll(ms.getChords());
				melody.addAll(ms.getMelody());
			}
			//output all
			MidiWriter.writeMusicFromNotes(predictedChords, melody);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private MusicSection buildSection() throws Exception
	{
		
		MusicSection sec = new MusicSection();
		for (int i=0;i<Settings.TOTAL_MEASURES;i++)
		{
			buildMeasure(sec.getChords(),sec.getMelody());
			
		}
		
		return sec;
	}
	
	private void buildMeasure(ArrayList<ArrayList<Note>> chordList, ArrayList<Note> melodyList) throws Exception
	{
		int basePitch=0;
		ArrayList<Note> chord;
		int totalChordTicks = (int) Settings.getDurationFromNoteType(Settings.NoteType.WHOLE);
		while(totalChordTicks > 0)
		{
			if(chordList.size() > 0)
			{
				chord = generator.predictedChordFromChord(chordList.get(chordList.size()-1));
				if(!compareChords(chord, chordList.get(chordList.size()-1)))
				{
					chordList.add(chord);
					totalChordTicks -= chord.get(0).endTickSource;
				}
			}
			else 
			{
				chord =  generator.predictedChordFromRandom();
				chordList.add(chord);
				totalChordTicks -= chord.get(0).endTickSource;
			}
			if (basePitch == 0)
				basePitch = chord.get(chord.size()-1).pitch;
		}
		
		MelodyGenerator mg = new MelodyGenerator();
		Note note;
		int totalMelodyTicks = (int) Settings.getDurationFromNoteType(Settings.NoteType.WHOLE);
		System.out.println("chords generated for measure");
		melodyList.addAll(mg.getMeasure(basePitch, totalMelodyTicks));
	}
	
	private boolean compareChords(ArrayList<Note> chord1, ArrayList<Note> chord2)
	{
		if(chord1.size() != chord2.size())
			return false;
		int i=0;
		for(Note n : chord1)
		{
			if(n.pitch != chord2.get(i).pitch)
				return false;
			i++;
		}
		return true;
	}
	
	StatisticalChordGenerator generator;
}
