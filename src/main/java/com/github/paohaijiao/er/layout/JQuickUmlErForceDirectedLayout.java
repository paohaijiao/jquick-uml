package com.github.paohaijiao.er.layout;


import com.github.paohaijiao.er.model.JQuickUmlERDiagram;
import com.github.paohaijiao.er.model.JQuickUmlErTable;
import com.github.paohaijiao.er.relation.JQuickUmlErRelationship;
import lombok.Data;

import java.util.*;

@Data
public class JQuickUmlErForceDirectedLayout {

    private static final double REPULSION_FORCE = 8000;

    private static final double ATTRACTION_FORCE = 0.04;

    private static final double IDEAL_DISTANCE = 300;

    private static final double DAMPING = 0.85;

    private static final int MAX_ITERATIONS = 500;

    private static final double MIN_TEMPERATURE = 0.1;

    public static void layout(JQuickUmlERDiagram diagram) {
        Map<String, JQuickUmlErTable> tables = diagram.getTables();
        List<JQuickUmlErRelationship> JQuickUmlErRelationships = diagram.getJQuickUmlErRelationships();
        if (tables.isEmpty()) return;
        initializePositions(tables.values());// 初始化位置
        /**
         * 力导向布局
         */
        double temperature = 100.0;
        for (int iteration = 0; iteration < MAX_ITERATIONS && temperature > MIN_TEMPERATURE; iteration++) {
            temperature *= DAMPING;
            Map<String, JQuickUmlErPoint> forces = new HashMap<>();
            /**
             * 计算排斥力
             */
            for (JQuickUmlErTable JQuickUmlErTable1 : tables.values()) {
                JQuickUmlErPoint force = new JQuickUmlErPoint(0, 0);
                for (JQuickUmlErTable JQuickUmlErTable2 : tables.values()) {
                    if (JQuickUmlErTable1 == JQuickUmlErTable2) continue;
                    JQuickUmlErPoint delta = JQuickUmlErTable2.getPosition().subtract(JQuickUmlErTable1.getPosition());
                    double distance = delta.length();
                    if (distance == 0) {
                        delta = new JQuickUmlErPoint(Math.random() - 0.5, Math.random() - 0.5);
                        distance = 0.1;
                    }
                    /**
                     * 库仑斥力
                     */
                    double repulsion = REPULSION_FORCE / (distance * distance);
                    JQuickUmlErPoint repulsionForce = delta.normalize().multiply(-repulsion);
                    force = force.add(repulsionForce);
                }

                forces.put(JQuickUmlErTable1.getName(), force);
            }
            /**
             * 计算吸引力（基于关系）
             */
            for (JQuickUmlErRelationship rel : JQuickUmlErRelationships) {
                JQuickUmlErTable source = tables.get(rel.getSourceTable());
                JQuickUmlErTable target = tables.get(rel.getTargetTable());
                if (source == null || target == null) continue;
                JQuickUmlErPoint delta = target.getPosition().subtract(source.getPosition());
                double distance = delta.length();
                if (distance == 0) continue;
                /**
                 * 胡克定律吸引力
                 */
                double attraction = ATTRACTION_FORCE * (distance - IDEAL_DISTANCE);
                JQuickUmlErPoint attractionForce = delta.normalize().multiply(attraction);
                /**
                 * 更新力
                 */
                JQuickUmlErPoint sourceForce = forces.get(source.getName());
                JQuickUmlErPoint targetForce = forces.get(target.getName());
                forces.put(source.getName(), sourceForce.add(attractionForce));
                forces.put(target.getName(), targetForce.subtract(attractionForce));
            }
            /**
             * 应用力（考虑温度）
             */
            for (JQuickUmlErTable JQuickUmlErTable : tables.values()) {
                JQuickUmlErPoint force = forces.get(JQuickUmlErTable.getName());
                if (force.length() > temperature) {
                    force = force.normalize().multiply(temperature);
                }
                JQuickUmlErPoint newPos = JQuickUmlErTable.getPosition().add(force);
                JQuickUmlErTable.setPosition(newPos);
            }
            /**
             * 防止重叠
             */
            avoidOverlap(tables.values());
        }
        /**
         * 调整到画布中心
         */
        centerDiagram(tables.values(), diagram.getSize());
    }

    private static void initializePositions(Collection<JQuickUmlErTable> JQuickUmlErTables) {
        int count = JQuickUmlErTables.size();
        double radius = Math.min(400, count * 50);
        int i = 0;
        for (JQuickUmlErTable JQuickUmlErTable : JQuickUmlErTables) {
            double angle = 2 * Math.PI * i / count;
            double x = radius * Math.cos(angle);
            double y = radius * Math.sin(angle);
            JQuickUmlErTable.setPosition(x, y);
            i++;
        }
    }

