

/*
 * MergeSort
 * Takes Input of various sizes from an Input excel. 
 * Sorts them using Merge Sort algorithm.
 * Stores the execution time for corresponding input sizes in an Output excel
 * 
 * @author Kiran Gaitonde
 * 
 * References java2novice.com, stackoverflow.com
 */

package com.uncc.sort;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFRow;


public class MergeSort {
	private int[] array;
    private int[] tempMergArr;
    private int length;
    
    
    //Merger Sort algorithm
    
    private void doMergeSort(int lowerIndex, int higherIndex) {
         
        if (lowerIndex < higherIndex) {
            int middle = lowerIndex + (higherIndex - lowerIndex) / 2;
            // Below step sorts the left side of the array
            doMergeSort(lowerIndex, middle);
            // Below step sorts the right side of the array
            doMergeSort(middle + 1, higherIndex);
            // Now merge both sides
            mergeParts(lowerIndex, middle, higherIndex);
        }
    }
 
    private void mergeParts(int lowerIndex, int middle, int higherIndex) {
 
        for (int i = lowerIndex; i <= higherIndex; i++) {
            tempMergArr[i] = array[i];
        }
        int i = lowerIndex;
        int j = middle + 1;
        int k = lowerIndex;
        while (i <= middle && j <= higherIndex) {
            if (tempMergArr[i] <= tempMergArr[j]) {
                array[k] = tempMergArr[i];
                i++;
            } else {
                array[k] = tempMergArr[j];
                j++;
            }
            k++;
        }
        while (i <= middle) {
            array[k] = tempMergArr[i];
            k++;
            i++;
        }
    }
        
 
    public static void main(String a[]){
    	
        try {
        	
        	// read the input from Input.xls(per sheet)  file and initialize the array
        	    DataFormatter fmt = new DataFormatter();
        		FileInputStream fileInputStream = new FileInputStream("Input.xls");
        		HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
        		int noOfSheets = workbook.getNumberOfSheets();
        		System.out.println("No of Inputs = " + noOfSheets);
        		
        		HSSFWorkbook workbook2 = new HSSFWorkbook();
        		HSSFSheet sheet1 = workbook2.createSheet("MergeSort");
        		for(int input = 0 ; input < noOfSheets ; input++)
        		{
        			
        		        		
        			HSSFSheet sheet = workbook.getSheetAt(input);
        			int totalRows = sheet.getPhysicalNumberOfRows();
        			//System.out.println(totalRows);
        			int totalCols = sheet.getRow(0).getPhysicalNumberOfCells();
        			//System.out.println(totalCols);
        			int inputLength = totalRows*totalCols;
        			//System.out.println(inputLength);
        		
        			int[] inputArr = new int[inputLength];
        			int n=0;
        			for (int i=0; i<totalRows;i++)
        			{
        				HSSFRow row = sheet.getRow(i);        		
        				for (int j=0; j< row.getPhysicalNumberOfCells(); j++)
        				{  			
        					HSSFCell cell = row.getCell(j);				
        					String value = fmt.formatCellValue(cell);
        					//System.out.println(value);
        					inputArr[n]= Integer.valueOf(value);
        					n++;
        				}
        			}
        		
        			//Sort the array and calculate the execution time
        			MergeSort mms = new MergeSort();
        			long startTime = System.currentTimeMillis();
         			//long startTime = System.nanoTime();
        			mms.sort(inputArr); 
        			long stopTime = System.currentTimeMillis();
         			//long stopTime = System.nanoTime();
        			long elapsedTime = stopTime - startTime;
        			//System.out.println(elapsedTime);
	        
	        
        			//Store the execution time in the file Output.xls for         		
        			HSSFRow outputRow = sheet1.createRow((int)input);
        			outputRow.createCell(0).setCellValue(inputLength);
        			outputRow.createCell(1).setCellValue(elapsedTime);
        			FileOutputStream fileOut = new FileOutputStream("OutputMS.xls");
        			workbook2.write(fileOut);
        			fileOut.close();
	        
	        
        	  }
        	System.out.println("Done");

	     }
        catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
        catch (IOException e) {
			e.printStackTrace();
		}
        
        
    }  
    
    	public void sort(int inputArr[]) {
        this.array = inputArr;
        this.length = inputArr.length;
        this.tempMergArr = new int[length];
        doMergeSort(0, length - 1);
    }
 

}
