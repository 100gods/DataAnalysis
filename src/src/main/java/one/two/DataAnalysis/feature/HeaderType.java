/**
 * 
 */
package one.two.DataAnalysis.feature;

import com.sun.xml.bind.v2.runtime.RuntimeUtil.ToStringAdapter;


/**
 * @author saurabh
 *
 */
public enum HeaderType {
	
//	id,tstamp,Queue Time,Hold Time,Talk Time,Agent Skill,Agent Tenure,Case Severity,
//	Answer Within Service Level,Brand,Case Status,Product,Vendor,Location,Customer Satisfaction,Satisfaction with Agent,Brand - Satisfaction,Repurchase Likelihood,Recommend Likelihood,Problem Resolution,First Contact Resolution,Resolved within 2
	id("number"),
	tstamp("number"),
	QueueTime("number"),
	HoldTime("number"),
	TalkTime("number"),
	AgentSkill("words"),
	AgentTenure("number"),
	CaseSeverity("words"),
	AnswerWithinServiceLevel("words"),
	Brand("words"),
	CaseStatus("words");
	
	
	
	
	
	
	private String typeOf;
	private HeaderType(String type) {
		this.typeOf=type;
	}
	public String toString(){
	       return typeOf;
	    }

}
