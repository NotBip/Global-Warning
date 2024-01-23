/**
***********************************************
* @Author : Nusayba Hamou
* @Originally made : 2 OCT, 2023
* @Last Modified: 21 JAN, 2024
* @Description: Records/saves that contain all save file information
***********************************************
*/

package Objects.Saving;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Save {

    // variables
    int numberLevel;
    int weaponHold;
    int playerHealth;
    int cooldownWeap1;
    int cooldownWeap2;
    int damageWeap1;
    int damageWeap2;
    int amountBomb;
    int amountPotion;
    int amountKey;
    int amountGem;

    /********************************************************
     * Blank constructor will enter null string or 0 for each field
     */
    public Save() {
        numberLevel = 0;
        weaponHold = 1;
        playerHealth = 0;
        cooldownWeap1 = 250;
        cooldownWeap2 = 350;
        damageWeap1 = 10;
        damageWeap2 = 20;
        amountBomb = 3;
        amountPotion = 2;
        amountKey = 0;
        amountGem = 0;
    }// end Character

    /**********************************************************
     * Methods to assign values accessible from the child class
     */

    public void setLevel(int Level) {
        numberLevel = Level;
    }

    public void setHold(int hold) {
        weaponHold = hold;
    }

    public void setHealth(int Health) {
        playerHealth = Health;
    }

    public void setCooldown1(int Cooldown1) {
        cooldownWeap1 = Cooldown1;
    }

    public void setCooldown2(int Cooldown2) {
        cooldownWeap2 = Cooldown2;
    }

    public void setDamage1(int Damage1) {
        damageWeap1 = Damage1;
    }

    public void setDamage2(int Damage2) {
        damageWeap2 = Damage2;
    }

    public void setBomb(int Bomb) {
        amountBomb = Bomb;
    }

    public void setPotion(int Potion) {
        amountPotion = Potion;
    }

    public void setKey(int Key) {
        amountKey = Key;
    }

    public void setGem(int Gem) {
        amountGem = Gem;
    }

    /**********************************************************
     * Methods to retrieve values accessible from the child class
     */

    public int getLevel() {
        return numberLevel;
    }

    public int getHold() {
        return weaponHold;
    }

    public int getHealth() {
        return playerHealth;
    }

    public int getCooldown1() {
        return cooldownWeap1;
    }

    public int getCooldown2() {
        return cooldownWeap2;
    }

    public int getDamage1() {
        return damageWeap1;
    }

    public int getDamage2() {
        return damageWeap2;
    }

    public int getBomb() {
        return amountBomb;
    }

    public int getPotion() {
        return amountPotion;
    }

    public int getKey() {
        return amountKey;
    }

    public int getGem() {
        return amountGem;
    }

    /**********************************************************
     * method to write one record of information to a random access file
     */

    /**
     * @Method Name: writeRec
     * @Author: Nusayba Hamou
     * @Date 2 OCT 2023
     * @Description: writes a record to the raf
     * @Parameters: random access file
     * @Returns: N/A
     * @Throws/Exceptions: IOException
     **/

    public void writeRec(RandomAccessFile raf) throws IOException {
        raf.seek(0); // move pointer to position in file

        // write the ints to the file
        raf.writeInt(numberLevel);
        raf.writeInt(weaponHold);
        raf.writeInt(playerHealth);
        raf.writeInt(cooldownWeap1);
        raf.writeInt(cooldownWeap2);
        raf.writeInt(damageWeap1);
        raf.writeInt(damageWeap2);
        raf.writeInt(amountBomb);
        raf.writeInt(amountPotion);
        raf.writeInt(amountKey);
        raf.writeInt(amountGem);
    }// end writeRec

    /**********************************************************
     * method to read one record of information from a random access file
     */

    /**
     * @Method Name: readRec
     * @Author: Nusayba Hamou
     * @Date 2 OCT 2023
     * @Description: reads a record from the raf
     * @Parameters: random access file
     * @Returns: N/A
     * @Throws/Exceptions: IOException
     **/

    public void readRec(RandomAccessFile raf) throws IOException {
        raf.seek(0);// move pointer to position in file

        if (raf.length() != 0) {

            // read the ints from the file
            numberLevel = raf.readInt();
            weaponHold = raf.readInt();
            playerHealth = raf.readInt();
            cooldownWeap1 = raf.readInt();
            cooldownWeap2 = raf.readInt();
            damageWeap1 = raf.readInt();
            damageWeap2 = raf.readInt();
            amountBomb = raf.readInt();
            amountPotion = raf.readInt();
            amountKey = raf.readInt();
            amountGem = raf.readInt();

        } else {
            System.out.println("it's empty");
        }

    } // end readRec
} // save class
