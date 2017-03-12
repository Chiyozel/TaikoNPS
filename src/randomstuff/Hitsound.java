/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package randomstuff;

import exception.InvalidHitsoundException;

/**
 *
 * @author Gezochan
 */
public class Hitsound {

    public final int WHISTLE = 0b0010;
    public final int FINISH = 0b0100;
    public final int CLAP = 0b1000;

    private String type;
    private int number;

    public Hitsound(int hs) throws InvalidHitsoundException{
        if (hs > 15) {
            throw new InvalidHitsoundException();
        }
        number = hs;
        changeType();
    }

    private void changeType() {
        this.type = "";
        if (getBit(number, 1) == 1) {
            this.type += "[Whistle]";
        }
        if (getBit(number, 2) == 1) {
            this.type += "[Finish]";
        }
        if (getBit(number, 3) == 1) {
            this.type += "[Clap]";
        }
    }

    @Override
    public String toString() {
        return "Hitsound: " + type;
    }

    public void setHitsound(int type) {
        number=type;
        changeType();
    }

    public int getHitsound(){
        return number;
    }
    
    private int getBit(int n, int k) {
        return (n >> k) & 1;
    }
}
