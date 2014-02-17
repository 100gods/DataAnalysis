package one.two.DataAnalysis.feature;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class MetaData {

	public static class MetaDataMapper extends
			Mapper<LongWritable, Text, Text, Text> {
		public static String[] headers;

		@Override
		protected void setup(Context context) throws IOException,
				InterruptedException {
			headers = context.getConfiguration().get("header").trim()
					.split("\\,");
		}

		@Override
		protected void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			String[] words = value.toString().split("\\,");
			if (key.get() != 0) {
				for (int i = 2; i < 6; i++) {
					context.write(new Text(headers[i]), new Text(words[i]));
				}
			}

		}
	}

	public static class MetaDataReducer extends Reducer<Text, Text, Text, Text> {
		@Override
		protected void reduce(Text key, Iterable<Text> values, Context context)
				throws IOException, InterruptedException {
			System.out.println(HeaderType.valueOf(
					key.toString().trim().replaceAll("\\ ", "")).toString());
			switch (HeaderType.valueOf(
					key.toString().trim().replaceAll("\\ ", "")).toString()) {
			case "number":
				System.out.println("number me ");
				double max = Integer.MIN_VALUE;
				double min = Integer.MAX_VALUE;
				double avg = 0.0;
				Integer count = 0;
				for (Text value : values) {
					double curr = Double.parseDouble(value.toString().trim());
					max = max <= curr ? curr : max;
					min = min >= curr ? curr : min;
					avg += curr;
					count++;

				}
				context.write(
						key,
						new Text("max=" + String.valueOf(max) + "\t min="
								+ String.valueOf(min) + "\t AVg="
								+ String.valueOf(avg / count)));

				break;
			case "words":
				System.out.println("words me ");
				Map<String, Integer> countOfCat=new HashMap<String, Integer>();
				count=0;
				for (Text word : values) {
					count=countOfCat.get(word.toString()) ;
					if (null != count) {
						countOfCat.put(key.toString(), count++);
					}else{
						countOfCat.put(key.toString(), 1);
					}
				} 
				String tmp=new String();
				for (Entry<String, Integer> entry : countOfCat.entrySet()) {
					tmp+=entry.getKey()+"="+entry.getValue()+"\t";
				}
				context.write(key, new Text(tmp));
				break;

			default:
				break;
			}
			
			
			

		}

	}

	public static void main(String[] args) {
		try {
			Configuration configuration = new Configuration();
			configuration
					.set("header",
							"id,tstamp,Queue Time,Hold Time,Talk Time,Agent Skill,Agent Tenure,Case Severity,Answer Within Service Level,Brand,Case Status,Product,Vendor,Location,Customer Satisfaction,Satisfaction with Agent,Brand - Satisfaction,Repurchase Likelihood,Recommend Likelihood,Problem Resolution,First Contact Resolution,Resolved within 2");
			Job job = new Job(configuration);
			job.setJarByClass(MetaData.class);
			job.setJobName("Meata Data for valiables");

			FileInputFormat.addInputPath(job, new Path(args[0]));
			FileOutputFormat.setOutputPath(job, new Path(args[1]));

			job.setInputFormatClass(TextInputFormat.class);
			job.setOutputFormatClass(TextOutputFormat.class);

			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(Text.class);

			job.setMapperClass(MetaDataMapper.class);
			job.setReducerClass(MetaDataReducer.class);
			job.waitForCompletion(true);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
