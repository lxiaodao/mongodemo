package nosql.mongodb.analysis;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FileAnalysisPerformance implements IPerformance {
    private String filepath;
    
    public FileAnalysisPerformance(String filepath) {
		super();
		this.filepath = filepath;
	}

	public void analysis() {
		

		// 建立缓冲流
		BufferedReader buffer;
		List<Element> list=new ArrayList<Element>();
		int failNumber=0;
		long allTime=0;
		try {
			buffer = new BufferedReader(new InputStreamReader(
					new FileInputStream(filepath)));
        
			while (buffer.ready()) {
				String firstLine = buffer.readLine();
			
				if (firstLine != null && !firstLine.equals("")) {

					if(firstLine.contains("false")||firstLine.contains("true")){
						String[] arr=firstLine.trim().split(",");
						boolean sucess="true".equals(arr[0]);
						if(!sucess){
							failNumber++;
						}
						allTime+=Long.parseLong(arr[1]);
						list.add(new Element(sucess,Long.parseLong(arr[1])));
						
					}

				}
				
			} 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//排序
		Collections.sort(list, new Comparator<Element>(){

			@Override
			public int compare(Element o1, Element o2) {
				
				return (int)(o1.getNeedtime()-o2.getNeedtime()) ;
			}}
		
		);

        //打印结果
		System.out.println("---ALL number----:"+list.size());
		System.out.println("---FAIL number----:"+failNumber);
		System.out.println("---SUCESS number----:"+(list.size()-failNumber));		
		System.out.println("---Max time----:"+(list.get(list.size()-1).getNeedtime()));
		System.out.println("---Min time----:"+(list.get(0).getNeedtime()));
		System.out.println("---90% AVERAGE time----:"+(allTime/list.size()));
	}

}
class Element{
	private boolean sucess;
	public long getNeedtime() {
		return needtime;
	}

	public void setNeedtime(long needtime) {
		this.needtime = needtime;
	}

	private long needtime;
	
	public Element(boolean sucess, long needtime) {
		super();
		this.sucess = sucess;
		this.needtime = needtime;
	}
	
}