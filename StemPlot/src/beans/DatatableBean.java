package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import databaseAccess.DatabaseAccess;

@ManagedBean
@RequestScoped
public class DatatableBean {

	@ManagedProperty(value = "#{startUpBean}")
	protected StartUpBean startUpBean;
	
	public DatatableBean() {
		super();
		
	}

	public StartUpBean getStartUpBean() {
		return startUpBean;
	}

	public void setStartUpBean(StartUpBean startUpBean) {
		this.startUpBean = startUpBean;
//		DatabaseAccess.getDataNumbers(this.startUpBean.getSessionFactory(),"Mus musculus");
	}

	public String[][][][] getMouse() {
		return this.startUpBean.getSampleTypes(0);
	}
	
	public String[][][][] getHuman() {
		return this.startUpBean.getSampleTypes(1);
	}
	
	public String[][][][] getDataTypes() { //TODO: change here
//		int i=-1;
//		
//		if(this.platform.equals("mouse4302")) i = 0;
//		else if(this.platform.equals("hgu133plus2")) i = 1;
		
		return this.startUpBean.getSampleTypes(0);
	}
	
}
