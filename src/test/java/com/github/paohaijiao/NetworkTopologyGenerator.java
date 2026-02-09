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
package com.github.paohaijiao;

/**
 * packageName com.github.paohaijiao.network
 *
 * @author Martin
 * @version 1.0.0
 * @since 2026/2/9
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.paohaijiao.config.TopologyConfig;
import com.github.paohaijiao.fonts.IconRenderer;
import com.github.paohaijiao.network.TopologyConnection;
import com.github.paohaijiao.network.TopologyNode;
import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.svggen.SVGGraphics2D;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.*;
import java.util.List;

/**
 * 网络拓扑图生成器 - 使用Font Awesome图标
 */
public class NetworkTopologyGenerator {

    private TopologyConfig config;

    private final Map<String, TopologyNode> nodes = new HashMap<>();

    private final List<TopologyConnection> connections = new ArrayList<>();

    private String title;

    private Dimension canvasSize;

    private Color backgroundColor;

    private StyleConfig styleConfig;

    /**
     * 默认构造函数
     */
    public NetworkTopologyGenerator() {
        loadDefaultConfig();
    }

    /**
     * 从YAML文件加载配置
     */
    public NetworkTopologyGenerator(String configFile) {
        loadConfigFromFile(configFile);
    }

    /**
     * 从配置对象创建
     */
    public NetworkTopologyGenerator(TopologyConfig config) {
        this.config = config;
        initializeFromConfig();
    }

    /**
     * 主方法 - 示例用法
     */
    public static void main(String[] args) {
        System.out.println("开始生成网络拓扑图...");
        //使用默认配置
        NetworkTopologyGenerator generator = new NetworkTopologyGenerator();
        generator.generateSVG("d://test//network_topology_default.svg");
        NetworkTopologyGenerator generator2 = new NetworkTopologyGenerator("D:\\idea\\jquick-uml\\src\\main\\resources\\topology-config.yaml"); //从配置文件生成
        generator2.generateSVG("d://test//network_topology_from_config.svg");
        NetworkTopologyGenerator generator3 = new NetworkTopologyGenerator();//方式3: 编程式配置
        TopologyNode customNode = new TopologyNode("custom-server", "server", "自定义服务器", new Point(500, 200));        // 添加自定义节点
        customNode.setColor(new Color(255, 165, 0)); // 橙色
        generator3.addNode(customNode);
        TopologyConnection customConn = new TopologyConnection("custom-server", "core-switch");  // 添加连接
        customConn.setColor(Color.BLUE);
        generator3.addConnection(customConn);
        generator3.setTitle("自定义企业网络拓扑图");
        generator3.generateSVG("d://test//network_topology_custom.svg");//生成
        generator3.saveConfig("d://test//custom-config.yaml");//保存配置
        System.out.println("拓扑图生成完成！");
    }

    /**
     * 加载默认配置
     */
    private void loadDefaultConfig() {
        this.config = createDefaultConfig();//创建默认配置
        initializeFromConfig();
    }

    /**
     * 从文件加载配置
     */
    private void loadConfigFromFile(String configFile) {
        try {
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            this.config = mapper.readValue(new File(configFile), TopologyConfig.class);
            initializeFromConfig();
        } catch (Exception e) {
            System.err.println("Failed to load config file: " + configFile);
            e.printStackTrace();
            loadDefaultConfig();
        }
    }

    /**
     * 从配置初始化
     */
    private void initializeFromConfig() {
        this.title = config.getTitle();
        this.canvasSize = new Dimension(config.getCanvas().getWidth(), config.getCanvas().getHeight());
        this.backgroundColor = config.getCanvas().getBackgroundColor();
        this.styleConfig = new StyleConfig(config.getStyles());
        // 初始化节点
        nodes.clear();
        if (config.getNodes() != null) {
            for (TopologyConfig.NodeConfig nodeConfig : config.getNodes()) {
                TopologyNode node = new TopologyNode(nodeConfig);
                nodes.put(node.getId(), node);
            }
        }
        // 初始化连接
        connections.clear();
        if (config.getConnections() != null) {
            for (TopologyConfig.ConnectionConfig connConfig : config.getConnections()) {
                TopologyConnection connection = new TopologyConnection(connConfig);
                connections.add(connection);
            }
        }
    }

