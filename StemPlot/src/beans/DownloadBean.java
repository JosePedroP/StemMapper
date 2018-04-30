package beans;

import java.io.OutputStream;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import databaseAccess.DatabaseAccess;

@ManagedBean
@RequestScoped
public class DownloadBean {

	@ManagedProperty(value = "#{startUpBean}")
	protected StartUpBean startUpBean;

	protected String inputVal = "";
	protected String filename = "";
	protected String extraparameters = "";
	
	protected String[][] samples = new String[][]{};
	
	public String getExtraparameters() {
		return extraparameters;
	}

	public void setExtraparameters(String extraparameters) {
		this.extraparameters = extraparameters;
	}
	
	public StartUpBean getStartUpBean() {
		return startUpBean;
	}

	public void setStartUpBean(StartUpBean startUpBean) {
		this.startUpBean = startUpBean;
	}
	
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getInputVal() {
		return inputVal;
	}

	public void setInputVal(String inputVal) {
		this.inputVal = inputVal;
	}

	public String getTable()
	{
		try {
			byte[] file = this.inputVal.getBytes();
			FacesContext fc = FacesContext.getCurrentInstance();
		    ExternalContext ec = fc.getExternalContext();
		    ec.responseReset();
		    ec.setResponseContentType("text/plain");
		    ec.setResponseContentLength(file.length);
		    ec.setResponseHeader("Content-Disposition", "attachment; filename=\""+this.filename+".txt\"");
		    OutputStream output = ec.getResponseOutputStream();
		    output.write(file);
		    fc.responseComplete();
			
		} catch(Exception e)
		{e.printStackTrace();}
		
		return null;
	}

	public String getText()
	{
		try {
			
			Object[] out1 = DatabaseAccess.getSampleForFile(this.startUpBean.getSessionFactory(), this.inputVal);
			
			String out = out1[0].toString();
			String name = (String)out1[1];
			
			byte[] file = out.getBytes();
			FacesContext fc = FacesContext.getCurrentInstance();
		    ExternalContext ec = fc.getExternalContext();
		    ec.responseReset();
		    ec.setResponseContentType("text/plain");
		    ec.setResponseContentLength(file.length);
		    ec.setResponseHeader("Content-Disposition", "attachment; filename=\""+name+".txt\"");
		    OutputStream output = ec.getResponseOutputStream();
		    output.write(file);
		    fc.responseComplete();
			
		} catch(Exception e)
		{e.printStackTrace();}
		
		return null;
	}

	public String getTextExp()
	{
		try {
			
//			System.out.println(">>"+this.extraparameters+"<<");
			Object[] out1;
			
			if(this.extraparameters.equals("")) out1 = DatabaseAccess.getExperimentForFile(this.startUpBean.getSessionFactory(), this.inputVal, null);
			else
			{
				String[] spe = this.extraparameters.split(";");
				Integer[] idzs = new Integer[spe.length];
				for(int y=0;y<spe.length;y++)
				{
					idzs[y] = new Integer(spe[y]);
				}
				
				out1 = DatabaseAccess.getExperimentForFile(this.startUpBean.getSessionFactory(), this.inputVal, idzs);
			}
			
			String out = out1[0].toString();
			String name = (String)out1[1];
			
			byte[] file = out.getBytes();
			FacesContext fc = FacesContext.getCurrentInstance();
		    ExternalContext ec = fc.getExternalContext();
		    ec.responseReset();
		    ec.setResponseContentType("text/plain");
		    ec.setResponseContentLength(file.length);
		    ec.setResponseHeader("Content-Disposition", "attachment; filename=\""+name+".txt\"");
		    OutputStream output = ec.getResponseOutputStream();
		    output.write(file);
		    fc.responseComplete();
			
		} catch(Exception e)
		{e.printStackTrace();}
		
		return null;
	}

	public String getExpMat()
	{
		try {
			
//			System.out.println(">>"+this.extraparameters+"<<");
			Object[] out1;
			
			
			
			if(this.extraparameters.equals("")) out1 = DatabaseAccess.getExpressionMatrixForFile(this.startUpBean.getSessionFactory(), this.inputVal, null);
			else
			{
				String[] spe = this.extraparameters.split(";");
				Integer[] idzs = new Integer[spe.length];
				for(int y=0;y<spe.length;y++)
				{
					idzs[y] = new Integer(spe[y]);
				}
				
				out1 = DatabaseAccess.getExpressionMatrixForFile(this.startUpBean.getSessionFactory(), this.inputVal, idzs);
			}
			
			String out = out1[0].toString();
			String name = (String)out1[1];
//			String name = "tez";
//			String out = out1;
			
			byte[] file = out.getBytes();
			FacesContext fc = FacesContext.getCurrentInstance();
		    ExternalContext ec = fc.getExternalContext();
		    ec.responseReset();
		    ec.setResponseContentType("text/plain");
		    ec.setResponseContentLength(file.length);
		    ec.setResponseHeader("Content-Disposition", "attachment; filename=\""+name+"_expressionmatrix.txt\"");
		    OutputStream output = ec.getResponseOutputStream();
		    output.write(file);
		    fc.responseComplete();
			
		} catch(Exception e)
		{e.printStackTrace();}
		
		return null;
	}

	public String heattable()
	{
		try {
			
			String[] vals = this.inputVal.split("\\*");
			
			
			String[] names = vals[2].split("\\|");
			
			StringBuffer res = new StringBuffer();
			
			res.append("Gene_name");
			
			for(String name: names)
			{
				res.append("\t");
				res.append(name);
			}
			
			res.append("\n");
			
			String[] rownames = vals[1].split("\\|");
			String[] rows = vals[0].split("\\?");
			
			for(int i=rownames.length-1;i>-1;i--)
			{
				res.append(rownames[i]);
				String[] colvals = rows[i].split("\\|");
				
				for(String col: colvals)
				{
					res.append("\t");
					res.append(col);
				}
				
				res.append("\n");
			}
			
			byte[] file = res.toString().getBytes();
			FacesContext fc = FacesContext.getCurrentInstance();
		    ExternalContext ec = fc.getExternalContext();
		    ec.responseReset();
		    ec.setResponseContentType("text/plain");
		    ec.setResponseContentLength(file.length);
		    ec.setResponseHeader("Content-Disposition", "attachment; filename=\"gene_expression.txt\"");
		    OutputStream output = ec.getResponseOutputStream();
		    output.write(file);
		    fc.responseComplete();
		    
		} catch(Exception e)
		{e.printStackTrace();}
		
		return null;
	}
	
	
}
