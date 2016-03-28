import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;


public class ChordsToArff {


	public ArrayList<ArrayList<Note>> outputToArff(ArrayList<Note> notes)
	{
		ArrayList<ArrayList<Note>> chords = null;
		File outFile;
		try
		{
			outFile = new File(Settings.ARFF_OUTPUT_FILENAME);
			PrintWriter writer = new PrintWriter(outFile);
			
			//create file header
			writer.println(Settings.ARFF_HEADER);
			writer.println();
			//write relation
			writer.println(Settings.ARFF_RELATION);
			writer.println();
			//write attribute number of notes
			writer.println(Settings.ATTRIB_NUM_NOTES);
			//write attribute pitch 1
			writer.println(Settings.ATTRIB_PTICH_1);
			//write attribute pitch 2
			writer.println(Settings.ATTRIB_PTICH_2);
			//write attribute pitch 3
			writer.println(Settings.ATTRIB_PTICH_3);
			//write attribute pitch 4
			writer.println(Settings.ATTRIB_PTICH_4);
			//write attribute pitch 5
			writer.println(Settings.ATTRIB_PTICH_5);
			writer.print(Settings.ATTRIB_CLASS);
			chords = getChords(notes);
			for(ArrayList<Note> l : chords)
			{
				writer.print("Chord" + chords.indexOf(l));
				if(chords.indexOf(l)<chords.size()-1)
					writer.print(", ");
				
			}
			writer.println("}\n");
			//write data header
			writer.println(Settings.ARFF_DATA_HEADER);
			
			for(ArrayList<Note> l : chords)
			{
				
				writer.print(l.size()+",");
				for(int i=0;i< Settings.MAX_NUM_OF_CHORDS; i++)
				{
					if(i<l.size())
						writer.print(l.get(i).pitch);
					else
						writer.print("?");
					//if(i < 4)
						writer.print(",");
				}
				writer.print("Chord" + chords.indexOf(l));
				writer.println();
			}
			writer.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return chords;
	}
	
	private ArrayList<ArrayList<Note>> getChords (ArrayList<Note> notes)
	{
		ArrayList<ArrayList<Note>> chords = new ArrayList<ArrayList<Note>>();
		//ArrayList<Note> initial = new ArrayList<Note>();
		//chords.add(initial);
		ArrayList<Note> chord = new ArrayList<Note>();
		for (Note n : notes)
		{
			/*boolean noteAdded = false;
			for(ArrayList<Note> l :chords)
			{
				if(l.size()==0 || n.compareTo(l.get(0))==0)
				{
					l.add(n);
					noteAdded = true;
				}
			}
			if (!noteAdded)
			{
				ArrayList<Note> newList = new ArrayList<Note>();
				newList.add(n);
				chords.add(newList);
			}*/
			if(chord.size()>0 && n.startTickSource == chord.get(0).startTickSource)
				chord.add(n);
			else 
			{
				if(chord.size()>0)
					chords.add(chord);
				chord = new ArrayList<Note>();
				chord.add(n);
			}
		}
		for(int i=0;i<chords.size();i++)
		{
			if (chords.get(i).size()<3)
				chords.remove(i);
		}
		return chords;
	}
}
