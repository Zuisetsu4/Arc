package com.example.arc;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import static javafx.scene.paint.Color.rgb;

public class Arc {

    public static boolean inRange(int s, int e, double c) {
        return (!(s > c && c > e) && (s > e)) || (s <= c && c <= e && s < e);
    }
    public static void drawArc(GraphicsContext gc, int xc, int yc, int r, int startAngle, int angle) {
        PixelWriter pw = gc.getPixelWriter();
        int x = 0, y = r, gap, delta = (2 - 2 * r);
        startAngle %= 360;
        angle %= 361;
        double clr = 255.0 / Math.abs(angle - startAngle);
        System.out.println();
        System.out.println(clr);
        while (y >= 0) {
            double currentAngle = Math.abs(Math.toDegrees(Math.atan(((double) y) / x)) - 90);
            if (inRange(startAngle, angle, currentAngle)) {
                // 1 четверть
                pw.setColor(xc + x, yc - y, rgb(0, (int) (currentAngle * clr),0));
            }
            if (inRange(startAngle, angle, 180 - currentAngle)) {
                // 2 четверть
                pw.setColor(xc + x, yc + y, rgb(0,(int) ((180 - currentAngle) * clr),0));
            }
            if (inRange(startAngle, angle, currentAngle + 180)) {
                // 3 четверть
                pw.setColor(xc - x, yc + y, rgb(0,(int) ((currentAngle + 180) * clr),0));
            }
            if (inRange(startAngle, angle, 360 - currentAngle)) {
                // 4 четверть
                pw.setColor(xc - x, yc - y, rgb(0,(int) ((360 - currentAngle) * clr),0));
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
