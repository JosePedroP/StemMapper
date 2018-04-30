package beans;

import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import databaseAccess.DatabaseAccess;

@ManagedBean(eager=true)
@ApplicationScoped
public class StartUpBean {
	
	protected int iterator;
	protected String basepath;
	protected String[] masterIdList;
	protected String[][] entrezToIdPairs;

	protected String[][] sampleToType;
	protected String[][] sampleNIds;
	
	protected String[][] typeToColor = new String[][]{};
	protected String defaultColor = "Rgba(0, 0, 0, 0.95)";

	protected String[][][][][] sampleTypes;
	
	protected SessionFactory sessionFactory = null;
	
	public StartUpBean()
	{
		super();

		this.iterator = 1;
		
		this.basepath = "/home/jose/stemPlotFiles/";
//		this.basepath = "/home/jose/phb/plotFile/";
//		this.basepath = "D:/frostemmapper/";
		
		try {
			Configuration configuration = new Configuration();
			configuration.configure("hibernate.cfg.xml");
			ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
			this.sessionFactory = configuration.buildSessionFactory(serviceRegistry);
			
			Object[] temp = DatabaseAccess.getMasterList(this.sessionFactory);
			
			this.masterIdList = (String[])temp[0];
			this.entrezToIdPairs = (String[][])temp[1];
			
			Object[] tem = DatabaseAccess.getSampleNTypes(this.sessionFactory); //adding the sample id to the mix
			this.sampleToType = (String[][])tem[0];
			this.sampleNIds = (String[][])tem[1];

			
			this.sampleTypes = new String[2][][][][];
			
			String[] arr = new String[]{"mouse4302","hgu133plus2"};
			
			
			
			
			for(int a=0;a<arr.length;a++)
			{
				this.sampleTypes[a] = DatabaseAccess.getSampleTypes(this.sessionFactory, arr[a]);
			}
			
			
			
			//TODO: Fix latter ------------------------------
			ArrayList<String> tempa = new ArrayList<String>();
			
			for(int i=0;i<this.sampleToType.length;i++)
			{
				if(!tempa.contains(this.sampleToType[i][1]))
					tempa.add(this.sampleToType[i][1]);
			}
			
			
			this.typeToColor = ColorConverter.getNColors(tempa.toArray(new String[]{}));
			
			
			//TODO: End of fix ------------------------------
			
			
		} catch(Exception e)
		{e.printStackTrace();}
	}
	
	public int getNextIteration()
	{
		int res = this.iterator;
		this.iterator++;
		
		return res;
	}

	public String getBasepath() {
		return basepath;
	}
	
	public String[] getMasterIdList() {
		return masterIdList;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public String[][][][] getSampleTypes(int a) {
		return sampleTypes[a];
	}
	
	public String symbolToEntrez(String symbol)
	{
		String res = null;
		
		for(int i=0;res==null && i<this.entrezToIdPairs.length;i++)
		{
			if(this.entrezToIdPairs[i][1].equals(symbol))
			{
				res = this.entrezToIdPairs[i][0];
			}
		}
		
		if(res==null) res = symbol;
		
		return res;
	}
	
	public String[] entrezAllIds(String entrez)
	{
		ArrayList<String> temp = new ArrayList<String>();
		
		temp.add(entrez);
		
		for(int i=0;i<this.entrezToIdPairs.length;i++)
		{
			if(this.entrezToIdPairs[i][0].equals(entrez))
			{
				temp.add(this.entrezToIdPairs[i][1]);
			}
		}
		
		String[] res = new String[temp.size()];
		
		for(int i=0;i<temp.size();i++) res[i] = temp.get(i);
		
		return res;
	}
	
	public String[] listOfAllIds(String[] input)
	{
		ArrayList<String> temp = new ArrayList<String>();
		
		for(int i=0;i<input.length;i++)
		{
			String[] ka = this.entrezAllIds(this.symbolToEntrez(input[i]));
			
			for(int k=0;k<ka.length;k++)
			{
				temp.add(ka[k]);
			}
		}
		
		return temp.toArray(new String[]{});
	}
	
	public String getSampleColor(String sample)
	{
		String res = this.defaultColor;
		
		String type = null;
		
		for(int i=0;type==null && i<this.sampleToType.length;i++)
		{
			if(sample.equals(this.sampleToType[i][0])) type = this.sampleToType[i][1];
		}
		
		boolean stop = false;
		
		for(int i=0;type!=null && !stop && i<this.typeToColor.length;i++)
		{
			if(type.equals(this.typeToColor[i][0]))
			{
				res = this.typeToColor[i][1];
				stop = true;
			}
		}
		
		return res;
	}
	
	public String[] getSamplesColor(String[] samples)
	{
		String[] res = new String[samples.length];
		
		for(int i=0;i<samples.length;i++)
		{
			res[i] = this.getSampleColor(samples[i]);
		}
		
		return res;
	}

	public String[][] getTypeToColor() {
		
		int siz = typeToColor.length;
		
		String[][] res = new String[siz][2];
		
		for(int i=0;i<siz;i++)
		{
			String[] dak = typeToColor[i][1].split("\\(")[1].split(",");
			
			res[i][0] = typeToColor[i][0];
			res[i][1] = "rgb("+dak[0]+", "+dak[1]+", "+dak[2]+")";
		}
		
		return res;
	}

	public String[][] getSampleNIds() {
		return sampleNIds;
	}
	
	
	
	
}
