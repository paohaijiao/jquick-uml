package com.github.paohaijiao.er.relation;

import com.github.paohaijiao.enums.JQuickUmlErRelationshipType;
import com.github.paohaijiao.er.layout.JQuickUmlErPoint;
import lombok.Data;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class JQuickUmlErRelationship {

    private String sourceTable;

    private String sourceColumn;

    private String targetTable;

    private String targetColumn;

    private JQuickUmlErRelationshipType type;

    private String label;

    private Color color;

    private List<JQuickUmlErPoint> pathJQuickUmlErPoints = new ArrayList<>();

    private JQuickUmlErPoint labelPosition;

    private boolean selected = false;

    public JQuickUmlErRelationship() {
    }

    public JQuickUmlErRelationship(String sourceTable, String sourceColumn, String targetTable, String targetColumn, JQuickUmlErRelationshipType type) {
        this.sourceTable = sourceTable;
        this.sourceColumn = sourceColumn;
        this.targetTable = targetTable;
        this.targetColumn = targetColumn;
        this.type = type;
    }

    public void addPathPoint(JQuickUmlErPoint JQuickUmlErPoint) {
        this.pathJQuickUmlErPoints.add(JQuickUmlErPoint);
    }

    public void clearPathPoints() {
        this.pathJQuickUmlErPoints.clear();
    }

    public boolean connectsTables(String table1, String table2) {
        return (sourceTable.equals(table1) && targetTable.equals(table2)) || (sourceTable.equals(table2) && targetTable.equals(table1));
    }


}
