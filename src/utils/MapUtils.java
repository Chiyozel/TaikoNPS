/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author Gezochan
 */
public class MapUtils {

    

    protected File newChart;
    protected File f;
    protected String filePath;
    protected File folder;
    protected boolean isNewFile;
    protected CopyOnWriteArrayList<Note> notes;
    protected int startTime;
    protected int endTime;
    protected int copyMarker;
    protected String contents;

    /**
     * Method that makes the list of all the notes.
     */
    protected void listNotes() {
        try {

            /* These objects are here to read the file from "osu blabla" to the
             last note recoded on the file. Basically, it will read the whole
             file, "ignoring" everything before [HitObjects], which is the
             most important thing.
             */
            InputStream i = new FileInputStream(filePath);
            InputStreamReader r = new InputStreamReader(i);
            BufferedReader br = new BufferedReader(r);
            /* String that will be the line to read */
            String l;

            /* While the line read isn't [HitObjects], do nothing at all.
             However, to make sure the user knows what he's copying, the map
             details is displayed, and gets confirmed or not.
             */
            while (!(l = br.readLine()).equalsIgnoreCase("[HitObjects]")) {
                if (l.startsWith("TitleUnicode:")
                        || l.startsWith("Version:")
                        || l.startsWith("Creator:")) {
                    System.out.println(l);
                }
            }

            /* Then, at that point, we read every line since now every line is
             a note, and create a note off the line. Yeah the new Note(blah) is
             long.
             */
            while ((l = br.readLine()) != null) {
                String[] values = l.split(",");
                String params = "";
                for (int index = 5; index < values.length; index++) {
                    if (index < values.length - 1) {
                        params += values[index] + ",";
                    } else {
                        params += values[index];
                    }
                }
                notes.add(new Note(
                        Integer.parseInt(values[0]),
                        Integer.parseInt(values[1]),
                        Long.parseLong(values[2]),
                        Integer.parseInt(values[3]),
                        Integer.parseInt(values[4]),
                        params)
                );
            }

        } catch (IOException | NumberFormatException e) {
            System.err.println(e.toString());
        }
    }

    /**
     * Method that copies the content of the map.
     */
    protected void copyMap() {
        try {
            /* These objects are here to read the file from "osu blabla" to the
             last note recoded on the file. Basically, it will read the whole
             file, "ignoring" everything before [HitObjects], which is the
             most important thing.
             */
            notes.clear();
            InputStream i = new FileInputStream(filePath);
            InputStreamReader r = new InputStreamReader(i);
            BufferedReader br = new BufferedReader(r);
            /* String that will be the line to read */
            String l;
            contents = "";

            /* While the line read isn't [HitObjects], do nothing at all.
             However, to make sure the user knows what he's copying, the map
             details is displayed, and gets confirmed or not.
             */
            while (!(l = br.readLine()).equalsIgnoreCase("[HitObjects]")) {
                contents += l + "\n";
            }

            /* Then, at that point, we read every line since now every line is
             a note, and create a note off the line. Yeah the new Note(blah) is
             long.
             */
            while ((l = br.readLine()) != null) {
                String[] values = l.split(",");
                String params = "";
                for (int index = 5; index < values.length; index++) {
                    if (index < values.length - 1) {
                        params += values[index] + ",";
                    } else {
                        params += values[index];
                    }
                }
                notes.add(new Note(
                        Integer.parseInt(values[0]),
                        Integer.parseInt(values[1]),
                        Long.parseLong(values[2]),
                        Integer.parseInt(values[3]),
                        Integer.parseInt(values[4]),
                        params)
                );
            }

        } catch (IOException | NumberFormatException e) {
            System.err.println(e.toString());
        }
    }

    protected void getPathMap(File file) {
        f = file;
        System.out.println("Name of the map to scan: " + f.getName());
        filePath = f.getAbsolutePath();
    }

    protected void readMap(File file) {
        try {

            this.getPathMap(file);
            /* These objects are here to read the file from "osu blabla" to the
             last note recoded on the file. Basically, it will read the whole
             file, "ignoring" everything before [HitObjects], which is the
             most important thing.
             */
            InputStream i = new FileInputStream(filePath);
            InputStreamReader r = new InputStreamReader(i);
            BufferedReader br = new BufferedReader(r);
            /* String that will be the line to read */
            String l;
            contents = "";
            while ((l = br.readLine()) != null) {
                contents += l + "\n";
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println(e.toString());
        }
    }

    /**
     * Deletes unnecessary notes from the notechart.
     */
    protected void deleteNotes() {
        /* For each note that isn't in the range, delete it. */
        notes.stream().filter((n) -> (n.getMillis() < startTime || n.getMillis() > endTime)).forEach((n) -> {
            notes.remove(n);
        });
    }
}
