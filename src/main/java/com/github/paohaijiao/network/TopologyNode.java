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

import com.github.paohaijiao.config.TopologyConfig;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * packageName com.github.paohaijiao.network
 *
 * @author Martin
 * @version 1.0.0
 * @since 2026/2/9
 */
public class TopologyNode {
    private final String id;
    private final String type;
    private final String label;
    private Point position;
    private Color color;
    private int size;
    private String iconType;
    private final Map<String, String> metadata;

    public TopologyNode(String id, String type, String label, Point position) {
        this.id = id;
        this.type = type;
        this.label = label;
        this.position = position;
        this.iconType = type;
        this.size = 48;
        this.metadata = new HashMap<>();
        setDefaultColor(type);
    }

    public TopologyNode(TopologyConfig.NodeConfig config) {
        this.id = config.getId();
        this.type = config.getType();
        this.label = config.getLabel();
        this.position = config.getPosition().toPoint();
        this.color = config.getColor();
        this.size = config.getSize();
        this.iconType = config.getIcon();
        this.metadata = new HashMap<>();
    }

    private void setDefaultColor(String type) {
        switch (type.toLowerCase()) {
            case "router":
                this.color = Color.RED;
                break;
            case "switch":
                this.color = Color.BLUE;
                break;
            case "server":
                this.color = Color.GREEN;
                break;
            case "firewall":
                this.color = Color.ORANGE;
                break;
            case "pc":
            case "client":
                this.color = Color.CYAN;
                break;
            case "cloud":
                this.color = Color.GRAY;
                break;
            case "wifi":
            case "ap":
                this.color = Color.MAGENTA;
                break;
            case "database":
                this.color = new Color(139, 0, 139); // 深紫色
                break;
            default:
                this.color = Color.BLACK;
        }
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getLabel() {
        return label;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public Color getColor() {
        return color;
    }

    // Setters
    public void setColor(Color color) {
        this.color = color;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getIconType() {
        return iconType;
    }

    public void setIconType(String iconType) {
        this.iconType = iconType;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(String key, String value) {
        metadata.put(key, value);
    }

    /**
     * 获取节点的边界矩形
     */
    public Rectangle getBounds() {
        return new Rectangle(
                position.x - size / 2,
                position.y - size / 2,
                size,
                size
        );
    }

    /**
     * 获取标签位置（在图标下方）
     */
    public Point getLabelPosition() {
        return new Point(position.x, position.y + size / 2 + 20);
    }
}
