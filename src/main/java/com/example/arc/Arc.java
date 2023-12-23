package com.example.arc;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;


public class Arc {

    public static boolean inRange(double s, double e, double c) {
        return (!(s > c && c > e) && (s > e)) || (s <= c && c <= e && s < e);
    }
    public static Color colorMultiple(Color clr, double clrRatio) {
        double clrRed = clr.getRed() * clrRatio;
        double clrGreen = clr.getGreen() * clrRatio;
        double clrBlue = clr.getBlue() * clrRatio;
        return Color.color(clrRed, clrGreen, clrBlue);
    }
    public static Color colorInterpolate (Color clr, double clrRatio, Color clr2) {
        double clrRed = clr.getRed() * (1 - clrRatio);
        double clrGreen = clr.getGreen() * (1 - clrRatio);
        double clrBlue = clr.getBlue() * (1 - clrRatio);
        double clr2Red = clr2.getRed() * clrRatio;
        double clr2Green = clr2.getGreen() * clrRatio;
        double clr2Blue = clr2.getBlue() * clrRatio;
        return Color.color(clrRed + clr2Red, clrGreen + clr2Green,clrBlue + clr2Blue);
    }
    public static void drawArc(GraphicsContext gc, int xc, int yc, int r,
                               int startAngle, int angle, Color clrStart,
                               Color clrEnd) {
        PixelWriter pw = gc.getPixelWriter();
        int x = 0, y = r, gap, delta = (2 - 2 * r);
        double srtAngle = startAngle * Math.PI / 180;
        double endAngle = angle * Math.PI / 180;
        if (angle < startAngle) {
            double clrCorrelation = (Math.PI * 2 - angle * Math.PI / 180) /
                    (Math.PI * 2 - angle * Math.PI / 180 + startAngle * Math.PI / 180);
            drawArc(gc, xc, yc, r, startAngle, 360, clrStart, colorMultiple(clrEnd, clrCorrelation));
            drawArc(gc, xc, yc, r, 0, angle, colorMultiple(clrEnd, clrCorrelation), clrEnd);
        } else {
            while (y >= 0) {
                double currentAngle = Math.abs(Math.atan(((double) y) / x) - Math.PI / 2);
                if (inRange(srtAngle, endAngle, currentAngle)) {
                    // 1 четверть
                    double ratio = Math.abs(currentAngle - srtAngle) / (endAngle - srtAngle);
                    pw.setColor(xc + x, yc - y, colorInterpolate(clrStart, ratio, clrEnd));
                }
                if (inRange(srtAngle, endAngle, Math.PI - currentAngle)) {
                    // 2 четверть
                    double ratio = Math.abs(Math.PI - currentAngle - srtAngle) / (endAngle - srtAngle);
                    pw.setColor(xc + x, yc + y, colorInterpolate(clrStart, ratio, clrEnd));
                }
                if (inRange(srtAngle, endAngle, currentAngle + Math.PI)) {
                    // 3 четверть
                    double ratio = Math.abs(currentAngle + Math.PI - srtAngle) / (endAngle - srtAngle);
                    pw.setColor(xc - x, yc + y, colorInterpolate(clrStart, ratio, clrEnd));
                }
                if (inRange(srtAngle, endAngle, Math.PI * 2 - currentAngle)) {
                    // 4 четверть
                    double ratio = Math.abs(Math.PI * 2 - currentAngle - srtAngle) / (endAngle - srtAngle);
                    pw.setColor(xc - x, yc - y, colorInterpolate(clrStart, ratio, clrEnd));
                }
                gap = 2 * (delta + y) - 1;
                if (delta < 0 && gap <= 0) {
                    x++;
                    delta += 2 * x + 1;
                    continue;
                }
                if (delta > 0 && gap > 0) {
                    y--;
                    delta -= 2 * y + 1;
                    continue;
                }
                x++;
                delta += 2 * (x - y);
                y--;
            }
        }
    }
}
