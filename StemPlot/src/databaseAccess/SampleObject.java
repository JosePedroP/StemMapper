package databaseAccess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

import beans.ColorConverter;

public class SampleObject {

	protected String na = "Undefined";
	
	protected Integer[] rowNames; //this is the entrez
	protected String[] columnNames; //sampleName
	protected double[][] matrix;
	protected double[][] matrixq;
	protected String[] sampleSuperType;
	protected String[] sampleType;
	protected String[] sampleCondition;
	protected String[] sampleWildOrNot;
	protected String[] sampleDBid;
	protected String[] samplecclass;
	
	protected String[] pureStem;
	
	protected String[][] sources;
	
	protected Integer[] typesids;

	//only intilized after the colors are requested
	protected String[][] sampleTypeColorLegend;
	protected String[][] sampleSuperTypeColorLegend;
	protected String[] sampleSuperTypeColors;
	protected String[][] sampleSCLegend;
	
	protected String[][] sampleConditionColorLegend;
	protected String[][] sampleWildOrNotLegend;
	
	protected String[][] pureStemLegend;
	protected String[] pureStemExtra;
	
	protected String[] sampleTypeColor;
	
	protected String[][] classLegend;

	protected String[] surfacemarkers;
	protected String[][] surfacemarkersLegend;
	
	public SampleObject(Integer[] rowNames, double[][] matrix, double[][] matrixq, Sample[] samples) {
		this.rowNames = rowNames;
		this.matrix = matrix;
		this.matrixq = matrixq;

		this.columnNames = new String[samples.length];
		this.sampleType = new String[samples.length];
		this.sampleSuperType = new String[samples.length];
		this.sampleCondition = new String[samples.length];
		this.sampleWildOrNot = new String[samples.length];
		this.sampleDBid = new String[samples.length];
		this.samplecclass = new String[samples.length];
		this.pureStem = new String[samples.length];
		this.surfacemarkers = new String[samples.length];
		
		HashMap<String,ArrayList<String>[]> temphash = new HashMap<String,ArrayList<String>[]>();
		int expsz = 0;
		
		
//		
		
		ArrayList<Integer> ttypesids = new ArrayList<Integer>();
		
		
		for(int i=0;i<samples.length;i++)
		{
			this.columnNames[i] = samples[i].getName();
			this.sampleType[i] = samples[i].getSampletype().getType();
			this.sampleSuperType[i] = samples[i].getSampletype().getSupertype().getSupertype();
			
			Integer samid = samples[i].getSampletype().getId();
			
			if(!ttypesids.contains(samid)) ttypesids.add(samid);
			
			Samplecondition sc = samples[i].getSamplecondition();
			
			if(sc==null) this.sampleCondition[i] = na;
			else this.sampleCondition[i] = sc.getConditions();
			if(samples[i].getWildtype().equals("u")) this.sampleWildOrNot[i] = na;
			else if(samples[i].getWildtype().equals("t")) this.sampleWildOrNot[i] = "Wild type";
			else this.sampleWildOrNot[i] = "Treated";
			
			this.sampleDBid[i] = samples[i].getId().toString();
			this.samplecclass[i] = samples[i].getSampletype().getClass_();
			if(this.samplecclass[i].equals("Mature")) this.samplecclass[i] = "Differentiated/Mature";
			
			if(samples[i].getPurestem()!=null && samples[i].getPurestem().equals("Y")) this.pureStem[i] = "Stem";
			else this.pureStem[i] = "Differentiated";
			
			this.surfacemarkers[i] = samples[i].getSurfacemarkers();
			
			String experimenname = samples[i].getExperiment().getName();
			
			ArrayList<String>[] tarr;
			
			if(temphash.containsKey(experimenname))
			{
				tarr = temphash.get(experimenname);
//				temphash.get(experimenname).add(this.sampleType[i]);
			}
			else
			{
				expsz++;
				tarr = new ArrayList[2];
				tarr[0] = new ArrayList<String>();
				tarr[1] = new ArrayList<String>();
//				tarr.add(this.sampleType[i]);
				temphash.put(experimenname, tarr);
			}
			
			if(!tarr[0].contains(this.sampleType[i]))
				tarr[0].add(this.sampleType[i]);
			
			if(!tarr[1].contains(this.sampleSuperType[i]))
				tarr[1].add(this.sampleSuperType[i]);
			
		}
		
		this.typesids = ttypesids.toArray(new Integer[]{});
		
		
		this.sources = new String[expsz][3];
		
		Set<String> sourcesname = temphash.keySet();
		
		int si = 0;
		
		for(String sourcen: sourcesname)
		{
			this.sources[si][0] = sourcen;
			ArrayList<String>[] tarr = temphash.get(sourcen);
			
			String[] ttypes = tarr[0].toArray(new String[]{});
			String[] tsupertypes = tarr[1].toArray(new String[]{});
			
			Arrays.sort(ttypes);
			Arrays.sort(tsupertypes);
			
			StringBuffer sb = new StringBuffer(ttypes[0]);
			
			for(int i=1;i<ttypes.length;i++)
			{
				sb.append(", ");
				sb.append(ttypes[i]);
			}
			
			this.sources[si][1] = sb.toString();
			
			
			sb = new StringBuffer(tsupertypes[0]);
			
			for(int i=1;i<tsupertypes.length;i++)
			{
				sb.append(", ");
				sb.append(tsupertypes[i]);
			}
			
			this.sources[si][2] = sb.toString();

			si++;
		}
		
	}

