package com.example.arc;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;


public class ArcController {

    @FXML
    AnchorPane anchorPane;
    @FXML
    private Canvas canvas;

    @FXML
    private void initialize() {
        Arc.drawArc(canvas.getGraphicsContext2D(), 300, 100, 50,  0,  200);
    }
}