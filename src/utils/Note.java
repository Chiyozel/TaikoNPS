/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import exception.InvalidHitsoundException;
import exception.InvalidTypeException;
import java.util.logging.Level;
import java.util.logging.Logger;
import randomstuff.Hitsound;
import randomstuff.NoteType;

/**
 *
 * @author Gezochan
 */
public class Note {

    private int x;
    private int y;
    private long ms;
    private int nt;
    private int hs;
    private String params;

    /**
     * Creates a new note, to be outputted in a file.
     *
     * @param x X-axis of the note
     * @param y Y-axis of the note
     * @param ms time position of the note
     * @param nt type of the note (Slider, Note, Spinner, New Combo)
     * @param hs hitsound of the note (No HS, Whistle, Clap, Finish)
     * @param params special parameters of the note (HS additions, possible
     * hitsounding)
     */
    public Note(int x, int y, long ms, int nt, int hs, String params) {
        this.x = x;
        this.y = y;
        this.ms = ms;
        this.nt = nt;
        this.hs = hs;
        this.params = params;
    }

    /**
     * 
     * @return Human-readable version of the note
     */
    @Override
    public String toString() {
        String s="";
        try {
            s = "NOTE {\n\tPosition: (" + x + "," + y + ")"
                    + "\n\tTime: " + ms
                    + "\n\tType: " + new NoteType(nt)
                    + "\n\tHitsounds: " + new Hitsound(hs)
                    + "\n\tSpecial Parameter: " + params
                    + "}";
        } catch (InvalidTypeException | InvalidHitsoundException ex) {
            Logger.getLogger(Note.class.getName()).log(Level.SEVERE, null, ex);
        }
        return s;
    }

    /**
     * 
     * @return osu!-readable version of the note
     */
    public String outputOsuFile() {
        return x + "," + y + "," + ms + "," + nt + "," + hs + "," + params;
    }
    /**
     * Moves the note in the timeline
     * @param offset Relative position of the note (in milliseconds)
     */
    public void move(int offset) {
        ms += offset;
    }

    public long getMillis() {
        return ms;
    }

    public int getHitsound() {
        return hs;
    }
    
    /**
     * Sets a new hitsound to the note
     * @param i The new hitsound
     */

    public void setHitsound(int i) {
        hs = i;
    }
    /**
     * Moves the note to a precise point in the 2D space
     * @param x X-axis of the note
     * @param y Y-axis of the note
     */
    public void moveTo(int x, int y){
        this.x = x;
        this.y = y;
    }
}