	public void combineMatrixes(
		Integer[] entrezIdsList, String[] allExperimentsNames, double[][] matrixM2, double[][] matrixqM2
	)
	{
		SampleObject res = null;

		String[] ncolumnNames = new String[allExperimentsNames.length+columnNames.length];
		double[][] nmatrix = new double[rowNames.length][allExperimentsNames.length+columnNames.length];
		double[][] nmatrixq = new double[rowNames.length][allExperimentsNames.length+columnNames.length];
		String[] nsampleSuperType = new String[allExperimentsNames.length+columnNames.length];
		String[] nsampleType = new String[allExperimentsNames.length+columnNames.length];
		String[] nsampleCondition = new String[allExperimentsNames.length+columnNames.length];
		String[] nsampleWildOrNot = new String[allExperimentsNames.length+columnNames.length];
		String[] nsampleDBid = new String[allExperimentsNames.length+columnNames.length];
		String[] nsamplecclass = new String[allExperimentsNames.length+columnNames.length];
		
		int[] pos = new int[rowNames.length];
		
		for(int x=0;x<rowNames.length;x++) pos[x] = -1;
		
		for(int x=0;x<rowNames.length;x++)
		{
			for(int i=0;pos[x]==-1 && i<entrezIdsList.length;i++)
			{
				if(entrezIdsList[i].equals(rowNames[x])) pos[x] = i;
			}
			
			int y=0;
			
//			for(int y2=0;y2<columnNames.length;y2++)
			for(;y<columnNames.length;y++)
			{
				nmatrix[x][y] = this.matrix[x][y];
				nmatrixq[x][y] = this.matrixq[x][y];
				if(x==0)
				{
					ncolumnNames[y] = this.columnNames[y];
					nsampleSuperType[y] = this.sampleSuperType[y];
					nsampleType[y] = this.sampleType[y];
					nsampleCondition[y] = this.sampleCondition[y];
					nsampleWildOrNot[y] = this.sampleWildOrNot[y];
					nsampleDBid[y] = this.sampleDBid[y];
					nsamplecclass[y] = this.samplecclass[y];
				}
//				y++;
			}
			
//			for(;y<allExperimentsNames.length;y++)
			for(int y2=0;y2<allExperimentsNames.length;y2++)
			{
				nmatrix[x][y] = matrixM2[pos[x]][y2];
				nmatrixq[x][y] = matrixqM2[pos[x]][y2];
				if(x==0)
				{
					ncolumnNames[y] = allExperimentsNames[y2];
					nsampleSuperType[y] = "User input";
					nsampleType[y] = "User input";
					nsampleCondition[y] = "User input";
					nsampleWildOrNot[y] = "User input";
					nsampleDBid[y] = "-1";
					nsamplecclass[y] = "User input";
				}
				y++;
			}
			
		}
		
		this.columnNames = ncolumnNames;
		this.matrix = nmatrix;
		this.matrixq = nmatrixq;
		this.sampleSuperType = nsampleSuperType;
		this.sampleType = nsampleType;
		this.sampleCondition = nsampleCondition;
		this.sampleWildOrNot = nsampleWildOrNot;
		this.sampleDBid = nsampleDBid;
		this.samplecclass = nsamplecclass;
	}
	
//	public void combineMatrixesAddInBegining(
//			Integer[] entrezIdsList, String[] allExperimentsNames, double[][] matrixM2
//		)
//		{
//			SampleObject res = null;
//
//			String[] ncolumnNames = new String[allExperimentsNames.length+columnNames.length];
//			double[][] nmatrix = new double[rowNames.length][allExperimentsNames.length+columnNames.length];
//			String[] nsampleSuperType = new String[allExperimentsNames.length+columnNames.length];
//			String[] nsampleType = new String[allExperimentsNames.length+columnNames.length];
//			String[] nsampleCondition = new String[allExperimentsNames.length+columnNames.length];
//			String[] nsampleWildOrNot = new String[allExperimentsNames.length+columnNames.length];
//			
//			int[] pos = new int[rowNames.length];
//			
//			for(int x=0;x<rowNames.length;x++) pos[x] = -1;
//			
//			for(int x=0;x<rowNames.length;x++)
//			{
//				for(int i=0;pos[x]==-1 && i<entrezIdsList.length;i++)
//				{
//					if(entrezIdsList[i].equals(rowNames[x])) pos[x] = i;
//				}
//				
//				int y=0;
//				
//				for(;y<allExperimentsNames.length;y++)
//				{
//					nmatrix[x][y] = matrixM2[pos[x]][y];
//					if(x==0)
//					{
//						ncolumnNames[y] = allExperimentsNames[y];
//						nsampleSuperType[y] = "User input";
//						nsampleType[y] = "User input";
//						nsampleCondition[y] = "User input";
//						nsampleWildOrNot[y] = "User input";
//					}
//				}
//				
//				for(int y2=0;y2<columnNames.length;y2++)
//				{
//					nmatrix[x][y] = this.matrix[x][y2];
//					if(x==0)
//					{
//						ncolumnNames[y] = this.columnNames[y2];
//						nsampleSuperType[y] = this.sampleSuperType[y2];
//						nsampleType[y] = this.sampleType[y2];
//						nsampleCondition[y] = this.sampleCondition[y2];
//						nsampleWildOrNot[y] = this.sampleWildOrNot[y2];
//					}
//					y++;
//				}
//			}
//			
//			this.columnNames = ncolumnNames;
//			this.matrix = nmatrix;
//			this.sampleSuperType = nsampleSuperType;
//			this.sampleType = nsampleType;
//			this.sampleCondition = nsampleCondition;
//			this.sampleWildOrNot = nsampleWildOrNot;
//		}
	
