package databaseAccess;
// Generated 28/mar/2017 18:11:50 by Hibernate Tools 4.0.1.Final

import java.util.HashSet;
import java.util.Set;

/**
 * Samplecondition generated by hbm2java
 */
public class Samplecondition implements java.io.Serializable {

	private Integer id;
	private String conditions;
	private Set samples = new HashSet(0);

	public Samplecondition() {
	}

	public Samplecondition(String conditions) {
		this.conditions = conditions;
	}

	public Samplecondition(String conditions, Set samples) {
		this.conditions = conditions;
		this.samples = samples;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getConditions() {
		return this.conditions;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

	public Set getSamples() {
		return this.samples;
	}

	public void setSamples(Set samples) {
		this.samples = samples;
	}

}