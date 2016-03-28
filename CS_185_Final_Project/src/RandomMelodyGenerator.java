import java.util.ArrayList;
import java.util.Random;


public class RandomMelodyGenerator 
{
	public static ArrayList<Integer> getRandomMeasure()
	{
		ArrayList<Note> result = new ArrayList<Note>();
		// set up lists of pitches and durations
				ArrayList<Integer> pitches = new ArrayList<Integer>();
				ArrayList<Integer> durations = new ArrayList<Integer>();
				Random randomGenerator = new Random();
				
				//initialize "setup" variables.
				//Only notes in the C Major scale will be chosen
				int maxPitchStep = 4;
				int maxStepRange = 24;
				int middleC = 60;
				int randomMiddleCBooster = 72;
				int longestDuration = 3;
				int[] wholeSteps = new int[]{0,2,4,5,7,9,11, 12, 14, 16, 17, 19, 21, 23, 24};

				// set previousNote so that there is a starting point
				int previousNote = randomGenerator.nextInt(maxStepRange);
				previousNote += randomMiddleCBooster;
				int note = 0;
				
				// create a bunch of each
				for( int i = 0; i < 48; i++ )
				{
					int diff = middleC;
					//Constrain the note generations so that the next note is no more than maxPitchStep steps away from previous note.
					while(diff > maxPitchStep)
					{
						note = randomGenerator.nextInt(maxStepRange);
						for(int w=0; w<wholeSteps.length; w++)
						{
							if (wholeSteps[w] == note)
							{
								note += randomMiddleCBooster;
								diff = note - previousNote;
								if (diff < 0)
									diff = diff * (-1);
							}
						}
					}
					pitches.add(note);
					previousNote = note;
					
					// note that durations are chosen from an array of 8 preset durations for SimpleMidiWriter
					int duration = randomGenerator.nextInt(longestDuration);
					durations.add(duration);

				}
				
				// save them to a midi file
				SimpleMidiWriter.saveToMidiFile(Settings.MELODY_OUTPUT_FILENAME, pitches, durations);	
				System.out.println("Done.");
		
		
		return pitches;
	}
}
