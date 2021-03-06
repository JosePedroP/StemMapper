package databaseAccess;
// Generated 31/ago/2017 12:20:35 by Hibernate Tools 4.0.1.Final

import java.util.HashSet;
import java.util.Set;

/**
 * Sample generated by hbm2java
 */
public class Sample implements java.io.Serializable {

	private Integer id;
	private Samplecondition samplecondition;
	private Sampletype sampletype;
	private Organisms organisms;
	private Sampletime sampletime;
	private Experiment experiment;
	private String name;
	private String source;
	private String platform;
	private String wildtype;
	private String invivo;
	private String purestem;
	private String surfacemarkers;
	private Set samplekeywordses = new HashSet(0);
	private Set expressions = new HashSet(0);

	public Sample() {
	}

	public Sample(Organisms organisms, Experiment experiment, String name, String platform, String wildtype,
			String invivo) {
		this.organisms = organisms;
		this.experiment = experiment;
		this.name = name;
		this.platform = platform;
		this.wildtype = wildtype;
		this.invivo = invivo;
	}

	public Sample(Samplecondition samplecondition, Sampletype sampletype, Organisms organisms, Sampletime sampletime,
			Experiment experiment, String name, String source, String platform, String wildtype, String invivo,
			String purestem, String surfacemarkers, Set samplekeywordses, Set expressions) {
		this.samplecondition = samplecondition;
		this.sampletype = sampletype;
		this.organisms = organisms;
		this.sampletime = sampletime;
		this.experiment = experiment;
		this.name = name;
		this.source = source;
		this.platform = platform;
		this.wildtype = wildtype;
		this.invivo = invivo;
		this.purestem = purestem;
		this.surfacemarkers = surfacemarkers;
		this.samplekeywordses = samplekeywordses;
		this.expressions = expressions;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Samplecondition getSamplecondition() {
		return this.samplecondition;
	}

	public void setSamplecondition(Samplecondition samplecondition) {
		this.samplecondition = samplecondition;
	}

	public Sampletype getSampletype() {
		return this.sampletype;
	}

	public void setSampletype(Sampletype sampletype) {
		this.sampletype = sampletype;
	}

	public Organisms getOrganisms() {
		return this.organisms;
	}

	public void setOrganisms(Organisms organisms) {
		this.organisms = organisms;
	}

	public Sampletime getSampletime() {
		return this.sampletime;
	}

	public void setSampletime(Sampletime sampletime) {
		this.sampletime = sampletime;
	}

	public Experiment getExperiment() {
		return this.experiment;
	}

	public void setExperiment(Experiment experiment) {
		this.experiment = experiment;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getPlatform() {
		return this.platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getWildtype() {
		return this.wildtype;
	}

	public void setWildtype(String wildtype) {
		this.wildtype = wildtype;
	}

	public String getInvivo() {
		return this.invivo;
	}

	public void setInvivo(String invivo) {
		this.invivo = invivo;
	}

	public String getPurestem() {
		return this.purestem;
	}

	public void setPurestem(String purestem) {
		this.purestem = purestem;
	}

	public String getSurfacemarkers() {
		return this.surfacemarkers;
	}

	public void setSurfacemarkers(String surfacemarkers) {
		this.surfacemarkers = surfacemarkers;
	}

	public Set getSamplekeywordses() {
		return this.samplekeywordses;
	}

	public void setSamplekeywordses(Set samplekeywordses) {
		this.samplekeywordses = samplekeywordses;
	}

	public Set getExpressions() {
		return this.expressions;
	}

	public void setExpressions(Set expressions) {
		this.expressions = expressions;
	}

}
