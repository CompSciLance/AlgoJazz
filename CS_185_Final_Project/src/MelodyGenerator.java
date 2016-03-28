import java.util.ArrayList;


public class MelodyGenerator 
{
	public ArrayList<Note> getMeasure(int basePitch, int ticks)
	{
		ArrayList<Note> finalMeasure = null;
		int score = 0;
		int counter = 0;
		generateInitialMeasureList(basePitch, ticks);
		while (finalMeasure == null)
		{
			ArrayList<Integer> removeIndexes = new ArrayList<Integer>();
			for(ArrayList<Note> measure : measures)
			{
				score = testMeasure(measure,basePitch);
				System.out.println("Score: "+score);
				if((counter >= requiredGenerations) && (score >= threshold))
				{
					finalMeasure = measure;
					break;
				}
				if(score < minimumScore)
					removeIndexes.add(measures.indexOf(measure));
				else
					evolveMeasure(measure, basePitch);
			}
			
			for(Integer i : removeIndexes)
			{
				measures.remove(i);
				measures.add(i,generateMeasure(basePitch, ticks));
			}
			counter++;
		}
		return finalMeasure;
	}

	private int testMeasure(ArrayList<Note> measure, int basePitch)
	{
		int score = 0;
		
		//5 pts for every note to encourage more notes
		score += 2*measure.size();
		
		//encourage notes to not be spread way apart
		if(maxDistance(measure) < 4)
			score += 25;
		else score -= 10;
		
		//encourage a bit of diversity
		score += totalDistance(measure);
		
		//encourage being in scale
		if(inScale(measure, basePitch))
			score += 10;
		
		if(repeatedNote(measure))
			score += 10;
		
		if(inSweetRange(measure))
			score += 10;
		
		if(outOfRange(measure))
			score -= 100;
		
		return score;
	}
	
	private boolean inSweetRange(ArrayList<Note> measure)
	{
		for(Note n : measure)
			if (n.pitch < 67 || n.pitch > 80)
				return false;
		return true;
	}
	
	private boolean repeatedNote(ArrayList<Note> measure)
	{
		for(int i=0;i<measure.size()-1;i++)
			if (measure.get(i).pitch == measure.get(i+1).pitch)
				return true;
		return false;
	}
	
	private boolean outOfRange(ArrayList<Note> measure)
	{	
		for(Note n : measure)
			if(n.pitch > 100 || n.pitch < -1)
				return true;
		
		return false;
	}
	
	private boolean inScale(ArrayList<Note> measure, int basePitch)
	{
		boolean result = true;
		boolean found = false;
		for (Note n : measure)
		{
			for(int i=0; i < wholeSteps.length;i++)
				if(wholeSteps[i]==(n.pitch-basePitch-7))
					found = true;
			if(!found)
			{
				result = false;
				break;
			}
		}
		return result;
	}
	
	private int totalDistance(ArrayList<Note> measure)
	{
		int distance = 0;
		
		for (int i=0;i<measure.size()-1;i++)
			if(measure.get(i+1).pitch != -1 && measure.get(i+1).pitch != -1)
				distance += Math.abs(measure.get(i+1).pitch - measure.get(i+1).pitch);
			else
				distance += 8;
		
		return distance;
	}
	
	private int maxDistance(ArrayList<Note> measure)
	{
		int distance = 100;
		int step = 0;
		for (int i=0;i<measure.size()-1;i++)
		{
			step = Math.abs(measure.get(i+1).pitch - measure.get(i+1).pitch);
			if (step < distance)
				distance = step;
		}
		
		return distance;
	}
	
	private void evolveMeasure(ArrayList<Note> measure, int basePitch)
	{
		int rand1 = (int) Math.floor(Math.random()*(wholeSteps.length/2));
		int rand2 = (int) Math.floor(Math.random()*(wholeSteps.length/2));
		int rand = (int) Math.floor(Math.random()*2);
		
		if (rand >1)
		{
			measure.get(0).pitch -= wholeSteps[rand1];
			measure.get(measure.size()-1).pitch += wholeSteps[rand1];
		}
		else
		{
			measure.get(0).pitch += wholeSteps[rand1];
			measure.get(measure.size()-1).pitch -= wholeSteps[rand1];
		}
		
		//add a rest
		measure.get(1).pitch = -1;
		
		//add a note
		addNoteToMeasure(measure, basePitch);
		
		//change another value
		if(measure.size() > 2)
			measure.get(2).pitch += 4;
	}
	
	private void addNoteToMeasure(ArrayList<Note> measure, int basePitch)
	{
		int i = -1;
		for(Note n : measure)
			if(n.endTickSource >= Settings.getDurationFromNoteType(Settings.NoteType.QUARTER))
				i = measure.indexOf(n);
		
		if(i<0) return;
		
		measure.get(i).endTickSource /= 2;
		Note note = generateNote(basePitch);
		note.endTickSource = measure.get(i).endTickSource;
		measure.add(note);
	}
	
	
	private void generateInitialMeasureList(int basePitch, int ticks)
	{
		ArrayList<ArrayList<Note>> list = new ArrayList<ArrayList<Note>>();
		for(int i=0;i<listSize;i++)
			list.add(generateMeasure(basePitch, ticks));
		measures = list;
	}
	
	private ArrayList<Note> generateMeasure(int basePitch, int ticks)
	{
		ArrayList<Note> list = new ArrayList<Note>();
		Note note;
		while(ticks > 0)
		{
			note = generateNote(basePitch);
			if (ticks - note.endTickSource >= 0)
			{
				list.add(note);
				ticks -= note.endTickSource;
				
			}
		}
		return list;
	}
	
	private Note generateNote(int basePitch)
	{
		Note note = new Note(Settings.TIME_RESOLUTION,4);
		int pitch = basePitch +7+ wholeSteps[(int) Math.floor(Math.random()*wholeSteps.length)];
		note.pitch = pitch;
		int duration = (int) Settings.getDurationFromNoteType(Settings.NoteType.values()[(int) Math.floor(Math.random()*6)]);
		note.setDuration(0, duration);
		note.velocity = Settings.VELOCITY;
		return note;
	}
	
	private int threshold = 60;
	private int minimumScore = 30;
	private int requiredGenerations = 10;
	private ArrayList<ArrayList<Note>> measures;
	private int listSize = 100;
	//int[] wholeSteps = new int[]{0,-2,-4,-5,-7,-9,-11,-12,-14,-16,-17,-19,-21,-23,-24,2,4,5,7,9,11,12,14,16,17,19,21,23,24};
	int[] wholeSteps = new int[]{0,2,4,5,7,9,11,12,14,16,17,19,21,23,24};
}
