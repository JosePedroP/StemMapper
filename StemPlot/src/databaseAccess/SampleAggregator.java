package databaseAccess;

import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.apache.commons.math3.stat.correlation.SpearmansCorrelation;

public class SampleAggregator {
	
	private int mainid;
	private double[] mainexpression;
	private int[] secids;
	private int[] secnames;
	private String[] secsymbol;
	private double[][] secexpressions;
	private int iterator;
	private boolean started;
	
	public SampleAggregator(int mainid, int numexps, int numsecs) {
		this.mainid=mainid;
		this.mainexpression=new double[numexps];
		this.secids=new int[numsecs];
		this.secnames=new int[numsecs];
		this.secsymbol=new String[numsecs];
		this.secexpressions=new double[numsecs][numexps];
		this.iterator=0;
		this.started=false;
		
	}
	
	public void addSample(Sample sam) {
		
		Object[] exps = sam.getExpressions().toArray();
		
		if(!this.started)
		{
			int z=0;
			for(Object expo: exps)
			{
				Expression exp = (Expression)expo;
				
				int gid = exp.getGenes().getId();
				
				if(gid==this.mainid)
				{
					this.mainexpression[this.iterator] = exp.getCfrma();
				}
				else
				{
					this.secids[z] = gid;
					this.secnames[z] = exp.getGenes().getEntrez();
					this.secsymbol[z] = exp.getGenes().getGenesymbol();
					this.secexpressions[z][this.iterator] = exp.getCfrma();
					z++;
				}
			}
			this.started=true;
		}
		else
		{
			for(Object expo: exps)
			{
				Expression exp = (Expression)expo;
				
				int gid = exp.getGenes().getId();
				
				if(gid==this.mainid)
				{
					this.mainexpression[this.iterator] = exp.getCfrma();
				}
				else
				{
					int z = -1;
					
					for(int i=0;z==-1 && i<this.secids.length;i++)
					{
						if(this.secids[i]==gid)
						{
							z=i;
						}
					}
					
					this.secexpressions[z][this.iterator] = exp.getCfrma();
				}
				
			}
		}
		
		this.iterator++;
		
	}

	public int[] getSecnames() {
		return secnames;
	}
	
	public String[] getSecsymbol() {
		return secsymbol;
	}

	public double[] calcualteSpearmansCorrelation()
	{
		SpearmansCorrelation sc = new SpearmansCorrelation();
		
		double[] res = new double[this.secexpressions.length];
		
		int i=0;
		
		for(double[] exp: this.secexpressions)
		{
			
			res[i] = sc.correlation(this.mainexpression, exp);
			i++;
		}
		
		return res;
	}

	public double[][] calcualtePearsonsCorrelation()
	{
		double[] res1 = new double[this.secexpressions.length];
		double[] res2 = new double[this.secexpressions.length];
		
		int i=0;
		
		for(double[] exp: this.secexpressions)
		{
			
			double[][] revmat = new double[this.mainexpression.length][2];
			
			for(int z=0;z<this.mainexpression.length;z++)
			{
				revmat[z][0] = this.mainexpression[z];
				revmat[z][1] = exp[z];
			}
			
			PearsonsCorrelation pc = new PearsonsCorrelation(revmat);
			
			res1[i] = pc.getCorrelationMatrix().getEntry(0, 1);
			res2[i] = pc.getCorrelationPValues().getEntry(0, 1);
			
//			res[i] = sc.correlation(this.mainexpression, exp);
			i++;
		}
		
		return new double[][]{res1,res2};
	}
	
	
	
}
