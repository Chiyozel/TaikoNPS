/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.Line2D;
import javax.swing.JPanel;

/**
 *
 * @author Unmei Muma
 */
class PlotNps extends JPanel{
    long[] data;
    int Interval;
    double nps;
    String mapName;
    
    final int PAD = 50;

    public PlotNps(long[] data, int Interval, double nps, String m){
        this.data = data;
        this.Interval = Interval;
        this.nps = nps;
        mapName = m;
    }
    
   protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        int w = getWidth();
        int h = getHeight();
        int ew = w-2*PAD;
        int eh = h-2*PAD;
        long max = getMax();
        
        // Set Font
        g2.setFont(new Font("serif", Font.PLAIN, 12));
        
        // Draw ordinate.
        g2.draw(new Line2D.Double(PAD, PAD, PAD, h-PAD));
        
        // Draw abcissa.
        g2.draw(new Line2D.Double(PAD, h-PAD, w-PAD, h-PAD));
        
        // Draw abcissa for each 1 NPS.
        g2.setPaint(Color.LIGHT_GRAY);
        for(int i = 0; i<=max; i++){
            g2.draw(new Line2D.Double(PAD+1, h - PAD - (i * (double)(h - 2*PAD))/getMax(), w-PAD, h - PAD - (i * (double)(h - 2*PAD))/getMax()));
        }
        
        // Draw Average NPS
        g2.setPaint(Color.blue);
        g2.draw(new Line2D.Double(PAD+1, h - PAD - (nps * (double)(h - 2*PAD))/getMax(), w-PAD, h - PAD - (nps * (double)(h - 2*PAD))/getMax()));
        
        // Draw labels.
        g2.setPaint(Color.BLACK);
        Font font = g2.getFont();
        FontRenderContext frc = g2.getFontRenderContext();
        LineMetrics lm = font.getLineMetrics("0", frc);
        float sh = lm.getAscent() + lm.getDescent();
        
        // Ordinate label.
        String s; float sy;
        for(int i = 0; i<=max; i++){
            s = Integer.toString(i);
            sy = h - PAD - (i * (float)(h - 2*PAD))/getMax() + lm.getAscent();
            float sw = (float)font.getStringBounds(s, frc).getWidth();
            float sx = PAD-10;
            FontMetrics fm = g2.getFontMetrics();
            g2.drawString(s, sx - fm.stringWidth(s), sy-7);
        }
        
        // Draw average text
        g2.setPaint(Color.blue);
        s = Float.toString((float)((int)(nps*1000))/1000);
        sy = (float)(h - PAD - (nps * (float)(h - 2*PAD))/getMax() + lm.getAscent());
        float sx = w - PAD + 3;
        FontMetrics fm = g2.getFontMetrics();
        g2.drawString(s, sx, sy-7);
        
        // Abcissa label.
        g2.setPaint(Color.black);
        s = "Average notes/sec : " + (double)((int)(nps*1000))/1000;
        sy = h - PAD + (PAD - sh)/2 + lm.getAscent();
        float sw = (float)font.getStringBounds(s, frc).getWidth();
        sx = (w - sw)/2;
        g2.drawString(s, sx, sy);
        
        // Map Name.
        g2.setFont(new Font("serif", Font.BOLD, 16));
        s = "NPS Chart of file: " + mapName;
        sy = (int) (PAD / 2.25);
        g2.drawString(s, PAD, sy);
        
        // Draw lines.
        double xInc = (double)(w - 2*PAD)/(data.length-1);
        double scale = (double)(h - 2*PAD)/getMax();
        g2.setPaint(Color.green.darker());
        for(int i = 0; i < data.length-1; i++) {
            double x1 = PAD + i*xInc;
            double y1 = h - PAD - scale*data[i];
            double x2 = PAD + (i+1)*xInc;
            double y2 = h - PAD - scale*data[i+1];
            g2.draw(new Line2D.Double(x1, y1, x2, y2));
        }
    }
 
    private long getMax() {
        long max = -Integer.MAX_VALUE;
        for(int i = 0; i < data.length; i++) {
            if(data[i] > max)
                max = data[i];
        }
        return max;
    }
}
