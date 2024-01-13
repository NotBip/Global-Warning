package Objects.Saving;

//import the stuff~
import java.io.*;
import java.util.*;

// uses the Character class reading from an ASCII file, writing to a Random Access Binary file
public class callSave  {
	
	
	/************************************************************
	method to write a binary file from an array of Character */
	
	/*
	 * Method Name: writeNewBinFile
	 * @Author: Nusayba Hamou
	 * @Date 01 OCT 2023
	 * @Description: writes to binary file
	 * @Parameters: string of file, object array of characters and # of records
	 * @Returns: N/A
	 * @Throws/Exceptions: IOException
	 * */
	
	public static void writeNewBinFile(String filename) throws IOException{
	RandomAccessFile raf = new RandomAccessFile(filename,"rw");
		//ReadWriteSave.writeRec(raf);
		raf.close();
	} // end writeNewBinFile
	
	
	
	
	/************************************************************
	method to read a binary file into an array of Character */
	
	/*
	 * Method Name: readNewBinFile
	 * @Author: Nusayba Hamou
	 * @Date 02 OCT 2023
	 * @Description: reads a binary file and fills it into a character array
	 * @Parameters: string of file, object array of characters
	 * @Returns: # of records Data Type: int
	 * @Throws/Exceptions: IOException
	 * */
	
	public static void readNewBinFile(String filename, Character inCharacters[]) throws IOException{
		RandomAccessFile raf = new RandomAccessFile(filename,"rw");
		//inCharacters[i].readRec(raf, i);
		
		raf.close();
	} // end readNewBinFile
	
	
} // end Interface