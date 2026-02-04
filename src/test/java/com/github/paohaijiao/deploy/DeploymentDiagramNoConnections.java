package com.github.paohaijiao.deploy;/*
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

import com.github.paohaijiao.deploy.component.ClusterComponent;
import com.github.paohaijiao.deploy.component.ServiceComponent;
import com.github.paohaijiao.deploy.layer.ClusterLayer;
import com.github.paohaijiao.deploy.layer.ServiceLayer;
import com.github.paohaijiao.deploy.model.*;
import org.apache.batik.svggen.SVGGraphics2D;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.io.FileWriter;
import java.util.List;

public class DeploymentDiagramNoConnections {

    public static void main(String[] args) {
        try {
            // 创建完整配置
            DeploymentArchitectureConfig config = new DeploymentArchitectureConfig();
            generateDeploymentDiagram("d://test//deployment_architecture_clean.svg", config);
            System.out.println("SVG文件生成成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成部署架构图（使用完整对象模型）
     */
    public static void generateDeploymentDiagram(String filename, DeploymentArchitectureConfig config) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.newDocument();
        SVGGraphics2D g2d = new SVGGraphics2D(document);

        // 获取配置
        CanvasConfig canvas = config.getCanvasConfig();
        ColorConfig colors = config.getColorConfig();
        FontConfig fonts = config.getFontConfig();
        LayoutConfig layout = config.getLayoutConfig();
        RootPlatform root = config.getRootPlatform();

        // 设置画布
        g2d.setSVGCanvasSize(canvas.getSize());
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setPaint(canvas.getBackgroundColor());
        g2d.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // 1. 画布标题
        g2d.setFont(fonts.getTitleFont());
        g2d.setPaint(colors.getTextColor());
        drawCenteredString(g2d, canvas.getTitle(), canvas.getCenterX(), layout.getTopMargin() - 25);

        // 2. 核心平台
        int rootBoxX = config.getRootBoxX();
        drawRoundedRect(g2d, rootBoxX, root.getBoxY(), layout.getRootBoxWidth(), layout.getRootBoxHeight(), layout.getRootArc(), colors.getRootColor());
        g2d.setFont(fonts.getRootTitleFont());
        g2d.setPaint(colors.getWhite());
        drawCenteredString(g2d, root.getName(), canvas.getCenterX(), root.getTitleY());
