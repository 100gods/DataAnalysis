package one.two.DataAnalysis.impl;

import one.two.DataAnalysis.IDataAnalysis;
import one.two.DataAnalysis.feature.MetaData;

public class DataAnalysis implements IDataAnalysis {

	@Override
	public void metaData(String[] args) {
		MetaData.main(args);
	}

}
