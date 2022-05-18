package sample;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortedPane extends Pane {

    private DoubleProperty spacing;

    public double getSpacing() {
        return spacing == null ? 0 : spacing.getValue();
    }

    public void setSpacing(double spacing) {
        if (this.spacing == null){
            this.spacing = new SimpleDoubleProperty(0);
        }
        this.spacing.set(spacing);
    }

    public SortedPane() {
        super();
    }

    public SortedPane(double spacing) {
        this();
        setSpacing(spacing);
    }

    public final DoubleProperty spacingProperty(){
        if (spacing == null){
            spacing = new SimpleDoubleProperty(0);
        }
        return spacing;
    }

    @Override
    protected void layoutChildren() {
        List<Node> sortedChildren = new ArrayList<>(getChildren());
        Collections.sort(sortedChildren, (c1, c2) -> new Double(c1.prefWidth(-1)).compareTo(new Double(c2.prefWidth(-1))));
        double currentX = getInsets().getLeft();
        for (Node c : sortedChildren) {
            double width = c.prefWidth(-1);
            double height = c.prefHeight(-1);
            layoutInArea(c, currentX, getInsets().getTop(), width, height, 0, HPos.CENTER, VPos.CENTER);
            currentX = currentX + width + getSpacing();
        }
    }

    @Override
    protected double computeMinWidth(double v) {
        return computePrefWidth(v);
    }

    @Override
    protected double computeMinHeight(double v) {
        return computePrefHeight(v);
    }

    @Override
    protected double computePrefWidth(double v) {
        double width = 0;
        for (Node c : getChildren()){
            width = width + c.prefWidth(-1);
        }
        double cumulatedSpacing = 0;
        if (getChildren().size() > 1){
            cumulatedSpacing = (getChildren().size() - 1) * getSpacing();
        }
        return getInsets().getLeft() + width + getInsets().getRight() + cumulatedSpacing;
    }

    @Override
    protected double computePrefHeight(double v) {
        double maxHeight = 0;
        for (Node c : getChildren()){
            maxHeight = Math.max(c.prefHeight(-1), maxHeight);
        }
        return getInsets().getTop() + maxHeight + getInsets().getBottom();
    }

    @Override
    protected double computeMaxWidth(double v) {
        return computePrefWidth(v);
    }

    @Override
    protected double computeMaxHeight(double v) {
        return computePrefHeight(v);
    }
}
