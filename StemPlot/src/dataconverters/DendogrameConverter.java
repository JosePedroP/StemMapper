package dataconverters;

import java.util.ArrayList;

public class DendogrameConverter {

	//"(A|B)|(C|D)"
	//"B|(C|D)"
	
	public static Dendograme convertMatrix(double[][] den, String[] lables) {
		
		int[][] iden = new int[den.length][2];
		
		for(int a=0;a<den.length;a++)
		{
			iden[a][0] = new Double(den[a][0]).intValue();
			iden[a][1] = new Double(den[a][1]).intValue();
		}
		
		return convertMatrix(iden, lables);
	}
	
	public static Dendograme convertMatrix(int[][] den, String[] lables) { 
		
		ArrayList<Dendograme> dendos = new ArrayList<Dendograme>();
		
		for(int i=0;i<den.length;i++)
		{
			convertMatrixRecursive(den, lables, dendos, i);
		}
		
		Dendograme res = null;
		int topLevel = -1;
		
		for(int i=0;i<dendos.size();i++)
		{
			if(dendos.get(i).getLevel()>topLevel)
			{
				res = dendos.get(i);
				topLevel = dendos.get(i).getLevel();
			}
		}
		
		return res;
	}
	
	public static Dendograme convertMatrixRecursive(int[][] den, String[] lables, ArrayList<Dendograme> dendos, int pos) { 
		
		Dendograme res = getIfHasBeenProcessed(pos, dendos);
		
		if(res==null)
		{
			int id = pos+1;
			
			int[] toconv = den[pos];
			
			res = new Dendograme(id);
			
			if(toconv[0]<0)
			{
				res.setLeafA(getLable(lables,toconv[0]));
			}
			else
			{
				int pos2 = toconv[0]-1;
				res.setBreanchA(convertMatrixRecursive(den, lables, dendos, pos2));
			}
			
			if(toconv[1]<0)
			{
				res.setLeafB(getLable(lables,toconv[1]));
			}
			else
			{
				int pos2 = toconv[1]-1;
				res.setBreanchB(convertMatrixRecursive(den, lables, dendos, pos2));
			}
			
			int level = 0;
			
			if(res.getBreanchA()!=null)
			{
				level = res.getBreanchA().getLevel();
			}
			
			if(res.getBreanchB()!=null && level<res.getBreanchB().getLevel())
			{
				level = res.getBreanchB().getLevel();
			}
			
			level++;
			
			res.setLevel(level);
			
			dendos.add(res);
			
		}
		
		return res;
		
	}
	
	
	
	protected static Dendograme getIfHasBeenProcessed(int pos, ArrayList<Dendograme> dendos)
	{
		Dendograme res = null;
		
		int idpos = pos+1;
		
		for(int i=0;res==null && i<dendos.size();i++)
		{
			if(dendos.get(i).getrReference()==idpos)
				res = dendos.get(i);
		}
		
		return res;
	}
	
	protected static boolean hasBeenProcessed(int pos, ArrayList<Dendograme> dendos)
	{
		boolean res = false;
		
		int idpos = pos+1;
		
		for(int i=0;!res && i<dendos.size();i++)
		{
			res = dendos.get(i).getrReference()==idpos; 
		}
		
		return res;
	}
	
	
	protected static boolean isPrimitive(int[] a)
	{
		return a[0]<0 && a[1]<0;
	}
	
	protected static String getLable(String[] lables, int p)
	{
		int z = 0-p;
		z--;
		return lables[z];
	}
	
	public static void main(String[] args) {
		
		int[][] den = new int[][]{{-1,-2},{-3,-4},{1,2}};
//		int[][] den = new int[][]{{-1,-2},{1,3},{-3,-4}};
		String[] lables = new String[]{"A","B","C","D"};
		
		Dendograme de = DendogrameConverter.convertMatrix(den, lables);
		
	}
	
}
