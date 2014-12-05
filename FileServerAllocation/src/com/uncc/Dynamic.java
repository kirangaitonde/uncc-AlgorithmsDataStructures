
// Dynamic programming for File Serve Allocation
//references : https://docs.oracle.com/javase/8/ 
//@Kiran Gaitonde

package com.uncc;

import java.util.Arrays;

public class Dynamic {
	
//returns an average of bandwidths rounded to next integer
	private static int getAverageBWPerServer(int [] bws, int m){
		double x = 0.0;
		for (int i = 0; i < bws.length; i++){
			 x = x + bws[i];			 
		}
		return((int) Math.ceil(x/m));
	}
	
//returns an array sorted in descending order
	private static int[] arrangeBWSDescending(int[] bws){		
		int d[] = new int[bws.length];
		Arrays.sort(bws);
		int j = bws.length;
		for (int i = 0; i < bws.length; i++){
			d[j-1] = bws[i];	
			j--;			
		}				
		return d;			
	}
	
//checks if a solution is possible with given files, noOfServes and budget	
	private static boolean isSolutionPossible(int [] bws, int m , int budgetPerServer){
		int serversBWs [] = new int [m];	
		
		for (int i = 0; i < bws.length; i++){			
			int flag = 1;
			for (int j = 0; j < m; j++){				
				if((budgetPerServer-serversBWs[j]) >= bws[i]){					
					serversBWs[j] = serversBWs[j] + bws[i];				
					break;
				}
				else{
					flag = flag+1;					
				}								
			}
			if(flag>m){				
				return false;
			}
		}			
		return true;
	}	
	
	
//runs Dynamic bottom up algorithm	
	public int runDynamic(int [] bws, int m)
	{
		//set initial budget
		int budgetPerServer = getAverageBWPerServer(bws,m);
		System.out.println("inttial budget : "+ budgetPerServer);
		int d[] = arrangeBWSDescending(bws);		
		/* if solution present for initial budget; then check if solution is present 
		for lower budget*/
		if(isSolutionPossible(d, m, budgetPerServer)){
			while(true){
				budgetPerServer = budgetPerServer-1;
				if (isSolutionPossible(d,m,budgetPerServer))
				{
					continue;
				}
				else{
					break;
				}
			}
			return budgetPerServer+1;			
		}
		/* if solution not present for initial budget; then check if solution is present 
		for next higher budget*/
		else{
			while(true){
				budgetPerServer = budgetPerServer + 1;
				if (isSolutionPossible(d,m,budgetPerServer)){
					break;
				}
				else{
					continue;
				}
			}
			return budgetPerServer;			
		}		
	}
		
//main function for testing
	public static void main(String []args) {
		GetInput gi = new GetInput();
		gi.getInput();
		int n = gi.getNoOfFiles();
		int noOfServers = gi.getNoOfServers();
		int[] bws = new int[n];
		bws = gi.readFile();
		Dynamic dbu = new Dynamic();		
		long startTime = System.currentTimeMillis();		
		int maxOptBw = 	dbu.runDynamic(bws,noOfServers);
		long stopTime = System.currentTimeMillis();		
		long executionTime = stopTime - startTime;
		System.out.println("Optimal Max Bandwidth : "+ maxOptBw);		
		System.out.println("Time taken : "+ executionTime);
		 
	 }

}