	public Integer[] getRowNames() {
		return rowNames;
	}

	public String[] getColumnNames() {
		return columnNames;
	}

	public double[][] getMatrix() {
		return matrix;
	}

	public double[][] getMatrixq() {
		return matrixq;
	}

	public String[] getSampleType() {
		return sampleType;
	}
	
	public String[] getSampleColor(String[] samplename)
	{
		String[] res = new String[samplename.length];
		
		int y = 0;
		
		for(String sam: samplename)
		{
			res[y] = getSampleColor(sam);
			y++;
		}
		
		return res;
	}
	
	public String getSampleColor(String samplename)
	{
		String res = null;
		
		for(int i=0;i<sampleType.length && res==null;i++)
		{
			if(this.columnNames[i].equals(samplename))
			{
				res = this.sampleTypeColor[i];
			}
		}
		
		return res;
	}
	
	public String[] getSampleColorNType(String samplename)
	{
		String res[] = new String[3];
		
		boolean found = false;
		
		for(int i=0;i<sampleType.length && !found;i++)
		{
			if(this.columnNames[i].equals(samplename))
			{
				res[0] = this.sampleTypeColor[i];
				res[1] = this.sampleType[i];
				res[2] = this.sampleSuperType[i];
				found = true;
			}
		}
		
		return res;
	}
	
