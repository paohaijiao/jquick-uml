package com.github.paohaijiao.er.model;

import com.github.paohaijiao.er.layout.JQuickUmlErPoint;
import com.github.paohaijiao.er.relation.JQuickUmlErRelationship;
import lombok.Data;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class JQuickUmlErTable {

    private String name;

    private String comment;

    private List<JQuickUmlErColumn> JQuickUmlErColumns = new ArrayList<>();

    private List<JQuickUmlErRelationship> JQuickUmlErRelationships = new ArrayList<>();

    private JQuickUmlErPoint position = new JQuickUmlErPoint(0, 0);

    private Dimension size = new Dimension(280, 150);

    private Color color;

    private Rectangle bounds;

    private boolean selected = false;

    private int zIndex = 0;

    public JQuickUmlErTable() {
    }

    public JQuickUmlErTable(String name) {
        this.name = name;
    }


    public void addColumn(JQuickUmlErColumn JQuickUmlErColumn) {
        if (JQuickUmlErColumn != null) {
            this.JQuickUmlErColumns.add(JQuickUmlErColumn);
        }
    }


    public void addRelationship(JQuickUmlErRelationship JQuickUmlErRelationship) {
        if (JQuickUmlErRelationship != null && !JQuickUmlErRelationships.contains(JQuickUmlErRelationship)) {
            this.JQuickUmlErRelationships.add(JQuickUmlErRelationship);
        }
    }

    public JQuickUmlErColumn getColumn(String name) {
        return JQuickUmlErColumns.stream()
                .filter(c -> c.getName().equals(name))
                .findFirst()
                .orElse(null);
    }


    public void setPosition(double x, double y) {
        this.position = new JQuickUmlErPoint(x, y);
        updateBounds();
    }

    public Dimension getSize() {
        return size;
    }

    public void setSize(Dimension size) {
        this.size = size;
        updateBounds();
    }

    public void setSize(int width, int height) {
        this.size = new Dimension(width, height);
        updateBounds();
    }


    public Rectangle getBounds() {
        if (bounds == null) {
            updateBounds();
        }
        return bounds;
    }

    private void updateBounds() {
        bounds = new Rectangle(
                (int) position.x,
                (int) position.y,
                size.width,
                size.height
        );
    }

    public boolean contains(JQuickUmlErPoint JQuickUmlErPoint) {
        return bounds != null && bounds.contains(JQuickUmlErPoint.x, JQuickUmlErPoint.y);
    }

    public JQuickUmlErPoint getCenter() {
        return new JQuickUmlErPoint(position.x + size.width / 2.0, position.y + size.height / 2.0);
    }

    public JQuickUmlErPoint getConnectionPoint(JQuickUmlErPoint target) {
        JQuickUmlErPoint center = getCenter();
        double dx = target.x - center.x;
        double dy = target.y - center.y;
        if (Math.abs(dx) > Math.abs(dy)) {// 水平方向
            double x = dx > 0 ? position.x + size.width : position.x;
            double y = center.y;
            return new JQuickUmlErPoint(x, y);
        } else {// 垂直方向
            double x = center.x;
            double y = dy > 0 ? position.y + size.height : position.y;
            return new JQuickUmlErPoint(x, y);
        }
    }
}
