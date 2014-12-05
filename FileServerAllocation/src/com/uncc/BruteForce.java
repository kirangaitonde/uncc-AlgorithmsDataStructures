
// Brute Force for File Serve Allocation
//references : https://docs.oracle.com/javase/8/
//@Kiran Gaitonde

package com.uncc;
import java.util.*;

public class BruteForce {
	
	private static int noOfServers;	
	private static int opMaxServerBw = 0;	
	
// arrange files on server for each permutation in all possible ways	
	private static void arrangeFilesForEachPermutation(int[] b){		
		int x = b.length;
		while(x>0){
			int [] servers = new int [noOfServers];
			for (int j = 0; j < x; j++){
				servers[0] = servers[0]+b[j];
			}			
			
			int[] bwSuff = new int [b.length-x];
			int l=0;
			for (int k = x; k < b.length; k++){				
				bwSuff[l] = b[k];
				l++;
			}			
			int[] remServers = arrangeFilesRndRbnForRemServers(bwSuff);
			int p=0;
			for (int j = 1; j < noOfServers; j++){				
				servers[j]=remServers[p];
				p++;
			}	
			
			Arrays.sort(servers);
			int mB = servers[servers.length-1];	
			if(opMaxServerBw==0) {
				opMaxServerBw = mB;
			}
			else{
				if(mB < opMaxServerBw){
					opMaxServerBw = mB;
				}
			}				
			x--;
		}
		
	}	
	
//arrange files on remaining servers in round robin order, and return server array
		private static int[] arrangeFilesRndRbnForRemServers(int[] b){			
			int m = noOfServers-1;
			int [] remservers = new int [m];		
			int n = b.length;
			
			if(n>m){
				int x = n % m;
				for (int i = 0; i < m; i++){
					remservers[i] = remservers[i]+b[i];							
				}						
				for (int k = m ; k < n-x; k=k+m){
					for (int j = 0; j < m; j++){
						remservers[j] = remservers[j]+b[k+j];						
					}	
				}					
				int l = 0;			
				for (int i = n-x; i < n; i++){			
					remservers[l] = remservers[l]+b[i];					
					l++;
				}
			}			
			else{
				int l = 0;			
				for (int i = 0; i < n; i++){			
					remservers[l] = remservers[l]+b[i];				
					l++;
				}				
			}							
			return remservers;
		}
		

	
/*generate all possible permutations of bandwidths; 
 then arrange each permutation on servers in all possible ways. 
 reference:this function is inspired by algorithm for permutations of string  
 which can found commonly*/ 
		
	private static void tryAllPermutations(int[] bwPreList, int[] bwList) {
	    int listLen = bwList.length;
	    if (bwList.length == 0) {
	    	arrangeFilesForEachPermutation(bwPreList);	    	
	    }
	    else {
	        for (int i = 0; i < listLen; i++)
	        	tryAllPermutations(concatArrayInteger(bwPreList, bwList[i]), concatArrays(getSubArray(bwList,0,i),getSubArray(bwList,i+1,listLen)));
	    }
	}

//concatenates an array of integers and an integer	
	private static int[] concatArrayInteger (int[] a , int b){	
		int [] c = new int[a.length+1];
		for (int i = 0; i < a.length; i++){
			c[i]=a[i];
		}
		c[a.length]=b;		
		return c;		
	}
	
//concatenates two arrays of integers		
	private static int[] concatArrays (int[] a , int[] b){	
		int [] c = new int[a.length+b.length];
		for (int i = 0; i < a.length; i++){
			c[i]=a[i];
		}
		int j=0;
		for (int i = a.length; i < (a.length+b.length); i++){
			c[i]=b[j];
			j++;
		}		
		return c;
		
	}	

//returns an sub array with given start and end index	
	private static int[] getSubArray (int[] a, int start, int end){
		int [] sub = new int[end-start];
		int j=0;
		for (int i = start; i < end; i++) {
			sub[j]=a[i];
			j++;
		}		
		return sub;		
	}

//runs brute force algorithm	
	public void runBruteForce(int[] b){
		int [] emp = new int[0];
		tryAllPermutations(emp,b);		
	}

//main function for testing	
	public static void main(String []args) {
		GetInput gi = new GetInput();
		gi.getInput();
		int n = gi.getNoOfFiles();
		noOfServers = gi.getNoOfServers();
		int[] bws = new int[n];
		bws = gi.readFile();
		BruteForce bf = new BruteForce();		
		long startTime = System.currentTimeMillis();		
		bf.runBruteForce(bws);
		long stopTime = System.currentTimeMillis();		
		long executionTime = stopTime - startTime;		
		System.out.println("Optimal Max Bandwidth : "+ opMaxServerBw);
		System.out.println("Time taken : "+ executionTime);		 
	 }
}
