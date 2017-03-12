/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.List;

/**
 *
 * @author Gezochan
 */
public class TaikoUtils {

    public static long[] NotePerSecond(List<Note> notes, int INTERVAL) {
        long minimum = 0;
        long maximum = notes.get(notes.size()-1).getMillis()+INTERVAL;
        long notesInInterval[] = new long[(int)(maximum/INTERVAL)+1];
        int q = 0, J;
        
        for(long A=minimum; A<maximum; A+=INTERVAL){
            J = 0;
            for(Note n:notes){
                if(n.getMillis() >= A && n.getMillis() <= A+INTERVAL)
                    J++;
            }
            notesInInterval[q] = J*(1000/INTERVAL);
            q++;
        }
        
        return notesInInterval;
    }

    public static void graph(long[] np) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
