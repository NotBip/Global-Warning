package Objects.Saving;

import java.io.IOException;
import java.io.RandomAccessFile;

public class ReadWriteSave {
	
    
    //variables 4 * whatver
    int numberSave;
    int numberLevel;
    int cooldownWeap1;
    int cooldownWeap2;
    int damageWeap1;
    int damageWeap2;
    int amountBomb;
    int amountPotion;
    int amountKey;
    int amountGem;
   
    private final long recLen = 102;
    /**********************************************************
    constructors same name as base class- first with parameters and second without)*/
    public ReadWriteSave ( int save,  int HitPoints, int Strength, int Constitution, int Intelligence, int Wisdom, int Dexterity, int Charisma) {
    
        
        numberSave = save;
        numberLevel = HitPoints;
        cooldownWeap1 = Strength;
        cooldownWeap2 = Constitution;
        damageWeap1 = Intelligence;
        damageWeap2 = Wisdom;
        amountBomb = Dexterity;
        amountPotion = Charisma;
    }//end Character
    /********************************************************
    Blank constructor will enter null string or 0 for each field*/
    public ReadWriteSave () {
    
        numberSave = 0;
        numberLevel = 0;
        cooldownWeap1 = 0;
        cooldownWeap2 = 0;
        damageWeap1 = 0;
        damageWeap2 = 0;
        amountBomb = 0;
        amountPotion = 0;
    }//end Character
    /**********************************************************
    Methods to assign values accessible from the child class */
    
    public void setLevel (int Level){  numberSave = Level;}
    public void setHitPoints (int HitPoints){numberLevel = HitPoints;}
    public void setStrength (int Strength){  cooldownWeap1 = Strength;}
    public void setConstitution (int Constitution){cooldownWeap2 = Constitution;}
    public void setIntelligence (int Intelligence){damageWeap1 = Intelligence;}
    public void setWisdom (int Wisdom){damageWeap2 = Wisdom;}
    public void setDexterity (int Dexterity){amountBomb = Dexterity;}
    public void setCharisma (int Charisma){amountPotion = Charisma;}
    
    
    /**********************************************************
    Methods to retrieve values accessible from the child class */
    
    public int getLevel (){  return numberSave; }
    public int getHitPoints (){return numberLevel;}
    public int getStrength (){ return cooldownWeap1; }
    public int getConstitution (){ return cooldownWeap2;}
    public int getIntelligence (){return damageWeap1;}
    public int getWisdom (){return damageWeap2;}
    public int getDexterity (){return amountBomb;}
    public int getCharisma (){return amountPotion;}
    
   
   
	/**********************************************************	
	method to write one record of information to a random access file */
   
   /*
 	 * Method Name: writeRec
 	 * @Author: Nusayba Hamou
 	 * @Date 02 OCT 2023
 	 * @Description: writes a character record to the raf
 	 * @Parameters: random access file and # of records
 	 * @Returns: N/A
 	 * @Throws/Exceptions: IOException
 	 * */
   
     public void writeRec (RandomAccessFile raf, int recordNumber) throws IOException {
        raf.seek (recordNumber * recLen); // move pointer to position in file
        System.out.println("Working....");//to make sure it's working...lol
        
        
     // write the ints to the file
        raf.writeInt (numberSave);
        raf.writeInt (numberLevel);
        raf.writeInt (cooldownWeap1);
        raf.writeInt (cooldownWeap2);
        raf.writeInt (damageWeap1);
        raf.writeInt (damageWeap2);
        raf.writeInt (amountBomb);
        raf.writeInt (amountPotion);
   }//end writeRec
  	/**********************************************************	
	method to read one record of information from a random access file */
     /*
  	 * Method Name: readRec
  	 * @Author: Nusayba Hamou
  	 * @Date 02 OCT 2023
  	 * @Description: reads a character record from the raf
  	 * @Parameters: random access file and # of records
  	 * @Returns: N/A
  	 * @Throws/Exceptions: IOException
  	 * */
     
     public void readRec (RandomAccessFile raf, int recNum) throws IOException   {
        raf.seek (recNum * recLen);// move pointer to position in file
        
        // read the ints from the file
        numberSave = raf.readInt();
        numberLevel = raf.readInt ();
        cooldownWeap1 = raf.readInt ();
        cooldownWeap2 = raf.readInt ();
        damageWeap1 = raf.readInt ();
        damageWeap2 = raf.readInt ();
        amountBomb = raf.readInt ();
        amountPotion = raf.readInt ();
        
    }  // end readRec
} // Character class

