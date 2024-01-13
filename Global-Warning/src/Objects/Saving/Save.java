package Objects.Saving;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Save {
	
    
    //variables 
    int numberLevel;
    int cooldownWeap1;
    int cooldownWeap2;
    int damageWeap1;
    int damageWeap2;
    int amountBomb;
    int amountPotion;
    int amountKey;
    int amountGem;
   
    /**********************************************************
    constructors same name as base class- first with parameters and second without)*/
    public Save (  int lvl, int cd1, int cd2, int dmg1, int dmg2, int bomb, int potion, int key, int gem) {
        numberLevel = lvl;
        cooldownWeap1 = cd1;
        cooldownWeap2 = cd2;
        damageWeap1 = dmg1;
        damageWeap2 = dmg2;
        amountBomb = bomb;
        amountPotion = potion;
        amountKey = key;
        amountGem = gem;
    }//end Character
    /********************************************************
    Blank constructor will enter null string or 0 for each field*/
    public Save () {
        numberLevel = 0;
        cooldownWeap1 = 0;
        cooldownWeap2 = 0;
        damageWeap1 = 0;
        damageWeap2 = 0;
        amountBomb = 0;
        amountPotion = 0;
        amountKey = 0;
        amountGem = 0;
    }//end Character
    /**********************************************************
    Methods to assign values accessible from the child class */
    
    public void setLevel (int Level){numberLevel = Level;}
    public void setCooldown1 (int Cooldown1){  cooldownWeap1 = Cooldown1;}
    public void setCooldown2 (int Cooldown2){cooldownWeap2 = Cooldown2;}
    public void setDamage1 (int Damage1){damageWeap1 = Damage1;}
    public void setDamage2 (int Damage2){damageWeap2 = Damage2;}
    public void setBomb (int Bomb){amountBomb = Bomb;}
    public void setPotion (int Potion){amountPotion = Potion;}
    public void setKey (int Key){amountKey=Key;}
    public void setGem (int Gem){amountGem = Gem;}
    
    
    /**********************************************************
    Methods to retrieve values accessible from the child class */
  
    public int getLevel (){return numberLevel;}
    public int getCooldown1 (){ return cooldownWeap1; }
    public int getCooldown2 (){ return cooldownWeap2;}
    public int getDamage1 (){return damageWeap1;}
    public int getDamage2 (){return damageWeap2;}
    public int getBomb (){return amountBomb;}
    public int getPotion (){return amountPotion;}
    public int getKey (){return amountKey;}
    public int getGem (){return amountGem;}
    
   
   
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
   
     public void writeRec (RandomAccessFile raf) throws IOException {
        raf.seek (0); // move pointer to position in file
        System.out.println("Working....");//to make sure it's working...lol
    
     // write the ints to the file
        raf.writeInt (numberLevel);
        raf.writeInt (cooldownWeap1);
        raf.writeInt (cooldownWeap2);
        raf.writeInt (damageWeap1);
        raf.writeInt (damageWeap2);
        raf.writeInt (amountBomb);
        raf.writeInt (amountPotion);
        raf.writeInt (amountKey);
        raf.writeInt (amountGem);
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
     
     public void readRec (RandomAccessFile raf) throws IOException   {
        raf.seek (0);// move pointer to position in file
        
        // read the ints from the file
        numberLevel = raf.readInt ();
        cooldownWeap1 = raf.readInt ();
        cooldownWeap2 = raf.readInt ();
        damageWeap1 = raf.readInt ();
        damageWeap2 = raf.readInt ();
        amountBomb = raf.readInt ();
        amountPotion = raf.readInt ();
        amountKey = raf.readInt ();
        amountGem = raf.readInt ();
        System.out.println(amountGem + "nah id win");
        
    }  // end readRec
} // Character class