	public String[] getTypeColors()
	{

		String[] res = new String[sampleType.length];
		this.sampleSuperTypeColors = new String[sampleType.length]; 
//		this.sampleTypeColorLegend = new String[sampleType.length][2];
		
		HashMap<String,int[]> gradref = new HashMap<String,int[]>();
		HashMap<String,Integer> assignedColor = new HashMap<String,Integer>();
		
		HashMap<String,ArrayList<String>> templegend = new HashMap<String,ArrayList<String>>();
		HashMap<String,ArrayList<String>> templegen2 = new HashMap<String,ArrayList<String>>();
		
		int maxx = 0;
		
		int nleg = 0;
		
		for(int i=0;i<sampleType.length;i++)
		{
			int x = 0, y = 0;
			
			int yoption = 0;
			
			if(gradref.containsKey(sampleSuperType[i]))
			{
				int[] cord = gradref.get(sampleSuperType[i]);
				x = cord[0];
				int maxy = cord[1];
				
				if(assignedColor.containsKey(sampleType[i]))
				{
					y = assignedColor.get(sampleType[i]).intValue();
					yoption = 1;
				}
				else
				{
					maxy++;
					y = maxy;
					assignedColor.put(sampleType[i], new Integer(maxy));
					templegend.get(sampleSuperType[i]).add(sampleType[i]);
					templegen2.get(sampleSuperType[i]).add(this.samplecclass[i]);
					nleg++;
					yoption = 2;
					gradref.put(sampleSuperType[i], new int[]{x,maxy});
				}
			}
			else
			{
				x = maxx;
				maxx++;
				gradref.put(sampleSuperType[i], new int[]{x,0}); //second is maxy
				assignedColor.put(sampleType[i], new Integer(0));
				
				ArrayList<String> temp = new ArrayList<String>();
				temp.add(sampleType[i]);
				templegend.put(sampleSuperType[i], temp);
				
				ArrayList<String> temp2 = new ArrayList<String>();
				temp2.add(this.samplecclass[i]);
				templegen2.put(sampleSuperType[i], temp2);
				
				nleg++;
			}
			
			res[i] = ColorConverter.getGradientColor(x,y);
			this.sampleSuperTypeColors[i] = ColorConverter.getGradientColor(x,0);
		}

//		this.sampleTypeColorLegend = (new ArrayList<String[]>()).toArray(new String[][]{}); //legend fixing now
		
		
//		Iterator keys = templegend.keySet().iterator();
		Object[] keys = templegend.keySet().toArray();
		
		this.sampleTypeColorLegend = new String[nleg+keys.length][2];
		this.sampleSuperTypeColorLegend = new String[keys.length][2];
		
		nleg = 0;
		int supernleg = 0;
		
		for(Object key: keys)
		{
//			Object key = keys.next();
			
			this.sampleTypeColorLegend[nleg][0] = (String)key;
			this.sampleTypeColorLegend[nleg][1] = "nill";
			nleg++;
			
			ArrayList<String> samplets = templegend.get(key);
			ArrayList<String> samplclass = templegen2.get(key);
			int x = gradref.get((String)key)[0];
			
//			Arrays.sort(a);
			
			int j=0;
			String[] temp1 = new String[samplets.size()];
			String[] tempindex = new String[samplets.size()];
			String[] temp2 = new String[samplets.size()];
			
			for(String samplet:samplets)
			{
				int y = assignedColor.get(samplet);
				
				StringBuffer sb;
				if(samplclass.get(j).equals("User input"))
				{
					sb = new StringBuffer(samplet);
				}
				else
				{
					sb = new StringBuffer(samplclass.get(j));
					sb.append(" :: ");
					sb.append(samplet);
				}
				
//				this.sampleTypeColorLegend[nleg][0] = sb.toString();
//				this.sampleTypeColorLegend[nleg][1] = ColorConverter.getGradientColor(x,y);
//				nleg++;
				temp1[j] = sb.toString();
				tempindex[j] = temp1[j];
				temp2[j] = ColorConverter.getGradientColor(x,y);
				j++;
			}
			
			
			Arrays.sort(tempindex);
			
			
			for(int s=0;s<tempindex.length;s++)
			{
				int z = -1;
				
				for(int q=0;z==-1 && q<temp1.length;q++)
				{
					if(tempindex[s].equals(temp1[q])) z = q;
				}
				
				this.sampleTypeColorLegend[nleg][0] = temp1[z];
				this.sampleTypeColorLegend[nleg][1] = temp2[z];
				nleg++;
			}
			
			/*
			
			for(String samplet:samplets)
			{
				int y = assignedColor.get(samplet);
				
				StringBuffer sb = new StringBuffer(samplclass.get(j));
				sb.append(" :: ");
				sb.append(samplet);
				this.sampleTypeColorLegend[nleg][0] = sb.toString();
				this.sampleTypeColorLegend[nleg][1] = ColorConverter.getGradientColor(x,y);
				nleg++;
				j++;
			}
			*/
			
			this.sampleSuperTypeColorLegend[supernleg][0] = (String)key;
			this.sampleSuperTypeColorLegend[supernleg][1] = ColorConverter.getGradientColor(x,0);
			supernleg++;
		}
		
		this.sampleTypeColor = res;
		
		return res;
	}
	
	
	
	
	
