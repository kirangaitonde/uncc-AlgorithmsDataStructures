
// to get inputs from the user
//references : https://docs.oracle.com/javase/8/ 
//@Kiran Gaitonde

package com.uncc;

import java.io.*;

public class GetInput {
	
	   private String filePath; // path of file containing bandwidth requirements; 
	   							// one bandwidth per line
	   private int noOfServers; // number of servers
	   private int noOfFiles;   // number of files
	   
	   public void setFilePath(String path) {
	       this.filePath = path;
	    }
	   public String getFilePath() {
	       return filePath; 
	   }	   
	   public void setNoOfServers(int m) {
	       this.noOfServers = m;
	    }
	   public int getNoOfServers() {
	       return noOfServers;
	    }
	   public void setNoOfFiles(int n) {
	       this.noOfFiles = n;
	    }
	   public int getNoOfFiles() {
	       return noOfFiles;
	    }
	   
// gets inputs from user      
	   public void getInput(){
		  BufferedReader b = new BufferedReader(new InputStreamReader(System.in));
		  try{
			 System.out.print("Enter file path having bandwith requirements: "); 
			 setFilePath(b.readLine());
			 System.out.print("Enter #servers: "); 
			 setNoOfServers(Integer.parseInt(b.readLine()));
			 System.out.print("Enter #files: "); 
			 setNoOfFiles(Integer.parseInt(b.readLine()));			  
		   } 
	      catch (IOException e) {
	         System.out.println(e);
	         System.exit(1);
	      }		 
	   }
	   
// returns an array containing bandwidths from the given file path
	   public int[] readFile() {
		    int n = getNoOfFiles();
			int[] bws = new int[n];
			String path = getFilePath();
			try{
				FileReader f = new FileReader(path);
				BufferedReader b = new BufferedReader(f);
				String bw;
				for (int i = 0; i < n; i++){
					bw = b.readLine();	
					bws[i] = Integer.parseInt(bw);
					
				}
				b.close();				
			}				
			catch (IOException e) {
			         System.out.println(e);
			         System.exit(1);
			      }	
			return bws;
	   }
	   
// main function for testing	   
	   public static void main(String []args) {
		 GetInput gi = new GetInput();
    	 gi.getInput();
    	 gi.readFile();
    	 System.out.println(gi.getFilePath());
    	 System.out.println(gi.getNoOfServers());
    	 System.out.println(gi.getNoOfFiles());
    	 System.out.println(gi.readFile());
	    }       
}
