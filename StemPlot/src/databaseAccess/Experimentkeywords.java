package databaseAccess;
// Generated 28/mar/2017 18:11:50 by Hibernate Tools 4.0.1.Final

/**
 * Experimentkeywords generated by hbm2java
 */
public class Experimentkeywords implements java.io.Serializable {

	private Integer id;
	private Experiment experiment;
	private Keywords keywords;

	public Experimentkeywords() {
	}

	public Experimentkeywords(Experiment experiment, Keywords keywords) {
		this.experiment = experiment;
		this.keywords = keywords;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Experiment getExperiment() {
		return this.experiment;
	}

	public void setExperiment(Experiment experiment) {
		this.experiment = experiment;
	}

	public Keywords getKeywords() {
		return this.keywords;
	}

	public void setKeywords(Keywords keywords) {
		this.keywords = keywords;
	}

}