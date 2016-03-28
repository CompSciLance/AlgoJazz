
import java.util.ArrayList;

import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.rules.ZeroR;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;


public class StatisticalChords 
{
	public StatisticalChords(String arffName) throws Exception
	{
		dataSource = new DataSource(arffName);
		dataInstances = dataSource.getDataSet();
		if( dataInstances.classIndex() == -1 ) dataInstances.setClassIndex(dataInstances.numAttributes() - 1); // make sure we have a "class"
		
		// build the classifier
		// http://weka.sourceforge.net/doc.dev/weka/classifiers/Classifier.html
		// ZeroR pitchClassifier = new ZeroR(); // classify as the mean (if class is numeric) or the mode (if class is nominal) class
		// Classifier classifier = new Ridor();
		dataClassifier = new NaiveBayes();
		dataClassifier.buildClassifier(dataInstances);
	}
	
	public int getPredictedIndexFromChord(ArrayList<Note> chord) throws Exception
	{
		int p1 = chord.get(0).pitch;
		//int p2 = chord.get(1).pitch;
		//int p3 = 0;
		//if(chord.size()>= 3)
		//	p3 = chord.get(2).pitch;
		int p2 = (int)(48+(Math.floor(Math.random()*22)));
		int p3 = (int)(48+(Math.floor(Math.random()*22)));
		
		Instance test = new Instance(6);
		test.setDataset(dataInstances);
		test.setValue(1, p1); // pitch1
		test.setValue(2, p2); // pitch2
		if(p3 > 0)
			test.setValue(3, p3); // pitch3

		int prediction = (int)dataClassifier.classifyInstance(test);
		return prediction;
	}
	
	public int getPredictedIndexFromRandom() throws Exception
	{
		int p1 = (int)(48+(Math.floor(Math.random()*22)));
		int p2 = (int)(48+(Math.floor(Math.random()*22)));
		int p3 = (int)(48+(Math.floor(Math.random()*22)));
		
		
		Instance test = new Instance(6);
		test.setDataset(dataInstances);
		test.setValue(1, p1); // pitch1
		test.setValue(2, p2); // pitch2
		test.setValue(3, p3); // pitch3

		int prediction = (int)dataClassifier.classifyInstance(test);
		return prediction;
	}
	
	DataSource dataSource;
	Instances dataInstances;
	Classifier dataClassifier;
	
	
	public StatisticalChords() 
	{
		
	
	}
	public ArrayList<Integer> start() throws Exception
	{
	// load the data for classification
			DataSource source = new DataSource("chords.arff");
			Instances data = source.getDataSet();
			if( data.classIndex() == -1 ) data.setClassIndex(data.numAttributes() - 1); // make sure we have a "class"
			
			// build the classifier
			// http://weka.sourceforge.net/doc.dev/weka/classifiers/Classifier.html
			//ZeroR pitchClassifier = new ZeroR(); // classify as the mean (if class is numeric) or the mode (if class is nominal) class
			//Classifier classifier = new Ridor();
			Classifier classifier = new NaiveBayes();
			classifier.buildClassifier(data);
			
			// http://weka.8497.n7.nabble.com/Predicting-in-java-td27363.html
			// http://weka.sourceforge.net/doc.dev/weka/core/Instance.html
			
			String[] pitches = {"C","D","E","F","G","A","B"};
			String[] chords = {"C-Major","D-minor","E-minor","F-Major","G-Major","A-minor","B-diminished"};
			ArrayList<Integer> predictions = new ArrayList<Integer>();
			// classify some new data
			for( int i = 0; i < 24; i++ )
			{
				int p1 = (int)(48+(Math.floor(Math.random()*22)));
				int p2 = (int)(48+(Math.floor(Math.random()*22)));
				int p3 = (int)(48+(Math.floor(Math.random()*22)));
				
				
				Instance test = new Instance(6);
				test.setDataset(data);
				test.setValue(1, p1); // pitch1
				test.setValue(2, p2); // pitch2
				test.setValue(3, p3); // pitch3

				int prediction = (int)classifier.classifyInstance(test);
				predictions.add(prediction);
				System.out.println("class prediction for " + p1 + " and " + p2 + ": " + prediction);		
				
			}
			return predictions;
	}
}
