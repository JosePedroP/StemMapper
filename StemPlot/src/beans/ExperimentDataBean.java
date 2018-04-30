package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import databaseAccess.DatabaseAccess;

@ManagedBean
@RequestScoped
public class ExperimentDataBean {

	@ManagedProperty(value = "#{startUpBean}")
	protected StartUpBean startUpBean;
	
	protected String setId = "";
	
	protected String name = "";
	protected String types = "";
	protected String supertypes = "";
	protected String extraparameters = "";
	
	protected String[][] samples = new String[][]{};
	
	public String getExtraparameters() {
		return extraparameters;
	}

	public void setExtraparameters(String extraparameters) {
		this.extraparameters = extraparameters;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
	}

	public String getSetId() {
		return setId;
	}
	
	public void setSetId(String setId) {
		this.setId = setId;
	}

	public StartUpBean getStartUpBean() {
		return startUpBean;
	}

	public void setStartUpBean(StartUpBean startUpBean) {
		this.startUpBean = startUpBean;
	}
	
	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
	}

	public String getSupertypes() {
		return supertypes;
	}

	public void setSupertypes(String supertypes) {
	}
	
	public String[][] getSamples() {
		return samples;
	}

	public void setSamples(String[][] samples) {
	}

	public String getSampleData() throws Exception 
	{
//		System.out.println("this.extraparameters:"+this.extraparameters+"<");
		
		Object[] resp;
		
		if(this.extraparameters.equals(""))
			resp = DatabaseAccess.getExperiment(this.startUpBean.getSessionFactory(), this.setId);
		else
		{
			String[] spe = this.extraparameters.split(";");
			Integer[] idzs = new Integer[spe.length];
			for(int y=0;y<spe.length;y++)
			{
				idzs[y] = new Integer(spe[y]);
//				System.out.println(idzs[y]);
			}
			
			resp = DatabaseAccess.getExperiment(this.startUpBean.getSessionFactory(),
				this.setId, idzs);
		}
		
		String[] res = (String[])resp[0];
		
		this.name = res[0];
		this.types = res[1];
		this.supertypes = res[2];
		
		this.samples = (String[][])resp[1];
		
		return null;
	}
	
	
	
}
