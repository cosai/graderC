package grader;

import java.io.*;
import java.util.*;


public class grader {

	static String gccpath="gcc.exe";
	static ArrayList<String> inputfname;
	static ArrayList<String> outputfname;
	static ArrayList<String> checkfile;
	static String reportfile="report.txt";
	static String batchfile="somefile.bat";
	static ArrayList<ArrayList<String>> checkfilesread;
	static String gradefile="grades.txt";
	static final String SEPARATOR="\\"; 
	static ArrayList<ArrayList<String>> inputfilesread;
	static ArrayList<ArrayList<String>> outputfilesread;
	final static int MAXFILESIZE=1000000;
	
	public static void main(String[] args){
		//reads the filenames
		inputfname=new ArrayList<String>();
		outputfname=new ArrayList<String>();
		checkfile=new ArrayList<String>();
		
		config("config.txt");
		//deletereport();
		
		for(int i=0;i<checkfile.size();i++){
			p("checkfile:"+checkfile.get(i));
			p("outputile:"+outputfname.get(i));
			p("inputfile:"+inputfname.get(i));
		}
		
		checkfilesread=new ArrayList<ArrayList<String>>();
		inputfilesread=new ArrayList<ArrayList<String>>();
		outputfilesread=new ArrayList<ArrayList<String>>();
		
		//reads checkfile and puts the read files to arraylists and 
		//puts these arraylists to arr arraylist
		
		checkfilesread=readCheck(checkfile);
		inputfilesread=readCheck(inputfname);
		
		/*
		for(int i=0;i<arr.size();i++){
			for(int j=0;j<arr.get(i).size();j++){
				p(arr.get(i).get(j));
			}
			p("\n\n");
		}
		*/
		
		if(checkfilesread!=null){
			
			if(args.length==1 && args[0].contains("compile")){
				compileall();
			}
			
			if(args.length==1 && args[0].contains("run")){
				runall();
			}
			
			if(args.length==1 && args[0].contains("grade")){
				compareall();
			}
			p("FINISHED GRADING.SEE grades.txt and report.txt");
		}else{
			p("Array is Empty");
		}
		
	}
	
	public static void compileall(){
		try{
			File folder = new File(".");
			File[] listOfFiles = folder.listFiles();

		    for (int i = 0; i < listOfFiles.length; i++) {
		    	String fnamewithext=listOfFiles[i].getName();
		    	String fname=fnamewithext;
		    	int pos = fnamewithext.lastIndexOf(".");
		    	if (pos > 0) {
		    	    fname = fname.substring(0, pos);
		    	}
		    	
				if (listOfFiles[i].isFile() && isC(listOfFiles[i].getName())) {
					
					//create the directory with file name
					File directory=(new File("."+SEPARATOR +fname));
					if(!directory.isDirectory() || !directory.exists()){
						boolean success = directory.mkdirs();
						if (!success) {
						    // Directory creation failed
							System.out.println("Directory can not be created");
						}
						
					}
					String com=gccpath+" "+System.getProperty("user.dir")+SEPARATOR+fname+".c -o "
								+System.getProperty("user.dir")+SEPARATOR+fname+SEPARATOR+fname+".exe";
					compile(com);
					
					
				}//if it is file 
				
		    }//end of for listing files
			
			
		}catch(Exception e){
			System.out.println(e);
		}
	}	
	
