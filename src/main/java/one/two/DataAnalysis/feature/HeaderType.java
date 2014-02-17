/**
 * 
 */
package one.two.DataAnalysis.feature;

import java.sql.Timestamp;

/**
 * @author saurabh
 *
 */
public enum HeaderType {
	
//	id,tstamp,Queue Time,Hold Time,Talk Time,Agent Skill,Agent Tenure,Case Severity,Answer Within Service Level,Brand,Case Status,Product,Vendor,Location,Customer Satisfaction,Satisfaction with Agent,Brand - Satisfaction,Repurchase Likelihood,Recommend Likelihood,Problem Resolution,First Contact Resolution,Resolved within 2
	id(int.class),
	tstamp(Timestamp.class),
	QueueTime(int.class),
	HoldTime(int.class),
	TalkTime(int.class);
	
	
	private Class typeOf;
	private HeaderType(Class type) {
		this.typeOf=type;
	}

}
