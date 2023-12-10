package com.example.arc;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

import static javafx.scene.paint.Color.rgb;

public class Arc {

    public static boolean inRange(int s, int e, double c) {
        return (!(s > c && c > e) && (s > e)) || (s <= c && c <= e && s < e);
    }
    public static Color colorInterpolate (Color clr, double clrRatio, Color clr2, double clr2Ratio) {
        double clrRed = clr.getRed() * clrRatio;
        double clrGreen = clr.getGreen() * clrRatio;
        double clrBlue = clr.getBlue() * clrRatio;
        double clr2Red = clr2.getRed() * clr2Ratio;
        double clr2Green = clr2.getGreen() * clr2Ratio;
        double clr2Blue = clr2.getBlue() * clr2Ratio;
        return Color.color(clrRed + clr2Red, clrGreen + clr2Green,clrBlue + clr2Blue);
    }
    public static void drawArc(GraphicsContext gc, int xc, int yc, int r,
                               int startAngle, int angle, Color clrStart,
                               Color clrEnd) {
        PixelWriter pw = gc.getPixelWriter();
        int x = 0, y = r, gap, delta = (2 - 2 * r);
        startAngle %= 360;
        angle %= 361;
        while (y >= 0) {
            double currentAngle = Math.abs(Math.toDegrees(Math.atan(((double) y) / x)) - 90);
            if (inRange(startAngle, angle, currentAngle)) {
                // 1 четверть
                double ratio = 1 - currentAngle / 360;
                pw.setColor(xc + x, yc - y, colorInterpolate(clrStart, ratio, clrEnd, 1 - ratio));
            }
            if (inRange(startAngle, angle, 180 - currentAngle)) {
                // 2 четверть
                double ratio = 0.75 - currentAngle / 360;
                pw.setColor(xc + x, yc + y, colorInterpolate(clrStart, ratio, clrEnd, 1 - ratio));
            }
            if (inRange(startAngle, angle, currentAngle + 180)) {
                // 3 четверть
                double ratio = 0.5 - currentAngle / 360;
                pw.setColor(xc - x, yc + y, colorInterpolate(clrStart, ratio, clrEnd, 1 - ratio));
            }
            if (inRange(startAngle, angle, 360 - currentAngle)) {
                // 4 четверть
                double ratio = 0.25 - currentAngle / 360;
                pw.setColor(xc - x, yc - y, colorInterpolate(clrStart, ratio, clrEnd, 1 - ratio));
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
