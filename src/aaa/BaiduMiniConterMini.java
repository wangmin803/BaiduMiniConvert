package aaa;

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
	public static void main(String args[])
	{
		String dir = "D:/baidumini2";
	
		String dir1 = "D:/baidumini3";
		File filenew = new File(dir1);
		if(!filenew.exists()) {
			filenew.mkdir();
		}
		
		print_file(dir);

	}
	public static void print_file (String dir)
	{
		File file = new File(dir);
		
	
		File files[] = file.listFiles();
		for(File tempFile : files)
		{
			
	
			if(!tempFile.getAbsolutePath().contains("\\.")) {
				if(tempFile.isDirectory())
				{
					
					File filenew1 = new File(tempFile.getAbsolutePath().replace("baidumini2", "baidumini3"));
					if(!filenew1.exists()) {
						filenew1.mkdirs();
					}
				
				
					print_file(tempFile.getAbsolutePath());
				}else{
					fileCount++;
					
					String path1=tempFile.getAbsolutePath();
					String path2 = tempFile.getAbsolutePath().replace("baidumini2", "baidumini3");
					readwrite(path1,path2);
				}
			}
			
		}
	}
	
	
	
	public static void  readwrite(String path1,String path2) {
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
		
					
				
					 path2 = 	processFileName("wx",path2);
					 System.out.println(path2);
					String s = 	processFileContent("wx",sb.toString(),path2);
					
					if(s.indexOf("wx.setPageInfo")>-1) {
						String s1 = s.substring(0,s.indexOf("wx.setPageInfo"));
				
						String s2 = s.substring(s.indexOf("wx.setPageInfo")+1);
						String s3 =s2.substring(s2.indexOf(");")+3);
						System.out.println(s3);
						s=s1+s3;
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
		if(path2.contains(".js")||path2.contains("xml")||path2.contains("wxss")) {
			if(type.equals("wx")) {
				
				s=s.replace("swan.", "wx.").replace("s-", "wx:").replace("item, index in list", "{{list}}").replace("weui.css", "weui.wxss");
			}
		}
	
		
		return s;
	}
	
	private static String  processFileName(String type, String path2) {
		// TODO Auto-generated method stub
		if(type.equals("wx")) {
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
		
		return path2;
	}

}