	public String[] getTypeAltColors()
	{
		Object[] temp = ColorConverter.getNColorsRep(this.sampleType, this.na);
		this.sampleTypeColorLegend = (String[][])temp[1];
		return (String[])temp[0];
		
	}
	
	
	
	
	
	
	public String[] getTypeAltColors2()
	{
		HashMap<String,Integer> comp = new HashMap<String,Integer>();
		int utest = 0;
		
		
		String[] res = new String[sampleType.length];
//		this.sampleTypeColorLegend = new String[sampleType.length][2];
		
		HashMap<String,int[]> gradref = new HashMap<String,int[]>();
		HashMap<String,Integer> assignedColor = new HashMap<String,Integer>();
		
		HashMap<String,ArrayList<String>> templegend = new HashMap<String,ArrayList<String>>();
		HashMap<String,ArrayList<String>> templegen2 = new HashMap<String,ArrayList<String>>();
		
		int maxx = 0;
		
		int nleg = 0;
		
		for(int i=0;i<sampleType.length;i++)
		{
			int x = 0, y = 0;
			
			int yoption = 0;
			
			if(gradref.containsKey(sampleSuperType[i]))
			{
				int[] cord = gradref.get(sampleSuperType[i]);
				x = cord[0];
				int maxy = cord[1];
				
				if(assignedColor.containsKey(sampleType[i]))
				{
					y = assignedColor.get(sampleType[i]).intValue();
					yoption = 1;
				}
				else
				{
					maxy++;
					y = maxy;
					assignedColor.put(sampleType[i], new Integer(maxy));
					templegend.get(sampleSuperType[i]).add(sampleType[i]);
					templegen2.get(sampleSuperType[i]).add(this.samplecclass[i]);
					nleg++;
					yoption = 2;
					gradref.put(sampleSuperType[i], new int[]{x,maxy});
				}
			}
			else
			{
				x = maxx;
				maxx++;
				gradref.put(sampleSuperType[i], new int[]{x,0}); //second is maxy
				assignedColor.put(sampleType[i], new Integer(0));
				
				ArrayList<String> temp = new ArrayList<String>();
				temp.add(sampleType[i]);
				templegend.put(sampleSuperType[i], temp);
				
				ArrayList<String> temp2 = new ArrayList<String>();
				temp2.add(this.samplecclass[i]);
				templegen2.put(sampleSuperType[i], temp2);
				
				nleg++;
			}
			
			
//			comp.containsKey(key)
			
//			res[i] = ColorConverter.getGradientColor(x,y);
			
			int num = -2;
			
			if(comp.containsKey(sampleType[i])) num = comp.get(sampleType[i]);
			else 
			{
				num = utest;
				utest++;
				comp.put(sampleType[i],new Integer(num));
			}
			
			res[i] = ColorConverter.getNColorsRepN(num, sampleType[i], "User input")[1];
		}

//		this.sampleTypeColorLegend = (new ArrayList<String[]>()).toArray(new String[][]{}); //legend fixing now
		
		
//		Iterator keys = templegend.keySet().iterator();
		Object[] keys = templegend.keySet().toArray();
		
		this.sampleTypeColorLegend = new String[nleg+keys.length][2];
		
		nleg = 0;
		
		for(Object key: keys)
		{
//			Object key = keys.next();
			
			this.sampleTypeColorLegend[nleg][0] = (String)key;
			this.sampleTypeColorLegend[nleg][1] = "nill";
			nleg++;
			
			ArrayList<String> samplets = templegend.get(key);
			ArrayList<String> samplclass = templegen2.get(key);
			int x = gradref.get((String)key)[0];
			
//			Arrays.sort(a);
			
			int j=0;
			String[] temp1 = new String[samplets.size()];
			String[] tempindex = new String[samplets.size()];
			String[] temp2 = new String[samplets.size()];
			
			for(String samplet:samplets)
			{
				int y = assignedColor.get(samplet);
				
				StringBuffer sb;
				if(samplclass.get(j).equals("User input"))
				{
					sb = new StringBuffer(samplet);
				}
				else
				{
					sb = new StringBuffer(samplclass.get(j));
					sb.append(" :: ");
					sb.append(samplet);
				}
				
//				this.sampleTypeColorLegend[nleg][0] = sb.toString();
//				this.sampleTypeColorLegend[nleg][1] = ColorConverter.getGradientColor(x,y);
//				nleg++;
				temp1[j] = sb.toString();
				tempindex[j] = temp1[j];
				temp2[j] = ColorConverter.getGradientColor(x,y);
				
				temp2[j] = ColorConverter.getNColorsRepN(comp.get(samplet), samplclass.get(j), "User input")[1];
				
				j++;
			}
			
			
			Arrays.sort(tempindex);
			
			
			for(int s=0;s<tempindex.length;s++)
			{
				int z = -1;
				
				for(int q=0;z==-1 && q<temp1.length;q++)
				{
					if(tempindex[s].equals(temp1[q])) z = q;
				}
				
				this.sampleTypeColorLegend[nleg][0] = temp1[z];
				this.sampleTypeColorLegend[nleg][1] = temp2[z];
				nleg++;
			}
			
		}
		
		this.sampleTypeColor = res;
		
		return res;
	}
	
	
	
	
	
	
	
	
	
	
	public String[][] getTypeColorsLegend()
	{
		return sampleTypeColorLegend;
	}
	
