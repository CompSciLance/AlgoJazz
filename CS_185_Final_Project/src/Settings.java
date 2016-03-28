
public class Settings 
{
	public static long getDurationFromNoteType(NoteType note)
	{
		switch(note)
		{
		case SIXTEENTH:
			return  (long) (.25 * Settings.TIME_RESOLUTION);
		case EIGHT:
			return  (long) (.5 * Settings.TIME_RESOLUTION);
		case QUARTER:
			return  (long) (1.0 * Settings.TIME_RESOLUTION);
		case DOTTED_QUARTER:
			return  (long) (1.5 * Settings.TIME_RESOLUTION);
		case HALF:
			return  (long) (2.0 * Settings.TIME_RESOLUTION);
		case DOTTED_HALF:
			return  (long) (3.0 * Settings.TIME_RESOLUTION);
		case WHOLE:
			return  (long) (4.0 * Settings.TIME_RESOLUTION);	
		default:
			return Settings.TIME_RESOLUTION;
		}
	}
	
	public static enum NoteType
	{
		SIXTEENTH, EIGHT, QUARTER, DOTTED_QUARTER, HALF, DOTTED_HALF, WHOLE
	}
	
	public static final int VELOCITY = 96; //orig 96
	public static final int DUARTION = 240; //orig 240
	public static final int TIME_RESOLUTION = 120; //orig 120
	public static final int TOTAL_SECTIONS = 2;
	public static final int TOTAL_MEASURES = 6;
	public static final int BEATS_PER_MEASURE = 4;
	public static final int TOTAL_INPUT_FILES = 6;
	
	
	public static final String INPUT_FILENAME_BASE = "input";
	public static final String FINAL_OUTPUT_FILENAME = "output.mid";
	public static final String CHORDS_OUTPUT_FILENAME = "algo_chords.mid";
	public static final String MELODY_OUTPUT_FILENAME = "algo_melody.mid";
	
	
	
	public static final String ARFF_HEADER = "% Chords\n%";
	public static final String ARFF_RELATION = "@RELATION chords";
	public static final String ARFF_DATA_HEADER = "@DATA";
	public static final String ATTRIB_NUM_NOTES = "@ATTRIBUTE NumberOfNotes NUMERIC";
	public static final String ATTRIB_PTICH_1 = "@ATTRIBUTE pitch1 NUMERIC";
	public static final String ATTRIB_PTICH_2 = "@ATTRIBUTE pitch2 NUMERIC";
	public static final String ATTRIB_PTICH_3 = "@ATTRIBUTE pitch3 NUMERIC";
	public static final String ATTRIB_PTICH_4 = "@ATTRIBUTE pitch4 NUMERIC";
	public static final String ATTRIB_PTICH_5 = "@ATTRIBUTE pitch5 NUMERIC";
	public static final String ATTRIB_CLASS = "@ATTRIBUTE class {";
	public static final String ARFF_OUTPUT_FILENAME = "chords.arff";
	public static final int MAX_NUM_OF_CHORDS = 5;
}
