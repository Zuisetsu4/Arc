package com.example.arc;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;


public class ArcController {

    @FXML
    AnchorPane anchorPane;
    @FXML
    private Canvas canvas;

    @FXML
    private void initialize() {
        Arc.drawArc(canvas.getGraphicsContext2D(), 300, 100, 50,  80,   20, Color.BLUE, Color.RED);
    }
}