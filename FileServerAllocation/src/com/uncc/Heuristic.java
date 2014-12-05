
// Heuristic approach for File Serve Allocation
//references : https://docs.oracle.com/javase/8/ 
//@Kiran Gaitonde

package com.uncc;
import java.util.*;

public class Heuristic {	 
	
//returns array sorted in descending order
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
	
/*arrange files in following way : assign each file to a server having minimum 	
sum of bandwidths of files already assigned */
	private static int arrangeFilesMinBWFirst(int[] b, int m)
	{
		ArrayList<Integer> serverBw = new ArrayList<Integer>(m);
		int n = b.length;				
		
		for (int i = 0; i < m; i++){
			serverBw.add(b[i]);					
		}
		
		for (int i = m; i < n; i++){
			int x = b[i]+ Collections.min(serverBw);
	        int index  = serverBw.indexOf(Collections.min(serverBw));
	        serverBw.remove(index);
	        serverBw.add(index,x);
		}		
		return Collections.max(serverBw);	
		
	}

/*arrange files in zig zag order, i.e, assuming servers are numbered as S1,S2...Sm; 
first arrange files from S1 to Sm, then Sm to S1, repeat until all files are 
assigned*/	
	private static int arrangeFilesZigZag(int[] bws, int m){
		int serversBWs [] = new int [m];
		int k =0;
		int l =m-1;
		for (int i = 0; i < bws.length; i++){
			if(k < m){
				serversBWs[k] = serversBWs[k] + bws[i];				
				if(k == m-1){
					l = m-1;
				}
				k++;
				continue;
			}
			if(l>-1){
				serversBWs[l] = serversBWs[l] + bws[i];				
				if(l == 0){
					k = 0;
				}
				l--;
				continue;
			}			
		}		
	    Arrays.sort(serversBWs);
	    return serversBWs[m-1];	
	}

//sorts the bandwidths in descending order and run's minimum server bandwidth first approach 	
	public int runHeuristic1(int[] bws, int m){		
		int d[] = new int[bws.length];
		d = arrangeBWSDescending(bws);		
		return(arrangeFilesMinBWFirst(d,m));
	}

//sorts the bandwidths in descending order and run's zig zag appproach	
	public int runHeuristic2(int[] bws, int m){			
		int d[] = new int[bws.length];
		d = arrangeBWSDescending(bws);							
		return (arrangeFilesZigZag(d,m));			
	}
	
// main function for testing	
	public static void main(String []args) {
		GetInput gi = new GetInput();
		gi.getInput();
		int n = gi.getNoOfFiles();
		int m = gi.getNoOfServers();
		int[] bws = new int[n];
		bws = gi.readFile();
		int optMaxB;			
		Heuristic h = new Heuristic();
		long startTime = System.currentTimeMillis();
		optMaxB = h.runHeuristic1(bws,m);
		//optMaxB = h.runHeuristic2(bws,m);
		long stopTime = System.currentTimeMillis();		
		long executionTime = stopTime - startTime;
		System.out.println("Maximum Bandwidth : "+ optMaxB);
		System.out.println("Time taken : "+ executionTime);
	}	   	 
}
