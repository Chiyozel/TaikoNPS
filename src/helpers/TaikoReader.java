/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import java.io.File;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.JFrame;
import utils.FolderUtils;
import utils.MapUtils;
import utils.Note;
import utils.TaikoUtils;

/**
 *
 * @author Gezochan
 */
public class TaikoReader extends MapUtils implements FolderUtils {

    /**
     * Constructor of NoteChart.
     */
    public TaikoReader() {

        /* You know, to input the values you need that, in console.*/
        Scanner sc = new Scanner(System.in);

        /* Checks if the osu!maps folder exists. If it's not the case, then the
         program creates it. It will close itself after 5 seconds.
         */
        folder = new File("./osuMaps/");


        /* Input file Array. Since the folder exists, it will search for maps
         in the folder ./osuMaps — and adds a file f if for some reason one
         gets to use 1 file only (not supported yet).
         */
        CopyOnWriteArrayList<File> files = FolderUtils.listFiles(new File("./osuMaps/"));
        if (f != null) {
            files.add(f);
        }

        /* Creates an empty list of notes and then processes the files in the 
         folder.
         If there are no files present, it will notice the user.
         */
        notes = new CopyOnWriteArrayList<>();
        if (files.size() > 0) {
            files.stream().forEach((file) -> {
                readMap(file);
                if (contents.contains("Mode: 1")) {
                    taikoModification(file, sc);
                }
            });
        } else {
            System.out.println("No files found!");
        }
    }

    /**
     * Processes a map file.
     *
     * @param file
     * @param sc
     */
    private void taikoModification(File file, Scanner sc) {
        f = file;
        filePath = f.getAbsolutePath();
        
        System.out.println("Do you want to scan this file? (y/n) ");
        if (sc.next().equals("y")) {

            this.copyMap();
            long length = length(notes);
            int INTERVAL = 1000;
            double avgNps = computeNps(notes);
            long np[] = TaikoUtils.NotePerSecond(notes, INTERVAL);
            

            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new PlotNps(np, INTERVAL, avgNps, f.getName()));
            frame.setSize(1024,768);
            frame.setVisible(true);

        }
    }
    
    private double computeNps(List<Note> list){
        Note nb = list.get(0);
        Note ne = list.get(list.size()-1);
        double difference = ne.getMillis() - nb.getMillis();
        double amtNotes = list.size();
        System.out.println(100000*amtNotes/difference);
        
        return (100000*amtNotes/difference)/100;
    }
    
    private long length(List<Note> list){
        Note nb = list.get(0);
        Note ne = list.get(list.size()-1);
        return ne.getMillis() - nb.getMillis();
    }
    
    private boolean isTaikoMap() {
        return contents.contains("Mode: 1\nLetterboxInBreaks");
    }
}