//        g2d.setFont(fonts.getRootIpFont());
        drawCenteredString(g2d, root.getDisplayIp(), canvas.getCenterX(), root.getIpY());
        // 3. 核心服务层
        drawServiceLayer(g2d, config.getCoreServices(), config);
        // 4. 应用服务层
        drawServiceLayer(g2d, config.getAppServices(), config);
        // 5. 数据处理集群
        drawClusterLayer(g2d, config.getDataProcessingClusters(), config, layout.getClusterBoxHeight());
        // 6. 支撑服务层
        drawClusterLayer(g2d, config.getSupportServices(), config, layout.getSupportClusterBoxHeight());
        // 7. IP分布说明
        drawIPDistribution(g2d, config);
        // 8. 图例
        drawLegend(g2d, config);
        g2d.setFont(fonts.getTimeFont());
        g2d.setPaint(colors.getLightTextColor());
        drawCenteredString(g2d, "生成时间: " + new java.util.Date(), canvas.getCenterX(), config.getTimeStampY());
        saveSVG(g2d, document, filename);
    }

    /**
     * 绘制服务层
     */
    private static void drawServiceLayer(SVGGraphics2D g2d, ServiceLayer layer, DeploymentArchitectureConfig config) {
        CanvasConfig canvas = config.getCanvasConfig();
        FontConfig fonts = config.getFontConfig();
        ColorConfig colors = config.getColorConfig();
        LayoutConfig layout = config.getLayoutConfig();
        // 绘制层标题
        g2d.setFont(fonts.getLayerTitleFont());
        g2d.setPaint(colors.getTextColor());
        drawCenteredString(g2d, layer.getName(), canvas.getCenterX(), layer.getTitleY());
        // 绘制服务组件
        List<ServiceComponent> services = layer.getServices();
        int startX = layout.getLeftMargin();
        int y = layer.getBoxStartY();
        for (int i = 0; i < services.size(); i++) {
            ServiceComponent service = services.get(i);
            int x = startX + i * layout.getServiceBoxSpacing();
            drawServiceBox(g2d, x, y, service, config);
        }
    }

    /**
     * 绘制集群层
     */
    private static void drawClusterLayer(SVGGraphics2D g2d, ClusterLayer layer, DeploymentArchitectureConfig config, int boxHeight) {
        CanvasConfig canvas = config.getCanvasConfig();
        FontConfig fonts = config.getFontConfig();
        ColorConfig colors = config.getColorConfig();
        LayoutConfig layout = config.getLayoutConfig();
        // 绘制集群标题框（仅数据处理集群有）
        if ("数据处理集群".equals(layer.getName())) {
            int titleBoxWidth = config.getClusterTitleBoxWidth();
            drawRoundedRect(g2d, layout.getLeftMargin(), layer.getTitleBoxY(), titleBoxWidth, layout.getClusterTitleBoxHeight(), layout.getClusterArc(), colors.getClusterColor());
            g2d.setFont(fonts.getClusterTitleFont());
            g2d.setPaint(colors.getWhite());
            drawCenteredString(g2d, layer.getName(), canvas.getCenterX(),
                    layer.getTitleBoxY() + layout.getClusterTitleBoxHeight() / 2 + 5);
        } else {
            // 支撑服务层的标题
            g2d.setFont(fonts.getLayerTitleFont());
            g2d.setPaint(colors.getTextColor());
            drawCenteredString(g2d, layer.getName(), canvas.getCenterX(), layer.getTitleBoxY());
        }

        // 绘制集群组件
        List<ClusterComponent> clusters = layer.getClusters();
        int startX = layout.getLeftMargin();
        int y = layer.getBoxStartY();
        for (int i = 0; i < clusters.size(); i++) {
            ClusterComponent cluster = clusters.get(i);
            int x = startX + i * layout.getClusterBoxSpacing();
            drawClusterBox(g2d, x, y, layout.getClusterBoxWidth(), boxHeight, cluster, config);
        }
    }

    /**
     * 绘制服务组件
     */
    private static void drawServiceBox(SVGGraphics2D g2d, int x, int y, ServiceComponent service, DeploymentArchitectureConfig config) {
        LayoutConfig layout = config.getLayoutConfig();
        FontConfig fonts = config.getFontConfig();
        ColorConfig colors = config.getColorConfig();
        int width = layout.getServiceBoxWidth();
        int height = layout.getServiceBoxHeight();
        // 绘制圆角矩形
        drawRoundedRect(g2d, x, y, width, height, layout.getDefaultArc(), service.getColor());
        // 绘制服务名称
        g2d.setFont(fonts.getServerFont());
        g2d.setPaint(colors.getWhite());
        drawCenteredString(g2d, service.getName(), x + width / 2, y + 30);
        // 绘制服务描述
        g2d.setFont(fonts.getDescFont());
        g2d.setPaint(colors.getDescTextColor());
        drawCenteredString(g2d, service.getDesc(), x + width / 2, y + 48);
        // 绘制IP框
        int ipBoxX = x + (width - layout.getIpBoxWidth()) / 2;
        drawRoundedRect(g2d, ipBoxX, y + 55, layout.getIpBoxWidth(), layout.getIpBoxHeight(), layout.getSmallArc(), service.getIpColor());
        // 绘制IP文本
        g2d.setFont(fonts.getIpFont());
        g2d.setPaint(colors.getIpTextColor());
        drawCenteredString(g2d, service.getDisplayIp(), x + width / 2, y + layout.getIpBoxTextOffset());
    }

    /**
     * 绘制集群组件
     */
    private static void drawClusterBox(SVGGraphics2D g2d, int x, int y, int width, int height, ClusterComponent cluster, DeploymentArchitectureConfig config) {
        LayoutConfig layout = config.getLayoutConfig();
        FontConfig fonts = config.getFontConfig();
        ColorConfig colors = config.getColorConfig();
        // 绘制外框
        drawRoundedRect(g2d, x, y, width, height, layout.getDefaultArc(), cluster.getBorderColor());
        // 绘制内框
        int innerWidth = width - 30;
        int innerHeight = 35;
        drawRoundedRect(g2d, x + 15, y + 15, innerWidth, innerHeight, layout.getSmallArc(), cluster.getInnerColor());
        // 绘制集群名称
        g2d.setFont(fonts.getServerFont());
        g2d.setPaint(colors.getTextColor());
        drawCenteredString(g2d, cluster.getName(), x + width / 2, y + 38);
        // 绘制IP
        g2d.setFont(fonts.getIpFont());
        g2d.setPaint(colors.getIpTextColor());
        drawCenteredString(g2d, cluster.getDisplayIp(), x + width / 2, y + 60);

        // 绘制进程列表
        g2d.setFont(fonts.getSmallFont());
        g2d.setPaint(colors.getSecondaryTextColor());
        List<String> processes = cluster.getProcesses();
        for (int i = 0; i < processes.size(); i++) {
            g2d.drawString(processes.get(i), x + 25, y + 85 + i * layout.getClusterContentSpacing());
        }
    }

    /**
     * 绘制IP分布说明
     */
    private static void drawIPDistribution(SVGGraphics2D g2d, DeploymentArchitectureConfig config) {
        int x = config.getIpDistributionStartX();
        int y = config.getIpDistributionStartY();
        FontConfig fonts = config.getFontConfig();
        ColorConfig colors = config.getColorConfig();
        LayoutConfig layout = config.getLayoutConfig();
        // 标题
        g2d.setFont(fonts.getBoldFont(fonts.getServerSize() - 1));
        g2d.setPaint(colors.getTextColor());
        g2d.drawString("IP地址分布:", x, y);
        // 分布列表
        g2d.setFont(fonts.getPlainFont(fonts.getDescSize()));
        g2d.setPaint(colors.getSecondaryTextColor());
        String[] distributions = config.getIpDistribution().getDistributionsArray();
        for (int i = 0; i < distributions.length; i++) {
            g2d.drawString(distributions[i], x, y + 25 + i * layout.getLineSpacing());
        }
    }

    /**
     * 绘制图例
     */
    private static void drawLegend(SVGGraphics2D g2d, DeploymentArchitectureConfig config) {
        int x = config.getLegendStartX();
        int y = config.getLegendStartY();
        LegendConfig legend = config.getLegendConfig();
        ColorConfig colors = config.getColorConfig();
        FontConfig fonts = config.getFontConfig();
        LayoutConfig layout = config.getLayoutConfig();
        // 绘制图例外框
        drawRoundedRect(g2d, x, y, legend.getWidth(), legend.getHeight(), layout.getDefaultArc(), colors.getLegendBgColor());
        g2d.setPaint(colors.getBorderColor());
        g2d.drawRoundRect(x, y, legend.getWidth(), legend.getHeight(), layout.getDefaultArc(), layout.getDefaultArc());
        // 图例标题
        g2d.setFont(fonts.getLegendTitleFont());
        g2d.setPaint(colors.getTextColor());
        drawCenteredString(g2d, legend.getTitle(), x + legend.getWidth() / 2, y + 25);
        g2d.setFont(fonts.getLegendTextFont());
        List<LegendItem> items = legend.getItems();
        drawLegendItem(g2d, x + 20, y + 45, getColorByKey(colors, items.get(0).getColorKey()), items.get(0).getText(), config);
        drawLegendItem(g2d, x + 20, y + 70, getColorByKey(colors, items.get(1).getColorKey()), items.get(1).getText(), config);
        drawLegendItem(g2d, x + 20, y + 95, getColorByKey(colors, items.get(2).getColorKey()), items.get(2).getText(), config);
        drawLegendItem(g2d, x + 180, y + 45, getColorByKey(colors, items.get(3).getColorKey()), items.get(3).getText(), config);
        drawLegendItem(g2d, x + 180, y + 70, getColorByKey(colors, items.get(4).getColorKey()), items.get(4).getText(), config);
        drawLegendItem(g2d, x + 180, y + 95, getColorByKey(colors, items.get(5).getColorKey()), items.get(5).getText(), config);
    }

    /**
     * 根据颜色键获取颜色
     */
    private static Color getColorByKey(ColorConfig colors, String colorKey) {
        switch (colorKey) {
            case "rootColor":
                return colors.getRootColor();
            case "coreColor":
                return colors.getCoreColor();
            case "appColor":
                return colors.getAppColor();
            case "dataColor":
                return colors.getDataColor();
            case "supportColor":
                return colors.getSupportColor();
            case "ipColor":
                return colors.getIpColor();
            default:
                return Color.GRAY;
        }
    }

    /**
     * 绘制图例项
     */
    private static void drawLegendItem(SVGGraphics2D g2d, int x, int y, Color color, String text, DeploymentArchitectureConfig config) {
        g2d.setPaint(color);
        g2d.fillRect(x, y - 10, 14, 14);
        g2d.setPaint(Color.GRAY);
        g2d.drawRect(x, y - 10, 14, 14);
        g2d.setPaint(config.getColorConfig().getTextColor());
        g2d.drawString(text, x + 25, y);
    }

    /**
     * 绘制圆角矩形
     */
    private static void drawRoundedRect(SVGGraphics2D g2d, int x, int y, int width, int height, int arc, Color color) {
        g2d.setPaint(color);
        g2d.fillRoundRect(x, y, width, height, arc, arc);
        Color borderColor = new Color(Math.max(color.getRed() - 40, 0), Math.max(color.getGreen() - 40, 0), Math.max(color.getBlue() - 40, 0));
        g2d.setPaint(borderColor);
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.drawRoundRect(x, y, width, height, arc, arc);
        g2d.setStroke(new BasicStroke(1));
    }

    /**
     * 居中绘制字符串
     */
    private static void drawCenteredString(SVGGraphics2D g2d, String text, int x, int y) {
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        g2d.drawString(text, x - textWidth / 2, y);
    }

    /**
     * 保存SVG文件
     */
    private static void saveSVG(SVGGraphics2D g2d, Document document, String filename) throws Exception {
        try (FileWriter writer = new FileWriter(filename)) {
            g2d.stream(writer, true);
        }
    }
}