	public static void runall(){
		try{
			File folder = new File(".");
			File[] listOfFiles = folder.listFiles();

		    for (int i = 0; i < listOfFiles.length; i++) {
		    	String fnamewithext=listOfFiles[i].getName();
		    	String fname=fnamewithext;
		    	int pos = fnamewithext.lastIndexOf(".");
		    	if (pos > 0) {
		    	    fname = fname.substring(0, pos);
		    	}
		    	
				if (listOfFiles[i].isFile() && isC(fnamewithext)) {
					
					for(int u=0;u<inputfname.size();u++){
						
						String commandrun="\""+System.getProperty("user.dir")+SEPARATOR+fname+SEPARATOR+fname+".exe\" >"+
									"\""+System.getProperty("user.dir")+SEPARATOR+fname+SEPARATOR+outputfname.get(u)+"\""+" <"+
									"\""+System.getProperty("user.dir")+SEPARATOR+inputfname.get(u)+"\"";
						
							//outputfilesread.add(runreal(inputfilesread.get(u),fname));
							String executable=System.getProperty("user.dir")+SEPARATOR+fname+SEPARATOR+fname+".exe";
							if(ifexists(executable)){
								addtobatch(commandrun);
								writeArrayList(outputfilesread,outputfname,fname);
							}else{
								addtobatch("REM "+fname+" doesn't get compiled");
								try{
									FileWriter f=new FileWriter(System.getProperty("user.dir")+SEPARATOR+fname+SEPARATOR+"NOTCOMPILED.txt");
									f.write(fname+" doesnt get compiled\n");
									f.close();
								}catch(Exception e){
									e.printStackTrace();
								}
							}//end of checking if file exists
							
								
					}//end of for	
					
				}//if it is file 
				
		    }//end of for listing files
		}catch(Exception e){
			System.out.println(e);
		}
	}	
	
	
	public static void writeArrayList(ArrayList<ArrayList<String>> arr,ArrayList<String> iname,String user){
		
		String filename=System.getProperty("user.dir")+SEPARATOR+user+SEPARATOR;
		for(int i=0;i<iname.size();i++){
			String filenametowrite=filename+iname.get(i);
			try{
				BufferedWriter bw=new BufferedWriter(new FileWriter(new File(filenametowrite)));
				for(int j=0;j<arr.get(i).size();j++){
					bw.write(arr.get(i).get(j));
				}
				bw.close();
			}catch(Exception e){
				p(e.toString());
			}
			
		}
	}
	
	public static void compareall(){
		try{
			File folder = new File(".");
			File[] listOfFiles = folder.listFiles();

		    for (int i = 0; i < listOfFiles.length; i++) {
		    	String fnamewithext=listOfFiles[i].getName();
		    	String fname=fnamewithext;
		    	int pos = fnamewithext.lastIndexOf(".");
		    	if (pos > 0) {
		    	    fname = fname.substring(0, pos);
		    	}
		    	//fname is filename without extension
		    	
				if (listOfFiles[i].isFile() && isC(fnamewithext)) {
					
					//String com=gccpath+" "+System.getProperty("user.dir")+SEPARATOR+fname+".c -o "
					//			+System.getProperty("user.dir")+SEPARATOR+fname+SEPARATOR+fname+".exe";
					
					for(int u=0;u<outputfname.size();u++){
						compare(fname,outputfname.get(u),u);
					}
					
				}//if it is file 
				
		    }//end of for listing files
			
			
		}catch(Exception e){
			System.out.println(e);
		}
	}	

	public static boolean execCom(String command){
		try {
			Process process = Runtime.getRuntime().exec(command);
			//process.waitFor();
			if(!command.contains("gcc.exe"))
				wait(60);
			
			if(process.exitValue()<0){
				return false;
			}
			if(!command.contains("gcc.exe"))
				process.destroy();
		} catch (FileNotFoundException f){
			p("File not found for command "+command);
			return false;
		}catch(Exception ex) {
			p(ex+"\n ERROR: "+ex.getMessage());
			return false;
		}
		return true;
	}//execCom
	
	public static void compile(String com){
		p(com);
		execCom(com);
	}
	
	
	//returns -1 if file not exist
	//returns -2 if the program crashes
	//returns 0 if all fine
	public static int run(String fname,String outputf,String inputf){
		String com=System.getProperty("user.dir")+SEPARATOR+fname+SEPARATOR+fname+".exe >"+
				System.getProperty("user.dir")+SEPARATOR+fname+SEPARATOR+outputf+" <"+
				System.getProperty("user.dir")+SEPARATOR+inputf;
		
		if(!ifexists(System.getProperty("user.dir")+SEPARATOR+fname+SEPARATOR+fname+".exe")){
			return -1;
		}
		boolean ret=execCom(com);
		if(!ret){
			return -2;
		}
		return 0;
	}
	