    /**
     * 创建默认配置
     */
    private TopologyConfig createDefaultConfig() {
        TopologyConfig defaultConfig = new TopologyConfig();   // 设置默认节点
        List<TopologyConfig.NodeConfig> defaultNodes = new ArrayList<>();
        defaultNodes.add(createNodeConfig("core-router", "router", "核心路由器", 600, 100, "#FF0000"));
        defaultNodes.add(createNodeConfig("firewall", "firewall", "防火墙", 600, 250, "#FFA500"));
        defaultNodes.add(createNodeConfig("core-switch", "switch", "核心交换机", 600, 400, "#0000FF"));
        defaultNodes.add(createNodeConfig("access-switch-1", "switch", "接入交换机-1", 300, 550, "#0000FF"));
        defaultNodes.add(createNodeConfig("access-switch-2", "switch", "接入交换机-2", 600, 550, "#0000FF"));
        defaultNodes.add(createNodeConfig("access-switch-3", "switch", "接入交换机-3", 900, 550, "#0000FF"));
        defaultNodes.add(createNodeConfig("web-server", "server", "Web服务器", 400, 100, "#008000"));
        defaultNodes.add(createNodeConfig("db-server", "database", "数据库服务器", 600, 100, "#8B008B"));
        defaultNodes.add(createNodeConfig("app-server", "server", "应用服务器", 800, 100, "#008000"));
        defaultNodes.add(createNodeConfig("pc-1", "pc", "PC-1", 200, 700, "#00FFFF"));
        defaultNodes.add(createNodeConfig("pc-2", "pc", "PC-2", 400, 700, "#00FFFF"));
        defaultNodes.add(createNodeConfig("pc-3", "pc", "PC-3", 600, 700, "#00FFFF"));
        defaultNodes.add(createNodeConfig("pc-4", "pc", "PC-4", 800, 700, "#00FFFF"));
        defaultNodes.add(createNodeConfig("pc-5", "pc", "PC-5", 1000, 700, "#00FFFF"));
        defaultNodes.add(createNodeConfig("ap-1", "wifi", "无线AP", 900, 250, "#FF00FF"));
        defaultNodes.add(createNodeConfig("internet", "cloud", "互联网", 600, 50, "#808080"));
        defaultConfig.nodes = defaultNodes;
        List<TopologyConfig.ConnectionConfig> defaultConnections = new ArrayList<>();
        defaultConnections.add(createConnectionConfig("internet", "core-router", "solid", "#000000"));
        defaultConnections.add(createConnectionConfig("core-router", "firewall", "solid", "#000000"));
        defaultConnections.add(createConnectionConfig("firewall", "core-switch", "solid", "#000000"));
        defaultConnections.add(createConnectionConfig("core-switch", "access-switch-1", "solid", "#000000"));
        defaultConnections.add(createConnectionConfig("core-switch", "access-switch-2", "solid", "#000000"));
        defaultConnections.add(createConnectionConfig("core-switch", "access-switch-3", "solid", "#000000"));
        defaultConnections.add(createConnectionConfig("access-switch-1", "pc-1", "solid", "#000000"));
        defaultConnections.add(createConnectionConfig("access-switch-1", "pc-2", "solid", "#000000"));
        defaultConnections.add(createConnectionConfig("access-switch-2", "pc-3", "solid", "#000000"));
        defaultConnections.add(createConnectionConfig("access-switch-3", "pc-4", "solid", "#000000"));
        defaultConnections.add(createConnectionConfig("access-switch-3", "pc-5", "solid", "#000000"));
        defaultConnections.add(createConnectionConfig("web-server", "core-router", "solid", "#000000"));
        defaultConnections.add(createConnectionConfig("db-server", "core-router", "solid", "#000000"));
        defaultConnections.add(createConnectionConfig("app-server", "core-router", "solid", "#000000"));
        defaultConnections.add(createConnectionConfig("ap-1", "core-switch", "solid", "#000000"));
        defaultConnections.add(createConnectionConfig("ap-1", "pc-4", "dashed", "#FF0000", "WiFi"));
        defaultConnections.add(createConnectionConfig("ap-1", "pc-5", "dashed", "#FF0000", "WiFi"));
        defaultConfig.connections = defaultConnections;
        return defaultConfig;
    }

    private TopologyConfig.NodeConfig createNodeConfig(String id, String type, String label,   int x, int y, String color) {
        TopologyConfig.NodeConfig node = new TopologyConfig.NodeConfig();
        node.id = id;
        node.type = type;
        node.label = label;
        node.position = new TopologyConfig.PositionConfig();
        node.position.x = x;
        node.position.y = y;
        node.color = color;
        node.size = 48;
        return node;
    }

    private TopologyConfig.ConnectionConfig createConnectionConfig(String from, String to, String style, String color) {
        return createConnectionConfig(from, to, style, color, null);
    }

    private TopologyConfig.ConnectionConfig createConnectionConfig(String from, String to,  String style, String color, String label) {
        TopologyConfig.ConnectionConfig conn = new TopologyConfig.ConnectionConfig();
        conn.from = from;
        conn.to = to;
        conn.style = style;
        conn.color = color;
        conn.width = 2;
        conn.label = label;
        return conn;
    }

