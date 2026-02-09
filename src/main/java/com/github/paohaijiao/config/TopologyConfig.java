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
package com.github.paohaijiao.config;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.awt.*;
import java.util.List;

/**
 * packageName com.github.paohaijiao.config
 *
 * @author Martin
 * @version 1.0.0
 * @since 2026/2/9
 */
public class TopologyConfig {

    @JsonProperty("nodes")
    public List<NodeConfig> nodes;
    @JsonProperty("connections")
    public List<ConnectionConfig> connections;
    @JsonProperty("title")
    private String title = "企业网络拓扑图";
    @JsonProperty("canvas")
    private CanvasConfig canvas = new CanvasConfig();
    @JsonProperty("legend")
    private LegendConfig legend = new LegendConfig();

    @JsonProperty("styles")
    private StyleConfig styles = new StyleConfig();

    public String getTitle() {
        return title;
    }

    public CanvasConfig getCanvas() {
        return canvas;
    }

    public List<NodeConfig> getNodes() {
        return nodes;
    }

    public List<ConnectionConfig> getConnections() {
        return connections;
    }

    public LegendConfig getLegend() {
        return legend;
    }

    public StyleConfig getStyles() {
        return styles;
    }

    // 内部配置类
    public static class CanvasConfig {
        @JsonProperty("width")
        private int width = 1200;

        @JsonProperty("height")
        private int height = 800;

        @JsonProperty("backgroundColor")
        private String backgroundColor = "#FFFFFF";

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        public Color getBackgroundColor() {
            return Color.decode(backgroundColor);
        }
    }

    public static class NodeConfig {
        @JsonProperty("id")
        public String id;

        @JsonProperty("type")
        public String type;

        @JsonProperty("label")
        public String label;

        @JsonProperty("position")
        public PositionConfig position;

        @JsonProperty("icon")
        public String icon;

        @JsonProperty("color")
        public String color;

        @JsonProperty("size")
        public int size = 48;

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

        public PositionConfig getPosition() {
            return position;
        }

        public String getIcon() {
            return icon != null ? icon : type;
        }

        public Color getColor() {
            return color != null ? Color.decode(color) : getDefaultColor(type);
        }

        public int getSize() {
            return size;
        }

        private Color getDefaultColor(String type) {
            switch (type.toLowerCase()) {
                case "router":
                    return Color.RED;
                case "switch":
                    return Color.BLUE;
                case "server":
                    return Color.GREEN;
                case "firewall":
                    return Color.ORANGE;
                case "pc":
                case "client":
                    return Color.CYAN;
                case "cloud":
                    return Color.GRAY;
                case "wifi":
                case "ap":
                    return Color.MAGENTA;
                case "database":
                    return new Color(139, 0, 139); // 深紫色
                default:
                    return Color.BLACK;
            }
        }
    }

    public static class PositionConfig {
        @JsonProperty("x")
        public int x;

        @JsonProperty("y")
        public int y;

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public Point toPoint() {
            return new Point(x, y);
        }
    }

    public static class ConnectionConfig {
        @JsonProperty("from")
        public String from;

        @JsonProperty("to")
        public String to;

        @JsonProperty("style")
        public String style = "solid";

        @JsonProperty("color")
        public String color = "#000000";

        @JsonProperty("width")
        public int width = 2;

        @JsonProperty("label")
        public String label;

        public String getFrom() {
            return from;
        }

        public String getTo() {
            return to;
        }

        public String getStyle() {
            return style;
        }

        public Color getColor() {
            return Color.decode(color);
        }

        public int getWidth() {
            return width;
        }

        public String getLabel() {
            return label;
        }
    }

    public static class LegendConfig {
        @JsonProperty("enabled")
        private boolean enabled = true;

        @JsonProperty("position")
        private PositionConfig position = new PositionConfig();

        @JsonProperty("backgroundColor")
        private String backgroundColor = "#F5F5F5";

        @JsonProperty("borderColor")
        private String borderColor = "#CCCCCC";

        public boolean isEnabled() {
            return enabled;
        }

        public PositionConfig getPosition() {
            if (position.getX() == 0 && position.getY() == 0) {
                position = new PositionConfig();
                position.x = 50;
                position.y = 650;
            }
            return position;
        }

        public Color getBackgroundColor() {
            return Color.decode(backgroundColor);
        }

        public Color getBorderColor() {
            return Color.decode(borderColor);
        }
    }

    public static class StyleConfig {
        @JsonProperty("fontFamily")
        private String fontFamily = "Arial";

        @JsonProperty("titleFontSize")
        private int titleFontSize = 24;

        @JsonProperty("labelFontSize")
        private int labelFontSize = 12;

        @JsonProperty("legendFontSize")
        private int legendFontSize = 12;

        @JsonProperty("titleColor")
        private String titleColor = "#000000";

        @JsonProperty("labelColor")
        private String labelColor = "#333333";

        public String getFontFamily() {
            return fontFamily;
        }

        public int getTitleFontSize() {
            return titleFontSize;
        }

        public int getLabelFontSize() {
            return labelFontSize;
        }

        public int getLegendFontSize() {
            return legendFontSize;
        }

        public Color getTitleColor() {
            return Color.decode(titleColor);
        }

        public Color getLabelColor() {
            return Color.decode(labelColor);
        }
    }
}
