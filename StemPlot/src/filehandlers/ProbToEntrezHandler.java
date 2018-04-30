package filehandlers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.math3.stat.descriptive.moment.GeometricMean;

public class ProbToEntrezHandler {

	public static Object[] loadEnzymeSynonymous(String file) throws Exception {
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		
		ArrayList<String> tempMaster = new ArrayList<String>();
		ArrayList<String[]> tempPair = new ArrayList<String[]>();
		
		while(br.ready())
		{
			String[] ln = br.readLine().split("\t");
			String enz = ln[0];
			
			tempMaster.add(enz);
			
			for(int i=1;i<ln.length;i++)
			{
				tempPair.add(new String[]{enz,ln[i]});
				tempMaster.add(ln[i]);
			}
		}
		
		br.close();
		fr.close();
		
		String[] res1 = new String[tempMaster.size()];
		
		for(int i=0;i<tempMaster.size();i++)
		{
			res1[i] = tempMaster.get(i);
		}
		
		Arrays.sort(res1);
		
		String[][] res2 = new String[tempPair.size()][];
		
		for(int i=0;i<tempPair.size();i++)
		{
			res2[i] = tempPair.get(i);
		}
		
		return new Object[]{res1,res2};
	}
	
	public static Object[] loadProbToEntrezTable(String referenceFile) throws Exception {
		FileReader fr = new FileReader(referenceFile);
		BufferedReader br = new BufferedReader(fr);
			
		ArrayList<String[]> temp = new ArrayList<String[]>();
		ArrayList<String> tempEntrezIdsList = new ArrayList<String>();
		
		while(br.ready())
		{
			String[] ln = br.readLine().split("\\s+");
				
			String probId = ln[0];
			String entrezId = ln[1];
			
			temp.add(new String[]{probId,entrezId});
			
			if(!tempEntrezIdsList.contains(entrezId)) tempEntrezIdsList.add(entrezId);
		}
		
		String[] entrezIdsList = new String[tempEntrezIdsList.size()];
		
		for(int i=0;i<tempEntrezIdsList.size();i++) entrezIdsList[i] = tempEntrezIdsList.get(i);
		
		br.close();
		fr.close();
		
		String[][] probNEntrez = getTheArray(temp);
		
		Arrays.sort(entrezIdsList);
		
		return new Object[]{probNEntrez,entrezIdsList};
	}

	
	protected static String[][] getTheArray(ArrayList<String[]> temp)
	{
		String[][] res = new String[temp.size()][];
		
		for(int i=0;i<temp.size();i++)
		{
			res[i] = temp.get(i);
		}
		
		return res;
	}
	
	
	public static void creatProcessedFile(String[][] probNEntrez, String fileToProcess, String processedDataFile, String[] entrezIdsList) throws Exception {
		
		FileReader fr = new FileReader(fileToProcess);
		BufferedReader br = new BufferedReader(fr);
		
		String[] allExperimentsNames = br.readLine().replace("\"", "").split("\\s+");
		
		ArrayList<String> tempProbs = new ArrayList<String>();
		ArrayList<double[]> tempValues = new ArrayList<double[]>();
		
		while(br.ready())
		{
			String[] line = br.readLine().replace("\"", "").split("\\s+");
			
			double[] data = new double[line.length-1];
			
			for(int i=1;i<line.length;i++)
			{
				
				
				data[i-1] = new Double(line[i]).doubleValue();
			}
			
			String prob = line[0];
			
			tempProbs.add(prob);
			tempValues.add(data);
		}
		
		br.close();
		fr.close();
		
		String[] probs = new String[tempProbs.size()];
		
		for(int i=0;i<tempProbs.size();i++)
		{
			probs[i] = tempProbs.get(i);
		}
		
		double[][] values = new double[tempValues.size()][];
		
		for(int i=0;i<tempValues.size();i++)
		{
			values[i] = tempValues.get(i);
		}
		
		double[][] entrezIdsMatrix = new double[entrezIdsList.length][allExperimentsNames.length];
		
		for(int a=0;a<entrezIdsMatrix.length;a++)
		{
			for(int b=0;b<entrezIdsMatrix[a].length;b++)
			{
				entrezIdsMatrix[a][b] = 0;
			}
		}
		
		for(int i=0;i<entrezIdsList.length;i++)
		{
			int tot = 0;
			
			for(int a=0;a<probNEntrez.length;a++)
			{
				boolean foundToBeEntrez = probNEntrez[a][1].equals(entrezIdsList[i]);
				
				if(foundToBeEntrez)
				{
					//it found a prob (probNEntrez[a][0]) that corresponds to an entrez
					
					boolean foundProbLocation = false;
					
					for(int b=0;b<probs.length && !foundProbLocation;b++)
					{
						if(probs[b].equals(probNEntrez[a][0]))
						{
							foundProbLocation = true;
							tot++;
							
							for(int y=0;y<values[b].length;y++)
							{
								entrezIdsMatrix[i][y] += values[b][y];
							}
							
						}
					}
				}
			}
			
			if(tot>1) //averange only if more than one was found
			{
				for(int y=0;y<entrezIdsMatrix[i].length;y++)
				{
					entrezIdsMatrix[i][y] += entrezIdsMatrix[i][y]/tot;
				}
			}
			
		}
		
		FileWriter fw = new FileWriter(processedDataFile);
		BufferedWriter bw = new BufferedWriter(fw);

		String lin = "";
		
		for(int i=0;i<allExperimentsNames.length;i++)
		{
			if(i>0) lin+="\t";
			lin+=allExperimentsNames[i];
		}
		
		lin+="\n";
		
		bw.write(lin);
		
		for(int i=0;i<entrezIdsList.length;i++)
		{
			lin = entrezIdsList[i];
			
			for(int b=0;b<entrezIdsMatrix[i].length;b++)
			{
				lin+="\t"+entrezIdsMatrix[i][b];
			}
			
			lin+="\n";
			
			bw.write(lin);
		}
		
		bw.close();
		fw.close();
		
	}
	
//	returns the data that normaly goes to the processedFile.cvs file after the normalized.csv file is processed to convert prob ids into entrez values	
	public static Object[] getProcessedMatrix(String[][] probNEntrez, String fileToProcess, String[] entrezIdsList) throws Exception { 
		
		FileReader fr = new FileReader(fileToProcess);
		BufferedReader br = new BufferedReader(fr);
		
		String[] allExperimentsNames = br.readLine().replace("\"", "").split("\\s+");
		
		ArrayList<String> tempProbs = new ArrayList<String>();
		ArrayList<double[]> tempValues = new ArrayList<double[]>();
		
		while(br.ready())
		{
			String[] line = br.readLine().replace("\"", "").split("\\s+");
			
			double[] data = new double[line.length-1];
			
			for(int i=1;i<line.length;i++)
			{
				
				
				data[i-1] = new Double(line[i]).doubleValue();
			}
			
			String prob = line[0];
			
			tempProbs.add(prob);
			tempValues.add(data);
		}
		
		br.close();
		fr.close();
		
		String[] probs = new String[tempProbs.size()];
		
		for(int i=0;i<tempProbs.size();i++)
		{
			probs[i] = tempProbs.get(i);
		}
		
		double[][] values = new double[tempValues.size()][];
		
		for(int i=0;i<tempValues.size();i++)
		{
			values[i] = tempValues.get(i);
		}
		
		double[][] entrezIdsMatrix = new double[entrezIdsList.length][allExperimentsNames.length];
		
		for(int a=0;a<entrezIdsMatrix.length;a++)
		{
			for(int b=0;b<entrezIdsMatrix[a].length;b++)
			{
				entrezIdsMatrix[a][b] = 0;
			}
		}
		
		for(int i=0;i<entrezIdsList.length;i++)
		{
			int tot = 0;
			
			for(int a=0;a<probNEntrez.length;a++)
			{
				boolean foundToBeEntrez = probNEntrez[a][1].equals(entrezIdsList[i]);
				
				if(foundToBeEntrez)
				{
					//it found a prob (probNEntrez[a][0]) that corresponds to an entrez
					
					boolean foundProbLocation = false;
					
					for(int b=0;b<probs.length && !foundProbLocation;b++)
					{
						if(probs[b].equals(probNEntrez[a][0]))
						{
							foundProbLocation = true;
							tot++;
							
							for(int y=0;y<values[b].length;y++)
							{
								entrezIdsMatrix[i][y] += values[b][y];
							}
							
						}
					}
				}
			}
			
			if(tot>1) //averange only if more than one was found
			{
				for(int y=0;y<entrezIdsMatrix[i].length;y++)
				{
					entrezIdsMatrix[i][y] += entrezIdsMatrix[i][y]/tot;
				}
			}
			
		}
		
		
		Object[] res = new Object[2];
		
		res[0] = allExperimentsNames;
		res[1] = entrezIdsMatrix;
		
		return res;
	}
	
	
	public static double[][] getProcessedMatrix(String[][] probNEntrez, 
			double[][] values, String[] allExperimentsNames, String[] probs,			
			String[] entrezIdsList) throws Exception
	{
		double[][] entrezIdsMatrix = new double[entrezIdsList.length][allExperimentsNames.length];
		
		for(int a=0;a<entrezIdsMatrix.length;a++)
		{
			for(int b=0;b<entrezIdsMatrix[a].length;b++)
			{
				entrezIdsMatrix[a][b] = 0;
			}
		}
		
		for(int i=0;i<entrezIdsList.length;i++)
		{
			int tot = 0;
			GeometricMean gm = new GeometricMean();
			for(int a=0;a<probNEntrez.length;a++)
			{
				boolean foundToBeEntrez = probNEntrez[a][1].equals(entrezIdsList[i]);
				
				if(foundToBeEntrez)
				{
					//it found a prob (probNEntrez[a][0]) that corresponds to an entrez
					
					boolean foundProbLocation = false;
					
					for(int b=0;b<probs.length && !foundProbLocation;b++)
					{
						if(probs[b].equals(probNEntrez[a][0]))
						{
							foundProbLocation = true;
							tot++;
							
							for(int y=0;y<values[b].length;y++)
							{
								entrezIdsMatrix[i][y] += values[b][y];
								gm.increment(values[b][y]);
							}
							
						}
					}
				}
			}
			
			if(tot>1) //averange only if more than one was found
			{
				for(int y=0;y<entrezIdsMatrix[i].length;y++)
				{
//					entrezIdsMatrix[i][y] += entrezIdsMatrix[i][y]/tot;
					entrezIdsMatrix[i][y] = gm.getResult();
				}
			}
			
		}
		
		return entrezIdsMatrix;
	}
	
	
	
	public static String[][] getDoubleListFromFile(String file) throws Exception {
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		
		ArrayList<String[]> temp = new ArrayList<String[]>();
		
		while(br.ready())
		{
			String[] lin = br.readLine().split("\t");
			temp.add(lin);
		}
		
		br.close();
		fr.close();
		
		return temp.toArray(new String[][]{});
	}
	
}
