package databaseAccess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class DatabaseAccess {

	
	public static Object[] getMasterList(SessionFactory sessionFactory) {
		
		Object[] res = new Object[]{};
		
		Session s = null;
		
		try{
			s = sessionFactory.openSession();
		} catch(Exception e)
		{e.printStackTrace();}
		
		if(s==null) return res;
		
		try {
			Transaction tx = s.beginTransaction();
			try {
				
				ArrayList<String> temp = new ArrayList<String>();
				ArrayList<String[]> temp2 = new ArrayList<String[]>();
				
				String queryString = "FROM Genes";
				
				Query queryObject = s.createQuery(queryString);
				
				List l = queryObject.list();
				
				for(int y=0;y<l.size();y++)
				{
					Genes ge = (Genes)l.get(y);
					String entrez = ""+ge.getEntrez();
					
					temp.add(entrez);
					
					
					Object[] aliases = ge.getGenealiases().toArray();
					
					for(int z=0;z<aliases.length;z++)
					{
						Genealias ga = (Genealias)aliases[z];
						String alias = ga.getAlias();
						temp.add(alias);
						temp2.add(new String[]{entrez,alias});
					}
				}
				
				tx.commit();
				
				res = new Object[]{temp.toArray(new String[]{}),temp2.toArray(new String[][]{})};
				
			} catch(RuntimeException re)
			{
				re.printStackTrace();
				tx.rollback();
			}
			
		}	catch(Exception e)
		{e.printStackTrace();}
		finally
		{s.close();}
		
		return res;
	}

	public static String[][][][] getSampleTypes(SessionFactory sessionFactory, String platform) {
		
		String[][][][] res = new String[0][][][];
		
		Session s = null;
		
		try{
			s = sessionFactory.openSession();
		} catch(Exception e)
		{e.printStackTrace();}
		
		if(s==null) return res;
		
		try {
			Transaction tx = s.beginTransaction();
			try {
				StringBuffer queryString = new StringBuffer(
					"select distinct sup "
						+ "from Supertype as sup "
						+ "join sup.sampletypes as sap "
						+ "join sap.samples as sam "
						+ "where sam.platform = '"	
				);
				queryString.append(platform);
				queryString.append("'");
				
				Query queryObject = s.createQuery(queryString.toString());
				
				List l = queryObject.list();
				
				Object[] lls = l.toArray();
				
				Arrays.sort(lls);
				
				res = new String[l.size()][][][];
				
				for(int y=0;y<lls.length;y++)
				{
					Supertype sup = (Supertype)lls[y];
					Iterator types = sup.getSampletypes().iterator();
					
//					res[y] = new String[sup.getSampletypes().size()+1][];
					res[y] = new String[5][][];
					

					res[y][4] = new String[][]{new String[]{sup.getId().toString()}};
					
					
					res[y][0] = new String[][]{new String[]{sup.getSupertype()}};
					res[y][2] = new String[][]{new String[]{sup.getId().toString()}};
					
					
					
					
					queryString = new StringBuffer(
						"select distinct sap "
						+ "from Sampletype as sap "
						+ "join sap.samples as sam "
						+ "where sam.platform = '"	
					);
					queryString.append(platform);
					queryString.append("' and sap.supertype.id = ");
					queryString.append(sup.getId());
					
					Query queryObject2 = s.createQuery(queryString.toString());
					
					List l2 = queryObject2.list();

					
					
//					if(l2.size()>1) res[y][3] = new String[][]{new String[]{"All"}};
//					else res[y][3] = new String[][]{new String[]{"Single"}};
					
					
					
					int addition = 0;
					ArrayList<Sampletype>[] lis = new ArrayList[]{new ArrayList<Sampletype>(),new ArrayList<Sampletype>(),new ArrayList<Sampletype>()};
					
					
					for(Object l2o: l2)
					{
						Sampletype typ = (Sampletype)l2o;
						String clasz = typ.getClass_();
						
						int f = -1;
						
						if(clasz.equals("Stem")) f = 0;
						else if(clasz.equals("Progenitor")) f = 1;
						else if(clasz.equals("Mature")) f = 2;
						
						lis[f].add(typ);
					}
					
					if(lis[0].size()>0) addition++;
					if(lis[1].size()>0) addition++;
					if(lis[2].size()>0) addition++;
					
					


					
					
					if(addition>1) res[y][3] = new String[][]{new String[]{"All"}};
					else res[y][3] = new String[][]{new String[]{"Single"}};
					
					
					res[y][1] = new String[l2.size()+addition][5];

					int z = 0;
					int k = 0;
					
					for(ArrayList<Sampletype> l2ss: lis)
					{
						k++;
						if(l2ss.size()>0)
						{
							
							
							
							Object[] l2s = l2ss.toArray();
							
							//if only one extratype do this
							Arrays.sort(l2s);
							
							String cycle = null;
							
							if(k==1) cycle = "Stem";
							else if(k==2) cycle = "Progenitor";
							else if(k==3) cycle = "Differentiated/Mature";
							
							res[y][1][z][0] = cycle;
							res[y][1][z][1] = "-1";
							res[y][1][z][2] = "-1";
							res[y][1][z][3] = "classcheck";
							res[y][1][z][4] = Integer.toString(k);
							z++;
							
							
							for(Object l2o: l2s)
							{
								Sampletype typ = (Sampletype)l2o;
								res[y][1][z][1] = typ.getId().toString();
								
								queryString = new StringBuffer(
									"select count(distinct sam.id) "
									+ "from Sample as sam "
									+ "join sam.sampletype as typ "
									+ "where sam.platform = '"
								);
								queryString.append(platform);
								queryString.append("' and typ.type = '");
								queryString.append(typ.getType());
								queryString.append("'");
								
								
								Query queryObject3 = s.createQuery(queryString.toString());
								
								String vount = queryObject3.uniqueResult().toString();
								
//								String count = (s.createQuery(queryString.toString()).uniqueResult()).toString();
								
//								System.out.println("count "+count);
								
//								res[y][1][z][0] = typ.getType()+" ("+vount+")";
								res[y][1][z][0] = typ.getType();
								res[y][1][z][2] = vount;
								res[y][1][z][3] = Integer.toString(k);
//								res[y][1][z][3] = cycle;
								
								z++;
							}
							
							
							
							
							
							
							
							
							
						}
					}
					
					
					
					
					
					
					
					
					
					
				}
				
				tx.commit();
				
			} catch(RuntimeException re)
			{
				re.printStackTrace();
				tx.rollback();
			}
			
		}	catch(Exception e)
		{e.printStackTrace();}
		finally
		{s.close();}
		
		return res;
	}

	public static String[][][][] getSampleTypes(SessionFactory sessionFactory) {
		
		String[][][][] res = new String[0][][][];
		
		Session s = null;
		
		try{
			s = sessionFactory.openSession();
		} catch(Exception e)
		{e.printStackTrace();}
		
		if(s==null) return res;
		
		try {
			Transaction tx = s.beginTransaction();
			try {
				
				String queryString = "select distinct sup from Supertype as sup";
				
				Query queryObject = s.createQuery(queryString);
				
				List l = queryObject.list();
				
				res = new String[l.size()][][][];
				
				for(int y=0;y<l.size();y++)
				{
					Supertype sup = (Supertype)l.get(y);
					Iterator types = sup.getSampletypes().iterator();
					
//					res[y] = new String[sup.getSampletypes().size()+1][];
					res[y] = new String[5][][];
					

					res[y][4] = new String[][]{new String[]{sup.getId().toString()}};
					
					
					res[y][0] = new String[][]{new String[]{sup.getSupertype()}};
					res[y][1] = new String[sup.getSampletypes().size()][2];
					res[y][2] = new String[][]{new String[]{sup.getId().toString()}};
					
					if(sup.getSampletypes().size()>1) res[y][3] = new String[][]{new String[]{"All"}};
					else res[y][3] = new String[][]{new String[]{"Single"}};
					
					int z = 0;
					
					while(types.hasNext())
					{
						Sampletype typ = (Sampletype)types.next();
						res[y][1][z][0] = typ.getType();
						res[y][1][z][1] = typ.getId().toString();
						z++;
					}
				}
				
				tx.commit();
				
			} catch(RuntimeException re)
			{
				re.printStackTrace();
				tx.rollback();
			}
			
		}	catch(Exception e)
		{e.printStackTrace();}
		finally
		{s.close();}
		
		return res;
	}
		
	public static Object[] getSampleNTypes(SessionFactory sessionFactory) {

		String[][] res1 = new String[][]{};
		String[][] res2 = new String[][]{};
		
		HashMap<String,ArrayList<String>> supertypetosubtype = new HashMap<String,ArrayList<String>>();
		HashMap<String,ArrayList<String[]>> typetoids = new HashMap<String,ArrayList<String[]>>();
		
		Object[] res = new Object[]{res1,res2};
		
		Session s = null;
		
		try{
			s = sessionFactory.openSession();
		} catch(Exception e)
		{e.printStackTrace();}
		
		if(s==null) return res;
		
		try {
			Transaction tx = s.beginTransaction();
			try {
				
				String queryString = "select samp.name, samp.sampletype.type, samp.id, samp.sampletype.supertype.supertype from Sample as samp";
				
				Query queryObject = s.createQuery(queryString);
				
				List l = queryObject.list();
				
				res1 = new String[l.size()][2];
//				res2 = new String[l.size()][2];
				
				for(int y=0;y<l.size();y++)
				{
					Object[] re = (Object[])l.get(y);
					
					res1[y][0] = (String)re[0];
					res1[y][1] = (String)re[1];
					
					String supertype = (String)re[3];
					String type = (String)re[1];
					String id = re[2].toString();
					
					ArrayList<String> supertypetosubtypeL;
					ArrayList<String[]> typetoidsL;
					
					if(typetoids.containsKey(type)) typetoidsL = typetoids.get(type);
					else
					{
						typetoidsL = new ArrayList<String[]>();
						typetoids.put(type, typetoidsL);
					}
					
					typetoidsL.add(new String[]{(String)re[0],id});
					
					if(supertypetosubtype.containsKey(supertype))
						supertypetosubtypeL = supertypetosubtype.get(supertype);
					else
					{
						supertypetosubtypeL = new ArrayList<String>();
						supertypetosubtype.put(supertype, supertypetosubtypeL);
					}
					
					if(!supertypetosubtypeL.contains(type))
					{
						supertypetosubtypeL.add(type);
					}
					
//					res2[y][0] = (String)re[0];
//					res2[y][1] = re[2].toString();
				}
				
				String[] supertypekeys = supertypetosubtype.keySet().toArray(new String[]{});
				
				res2 = new String[l.size()+supertypekeys.length+typetoids.keySet().size()][2];
				
				int k=0;
				
				for(int a=0;a<supertypekeys.length;a++)
				{
					res2[k][0] = supertypekeys[a];
					res2[k][1] = "super";
					k++;
					
					ArrayList<String> typeinsubtype = supertypetosubtype.get(supertypekeys[a]);
					
					for(int b=0;b<typeinsubtype.size();b++)
					{
						res2[k][0] = typeinsubtype.get(b);
						res2[k][1] = "type";
						ArrayList<String[]> typenids = typetoids.get(res2[k][0]);
						k++;
						
						for(String[] da: typenids)
						{
							res2[k] = da;
							k++;
						}
					}
					
				}
				
				res = new Object[]{res1,res2};
				
				tx.commit();
				
			} catch(RuntimeException re)
			{
				re.printStackTrace();
				tx.rollback();
			}
			
		}	catch(Exception e)
		{e.printStackTrace();}
		finally
		{s.close();}
		
		return res;
	}
	
	//TODO: Add system to warn of repeated inputs and nomatches
	public static Object[][] convertToEntrez(SessionFactory sessionFactory, String[] ids, int organims) {
		
		Object[][] res = new Object[][]{};
		
		Session s = null;
		
		try{
			s = sessionFactory.openSession();
		} catch(Exception e)
		{e.printStackTrace();}
		
		if(s==null) return res;
		
		ArrayList<Integer> tpossibleEntrez = new ArrayList<Integer>();
		
		for(String idd: ids)
		{
			if(StringUtils.isNumeric(idd))
			{
				tpossibleEntrez.add(new Integer(idd));
			}
		}
		
		Integer[] possibleEntrez = tpossibleEntrez.toArray(new Integer[]{});
		
		try {
			Transaction tx = s.beginTransaction();
			try {
				
				String queryString;
				Query queryObject;
				
				List l;
				
				if(possibleEntrez.length>0)
				{
					l = new ArrayList();
					
					queryString = "select distinct gen from Genes gen join gen.genealiases as als "
						+ "where (als.alias in (:ids) or gen.entrez in (:ent)) and gen.organisms.id = "+organims;
					queryObject = s.createQuery(queryString).setParameterList("ids", ids).setParameterList("ent", possibleEntrez);
					
					List tl = queryObject.list();
					
					ArrayList<Integer> added = new ArrayList<Integer>();
					
					for(Object tlo: tl)
					{
						Genes tlg = (Genes)tlo;
						
						if(!added.contains(tlg.getEntrez()))
						{
							l.add(tlo);
							added.add(tlg.getEntrez());
						}
					}
					
					queryString = "select distinct gen from Genes gen "
						+ "where gen.entrez in (:ent) and gen.organisms.id = "+organims;
					queryObject = s.createQuery(queryString).setParameterList("ent", possibleEntrez);
					
					tl = queryObject.list();
					
					for(Object tlo: tl)
					{
						Genes tlg = (Genes)tlo;
						
						if(!added.contains(tlg.getEntrez()))
						{
							l.add(tlo);
							added.add(tlg.getEntrez());
						}
					}
					
				}
				else
				{
					queryString = "select distinct gen from Genes gen join gen.genealiases as als "
						+ "where als.alias in (:ids) and gen.organisms.id = "+organims;
					queryObject = s.createQuery(queryString).setParameterList("ids", ids);
					
					l = queryObject.list();
				}
				
				ArrayList<Integer> temp1 = new ArrayList<Integer>();
				ArrayList<String> temp2 = new ArrayList<String>();
				
				
				boolean[] unfound = new boolean[ids.length]; 
				
				for(int x=0;x<ids.length;x++) unfound[x] = false;
				
				for(int y=0;y<l.size();y++)
				{
					Genes ga = (Genes)l.get(y);
					
					StringBuffer lable = new StringBuffer("");
					boolean first = true;
					
					temp1.add(new Integer(ga.getEntrez()));
					
					Object[] gals = ga.getGenealiases().toArray();
					
					boolean addedtolabel = false;
					
					
					for(Object gal: gals)
					{
						String gls = ((Genealias)gal).getAlias();
						
						boolean found = false;
						
						for(int x=0;x<ids.length && !found;x++)
						{
							if(ids[x].equalsIgnoreCase(gls))
							{
								unfound[x] = true;
								found = true;
								
								if(first) first = false;
								else
								{
									lable.append("\\");
								}
								if(ga.getGenesymbol()!=null )
								{
									if(!ga.getGenesymbol().equals(gls)) 
									{
										lable.append(gls);
										addedtolabel = true;
									}
								}
								else lable.append(gls);
							}
						}
					}
					
					if(!addedtolabel && ga.getGenesymbol()!=null)
					{
						lable.append(ga.getGenesymbol());
					}
					
					if(addedtolabel && ga.getGenesymbol()!=null)
					{
						StringBuffer firstflabel = new StringBuffer(ga.getGenesymbol());
						firstflabel.append("\\");
						firstflabel.append(lable);
						lable=firstflabel;
					}
					
					temp2.add(lable.toString());
					
				}
				
				tx.commit();
				
				StringBuffer sb1 = new StringBuffer("");
				int unfoundnum = 0;
				
				for(int x=0;x<ids.length;x++)
				{
					if(!unfound[x])
					{
						if(unfoundnum>0) sb1.append(", ");
						sb1.append(ids[x]);
						unfoundnum++;
					}
				}
				
				StringBuffer sb;
				
				if(unfoundnum==0) sb = new StringBuffer("");
				else if(unfoundnum==1)
				{
					sb = new StringBuffer("No match with the following id:");
					sb.append(sb1);
				}
				else 
				{
					sb = new StringBuffer("No match with the following ids: ");
					sb.append(sb1);
				}
				
				res = new Object[][]{temp1.toArray(new Integer[]{}), temp2.toArray(new String[]{}), new String[]{sb.toString()}};
				
			} catch(RuntimeException re)
			{
				re.printStackTrace();
				tx.rollback();
			}
			
		}	catch(Exception e)
		{e.printStackTrace();}
		finally
		{s.close();}
		
		return res;
	}
	
	public static SampleObject extractExpressionTable(SessionFactory sessionFactory, String[] pla, String[] typ, Integer[] ids) {

		Sample testsamp = null;
		SampleObject res = null;
		
		Session s = null;
		
		try{
			s = sessionFactory.openSession();
		} catch(Exception e)
		{e.printStackTrace();}
		
		if(s==null) return res;
		
		try {
			Transaction tx = s.beginTransaction();
			try {
				
				String queryString;
				Query queryObject;
				
				if(ids==null)
				{
					queryString = "Select sam, gen.entrez, exp.cfrma, exp.quantiled " +
						"from Sample as sam " +
						"join sam.expressions as exp " +
						"join exp.genes as gen " +
						"where sam.platform in (:pla) and " +
						"sam.sampletype.type in (:typ)";
						
					queryObject = s.createQuery(queryString).setParameterList("pla", pla).setParameterList("typ", typ);
				}
				else
				{
					queryString = "Select sam, gen.entrez, exp.cfrma, exp.quantiled " +
						"from Sample as sam " +
						"join sam.expressions as exp " +
						"join exp.genes as gen " +
						"where sam.platform in (:pla) and " +
						"sam.sampletype.type in (:typ) and " +
						"gen.entrez in (:ids)";
					
					queryObject = s.createQuery(queryString).setParameterList("pla", pla).setParameterList("typ", typ).setParameterList("ids", ids);
				}
				
				List l = queryObject.list();
				
				ArrayList<String> tempCN = new ArrayList<String>(); //column names
				ArrayList<Integer> tempRN = new ArrayList<Integer>(); //row names
				ArrayList<Sample > tempSam = new ArrayList<Sample>(); //sample conditions
				
				
				//queryString = "Select sam.name, sam.id, sam.sampletype.type, gen.entrez, exp.expression, sam.samplecondition.conditions, sam ";
				
				for(int y=0;y<l.size();y++)
				{
					Object[] re = (Object[])l.get(y);
					Sample sam = (Sample)re[0];
					Integer entrez = (Integer)re[1];
					
					
					if(!tempCN.contains(sam.getName()))
					{
						tempCN.add(sam.getName());
						tempSam.add(sam);
					}
					if(!tempRN.contains(entrez)) tempRN.add(entrez);
				}
				
				tx.commit();
				
				//Columns are samples and rows genes
//				Integer[] columnIds = new Integer[tempCN.size()]; //no need for this samples has everything I need
				Sample[] samples = new Sample[tempSam.size()];
				Integer[] rowNames = new Integer[tempRN.size()];
				
				for(int i=0;i<tempSam.size();i++)
				{
					samples[i] = tempSam.get(i);
				}
				
				for(int i=0;i<tempRN.size();i++)
				{
					rowNames[i] = tempRN.get(i);
				}
				
				double[][] matrix = new double[rowNames.length][samples.length];
				double[][] matrixq = new double[rowNames.length][samples.length];
				
//				queryString = "Select sam, gen.entrez, exp.expression "
//				queryString = "Select sam.name, sam.id, sam.sampletype.type, gen.entrez, exp.expression, sam.samplecondition.conditions, sam ";
				
				for(int y=0;y<l.size();y++)
				{
					Object[] re = (Object[])l.get(y);
					Sample sample = (Sample)re[0];
					Integer geneId = (Integer)re[1];
					
					int rp = -1;
					int cp = -1;
					
					for(int u=0;rp==-1 && u<rowNames.length;u++)
					{
						if(rowNames[u].equals(geneId)) rp = u;
					}
					
					for(int u=0;cp==-1 && u<samples.length;u++)
					{
						if(samples[u].equals(sample)) cp = u;
					}
					
					matrix[rp][cp] = ((Double)re[2]).doubleValue();
					matrixq[rp][cp] = ((Double)re[3]).doubleValue();
					
				}
				
				res = new SampleObject(rowNames,matrix,matrixq,samples); //samples will provide the columns names
			
			} catch(RuntimeException re)
			{
				re.printStackTrace();
				tx.rollback();
			}
		}	catch(Exception e)
		{e.printStackTrace();}
		finally
		{s.close();}
		
		return res;
	}
	
	public static Object[] getProbToEntrezTable(SessionFactory sessionFactory, String arrayType) {
		
		Object[] res = null;
		
		Session s = null;
		
		try{
			s = sessionFactory.openSession();
		} catch(Exception e)
		{e.printStackTrace();}
		
		if(s==null) return res;
		
		try {
			Transaction tx = s.beginTransaction();
			try {
				
				String queryString = "select prob from Probes as prob " +
					"where prob.platform='"+arrayType+"'";
				
				Query queryObject = s.createQuery(queryString);
				
				List l = queryObject.list();
				
				ArrayList<String[]> temp = new ArrayList<String[]>();
				ArrayList<String> temp2 = new ArrayList<String>();
				
				for(int y=0;y<l.size();y++)
				{
					Probes pro = (Probes)l.get(y);
					
					String entz = Integer.toString(pro.getGenes().getEntrez());
					String prob = pro.getProbe();
					
					temp.add(new String[]{prob,entz});
					if(!temp2.contains(entz)) temp2.add(entz);
				}
				
				tx.commit();
				
				res = new Object[]{temp.toArray(new String[][]{}),temp2.toArray(new String[]{})};
				
			} catch(RuntimeException re)
			{
				re.printStackTrace();
				tx.rollback();
			}
			
		}	catch(Exception e)
		{e.printStackTrace();}
		finally
		{s.close();}
		
		return res;
	}
	
	public static Object[] searchForGene(SessionFactory sessionFactory, String[] ids, Integer[] entrezes) {
		
		String[][] res = null;
		String[][] generes = null;
		
		Session s = null;
		
		try{
			s = sessionFactory.openSession();
		} catch(Exception e)
		{e.printStackTrace();}
		
		if(s==null) return null;
		
		try {
			Transaction tx = s.beginTransaction();
			try {
				Query queryObject;
				
				if(entrezes.length>0)
				{
					String queryString = "select exp from Expression as exp join exp.genes.genealiases as als "
						+ "where als.alias in (:ids) or exp.genes.entrez in (:ent)";
					queryObject = s.createQuery(queryString).setParameterList("ids", ids).setParameterList("ent", entrezes);
				}
				else
				{
					String queryString = "select exp from Expression as exp join exp.genes.genealiases as als "
						+ "where als.alias in (:ids)";
					queryObject = s.createQuery(queryString).setParameterList("ids", ids);
				}
				
				List l = queryObject.list();
				
				Object[][] temp = new Object[l.size()][5];
				
				ArrayList<Integer> tempgeneids = new ArrayList<Integer>();
				ArrayList<String> tempgenealias = new ArrayList<String>();
				ArrayList<Integer> tempsampleids = new ArrayList<Integer>();
				
				for(int y=0;y<l.size();y++)
				{
					Expression exp = (Expression)l.get(y);
					
					Genes gen = exp.getGenes();
					Sample sam = exp.getSample();
					
					temp[y][0] = gen.getId(); //gene id
					temp[y][1] = sam.getId(); //sample id
//					temp[y][2] = exp.getExpression(); //expression
					temp[y][2] = exp.getCfrma(); //expression
					temp[y][3] = gen.getEntrez(); //gene entrez id
					temp[y][4] = sam.getName(); //sample name
					
					if(!tempgeneids.contains(exp.getGenes().getId()))
					{
						tempgeneids.add(exp.getGenes().getId());
						Object[] genal = gen.getGenealiases().toArray();
						
						StringBuffer tempst = new StringBuffer("");
						
						Arrays.sort(genal);
						
						for(int u=0;u<genal.length;u++)
						{
							if(u>0) tempst.append(", ");
							tempst.append(((Genealias)genal[u]).getAlias());
						};
						
						tempgenealias.add(tempst.toString());
					}
					if(!tempsampleids.contains(exp.getSample().getId())) tempsampleids.add(exp.getSample().getId());
					
				}
				
				tx.commit();
				
				Integer[] ordergeneids = tempgeneids.toArray(new Integer[]{});
				Integer[] ordersampleids = tempsampleids.toArray(new Integer[]{});
				

				//first row is for the samples names, first column is for gene ids -- better invert it
//				res = new String[tempgeneids.size()+1][tempsampleids.size()+1];
				res = new String[tempsampleids.size()+1][tempgeneids.size()+1];
				res[0][0] = "";
				
				generes = new String[tempgeneids.size()][2];
				
				for(int y=0;y<temp.length;y++)
				{
					int a=-1, b=-1;
					for(int i=0;a==-1 && i<ordergeneids.length;i++)
					{
						if(ordergeneids[i].equals((Integer)temp[y][0]))
							a=i+1;
					}
					
					if(res[0][a]==null)
					{
						res[0][a] = temp[y][3].toString();
						
						generes[a-1][0] = res[0][a];
						generes[a-1][1] = tempgenealias.get(a-1);
						
					}
					
					for(int i=0;b==-1 && i<ordersampleids.length;i++)
					{
						if(ordersampleids[i].equals((Integer)temp[y][1]))
							b=i+1;
					}
					
					if(res[b][0]==null) res[b][0] = temp[y][4].toString();
					
					
					res[b][a] = temp[y][2].toString();
				}
				
			} catch(RuntimeException re)
			{
				re.printStackTrace();
				tx.rollback();
			}
			
		}	catch(Exception e)
		{e.printStackTrace();}
		finally
		{s.close();}
		
		
		return new Object[]{res, generes};
	}
	
	public static String[] getSample(SessionFactory sessionFactory, String ids) {
		
		String[] res = null;
		
		Session s = null;
		
		try{
			s = sessionFactory.openSession();
		} catch(Exception e)
		{e.printStackTrace();}
		
		if(s==null) return null;
		
		try {
			Transaction tx = s.beginTransaction();
			try {
				Query queryObject;
				
				StringBuffer queryString;
				
				if(ids.startsWith("G"))
				{
					queryString = new StringBuffer("select sam from Sample as sam where sam.name = '");
					queryString.append(ids);
					queryString.append("'");
				}
				else
				{
					queryString = new StringBuffer("select sam from Sample as sam where sam = ");
					queryString.append(ids);
					
				}
				
				queryObject = s.createQuery(queryString.toString());
				
				List l = queryObject.list();
				
				if(l.size()>0)
				{
					Sample sam = (Sample)l.get(0);
					
					res = new String[11];
					res[10] = sam.getSurfacemarkers();
					res[0] = sam.getName();
					res[1] = sam.getSampletype().getSupertype().getSupertype();
					res[2] = sam.getSampletype().getType();
					res[3] = sam.getExperiment().getName();
					if(sam.getWildtype().equals("t")) res[4] = "Wildtype";
					else res[4] = sam.getSamplecondition().getConditions();
					if(sam.getInvivo().equals("t")) res[5] = "InVivo";
					else res[5] = "InVitro";
					
					if(sam.getSampletime()!=null) res[6] = sam.getSampletime().getTime();
					else res[6] = "Unknown";
					res[7] = sam.getExperiment().getId().toString();
					
					if(sam.getPlatform().equals("mouse4302"))
					{
						res[8] = "Affymetrix Mouse Genome 430 2.0 Array";
						res[9] = "Mus musculus";
					}
					else
					{
						res[8] = "Affymetrix Human Genome U133 Plus 2.0 Array";
						res[9] = "Homo sapiens";
					}
				}
				
				tx.commit();
				
				
				
			} catch(RuntimeException re)
			{
				re.printStackTrace();
				tx.rollback();
			}
			
		}	catch(Exception e)
		{e.printStackTrace();}
		finally
		{s.close();}
		
		
		return res;
	}
	
	public static String[][] getSampleExpression(SessionFactory sessionFactory, String ids) {
		
		String[][] res = null;
		
		Session s = null;
		
		try{
			s = sessionFactory.openSession();
		} catch(Exception e)
		{e.printStackTrace();}
		
		if(s==null) return null;
		
		try {
			Transaction tx = s.beginTransaction();
			try {
				Query queryObject;
				
				StringBuffer queryString;
				
				if(ids.startsWith("G"))
				{
					queryString = new StringBuffer("select sam from Sample as sam where sam.name = '");
					queryString.append(ids);
					queryString.append("'");
				}
				else
				{
					queryString = new StringBuffer("select sam from Sample as sam where sam = ");
					queryString.append(ids);
					
				}
				
				queryObject = s.createQuery(queryString.toString());
				
				List l = queryObject.list();
				
				if(l.size()>0)
				{
					Sample sam = (Sample)l.get(0);
					
					Object[] exps = sam.getExpressions().toArray();
					
					res = new String[exps.length][3];
					
					for(int i=0;i<exps.length;i++)
					{
						Expression exp = (Expression)exps[i];
						
						res[i][0] = String.valueOf(exp.getGenes().getEntrez());
//						res[i][1] = String.valueOf(exp.getExpression());
						res[i][1] = String.valueOf(exp.getCfrma());
						if(exp.getGenes().getGenesymbol()!=null)
							res[i][2] = exp.getGenes().getGenesymbol();
						else res[i][2] = "NA";
					}
				}
				
				tx.commit();
				
			} catch(RuntimeException re)
			{
				re.printStackTrace();
				tx.rollback();
			}
			
		}	catch(Exception e)
		{e.printStackTrace();}
		finally
		{s.close();}
		
		return res;
	}
	
	public static Object[] getSampleForFile(SessionFactory sessionFactory, String ids) {
		
		String res2 = null;
		
		StringBuffer res = new StringBuffer("");
		
		Session s = null;
		
		try{
			s = sessionFactory.openSession();
		} catch(Exception e)
		{e.printStackTrace();}
		
		if(s==null) return new Object[]{res,res2};
		
		try {
			Transaction tx = s.beginTransaction();
			try {
				Query queryObject;
				
				StringBuffer queryString;
				
				if(ids.startsWith("G"))
				{
					queryString = new StringBuffer("select sam from Sample as sam where sam.name = '");
					queryString.append(ids);
					queryString.append("'");
				}
				else
				{
					queryString = new StringBuffer("select sam from Sample as sam where sam = ");
					queryString.append(ids);
					
				}
				
				queryObject = s.createQuery(queryString.toString());
				
				List l = queryObject.list();
				
				if(l.size()>0)
				{
					Sample sam = (Sample)l.get(0);
					
					res2 = sam.getName();
					
					handleSample(sam, res);
				}
				
				tx.commit();
				
				
				
			} catch(RuntimeException re)
			{
				re.printStackTrace();
				tx.rollback();
			}
			
		}	catch(Exception e)
		{e.printStackTrace();}
		finally
		{s.close();}
		
		
		return new Object[]{res,res2};
	}
	
	protected static void handleSample(Sample sam, StringBuffer res) {

		res.append("#Sample: ");
		res.append(sam.getName());
		res.append("\n#Tissue: ");
		res.append(sam.getSampletype().getSupertype().getSupertype());
		res.append("\n#Cell Type: ");
		res.append(sam.getSampletype().getType());
		res.append("\n#Conditions: ");
		if(sam.getWildtype().equals("t")) res.append("Wildtype");
		else res.append(sam.getSamplecondition().getConditions());
		res.append("\n#Experimental setting: ");
		if(sam.getInvivo().equals("t")) res.append("InVivo");
		else res.append("InVitro");
		res.append("\n#Age: ");
		if(sam.getSampletime()!=null) res.append(sam.getSampletime().getTime());
		else res.append("Unknown");
		res.append("\nEntrez_id\tGene_symbol\tLog2_exprs_val\tQuantile");
		
		Object[] exps = sam.getExpressions().toArray();
		
		for(int o=0;o<exps.length;o++)
		{
			Expression exp = (Expression)exps[o];
			
			res.append("\n");
			res.append(exp.getGenes().getEntrez());
			res.append("\t");
			if(exp.getGenes().getGenesymbol()!=null) res.append(exp.getGenes().getGenesymbol());
			else res.append("NA");
			res.append("\t");
			res.append(exp.getCfrma());
			res.append("\t");
			res.append(exp.getQuantiled());
		}
		
	}
	
	public static String[][][] searchForExperiments(SessionFactory sessionFactory, String[] types) {
		
		String[][][] res = null;
		
		Session s = null;
		
		try{
			s = sessionFactory.openSession();
		} catch(Exception e)
		{e.printStackTrace();}
		
		if(s==null) return null;
		
		try {
			Transaction tx = s.beginTransaction();
			try {
				
				String queryString = "select distinct exp from Experiment as exp join exp.samples as sam "
					+ "where sam.sampletype.type in (:types)";
				Query queryObject = s.createQuery(queryString).setParameterList("types", types);
				
				List l = queryObject.list();

				res = new String[2][][];
				res[0] = new String[l.size()][2];
				
				for(int y=0;y<l.size();y++)
				{
					Experiment exp = (Experiment)l.get(y);
					res[0][y][0] = exp.getId().toString();
					res[0][y][1] = exp.getName();
				}
				
				
				queryString = "select distinct sam.id from Sampletype as sam where sam.type in (:types)";
				queryObject = s.createQuery(queryString).setParameterList("types", types);
				l = queryObject.list();
				
				StringBuffer res2b = new StringBuffer("&v=");
				
				for(int y=0;y<l.size();y++)
				{
					if(y>0) res2b.append(";");
					res2b.append(l.get(y).toString());
				}
				
				res[1] = new String[][]{new String[]{res2b.toString()}};
				
				tx.commit();
				
			} catch(RuntimeException re)
			{
				re.printStackTrace();
				tx.rollback();
			}
			
		}	catch(Exception e)
		{e.printStackTrace();}
		finally
		{s.close();}
		
		
		return res;
	}
	
	public static Object[] getExperiment(SessionFactory sessionFactory, String id) {
		return getExperiment(sessionFactory, id, null);
	}
	
	public static Object[] getExperiment(SessionFactory sessionFactory, String id, Integer[] sampids) {
		
		String[] res = null;
		String[][] res2 = null;
		
		Session s = null;
		
		try{
			s = sessionFactory.openSession();
		} catch(Exception e)
		{e.printStackTrace();}
		
		if(s==null) return null;
		
		try {
			Transaction tx = s.beginTransaction();
			try {
				Query queryObject;
				
				StringBuffer queryString;
				
				
				if(id.startsWith("G"))
				{
					queryString = new StringBuffer("select exp from Experiment as exp where exp.name = '");
					queryString.append(id);
					queryString.append("'");
				}
				else
				{
					queryString = new StringBuffer("select exp from Experiment as exp where exp.id = ");
					queryString.append(id);
				}
				
				
				
				
				
				queryObject = s.createQuery(queryString.toString());
				
				List l = queryObject.list();
				
				if(l.size()>0)
				{
					Experiment exp = (Experiment)l.get(0);
					
					res = new String[3];
					res[0] = exp.getName();
					
					Object[] samps = exp.getSamples().toArray();
					
					ArrayList<String> types = new ArrayList<String>();
					ArrayList<String> supertypes = new ArrayList<String>();
					
					
					if(sampids==null)
					{
						res2 = new String[samps.length][5];
					
						for(int y=0;y<samps.length;y++)
						{
							Sample samp = (Sample)samps[y];
					
							res2[y][0] = samp.getName();
							res2[y][1] = String.valueOf(samp.getId());
						
							Sampletype st = samp.getSampletype();
							Supertype sst = st.getSupertype();
						
							res2[y][2] = st.getType();
							res2[y][3] = sst.getSupertype();
						
							if(!types.contains(st.getType()))
								types.add(st.getType());
							if(!supertypes.contains(sst.getSupertype()))
								supertypes.add(sst.getSupertype());
						
							if(samp.getSampletime()!=null) res2[y][4] = samp.getSampletime().getTime();
							else res2[y][4] = "Unknown";
						}
					}
					else
					{
						ArrayList<Sample> tres2 = new ArrayList<Sample>();
						
						for(int y=0;y<samps.length;y++)
						{
							Sample samp = (Sample)samps[y];
							
							Integer sid = samp.getSampletype().getId();
							
							boolean add = false;
							
							for(int h=0;!add && h<sampids.length;h++)
							{
								if(sampids[h].equals(sid))
									add = true;
							}
							
							if(add)
							{
								tres2.add(samp);
							}
							
							Sampletype st = samp.getSampletype();
							Supertype sst = st.getSupertype();
							
							if(!types.contains(st.getType()))
								types.add(st.getType());
							if(!supertypes.contains(sst.getSupertype()))
								supertypes.add(sst.getSupertype());
							
						}
						
						res2 = new String[tres2.size()][5];
						
						for(int y=0;y<tres2.size();y++)
						{
							Sample samp = tres2.get(y);
					
							res2[y][0] = samp.getName();
							res2[y][1] = String.valueOf(samp.getId());
						
							Sampletype st = samp.getSampletype();
							Supertype sst = st.getSupertype();
						
							res2[y][2] = st.getType();
							res2[y][3] = sst.getSupertype();
							
							if(samp.getSampletime()!=null) res2[y][4] = samp.getSampletime().getTime();
							else res2[y][4] = "Unknown";
						}
					}
					
					res[1] = arrayToString(supertypes);
					res[2] = arrayToString(types);
					
				}
				
				tx.commit();
				
				
				
			} catch(RuntimeException re)
			{
				re.printStackTrace();
				tx.rollback();
			}
			
		}	catch(Exception e)
		{e.printStackTrace();}
		finally
		{s.close();}
		
		
		return new Object[]{res, res2};
	}
	
	protected static String arrayToString(ArrayList<String> arr) {
		
		String[] tempa = arr.toArray(new String[]{});
		
		Arrays.sort(tempa);
		
		StringBuffer sb = new StringBuffer("");
		
		for(int i=0;i<tempa.length;i++)
		{
			if(i>0) sb.append(", ");
			sb.append(tempa[i]);
		}
		
		return sb.toString();
		
	}
	
	public static Object[] getExpressionMatrixForFile(SessionFactory sessionFactory, String id, Integer[] extradata) {
		
		StringBuffer res = new StringBuffer("");
		
		Session s = null;
		
		String res2 = null;
		
		try{
			s = sessionFactory.openSession();
		} catch(Exception e)
		{e.printStackTrace();}
		
		if(s==null) return new Object[]{res,res2};
		
		try {
			Transaction tx = s.beginTransaction();
			try {
				Query queryObject;
				StringBuffer queryString;
				
				if(id.startsWith("G"))
				{
					queryString = new StringBuffer("select exp from Experiment as exp where exp.name = '");
					queryString.append(id);
					queryString.append("'");
				}
				else
				{
					queryString = new StringBuffer("select exp from Experiment as exp where exp.id = ");
					queryString.append(id);
					
				}
				
				queryObject = s.createQuery(queryString.toString());
				
				List l = queryObject.list();
				
				if(l.size()>0)
				{
					Experiment exp = (Experiment)l.get(0);

					res2 = exp.getName();
//					res.append("#Experiment: ");
//					res.append(exp.getName());
//					res.append("\n");
					
					Object[] sams = exp.getSamples().toArray();
					
					
					Object[] samsUseThis;
					
					
					if(extradata!=null)
					{
						ArrayList<Object> tsamsUseThis = new ArrayList<Object>();
						
						for(int i=0;i<sams.length;i++)
						{
							Sample sam = (Sample)sams[i];
							boolean add = false;
							Integer sid = sam.getSampletype().getId();
							
							
							for(int j=0;!add && j<extradata.length;j++)
							{
								add=extradata[j].equals(sid);
							}
							
							if(add)
								tsamsUseThis.add(sam);
						}
						
						samsUseThis = tsamsUseThis.toArray();
						
					}
					else samsUseThis = sams;
					
					int numSamps = samsUseThis.length;
					
					HashMap<Integer,double[]> geneExpression = new HashMap<Integer,double[]>();
					
					String[] genesymbol = null;
					int[] entrezs = null;
					
					for(int i=0;i<numSamps;i++)
					{
						Sample sam = (Sample)samsUseThis[i];
						
						Object[] exps = sam.getExpressions().toArray();
						
						if(i==0)
						{
							genesymbol = new String[exps.length];
							entrezs = new int[exps.length];
						}
						
						for(int o=0;o<exps.length;o++)
						{
							Expression ex = (Expression)exps[o];
							int entrez = ex.getGenes().getEntrez();
							
							double[] expvals;
							
							if(i==0)
							{
								genesymbol[o] = ex.getGenes().getGenesymbol();
								entrezs[o] = entrez;
								expvals = new double[numSamps];
								geneExpression.put(new Integer(entrez), expvals);
							}
							else expvals = geneExpression.get(new Integer(entrez));
							
							expvals[i] = ex.getCfrma();
//							expvals[i] = ex.getExpression();
//							if(o==0 || o==1) System.out.println(expvals[i]);
						}
					}
					
					for(int i=0;i<genesymbol.length;i++)
					{
						res.append("\t");
						if(genesymbol[i]!=null)
						{
							res.append(genesymbol[i]);
							res.append(" (");
							res.append(entrezs[i]);
							res.append(")");
						}
						else res.append(entrezs[i]);
						
					}
					res.append("\n");
					
					for(int x=0;x<samsUseThis.length;x++)
					{
						Sample sam = (Sample)samsUseThis[x];
						
						res.append(sam.getName());
						
						for(int y=0;y<entrezs.length;y++)
						{
							double[] expvals = geneExpression.get(new Integer(entrezs[y]));
						
							res.append("\t");
							res.append(expvals[x]);
							
						}
						
						res.append("\n");
					}
					
				}
				
				tx.commit();
				
			} catch(RuntimeException re)
			{
				re.printStackTrace();
				tx.rollback();
			}
			
		}	catch(Exception e)
		{e.printStackTrace();}
		finally
		{s.close();}
		

		return new Object[]{res,res2};
//		return res.toString();
	}
	
	public static Object[] getExperimentForFile(SessionFactory sessionFactory, String id, Integer[] extradata) {
		
		StringBuffer res = new StringBuffer("");
		
		String res2 = null;
		
		Session s = null;
		
		try{
			s = sessionFactory.openSession();
		} catch(Exception e)
		{e.printStackTrace();}
		
		if(s==null) return new Object[]{res,res2};
		
		try {
			Transaction tx = s.beginTransaction();
			try {
				Query queryObject;
				
				StringBuffer queryString;
				
				if(id.startsWith("G"))
				{
					queryString = new StringBuffer("select exp from Experiment as exp where exp.name = '");
					queryString.append(id);
					queryString.append("'");
				}
				else
				{
					queryString = new StringBuffer("select exp from Experiment as exp where exp.id = ");
					queryString.append(id);
					
				}
				
				queryObject = s.createQuery(queryString.toString());
				
				List l = queryObject.list();
				
				if(l.size()>0)
				{
					Experiment exp = (Experiment)l.get(0);
					
					res2 = exp.getName();

					res.append("#Experiment: ");
					res.append(exp.getName());
					res.append("\n");
					
					Object[] sams = exp.getSamples().toArray();
					

					for(int i=0;i<sams.length;i++)
					{
						Sample sam = (Sample)sams[i];
						
//						
						
//						res.append("\n");
						boolean add = true;
						
						if(extradata!=null)
						{
							add=false;
							Integer sid = sam.getSampletype().getId();
							for(int j=0;!add && j<extradata.length;j++)
							{
								add=extradata[j].equals(sid);
							}
						}
						
						if(add)
						{
							handleSample(sam, res);
							res.append("\n");
						}
					}
					
				}
				
				tx.commit();
				
			} catch(RuntimeException re)
			{
				re.printStackTrace();
				tx.rollback();
			}
			
		}	catch(Exception e)
		{e.printStackTrace();}
		finally
		{s.close();}
		
		
		return new Object[]{res,res2};
	}
	
	public static SampleAggregator aggregatesamples(SessionFactory sessionFactory, int maingeneid, Integer[] sampleids) {
		
		SampleAggregator res = null;
		
		int zump=75;
		
		for(int a=0;a<sampleids.length;)
		{
			if(sampleids.length-a<zump) zump = sampleids.length-a;
			
			Integer[] temp = new Integer[zump];
			int b=0;	
			for(;b<zump;b++)
			{
				temp[b] = sampleids[a+b];
			}
			a+=b;
			
			res = aggregatesamples(sessionFactory, maingeneid, temp, res, sampleids.length);
			
		}
		
		return res;
	}
	
	public static SampleAggregator aggregatesamples(SessionFactory sessionFactory, int maingeneid, Integer[] temp, SampleAggregator res, int sampleidslength) {
		
		Session s = null;
		
		try{
			s = sessionFactory.openSession();
		} catch(Exception e)
		{e.printStackTrace();}
		
		try {
			Transaction tx = s.beginTransaction();
			
			try {
				String queryString = "select sam from Sample as sam where sam.id in (:ids)";
				Query queryObject = s.createQuery(queryString).setParameterList("ids", temp);
				
				List l = queryObject.list();
				
				
				for(Object samo: l)
				{
					Sample sam = (Sample)samo;
					if(res==null)
					{
						res = new SampleAggregator(maingeneid, sampleidslength, sam.getExpressions().size()-1);
					}
					res.addSample(sam);
				}
				
				
				tx.commit();
				
			} catch(RuntimeException re)
			{
				re.printStackTrace();
				tx.rollback();
			}
			

		} catch(Exception e)
		{e.printStackTrace();}
		finally
		{
			s.clear();
			s.close();
		}
		
		return res;
	}


	public static int convertEntrezToId(SessionFactory sessionFactory, String entrez) {
		
		int res = -1;
		
		Session s = null;
		
		try{
			s = sessionFactory.openSession();
		} catch(Exception e)
		{e.printStackTrace();}
		
		if(s==null) return res;
		
		try {
			Transaction tx = s.beginTransaction();
			try {
				
				String queryString = "select gen.id from Genes as gen " +
					"where gen.entrez = "+entrez;
				
				Query queryObject = s.createQuery(queryString);
				
				List l = queryObject.list();
				
				if(l.size()>0)
				{
					res = (Integer)l.get(0);
				}
				
				tx.commit();
				
			} catch(RuntimeException re)
			{
				re.printStackTrace();
				tx.rollback();
			}
			
		}	catch(Exception e)
		{e.printStackTrace();}
		finally
		{s.close();}
		
		return res;
	}
	
	
	public static String[] getDataNumbers(SessionFactory sessionFactory, String org) {
		
		String[] res = null;
		
		Session s = null;
		
		try{
			s = sessionFactory.openSession();
		} catch(Exception e)
		{e.printStackTrace();}
		
		if(s==null) return null;
		
		try {
			Transaction tx = s.beginTransaction();
			try {
				/*
				
				String queryString = "select exp.name, count(sam.id)"
						+ "from Experiment as exp join exp.samples as sam "
						+ "group by exp.name";
				
				*/
				String queryString = "select typ.type, typ.supertype.supertype, count(sam.id)"
						+ "from Sampletype as typ join typ.samples as sam "
						+ "where sam.organisms.name = '"+org+"'"
						+ "group by typ.supertype";
				Query queryObject = s.createQuery(queryString);
				
				List l = queryObject.list();

//				"select distinct sup "
//				+ "from Supertype as sup "
//				+ "join sup.sampletypes as sap "
//				+ "join sap.samples as sam "
//				+ "where sam.platform = '"	
//		);
				
				
//				res = new String[2][][];
//				res[0] = new String[l.size()][2];
				
				for(int y=0;y<l.size();y++)
				{
					Object[] tenp = (Object[])l.get(y);
					
					System.out.println();
					System.out.println(tenp[0].toString());
					System.out.println(tenp[1].toString());
					System.out.println(tenp[2].toString());
					
//					Experiment exp = (Experiment)l.get(y);
//					res[0][y][0] = exp.getId().toString();
//					res[0][y][1] = exp.getName();
				}
				
				tx.commit();
				
			} catch(RuntimeException re)
			{
				re.printStackTrace();
				tx.rollback();
			}
			
		}	catch(Exception e)
		{e.printStackTrace();}
		finally
		{s.close();}
		
		
		return res;
	}
}