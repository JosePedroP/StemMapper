package dataconverters;

public class Dendograme {

	protected String leafA;
	protected String leafB;
	protected Dendograme breanchA;
	protected Dendograme breanchB;
	protected int rReference;
	protected int level;
	
	public Dendograme(int rReference) {
		this.leafA = null;
		this.leafB = null;
		this.breanchA = null;
		this.breanchB = null;
		this.rReference = rReference;
		this.level = -1;
	}

	public String getLeafA() {
		return leafA;
	}

	public void setLeafA(String leafA) {
		this.leafA = leafA;
	}

	public String getLeafB() {
		return leafB;
	}

	public void setLeafB(String leafB) {
		this.leafB = leafB;
	}

	public Dendograme getBreanchA() {
		return breanchA;
	}

	public void setBreanchA(Dendograme breanchA) {
		this.breanchA = breanchA;
	}

	public Dendograme getBreanchB() {
		return breanchB;
	}

	public void setBreanchB(Dendograme breanchB) {
		this.breanchB = breanchB;
	}

	public int getrReference() {
		return rReference;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	@Override
	public String toString()
	{
//		String res = "|";
		String partA = "";
		String partB = "";
		
		if(this.leafA!=null)
		{
			partA = this.leafA;
		}
		else
		{
			partA = "("+this.breanchA.toString()+")";
		}
		
		if(this.leafB!=null)
		{
			partB = this.leafB;
		}
		else
		{
			partB = "("+this.breanchB.toString()+")";
		}
		
		return partA+"|"+partB;
	}
	
	public String[] getOrder()
	{
		String[] res = null;
		
		if(this.leafA!=null && this.leafB!=null)
		{
			res = new String[]{this.leafA, this.leafB};
		}
		else if(this.leafA!=null && this.leafB==null)
		{
			String[] stB = this.breanchB.getOrder();
			
			res = new String[stB.length+1];
			
			res[0] = this.leafA;
			
			int z = 1;
			
			for(int i=0;i<stB.length;i++)
			{
				res[z] = stB[i];
				z++;
			}
		}
		else if(this.leafA==null && this.leafB!=null)
		{
			String[] stA = this.breanchA.getOrder();
			
			res = new String[stA.length+1];
			
			int len = stA.length;
			
			for(int i=0;i<len;i++)
			{
				res[i] = stA[i];
			}
			
			res[len] = this.leafB;
		}
		else
		{
			String[] stA = this.breanchA.getOrder();
			String[] stB = this.breanchB.getOrder();
			
			int z = 0;
			
			res = new String[stA.length+stB.length];
			
			for(int i=0;i<stA.length;i++)
			{
				res[z] = stA[i];
				z++;
			}
			
			for(int i=0;i<stB.length;i++)
			{
				res[z] = stB[i];
				z++;
			}
		}
		
		
		return res;
	}
	
}
