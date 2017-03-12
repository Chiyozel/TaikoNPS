/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package randomstuff;

import exception.InvalidTypeException;

/**
 *
 * @author Gezochan
 */
public class NoteType {

    public final int NOTE = 1;
    public final int SLIDER = 2;
    public final int NEW_COMBO = 4;
    public final int SPINNER = 8;

    private String type;

    public NoteType(int type) throws InvalidTypeException {
        if (type > 15) {
            throw new InvalidTypeException();
        }
        this.type = "";
        if (getBit(type, 0)==1) {
            this.type += "[Note]";
        }
        if (getBit(type, 1)==1) {
            this.type += "[Slider]";
        }
        if (getBit(type, 2)==1) {
            this.type += "[New Combo]";
        }
        if (getBit(type, 3)==1) {
            this.type += "[Spinner]";
        }
    }

    @Override
    public String toString() {
        return "Note Type: " + type;
    }

    private int getBit(int n, int k) {
        return (n >> k) & 1;
    }
}