    /**
     * 生成SVG拓扑图
     */
    public void generateSVG(String outputPath) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.newDocument();
            SVGGraphics2D svgGenerator = new SVGGraphics2D(document);
            svgGenerator.setColor(backgroundColor);
            svgGenerator.fillRect(0, 0, canvasSize.width, canvasSize.height);
            drawTopology(svgGenerator);
            File outputFile = new File(outputPath);
            outputFile.getParentFile().mkdirs();
            try (Writer out = new FileWriter(outputFile)) {
                svgGenerator.stream(out, true);
                System.out.println("网络拓扑图已生成: " + outputFile.getAbsolutePath());
            }
        } catch (Exception e) {
            System.err.println("Failed to generate SVG: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 绘制完整的拓扑图
     */
    private void drawTopology(SVGGraphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        drawTitle(g2d);
        drawConnections(g2d);
        drawNodes(g2d);
        if (config.getLegend().isEnabled()) {
            drawLegend(g2d);
        }
    }

    /**
     * 绘制标题
     */
    private void drawTitle(Graphics2D g2d) {
        g2d.setFont(new Font(styleConfig.fontFamily, Font.BOLD, styleConfig.titleFontSize));
        g2d.setColor(styleConfig.titleColor);
        FontMetrics fm = g2d.getFontMetrics();
        int titleWidth = fm.stringWidth(title);
        int titleX = (canvasSize.width - titleWidth) / 2;
        g2d.drawString(title, titleX, 40);
    }

    /**
     * 绘制节点
     */
    private void drawNodes(Graphics2D g2d) {
        g2d.setFont(new Font(styleConfig.fontFamily, Font.PLAIN, styleConfig.labelFontSize));
        g2d.setColor(styleConfig.labelColor);
        for (TopologyNode node : nodes.values()) {
            drawNode(g2d, node);
        }
    }

    /**
     * 绘制单个节点
     */
    private void drawNode(Graphics2D g2d, TopologyNode node) {
        // 绘制图标
        GraphicsNode icon = IconRenderer.createIcon(node.getIconType(),  node.getColor(),  node.getSize());
        if (icon != null) {
            AffineTransform originalTransform = g2d.getTransform();    // 保存当前变换

            int x = node.getPosition().x - node.getSize() / 2;            // 计算图标位置（居中）
            int y = node.getPosition().y - node.getSize() / 2;
            g2d.translate(x, y);  // 移动到图标位置
            icon.paint(g2d);      // 绘制图标

            g2d.setTransform(originalTransform);           // 恢复变换
        } else {
            drawFallbackIcon(g2d, node);            // 如果图标渲染失败，绘制备用图形
        }
        drawNodeLabel(g2d, node);        // 绘制标签
    }

    /**
     * 绘制备用图标（当Font Awesome图标不可用时）
     */
    private void drawFallbackIcon(Graphics2D g2d, TopologyNode node) {
        g2d.setColor(node.getColor());
        Rectangle bounds = node.getBounds();
        switch (node.getType().toLowerCase()) {
            case "router":
                g2d.fillOval(bounds.x, bounds.y, bounds.width, bounds.height);
                break;
            case "switch":
            case "firewall":
                g2d.fillRoundRect(bounds.x, bounds.y, bounds.width, bounds.height, 10, 10);
                break;
            case "server":
            case "database":
                g2d.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
                break;
            case "pc":
            case "client":
                g2d.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);       // 绘制屏幕
                g2d.setColor(Color.WHITE);
                g2d.fillRect(bounds.x + 5, bounds.y + 5, bounds.width - 10, bounds.height - 10);
                g2d.setColor(node.getColor());
                break;
            case "cloud":         // 绘制云朵
                int cloudX = bounds.x;
                int cloudY = bounds.y;
                int cloudWidth = bounds.width;
                int cloudHeight = bounds.height;
                g2d.fillOval(cloudX, cloudY + cloudHeight / 4, cloudWidth / 2, cloudHeight / 2);
                g2d.fillOval(cloudX + cloudWidth / 4, cloudY, cloudWidth / 2, cloudHeight / 2);
                g2d.fillOval(cloudX + cloudWidth / 2, cloudY + cloudHeight / 4, cloudWidth / 2, cloudHeight / 2);
                break;
            case "wifi":
            case "ap":
                g2d.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
                g2d.setColor(Color.WHITE);           // 绘制WiFi信号
                g2d.setStroke(new BasicStroke(2));
                int centerX = bounds.x + bounds.width / 2;
                int centerY = bounds.y + bounds.height / 2;
                for (int i = 0; i < 3; i++) {
                    int radius = (i + 1) * 8;
                    g2d.drawArc(centerX - radius, centerY - radius, radius * 2, radius * 2, 225, 90);
                }
                break;
            default:
                g2d.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
        }
    }

    /**
     * 绘制节点标签
     */
    private void drawNodeLabel(Graphics2D g2d, TopologyNode node) {
        Point labelPos = node.getLabelPosition();
        FontMetrics fm = g2d.getFontMetrics();
        int labelWidth = fm.stringWidth(node.getLabel());
        g2d.setColor(styleConfig.labelColor);
        g2d.drawString(node.getLabel(), labelPos.x - labelWidth / 2, labelPos.y);
    }

    /**
     * 绘制连接线
     */
    private void drawConnections(Graphics2D g2d) {
        for (TopologyConnection connection : connections) {
            TopologyNode fromNode = nodes.get(connection.getFromNodeId());
            TopologyNode toNode = nodes.get(connection.getToNodeId());
            if (fromNode != null && toNode != null) {
                connection.draw(g2d, fromNode, toNode);
            }
        }
    }

    /**
     * 绘制图例
     */
    private void drawLegend(Graphics2D g2d) {
        TopologyConfig.LegendConfig legendConfig = config.getLegend();
        Point legendPos = legendConfig.getPosition().toPoint();
        int legendWidth = 180;
        int legendHeight = 200;
        g2d.setColor(legendConfig.getBackgroundColor());
        g2d.fillRect(legendPos.x - 10, legendPos.y - 30, legendWidth, legendHeight);
        g2d.setColor(legendConfig.getBorderColor());
        g2d.setStroke(new BasicStroke(1));
        g2d.drawRect(legendPos.x - 10, legendPos.y - 30, legendWidth, legendHeight);
        g2d.setFont(new Font(styleConfig.fontFamily, Font.BOLD, styleConfig.legendFontSize));
        g2d.setColor(Color.BLACK);
        g2d.drawString("图例", legendPos.x, legendPos.y);
        // 绘制图例项
        g2d.setFont(new Font(styleConfig.fontFamily, Font.PLAIN, styleConfig.legendFontSize));
        String[] legendItems = {"router:路由器", "switch:交换机", "server:服务器", "firewall:防火墙", "pc:客户端", "wifi:无线AP", "cloud:云/互联网", "database:数据库"};
        for (int i = 0; i < legendItems.length; i++) {
            String[] parts = legendItems[i].split(":");
            if (parts.length == 2) {
                String iconType = parts[0];
                String label = parts[1];
                int y = legendPos.y + 20 + i * 25;
                GraphicsNode icon = IconRenderer.createIcon(iconType, Color.BLACK, 16);               // 绘制图标
                if (icon != null) {
                    AffineTransform original = g2d.getTransform();
                    g2d.translate(legendPos.x, y - 8);
                    icon.paint(g2d);
                    g2d.setTransform(original);
                }
                g2d.setColor(Color.BLACK);          // 绘制标签
                g2d.drawString(label, legendPos.x + 25, y);
            }
        }
    }


    /**
     * 添加节点
     */
    public void addNode(TopologyNode node) {
        nodes.put(node.getId(), node);
    }

    /**
     * 移除节点
     */
    public void removeNode(String nodeId) {
        nodes.remove(nodeId);
        connections.removeIf(conn -> conn.getFromNodeId().equals(nodeId) || conn.getToNodeId().equals(nodeId));//同时移除相关的连接
    }

    /**
     * 添加连接
     */
    public void addConnection(TopologyConnection connection) {
        connections.add(connection);
    }

    /**
     * 更新节点颜色
     */
    public void updateNodeColor(String nodeId, Color color) {
        TopologyNode node = nodes.get(nodeId);
        if (node != null) {
            node.setColor(color);
        }
    }

    /**
     * 更新节点位置
     */
    public void updateNodePosition(String nodeId, Point position) {
        TopologyNode node = nodes.get(nodeId);
        if (node != null) {
            node.setPosition(position);
        }
    }

    /**
     * 设置标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取所有节点
     */
    public Collection<TopologyNode> getNodes() {
        return nodes.values();
    }

    /**
     * 获取所有连接
     */
    public List<TopologyConnection> getConnections() {
        return connections;
    }

    /**
     * 保存当前配置到文件
     */
    public void saveConfig(String configFile) {
        try {
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            mapper.writeValue(new File(configFile), config);
            System.out.println("配置已保存到: " + configFile);
        } catch (Exception e) {
            System.err.println("Failed to save config: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 样式配置包装类
     */
    private static class StyleConfig {

        String fontFamily;

        int titleFontSize;

        int labelFontSize;

        int legendFontSize;

        Color titleColor;

        Color labelColor;

        StyleConfig(TopologyConfig.StyleConfig style) {
            this.fontFamily = style.getFontFamily();
            this.titleFontSize = style.getTitleFontSize();
            this.labelFontSize = style.getLabelFontSize();
            this.legendFontSize = style.getLegendFontSize();
            this.titleColor = style.getTitleColor();
            this.labelColor = style.getLabelColor();
        }
    }
}
