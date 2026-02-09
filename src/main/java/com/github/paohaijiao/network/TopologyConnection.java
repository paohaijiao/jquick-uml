/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Copyright (c) [2025-2099] Martin (goudingcheng@gmail.com)
 */
package com.github.paohaijiao.network;

/**
 * packageName com.github.paohaijiao.network
 *
 * @author Martin
 * @version 1.0.0
 * @since 2026/2/9
 */

import com.github.paohaijiao.config.TopologyConfig;

import java.awt.*;

/**
 * 拓扑连接类
 */
public class TopologyConnection {

    private final String fromNodeId;
    private final String toNodeId;
    private Stroke stroke;
    private Color color;
    private String label;

    public TopologyConnection(String fromNodeId, String toNodeId) {
        this.fromNodeId = fromNodeId;
        this.toNodeId = toNodeId;
        this.color = Color.BLACK;
        this.stroke = new BasicStroke(1.5f);
        this.label = null;
    }

    public TopologyConnection(TopologyConfig.ConnectionConfig config) {
        this.fromNodeId = config.getFrom();
        this.toNodeId = config.getTo();
        this.color = config.getColor();
        this.label = config.getLabel();

        // 根据样式创建Stroke
        switch (config.getStyle().toLowerCase()) {
            case "dashed":
                this.stroke = new BasicStroke(config.getWidth(),
                        BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
                        0, new float[]{5}, 0);
                break;
            case "dotted":
                this.stroke = new BasicStroke(config.getWidth(),
                        BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL,
                        0, new float[]{1, 3}, 0);
                break;
            case "solid":
            default:
                this.stroke = new BasicStroke(config.getWidth());
                break;
        }
    }

    // Getters
    public String getFromNodeId() {
        return fromNodeId;
    }

    public String getToNodeId() {
        return toNodeId;
    }

    public Stroke getStroke() {
        return stroke;
    }

    public void setStroke(Stroke stroke) {
        this.stroke = stroke;
    }

    public Color getColor() {
        return color;
    }

    // Setters
    public void setColor(Color color) {
        this.color = color;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * 绘制连接线
     */
    public void draw(Graphics2D g2d, TopologyNode fromNode, TopologyNode toNode) {
        if (fromNode == null || toNode == null) {
            return;
        }

        g2d.setColor(color);
        g2d.setStroke(stroke);

        Point from = fromNode.getPosition();
        Point to = toNode.getPosition();

        // 绘制直线
        g2d.drawLine(from.x, from.y, to.x, to.y);

        // 绘制标签（如果有）
        if (label != null && !label.isEmpty()) {
            drawLabel(g2d, from, to);
        }
    }

    /**
     * 绘制连接标签
     */
    private void drawLabel(Graphics2D g2d, Point from, Point to) {
        // 计算中点
        int midX = (from.x + to.x) / 2;
        int midY = (from.y + to.y) / 2;

        // 保存当前字体和颜色
        Font originalFont = g2d.getFont();
        Color originalColor = g2d.getColor();

        // 设置标签样式
        g2d.setFont(new Font("Arial", Font.PLAIN, 10));
        g2d.setColor(Color.DARK_GRAY);

        // 绘制背景框
        FontMetrics fm = g2d.getFontMetrics();
        int labelWidth = fm.stringWidth(label);
        int labelHeight = fm.getHeight();

        g2d.setColor(new Color(255, 255, 255, 200)); // 半透明白色背景
        g2d.fillRect(midX - labelWidth / 2 - 2, midY - labelHeight / 2 - 2,
                labelWidth + 4, labelHeight + 4);

        g2d.setColor(Color.DARK_GRAY);
        g2d.drawRect(midX - labelWidth / 2 - 2, midY - labelHeight / 2 - 2,
                labelWidth + 4, labelHeight + 4);

        // 绘制文字
        g2d.drawString(label, midX - labelWidth / 2, midY + labelHeight / 4);

        // 恢复原始设置
        g2d.setFont(originalFont);
        g2d.setColor(originalColor);
    }
}
