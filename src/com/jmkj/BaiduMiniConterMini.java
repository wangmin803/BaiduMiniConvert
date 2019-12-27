package com.jmkj;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

public class BaiduMiniConterMini {

	public static int fileCount = 0;
	public static final String CONVERTE_TYPE_WX="wx";
	public static final String CONVERTE_TYPE_TOUTIAO="tt";
	
	
	public static final String CONVERTE_TYPE_DIRECTORY_WX="weixinminiprogram";
	public static final String CONVERTE_TYPE_DIRECTORY_TOUTIAO="toutiaomini";
	
	
	public static void main(String args[])
	
	{
		String parentdir="D:/work/";
		
		String dir = parentdir+"baidumini";
	
		String dir1 = parentdir+CONVERTE_TYPE_DIRECTORY_TOUTIAO;
		File filenew = new File(dir1);
		if(!filenew.exists()) {
			filenew.mkdir();
		}
		
		print_file(dir,CONVERTE_TYPE_WX,CONVERTE_TYPE_DIRECTORY_WX);

	}
	public static void print_file (String dir,String type,String typedirectory)
	{
		File file = new File(dir);
		
	
		File files[] = file.listFiles();
		for(File tempFile : files)
		{
			
	
			if(!tempFile.getAbsolutePath().contains("\\.")) {
				if(tempFile.isDirectory())
				{
					
					File filenew1 = new File(tempFile.getAbsolutePath().replace("baidumini", typedirectory));
					if(!filenew1.exists()) {
						filenew1.mkdirs();
					}
				
				
					print_file(tempFile.getAbsolutePath(),type,typedirectory);
				}else{
					fileCount++;
					
					String path1=tempFile.getAbsolutePath();
					String path2 = tempFile.getAbsolutePath().replace("baidumini", typedirectory);
					readwrite(path1,path2,type);
				}
			}
			
		}
	}
	
	
	
	public static void  readwrite(String path1,String path2,String type) {
		if(path1.contains("img")||path1.contains("image")) {
			
			File originalFile = new File(path1);//ָ��Ҫ��ȡ��ͼƬ
	        try {
	            File result = new File(path2);//Ҫд���ͼƬ
	            if (result.exists()) {//У����ļ��Ƿ��Ѵ���
	                result.delete();//ɾ����Ӧ���ļ����Ӵ�����ɾ��
	                result = new File(path2);//ֻ�Ǵ�����һ��File���󣬲�û���ڴ����´����ļ�
	            }
	            if (!result.exists()) {//����ļ�������
	                result.createNewFile();//���ڴ����´����ļ�������ʱ��СΪ0K
	            }
	            FileInputStream in = new FileInputStream(originalFile);
	            FileOutputStream out = new FileOutputStream(result);// ָ��Ҫд���ͼƬ
	            int n = 0;// ÿ�ζ�ȡ���ֽڳ���
	            byte[] bb = new byte[1024];// �洢ÿ�ζ�ȡ������
	            while ((n = in.read(bb)) != -1) {
	                out.write(bb, 0, n);// ����ȡ�����ݣ�д�뵽���������
	            }
	            //ִ�������Ϻ󣬴����µĸ��ļ�����������С��ʵ�ʴ�С
	            out.close();// �ر����������
	            in.close();
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();

	        }
			
			
		}else {
			try {
				BufferedReader  br =  new BufferedReader(new InputStreamReader(new FileInputStream(path1),"utf-8"));
				String b="";
				StringBuffer sb = new StringBuffer();
				try {
					while((b = br.readLine())!=null){
						//�õ��ļ����ݷŵ�sb��
						sb.append(b+"\r\n");
						//�������д�Լ����ÿһ�еĴ������
					}
		
					
				
					 path2 = 	processFileName(type,path2);
			
					String s = 	processFileContent(type,sb.toString(),path2);
					if(type.equals(BaiduMiniConterMini.CONVERTE_TYPE_WX)) {
						if(s.indexOf("wx.setPageInfo")>-1) {
							String s1 = s.substring(0,s.indexOf("wx.setPageInfo"));
					
							String s2 = s.substring(s.indexOf("wx.setPageInfo")+1);
							String s3 =s2.substring(s2.indexOf(");")+3);
						
							s=s1+s3;
						}
					}
					if(type.equals(BaiduMiniConterMini.CONVERTE_TYPE_TOUTIAO)) {
						if(s.indexOf("tt.setPageInfo")>-1) {
							String s1 = s.substring(0,s.indexOf("tt.setPageInfo"));
					
							String s2 = s.substring(s.indexOf("tt.setPageInfo")+1);
							String s3 =s2.substring(s2.indexOf(");")+3);
					
							s=s1+s3;
						}
						
					}
					
					
					
					BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path2),"utf-8"));
					bw.write(s);
					bw.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (UnsupportedEncodingException | FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		


		
		
		
	}
	private static String  processFileContent(String type, String s,String path2) {
		
		// TODO Auto-generated method stub
		if(type.equals(BaiduMiniConterMini.CONVERTE_TYPE_WX)) {
			if(path2.contains(".js")) {
					s=s.replace("swan.", "wx.");
			}
			if(path2.contains("xml")) {
					s=s.replace("s-", "wx:").replace("item, index in list", "{{list}}");
			}
			if(path2.contains("wxss")) {
					s=s.replace("weui.css", "weui.wxss");
			}
		}
		if(type.equals(BaiduMiniConterMini.CONVERTE_TYPE_TOUTIAO)) {
			if(path2.contains(".js")) {
					s=s.replace("swan.", "tt.");
			}
			if(path2.contains("ttml")) {
					s=s.replace("s-", "tt:").replace("item, index in list", "{{list}}").replace("tt:key=\"{{index}}\"", "tt:for-index=\"index\" tt:for-item=\"item\"");
			}
			if(path2.contains("ttss")) {
					s=s.replace("weui.css", "weui.ttss");
			}
		}
		
		return s;
	}
	
	private static String  processFileName(String type, String path2) {
		// TODO Auto-generated method stub
		
		
		if(type.equals(BaiduMiniConterMini.CONVERTE_TYPE_WX)) {
		   String frontPath = path2.substring(0,path2.lastIndexOf("."));
		   String suffix = path2.substring(path2.lastIndexOf(".") + 1);
		
			   switch(suffix)
			      {
			         case "css" :
			        	 suffix="wxss";
			            break;
			         case "swan" : 
			        	 suffix="wxml";  break;

			         default :
			        
			      }
	
		
		
			
			
			path2=frontPath+"."+suffix;
		
	
		}
		if(type.equals(BaiduMiniConterMini.CONVERTE_TYPE_TOUTIAO)) {
			   String frontPath = path2.substring(0,path2.lastIndexOf("."));
			   String suffix = path2.substring(path2.lastIndexOf(".") + 1);
			
				   switch(suffix)
				      {
				         case "css" :
				        	 suffix="ttss";
				            break;
				         case "swan" : 
				        	 suffix="ttml";  break;

				         default :
				        
				      }
		
			
			
				
				
				path2=frontPath+"."+suffix;
			
				
			}
		
		return path2;
	}

}
