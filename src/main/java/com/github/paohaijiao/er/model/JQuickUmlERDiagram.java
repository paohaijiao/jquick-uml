package com.github.paohaijiao.er.model;


import com.github.paohaijiao.er.layout.JQuickUmlErPoint;
import com.github.paohaijiao.er.relation.JQuickUmlErRelationship;
import lombok.Data;

import java.awt.*;
import java.util.*;
import java.util.List;

@Data
public class JQuickUmlERDiagram {

    private final Map<String, JQuickUmlErTable> tables = new LinkedHashMap<>();

    private final List<JQuickUmlErRelationship> JQuickUmlErRelationships = new ArrayList<>();

    private String title = "ER Diagram";

    private Dimension size = new Dimension(1600, 1200);

    private String author;

    private Date createdDate = new Date();

    private String description;

    public JQuickUmlERDiagram() {
    }

    public void addTable(JQuickUmlErTable JQuickUmlErTable) {
        if (JQuickUmlErTable != null && JQuickUmlErTable.getName() != null) {
            tables.put(JQuickUmlErTable.getName(), JQuickUmlErTable);
        }
    }

    public JQuickUmlErTable getTable(String name) {
        return tables.get(name);
    }

    public Map<String, JQuickUmlErTable> getTables() {
        return tables;
    }

    public List<JQuickUmlErRelationship> getJQuickUmlErRelationships() {
        return JQuickUmlErRelationships;
    }

    public void addRelationship(JQuickUmlErRelationship JQuickUmlErRelationship) {
        if (JQuickUmlErRelationship != null && !JQuickUmlErRelationships.contains(JQuickUmlErRelationship)) {
            JQuickUmlErRelationships.add(JQuickUmlErRelationship);
            /**
             * 添加到相关表中
             */
            JQuickUmlErTable sourceJQuickUmlErTable = getTable(JQuickUmlErRelationship.getSourceTable());
            JQuickUmlErTable targetJQuickUmlErTable = getTable(JQuickUmlErRelationship.getTargetTable());
            if (sourceJQuickUmlErTable != null) {
                sourceJQuickUmlErTable.addRelationship(JQuickUmlErRelationship);
            }
            if (targetJQuickUmlErTable != null) {
                targetJQuickUmlErTable.addRelationship(JQuickUmlErRelationship);
            }
        }
    }

    public void removeTable(String tableName) {
        JQuickUmlErTable JQuickUmlErTable = tables.remove(tableName);
        if (JQuickUmlErTable != null) {// 移除相关的关系
            JQuickUmlErRelationships.removeIf(rel -> rel.getSourceTable().equals(tableName) || rel.getTargetTable().equals(tableName));
        }
    }

    public void removeRelationship(JQuickUmlErRelationship JQuickUmlErRelationship) {
        JQuickUmlErRelationships.remove(JQuickUmlErRelationship);
    }

    public void setSize(int width, int height) {
        this.size = new Dimension(width, height);
    }

    public JQuickUmlErTable findTableContaining(JQuickUmlErPoint JQuickUmlErPoint) {
        for (JQuickUmlErTable JQuickUmlErTable : tables.values()) {
            if (JQuickUmlErTable.contains(JQuickUmlErPoint)) {
                return JQuickUmlErTable;
            }
        }
        return null;
    }

    public JQuickUmlErRelationship findRelationshipNear(JQuickUmlErPoint JQuickUmlErPoint, double tolerance) {
        for (JQuickUmlErRelationship rel : JQuickUmlErRelationships) {
            if (!rel.getPathJQuickUmlErPoints().isEmpty()) {
                for (int i = 0; i < rel.getPathJQuickUmlErPoints().size() - 1; i++) {
                    JQuickUmlErPoint p1 = rel.getPathJQuickUmlErPoints().get(i);
                    JQuickUmlErPoint p2 = rel.getPathJQuickUmlErPoints().get(i + 1);
                    double distance = distanceToSegment(JQuickUmlErPoint, p1, p2);// 计算点到线段的距离
                    if (distance < tolerance) {
                        return rel;
                    }
                }
            }
        }
        return null;
    }

    private double distanceToSegment(JQuickUmlErPoint p, JQuickUmlErPoint a, JQuickUmlErPoint b) {
        double length2 = (b.x - a.x) * (b.x - a.x) + (b.y - a.y) * (b.y - a.y);
        if (length2 == 0) return p.distance(a);
        double t = Math.max(0, Math.min(1, ((p.x - a.x) * (b.x - a.x) + (p.y - a.y) * (b.y - a.y)) / length2));
        JQuickUmlErPoint projection = new JQuickUmlErPoint(a.x + t * (b.x - a.x), a.y + t * (b.y - a.y));
        return p.distance(projection);
    }
}