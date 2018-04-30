package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import databaseAccess.DatabaseAccess;

@ManagedBean
@RequestScoped
public class SampleDataBean {

	@ManagedProperty(value = "#{startUpBean}")
	protected StartUpBean startUpBean;
	
	protected String setId = "";
	
	protected String name = "";
	protected String supertype = "";
	protected String type = "";
	protected String ename = "";
	protected String conditions = "";
	protected String vivovitro = "";
	protected String time = "";
	protected String eid = "";
	protected String organims = "";
	protected String platform = "";
	protected String surfacemarkers = "";
	
	protected String[][] expression = new String[][]{};
	
	protected boolean showButton = true;
	
	
	public String getOrganims() {
		return organims;
	}

	public void setOrganims(String organims) {
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
	}

	public boolean isShowButton() {
		return showButton;
	}

	public void setShowButton(boolean showButton) {
	}

	public String[][] getExpression() {
		return expression;
	}

	public void setExpression(String[][] expression) {
	}

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
	}

	public String getSupertype() {
		return supertype;
	}

	public void setSupertype(String supertype) {
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
	}

	public String getConditions() {
		return conditions;
	}

	public void setConditions(String conditions) {
	}

	public String getVivovitro() {
		return vivovitro;
	}

	public void setVivovitro(String vivovitro) {
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
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
	
	public String getSurfacemarkers() {
		return surfacemarkers;
	}

	public void setSurfacemarkers(String surfacemarkers) {
		this.surfacemarkers = surfacemarkers;
	}

	public String getSampleData() throws Exception 
	{
		String[] res = DatabaseAccess.getSample(this.startUpBean.getSessionFactory(), this.setId);
		
		this.name = res[0];
		this.supertype = res[1];
		this.type = res[2];
		this.ename = res[3];
		this.conditions = res[4];
		this.vivovitro = res[5];
		this.time = res[6];
		this.eid = res[7];
		this.platform = res[8];
		this.organims = res[9];
		this.surfacemarkers = res[10];
		
		return null;
	}
	
	public String getSampleExpression() throws Exception 
	{
		this.expression = DatabaseAccess.getSampleExpression(this.startUpBean.getSessionFactory(), this.setId);
		this.showButton = false;
		
		return null;
	}
	
	
	
}
