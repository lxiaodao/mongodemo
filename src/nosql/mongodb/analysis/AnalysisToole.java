package nosql.mongodb.analysis;

public class AnalysisToole {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String filepath="D:\\workspace\\Mongo\\mongo_total_2000.txt";
		FileAnalysisPerformance tool=new FileAnalysisPerformance(filepath);
		tool.analysis();

	}

}