	//compares userfname with ofname
	public static void compare(String userfname,String ofname,int ordernum){
		String useroutput=System.getProperty("user.dir")+SEPARATOR+userfname+SEPARATOR+ofname;
		String rline="";
		int i=0;
		int ok=0;
		
		String executable=System.getProperty("user.dir")+SEPARATOR+userfname+SEPARATOR+userfname+".exe";
		if(!ifexists(executable)){
			addtogrades(userfname+" "+0+" doesnt get compiled");
			addtoreport(useroutput+" does not get compiled "+0);	
			return;
		}
		
		if(!ifexists(useroutput)){
			addtogrades(userfname+" "+0+" does not exist");
			addtoreport(useroutput+" does not exist "+0);	
			return;
		}
		File ref=new File(useroutput);
		if(ref.length()>MAXFILESIZE){
			addtogrades(userfname+" "+0+" file too big");
			addtoreport(useroutput+" file too big "+0);	
			return;
		}
		if(ref.length()==0){
			addtogrades(userfname+" "+0+" file is empty");
			addtoreport(useroutput+" file is empty "+0);	
			return;
		}
		
		
		ArrayList<String> checkarr=checkfilesread.get(ordernum); 
		try{
			
			
			BufferedReader br=new BufferedReader(new FileReader(useroutput));
			while((rline=br.readLine()) !=null && i<checkarr.size()){
				if(rline.trim().length() != 0 && checkarr.get(i).trim().length() != 0){
					if(rline.trim().equals(checkarr.get(i).trim())){
			 			ok++;
			 		}
				}
				
				
	 			i++;
		 	}
			br.close();
		}catch(Exception e){
			System.out.println(e); 
		}
		
		if(i != checkarr.size()){
			addtoreport(userfname+"'s output is not complete");
		}
		
		addtoreport(userfname+" for output "+ofname+" "+ok);	
		addtogrades(userfname+" for output "+ofname+" "+ok);
	}
	
	//if file exists return true else false
	public static boolean ifexists(String fname){
		File f=new File(fname);
		return f.exists();
	}
	
	//makes program waits number of secs seconds
	public static void wait(int secs){
		try {
			 Thread.sleep(1000*secs);
		} catch (InterruptedException ie) {
			    p(ie.toString());
		}
	}
	
	//if file is C file returns true else false
	public static boolean isC(String x){
		if(x.charAt(x.length()-1)=='c' && x.charAt(x.length()-2)=='.')
			return true;
		return false;
	}
	
	//if file is exe file returns true else false
	public static boolean isExe(String x){
		if(x.contains(".exe"))
			return true;
		return false;
	}

	//reads config.txt file
	public static void config(String conf){
		String rline="";
		String inputfiles="";
		String outputfiles="";
		String checkfiles="";
		
		try{
			BufferedReader br=new BufferedReader(new FileReader(conf));
			while((rline=br.readLine()) !=null){
				if(rline.contains("inputfilename")){
					inputfiles=rline.substring(14);
					StringTokenizer st=new StringTokenizer(inputfiles);
					while(st.hasMoreTokens()){
						inputfname.add(st.nextToken());
					}
					
				}else if(rline.contains("outputfilename")){
					outputfiles=rline.substring(15);
					StringTokenizer st=new StringTokenizer(outputfiles);
					while(st.hasMoreTokens()){
						outputfname.add(st.nextToken());
					}
				}else if(rline.contains("checkfilename")){
					checkfiles=rline.substring(14);
					StringTokenizer st=new StringTokenizer(checkfiles);
					while(st.hasMoreTokens()){
						checkfile.add(st.nextToken());
					}
				}
		 	}
			br.close();
		}catch(FileNotFoundException e){
			p("config.txt file is not found.");
			System.exit(0);
		}catch(Exception e){
			System.out.println(e); 
		}
	}
	