    private static void avoidOverlap(Collection<JQuickUmlErTable> JQuickUmlErTables) {
        List<JQuickUmlErTable> JQuickUmlErTableList = new ArrayList<>(JQuickUmlErTables);
        for (int i = 0; i < JQuickUmlErTableList.size(); i++) {
            JQuickUmlErTable t1 = JQuickUmlErTableList.get(i);
            for (int j = i + 1; j < JQuickUmlErTableList.size(); j++) {
                JQuickUmlErTable t2 = JQuickUmlErTableList.get(j);
                double dx = t2.getPosition().x - t1.getPosition().x;
                double dy = t2.getPosition().y - t1.getPosition().y;
                double distance = Math.sqrt(dx * dx + dy * dy);
                double minDistance = Math.max(t1.getSize().width, t1.getSize().height) / 2 + Math.max(t2.getSize().width, t2.getSize().height) / 2 + 20;
                if (distance < minDistance) {
                    double overlap = minDistance - distance;
                    if (overlap > 0) {
                        JQuickUmlErPoint direction = new JQuickUmlErPoint(dx, dy).normalize();
                        JQuickUmlErPoint adjustment = direction.multiply(overlap / 2);
                        t1.setPosition(t1.getPosition().subtract(adjustment));
                        t2.setPosition(t2.getPosition().add(adjustment));
                    }
                }
            }
        }
    }

    private static void centerDiagram(Collection<JQuickUmlErTable> JQuickUmlErTables, java.awt.Dimension canvasSize) {
        if (JQuickUmlErTables.isEmpty()) return;
        /**
         * 计算边界
         */
        double minX = Double.MAX_VALUE, maxX = Double.MIN_VALUE;
        double minY = Double.MAX_VALUE, maxY = Double.MIN_VALUE;
        for (JQuickUmlErTable JQuickUmlErTable : JQuickUmlErTables) {
            minX = Math.min(minX, JQuickUmlErTable.getPosition().x);
            maxX = Math.max(maxX, JQuickUmlErTable.getPosition().x + JQuickUmlErTable.getSize().width);
            minY = Math.min(minY, JQuickUmlErTable.getPosition().y);
            maxY = Math.max(maxY, JQuickUmlErTable.getPosition().y + JQuickUmlErTable.getSize().height);
        }

        double width = maxX - minX;
        double height = maxY - minY;
        /**
         * 计算偏移量
         */
        double offsetX = (canvasSize.width - width) / 2 - minX;
        double offsetY = (canvasSize.height - height) / 2 - minY;
        /**
         * 应用偏移
         */
        for (JQuickUmlErTable JQuickUmlErTable : JQuickUmlErTables) {
            JQuickUmlErTable.setPosition(JQuickUmlErTable.getPosition().x + offsetX, JQuickUmlErTable.getPosition().y + offsetY);
        }
    }

    public static void calculateRelationshipPaths(JQuickUmlERDiagram diagram) {
        for (JQuickUmlErRelationship rel : diagram.getJQuickUmlErRelationships()) {
            rel.clearPathPoints();
            JQuickUmlErTable source = diagram.getTable(rel.getSourceTable());
            JQuickUmlErTable target = diagram.getTable(rel.getTargetTable());
            if (source == null || target == null) continue;
            /**
             * 获取连接点
             */
            JQuickUmlErPoint sourceJQuickUmlErPoint = source.getConnectionPoint(target.getCenter());
            JQuickUmlErPoint targetJQuickUmlErPoint = target.getConnectionPoint(source.getCenter());
            /**
             * 创建贝塞尔曲线控制点
             */
            double dx = targetJQuickUmlErPoint.x - sourceJQuickUmlErPoint.x;
            double dy = targetJQuickUmlErPoint.y - sourceJQuickUmlErPoint.y;
            JQuickUmlErPoint control1, control2;
            if (Math.abs(dx) > Math.abs(dy)) {
                /**
                 * 水平为主
                 */
                control1 = new JQuickUmlErPoint(sourceJQuickUmlErPoint.x + dx * 0.3, sourceJQuickUmlErPoint.y);
                control2 = new JQuickUmlErPoint(targetJQuickUmlErPoint.x - dx * 0.3, targetJQuickUmlErPoint.y);
            } else {
                /**
                 * 垂直为主
                 */
                control1 = new JQuickUmlErPoint(sourceJQuickUmlErPoint.x, sourceJQuickUmlErPoint.y + dy * 0.3);
                control2 = new JQuickUmlErPoint(targetJQuickUmlErPoint.x, targetJQuickUmlErPoint.y - dy * 0.3);
            }
            /**
             * 添加路径点
             */
            rel.addPathPoint(sourceJQuickUmlErPoint);
            rel.addPathPoint(control1);
            rel.addPathPoint(control2);
            rel.addPathPoint(targetJQuickUmlErPoint);
            /**
             * 计算标签位置
             */
            JQuickUmlErPoint labelPos = calculateBezierPoint(0.5, sourceJQuickUmlErPoint, control1, control2, targetJQuickUmlErPoint);
            rel.setLabelPosition(labelPos);
        }
    }

    private static JQuickUmlErPoint calculateBezierPoint(double t, JQuickUmlErPoint p0, JQuickUmlErPoint p1, JQuickUmlErPoint p2, JQuickUmlErPoint p3) {
        double u = 1 - t;
        double tt = t * t;
        double uu = u * u;
        double uuu = uu * u;
        double ttt = tt * t;
        double x = uuu * p0.x + 3 * uu * t * p1.x + 3 * u * tt * p2.x + ttt * p3.x;
        double y = uuu * p0.y + 3 * uu * t * p1.y + 3 * u * tt * p2.y + ttt * p3.y;
        return new JQuickUmlErPoint(x, y);
    }
}