	public String[] getConditionColors()
	{
		Object[] temp = ColorConverter.getNColorsRep(this.sampleCondition, this.na);
		this.sampleConditionColorLegend = (String[][])temp[1];
		return (String[])temp[0];
	}
	
	public String[][] getConditionColorsLegend()
	{
		return sampleConditionColorLegend;
	}
	
	public String[] getSampleWildOrNot()
	{
//		Object[] temp = ColorConverter.getNColorsRep(this.sampleWildOrNot, this.na);
		Object[] temp = ColorConverter.getBinColorsRep(this.sampleWildOrNot, this.na, true);
		this.sampleWildOrNotLegend = (String[][])temp[1];
		return (String[])temp[0];
	}
	
	public String[][] getsampleWildOrNotLegend()
	{
		return sampleWildOrNotLegend;
	}

	public String[] getSampleCondition() {
		return sampleCondition;
	}
	
	public String[] getSampleWildOrNot2()
	{
		return this.sampleWildOrNot;
	}

	public String[] getSampleDBid() {
		return sampleDBid;
	}
	
	public String[] getPureStem()
	{
		Object[] temp = ColorConverter.getBinColorsRep(this.pureStem, this.na, false);
		this.pureStemLegend = (String[][])temp[1];
		return (String[])temp[0];
	}
	
	public String[] getPureStemExtra()
	{
		return this.pureStem;
	}

	
	
	public String[][] getPureStemLegend() {
		return pureStemLegend;
	}
	
	public String[] getSampleClassColors()
	{
//		Object[] temp = ColorConverter.getNColorsRep(this.sampleWildOrNot, this.na);
		Object[] temp = ColorConverter.getTriColorsRep(this.samplecclass);
		this.classLegend = (String[][])temp[1];
		return (String[])temp[0];
	}

	public String[][] getClassLegend() {
		return classLegend;
	}

	public String[] getSamplecclass() {
		return samplecclass;
	}
	
	public Integer[] getTypesids() {
		return typesids;
	}

	public String[][] getSources() {
		return sources;
	}

	public String[] getSampleSuperType() {
		return sampleSuperType;
	}

	public String[][] getSampleSuperTypeColorLegend() {
		return sampleSuperTypeColorLegend;
	}

	public String[] getSampleSuperTypeColors() {
		return sampleSuperTypeColors;
	}
	
	
	
	public String[] getSurfacemarkersColors()
	{
		Object[] temp = ColorConverter.getNColorsRep(this.surfacemarkers, this.na);
		this.surfacemarkersLegend = (String[][])temp[1];
		return (String[])temp[0];
	}
	
	public String[][] getSurfacemarkersLegend()
	{
		return surfacemarkersLegend;
	}

	public String[] getSurfacemarkers() {
		return surfacemarkers;
	}
	
}
