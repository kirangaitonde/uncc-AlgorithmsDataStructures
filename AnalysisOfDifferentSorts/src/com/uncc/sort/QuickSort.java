
/*
 * QuickSort
 * Takes Input of various sizes from an Input excel. 
 * Sorts them using Quick Sort algorithm.
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

public class QuickSort {
	
	 	private int array[];
	 	private int length;
	    
	 	//Quick Sort algorithm
	    private void quickSort(int lowerIndex, int higherIndex) {
	         
	        int i = lowerIndex;
	        int j = higherIndex;
	        // calculate pivot number, I am taking pivot as middle index number
	        int pivot = array[lowerIndex+(higherIndex-lowerIndex)/2];
	        // Divide into two arrays
	        while (i <= j) {
	            
	            while (array[i] < pivot) {
	                i++;
	            }
	            while (array[j] > pivot) {
	                j--;
	            }
	            if (i <= j) {
	                exchangeNumbers(i, j);
	                //move index to next position on both sides
	                i++;
	                j--;
	            }
	        }
	        // call quickSort() method recursively
	        if (lowerIndex < j)
	            quickSort(lowerIndex, j);
	        if (i < higherIndex)
	            quickSort(i, higherIndex);
	    }
	 
	    private void exchangeNumbers(int i, int j) {
	        int temp = array[i];
	        array[i] = array[j];
	        array[j] = temp;
	    }
	     
	    
	    public static void main(String a[])
	    {
	    	  try {
	    		// read the input from Input.xls(per sheet)  file and initialize the array
	        	    DataFormatter fmt = new DataFormatter();
	        		FileInputStream fileInputStream = new FileInputStream("Input.xls");
	        		HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
	        		int noOfSheets = workbook.getNumberOfSheets();
	        		System.out.println("No of Inputs = " + noOfSheets);
	        		
	        		HSSFWorkbook workbook2 = new HSSFWorkbook();
	        		HSSFSheet sheet1 = workbook2.createSheet("QuickSort");
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
	        			QuickSort qs = new QuickSort();
	        			//long startTime = System.currentTimeMillis();
	         			long startTime = System.nanoTime();
	        			qs.sort(inputArr);
	        			//long stopTime = System.currentTimeMillis();
	         			long stopTime = System.nanoTime();
	        			long elapsedTime = stopTime - startTime;
	    	        
	        			//Store the execution time in the file Output.xls for         		
	        			HSSFRow outputRow = sheet1.createRow((int)input);
	        			outputRow.createCell(0).setCellValue(inputLength);
	        			outputRow.createCell(1).setCellValue(elapsedTime);
	        			FileOutputStream fileOut = new FileOutputStream("OutputQS.xls");
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
	    
	     public void sort(int[] inputArr) {
	         
	        if (inputArr == null || inputArr.length == 0) {
	            return;
	        }
	        this.array = inputArr;
	        length = inputArr.length;
	        quickSort(0, length - 1);
	    }
	 
	   
	    

}