	//reads the checkfile
	public static ArrayList<ArrayList<String>> readCheck(ArrayList<String> cfile){
		ArrayList<ArrayList<String>> all=new ArrayList<ArrayList<String>>();
		
		for(int i=0;i<cfile.size();i++){
			ArrayList<String> as=new ArrayList<String>();
			String readLine="";

			if(!new File(System.getProperty("user.dir")+SEPARATOR+cfile.get(i)).exists()){
				p("Can not find check file for output");
				return null;
			}
			
			try{
				BufferedReader bf= new BufferedReader(new FileReader(System.getProperty("user.dir")+SEPARATOR+cfile.get(i)));
			 	while((readLine=bf.readLine()) !=null){
			 		as.add(readLine);
			 	}
			 	bf.close();
			}catch(Exception e){
				System.out.println(e); 
			}
			all.add(as);
		}
		return all;
	}
	
	public static ArrayList<String> runreal(ArrayList<String> inputfname,String user){
		String line;
		String executable=System.getProperty("user.dir")+SEPARATOR+user+SEPARATOR+user+".exe";
		ArrayList<String> outputfname=new ArrayList<String>();
		
	    try {
		    Process proc = Runtime.getRuntime().exec(executable);
		    BufferedReader procInput = new BufferedReader(
		    		new InputStreamReader(proc.getInputStream()));
		      
		    BufferedWriter procOutput = new BufferedWriter(
		          new OutputStreamWriter(proc.getOutputStream()));
		    
		    for(int i=0;i<inputfname.size();i++){
		    //Give input to batch process
		    	procOutput.write(inputfname.get(i));
		    }
		    procOutput.flush();
		    procOutput.close();
		    
		    
			//Read output from batch process
			while ((line = procInput.readLine()) != null) {
				outputfname.add(line);
			}
	  
			
			procInput.close();
	      
		} catch (Exception e) {
		  e.printStackTrace();
		}
	    
	    return outputfname;
	}
	
	
	public static void addtoreport(String line){
		try{
			BufferedWriter bw=new BufferedWriter(new FileWriter(System.getProperty("user.dir")+SEPARATOR+reportfile,true));
			bw.write(line+"\r\n");
			bw.close();
		}catch(Exception e){
			System.out.println(e+"\n CAN NOT WRITE REPORT");
			System.exit(1);
		}
	}
	
	public static void addtogrades(String line){
		try{
			BufferedWriter bw=new BufferedWriter(new FileWriter(System.getProperty("user.dir")+SEPARATOR+gradefile,true));
			bw.write(line+"\r\n");
			bw.close();
		}catch(Exception e){
			System.out.println(e+"\n CAN NOT WRITE GRADES");
			System.exit(1);
		}
	}
	
	public static void addtobatch(String line){
		try{
			BufferedWriter bw=new BufferedWriter(new FileWriter(System.getProperty("user.dir")+SEPARATOR+batchfile,true));
			bw.write(line+"\r\n");
			bw.close();
		}catch(Exception e){
			System.out.println(e+" \n CAN NOT WRITE BATCH FILE");
			System.exit(1);
		}
	}
	
	public static void p(String s){
		System.out.println(s);
	}
	
	public static void deletereport(){
		try{
	    	File file = new File(System.getProperty("user.dir")+SEPARATOR+reportfile);
	        if(file.exists()){
	        	if(file.delete()){
		    		p(file.getName() + " is deleted!");
		    	}else{
		    		p("Delete operation is failed for report file.");
		    	}
	        }else{
	        	p("Report file does not exist");
	        }
	    	
    	   
    	}catch(Exception e){
    		e.printStackTrace();
    	}
	}
}
