package com.ibaiqiu.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ReadTxt {
	
    public static void readTxtFile(String filePath,List<String> tid){ 
        try { 
            String encoding="GBK"; 
            File file=new File(filePath); 
            if(file.isFile() && file.exists()){ //判断文件是否存在 
              InputStreamReader read = new InputStreamReader( 
              new FileInputStream(file),encoding);//考虑到编码格式 
              BufferedReader bufferedReader = new BufferedReader(read); 
              String lineTxt = null; 
              while((lineTxt = bufferedReader.readLine()) != null){ 
            	
//                System.out.println(temp[0]);
            	 tid.add("\""+lineTxt+"\"");
            	  
              } 
              read.close(); 
              System.out.println(tid.size());
        }else{ 
          System.out.println("找不到指定的文件"); 
        } 
        } catch (Exception e) { 
          System.out.println("读取文件内容出错"); 
          e.printStackTrace(); 
        } 
       
      } 
    
	    public static void main(String[] args) {
	    	 String BIfilePath = "D:\\temp.txt"; 
	    	 List<String> BItid = new ArrayList<>();
	    	readTxtFile(BIfilePath,BItid); 
	    	System.out.println(BItid);
		}
       
//      public static void main(String argv[]){ 
//    	List<String> diffTid = new ArrayList<>();
//    	List<String> diffTidA = new ArrayList<>();
//    	List<String> BItid = new ArrayList<>();
//    	List<String> ECStid = new ArrayList<>();
//        String BIfilePath = "D:\\BI.txt"; 
//        String ECSfilePath = "D:\\smcp.txt"; 
//        readTxtFile(BIfilePath,BItid); 
//        readTxtFile(ECSfilePath,ECStid); 
//        System.out.println(BItid.size()-ECStid.size());
//        for (int i = 0; i < BItid.size(); i++) {
//        	if(!ECStid.contains(BItid.get(i))) {
//        		diffTid.add(BItid.get(i));
//        	}
//       	}
//        for (int i = 0; i < ECStid.size(); i++) {
//			if(!BItid.contains(ECStid.get(i))) {
//				diffTidA.add(ECStid.get(i));
//			}
//		}
//        System.out.println(diffTid.size());
//        System.out.println(diffTidA.size());
//        for (int i = 0; i < diffTid.size(); i++) {
//			System.out.println(diffTid.get(i));
//		}
//        
//        /*for (int i = 0; i < diffTidA.size(); i++) {
//			System.out.println(diffTidA.get(i));
//		}*/
//      } 
     
}
