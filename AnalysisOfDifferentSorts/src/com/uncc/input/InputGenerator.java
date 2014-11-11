
/*
 * InputGenerator
 * Generates random Integer inputs of various sizes starting from 2, 4, 8, 16......1048576. 
 * The random numbers are stored in an Input excel, One sheet per input size
 *  
 * @author Kiran Gaitonde
 * 
 * References stackoverflow.com
 * 
 */

package com.uncc.input;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class InputGenerator {
	public static final void main(String... aArgs){
		try {
		HSSFWorkbook workbook = new HSSFWorkbook();
				
		for (int i = 2, n = 2; i <= 2000000 ; i= (int) Math.pow(2, n),n++)
		{
			HSSFSheet sheet = workbook.createSheet("InputSize-"+ i);
			Random randomGenerator = new Random();
			int colNo=0;
			int rowNo=0;
			HSSFRow row = sheet.createRow((int)rowNo);			
			for (int idx = 0; idx < i; idx++)
			{					
				int randomInt = randomGenerator.nextInt(1000);
				row.createCell(colNo).setCellValue(randomInt);
				colNo++;
				
				if(colNo > 255 && idx!= i-1)
				{
					rowNo++;
					row = sheet.createRow((int)rowNo);
					colNo=0;
				}	  		
        
			}   
		}
		FileOutputStream fileOut = new FileOutputStream("Input.xls");
    	workbook.write(fileOut);
    	fileOut.close();
		}
		
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
        catch (IOException e) {
			e.printStackTrace();
		}
	  
		System.out.println("Done");
	}
}


