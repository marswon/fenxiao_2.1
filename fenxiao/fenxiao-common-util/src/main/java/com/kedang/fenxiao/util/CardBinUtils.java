package com.kedang.fenxiao.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class CardBinUtils
{

	public static void main(String[] args)
	{
		try
		{
			String encoding = "GBK";
			File srcfile = new File("C:/Users/Ocean/Desktop/18. sys_bankcard init data.sql");
			File targetfile = new File("C:/Users/Ocean/Desktop/18. sys_bankcard init data_bak.sql");
			//判断文件是否存在
			if (srcfile.isFile() && srcfile.exists() && targetfile.isFile() && targetfile.exists())
			{ 
				InputStreamReader read = new InputStreamReader(new FileInputStream(srcfile), encoding);//考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				
				OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(targetfile), encoding);//考虑到编码格式
				BufferedWriter bufferedWriter = new BufferedWriter(write);
				
				int lineNum = 1;
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null)
				{
					System.out.println(lineTxt);
					if(lineNum % 2 == 0){
						bufferedWriter.newLine();
						String lineTxt3 = lineTxt.split(",")[3];
						if(lineTxt3.contains("||")) {
							String[] arr = lineTxt3.split("\\|\\|");
							String newlineTxt3 = "CONCAT_WS(char(10), " + arr[0] + "," + arr[2] + ")";
							//lineTxt.replace(lineTxt3, newlineTxt3);
							/*lineTxt.replaceAll("\\|\\|", "{{");
							lineTxt3.replaceAll("\\|\\|", "{{");
							lineTxt = lineTxt.replace(lineTxt3, newlineTxt3);*/

							String[] lineTxtArr = lineTxt.split(",");
							lineTxtArr[3] = newlineTxt3;
							StringBuilder sb = new StringBuilder();
							for(String str:lineTxtArr){
								sb.append(str + ",");
							}
							String s = sb.toString();
							lineTxt = s.substring(0, s.length()-1);
						}
						bufferedWriter.write(lineTxt);
					}else{
						if(lineNum>1){
							bufferedWriter.newLine();
						}
						bufferedWriter.write(lineTxt);
					}
					bufferedWriter.flush();
					++lineNum;
				}
				read.close();
				write.close();
			}
			else
			{
				System.out.println("找不到指定的文件");
			}
		}
		catch (Exception e)
		{
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
	}

}
