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
package com.github.paohaijiao.sequence;

import com.github.paohaijiao.deploy.model.ColorConfig;
import com.github.paohaijiao.deploy.model.LayoutConfig;
import com.github.paohaijiao.sequence.model.*;
import org.apache.batik.anim.dom.SVGDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.w3c.dom.Document;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.FileWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.github.paohaijiao.sequence.model.SequenceDiagramConfig.createLoginExample;
import static com.github.paohaijiao.sequence.model.SequenceDiagramConfig.createOrderProcessingExample;

/**
 * 时序图生成器（基于配置实体重构）
 */
public class SequenceDiagramGenerator {

    public static void main(String[] args) {
        try {
            // 示例1：使用订单处理示例配置
            SequenceDiagramConfig config = createOrderProcessingExample();
            generateSequenceDiagram(config, "d://test//order-sequence-diagram.svg");
            System.out.println("订单处理时序图已生成到 order-sequence-diagram.svg");
            SequenceDiagramConfig loginConfig = createLoginExample();
            generateSequenceDiagram(loginConfig, "d://test//login-sequence-diagram.svg");
            System.out.println("用户登录时序图已生成到 login-sequence-diagram.svg");
            SequenceDiagramConfig customConfig = createCustomConfig();
            generateSequenceDiagram(customConfig, "d://test//custom-sequence-diagram.svg");
            System.out.println("自定义时序图已生成到 custom-sequence-diagram.svg");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成时序图主方法
     */
    public static void generateSequenceDiagram(SequenceDiagramConfig config, String outputPath) {
        try {
            Document document = SVGDOMImplementation.getDOMImplementation()
            .createDocument(SVGDOMImplementation.SVG_NAMESPACE_URI, "svg", null);
            SVGGraphics2D svgGenerator = new SVGGraphics2D(document);
            CanvasConfig canvasConfig = config.getCanvasConfig();
            Dimension size = canvasConfig.getSize();
            svgGenerator.setSVGCanvasSize(size);
            drawSequenceDiagram(svgGenerator, config);
            Writer out = new FileWriter(outputPath);
            svgGenerator.stream(out, true);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 绘制时序图主逻辑
     */
    private static void drawSequenceDiagram(SVGGraphics2D g2d, SequenceDiagramConfig config) {
        CanvasConfig canvasConfig = config.getCanvasConfig();
        ColorConfig colorConfig = config.getColorConfig();
        FontConfig fontConfig = config.getFontConfig();
        LayoutConfig layoutConfig = config.getLayoutConfig();
        // 设置抗锯齿
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // 绘制背景
        g2d.setColor(canvasConfig.getBackgroundColor());
        g2d.fillRect(0, 0, canvasConfig.getWidth(), canvasConfig.getHeight());
        // 绘制标题
        drawTitle(g2d, config);
        // 绘制参与者
        drawParticipants(g2d, config);
        // 绘制生命线和消息
        drawMessages(g2d, config);
        // 绘制图例
        if (config.getLegendConfig().isShowLegend()) {
            drawLegend(g2d, config);
        }
        // 绘制时间戳
        if (config.isShowTimestamp()) {
            drawTimestamp(g2d, config);
        }
    }

    /**
     * 绘制标题
     */
    private static void drawTitle(SVGGraphics2D g2d, SequenceDiagramConfig config) {
        CanvasConfig canvasConfig = config.getCanvasConfig();
        ColorConfig colorConfig = config.getColorConfig();
        FontConfig fontConfig = config.getFontConfig();
        g2d.setColor(colorConfig.getTitleColor());
        g2d.setFont(fontConfig.getTitleFont());
        FontMetrics fm = g2d.getFontMetrics();
        String title = config.getTitle();
        int titleWidth = fm.stringWidth(title);
        g2d.drawString(title, (canvasConfig.getWidth() - titleWidth) / 2, 40);
    }

    /**
     * 绘制参与者
     */
    private static void drawParticipants(SVGGraphics2D g2d, SequenceDiagramConfig config) {
        List<String> participants = config.getParticipants();
        LayoutConfig layout = config.getLayoutConfig();
        ColorConfig colors = config.getColorConfig();
        FontConfig fonts = config.getFontConfig();
        int participantCount = participants.size();
        int startX = layout.getParticipantStartX();
        int participantWidth = layout.getParticipantWidth();
        g2d.setFont(fonts.getParticipantFont());
        for (int i = 0; i < participantCount; i++) {
            int x = startX + i * participantWidth;
            String participant = participants.get(i);
            // 绘制参与者名称
            FontMetrics fm = g2d.getFontMetrics();
            Rectangle2D textBounds = fm.getStringBounds(participant, g2d);
            int textX = x - (int)(textBounds.getWidth() / 2);
            g2d.setColor(colors.getParticipantColor());
            g2d.drawString(participant, textX, layout.getParticipantTitleY());
            // 绘制生命线起点点
            g2d.setColor(colors.getLifelineDotColor());
            g2d.fillOval(x - 2, layout.getLifelineStartY() - 2, 4, 4);
            // 绘制生命线（虚线）
            g2d.setColor(colors.getLifelineColor());
            g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, new float[]{3, 3}, 0));
            g2d.drawLine(x, layout.getLifelineStartY(), x, config.getCanvasConfig().getHeight() - layout.getBottomMargin());
        }
    }

    /**
     * 绘制消息
     */
    private static void drawMessages(SVGGraphics2D g2d, SequenceDiagramConfig config) {
        List<DiagramMessage> messages = config.getMessages();
        LayoutConfig layout = config.getLayoutConfig();
        ColorConfig colors = config.getColorConfig();
        FontConfig fonts = config.getFontConfig();
        int startX = layout.getParticipantStartX();
        int participantWidth = layout.getParticipantWidth();
        int startY = layout.getMessageStartY();
        int verticalSpacing = layout.getVerticalSpacing();
        g2d.setFont(fonts.getMessageFont());
        for (DiagramMessage msg : messages) {
            int fromX = startX + msg.getFromIndex() * participantWidth;
            int toX = startX + msg.getToIndex() * participantWidth;
            int y = startY + msg.getSequence() * verticalSpacing;
            boolean isReturnMessage = msg.isReturnMessage();
            // 设置线条样式和颜色
            if (isReturnMessage) {
                g2d.setColor(colors.getResponseLineColor());
                g2d.setStroke(new BasicStroke(layout.getMessageLineWidth(), BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, new float[]{5, 3}, 0));
            } else {
                g2d.setColor(colors.getRequestLineColor());
                g2d.setStroke(new BasicStroke(layout.getMessageLineWidth()));
            }

            // 绘制消息线（带箭头）
            drawMessageLine(g2d, fromX, y, toX, y, isReturnMessage, layout.getArrowSize());
            // 绘制消息文本
            drawMessageText(g2d, fromX, toX, y - 20, msg.getText(), isReturnMessage ? colors.getResponseTextColor() : colors.getRequestTextColor(), fonts);
            // 绘制消息数据
            if (msg.getData() != null && !msg.getData().isEmpty()) {
                drawDataText(g2d, fromX, toX, y + 5, msg.getData(), isReturnMessage ? colors.getResponseDataColor() : colors.getRequestDataColor(), fonts);
            }
        }
    }

    /**
     * 绘制消息线（带箭头）
     */
    private static void drawMessageLine(Graphics2D g2d, int x1, int y, int x2, int y2,
                                        boolean isReturnMessage, int arrowSize) {
        // 绘制主线
        g2d.drawLine(x1, y, x2, y2);
        // 绘制箭头
        if (isReturnMessage) {
            // 返回箭头：指向fromX方向（向左）
            if (x1 > x2) {
                // 从左向右返回，箭头指向左
                drawArrow(g2d, x2, y2, Math.PI, arrowSize);
            } else {
                // 从右向左返回，箭头指向右
                drawArrow(g2d, x2, y2, 0, arrowSize);
            }
        } else {
            // 请求箭头：指向toX方向（向右）
            if (x1 < x2) {
                // 从左向右请求，箭头指向右
                drawArrow(g2d, x2, y2, 0, arrowSize);
            } else {
                // 从右向左请求，箭头指向左
                drawArrow(g2d, x2, y2, Math.PI, arrowSize);
            }
        }
    }

    /**
     * 绘制箭头
     */
    private static void drawArrow(Graphics2D g2d, int x, int y, double angle, int arrowSize) {
        // 计算箭头两边的点
        double x1 = x - arrowSize * Math.cos(angle - Math.PI / 6);
        double y1 = y - arrowSize * Math.sin(angle - Math.PI / 6);
        double x2 = x - arrowSize * Math.cos(angle + Math.PI / 6);
        double y2 = y - arrowSize * Math.sin(angle + Math.PI / 6);
        // 绘制箭头
        g2d.drawLine(x, y, (int)x1, (int)y1);
        g2d.drawLine(x, y, (int)x2, (int)y2);
    }

    /**
     * 绘制消息文本
     */
    private static void drawMessageText(Graphics2D g2d, int x1, int x2, int y, String text, Color textColor, FontConfig fonts) {
        g2d.setFont(fonts.getMessageFont());
        FontMetrics fm = g2d.getFontMetrics();
        int midX = (x1 + x2) / 2;
        int textWidth = fm.stringWidth(text);
        int textX = midX - textWidth / 2;

        g2d.setColor(textColor);
        g2d.drawString(text, textX, y);
    }

    /**
     * 绘制数据文本
     */
    private static void drawDataText(Graphics2D g2d, int x1, int x2, int y, String data, Color dataColor, FontConfig fonts) {
        g2d.setFont(fonts.getDataFont());
        FontMetrics fm = g2d.getFontMetrics();
        // 截断过长的数据
        String displayText = data;
        if (data.length() > 25) {
            displayText = data.substring(0, 22) + "...";
        }
        int midX = (x1 + x2) / 2;
        int textWidth = fm.stringWidth(displayText);
        int textX = midX - textWidth / 2;
        g2d.setColor(dataColor);
        g2d.drawString(displayText, textX, y);
    }

    /**
     * 绘制图例
     */
    private static void drawLegend(SVGGraphics2D g2d, SequenceDiagramConfig config) {
        LegendConfig legendConfig = config.getLegendConfig();
        ColorConfig colors = config.getColorConfig();
        FontConfig fonts = config.getFontConfig();
        LayoutConfig layout = config.getLayoutConfig();
        int legendX = layout.getLegendX();
        int legendY = layout.getLegendY();
        int legendWidth = layout.getLegendWidth();
        int legendHeight = layout.getLegendHeight();
        // 绘制图例背景
        g2d.setColor(colors.getLegendBgColor());
        g2d.fillRoundRect(legendX, legendY, legendWidth, legendHeight, 8, 8);
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.drawRoundRect(legendX, legendY, legendWidth, legendHeight, 8, 8);
        // 绘制图例标题
        g2d.setColor(Color.BLACK);
        g2d.setFont(fonts.getBoldFont(config.getFontConfig().getLegendTitleSize()));
        FontMetrics fm = g2d.getFontMetrics();
        String legendTitle = legendConfig.getTitle();
        int titleWidth = fm.stringWidth(legendTitle);
        g2d.drawString(legendTitle, legendX + (legendWidth - titleWidth) / 2, legendY + 25);
        // 绘制图例项
        g2d.setFont(fonts.getPlainFont(config.getFontConfig().getLegendTextSize()));
        int startY = legendY + 45;
        int lineHeight = 25;
        for (int i = 0; i < legendConfig.getItems().size(); i++) {
            LegendItem item = legendConfig.getItems().get(i);
            int itemY = startY + i * lineHeight;
            // 绘制示例线条
            Color lineColor = getColorByKey(colors, item.getColorKey());
            if (lineColor != null) {
                g2d.setColor(lineColor);
                if (item.getColorKey().contains("request")) {
                    // 请求消息：实线向右箭头
                    g2d.setStroke(new BasicStroke(1.5f));
                    g2d.drawLine(legendX + 15, itemY, legendX + 55, itemY);
                    drawArrow(g2d, legendX + 55, itemY, 0, 6);
                } else if (item.getColorKey().contains("response")) {
                    // 响应消息：虚线向左箭头
                    g2d.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_BUTT,
                            BasicStroke.JOIN_MITER, 10, new float[]{5, 3}, 0));
                    g2d.drawLine(legendX + 55, itemY, legendX + 15, itemY);
                    drawArrow(g2d, legendX + 15, itemY, Math.PI, 6);
                } else {
                    // 其他类型：简单方块
                    g2d.fillRect(legendX + 15, itemY - 5, 10, 10);
                }
            }
            // 绘制文本
            g2d.setColor(Color.BLACK);
            g2d.drawString(item.getText(), legendX + 70, itemY + 5);
        }
    }

    /**
     * 根据颜色键获取颜色
     */
    private static Color getColorByKey(ColorConfig colors, String colorKey) {
        try {
            switch (colorKey) {
                case "requestLineColor":
                    return colors.getRequestLineColor();
                case "responseLineColor":
                    return colors.getResponseLineColor();
                case "lifelineColor":
                    return colors.getLifelineColor();
                case "titleColor":
                    return colors.getTitleColor();
                case "participantColor":
                    return colors.getParticipantColor();
                case "requestTextColor":
                    return colors.getRequestTextColor();
                case "responseTextColor":
                    return colors.getResponseTextColor();
                case "requestDataColor":
                    return colors.getRequestDataColor();
                case "responseDataColor":
                    return colors.getResponseDataColor();
                default: return Color.BLACK;
            }
        } catch (Exception e) {
            return Color.BLACK;
        }
    }

    /**
     * 绘制时间戳
     */
    private static void drawTimestamp(SVGGraphics2D g2d, SequenceDiagramConfig config) {
        ColorConfig colors = config.getColorConfig();
        FontConfig fonts = config.getFontConfig();
        SimpleDateFormat sdf = new SimpleDateFormat(config.getTimestampFormat());
        String timestamp = sdf.format(new Date());
        g2d.setColor(colors.getTimestampColor());
        g2d.setFont(fonts.getPlainFont(fonts.getTimestampSize()));
        FontMetrics fm = g2d.getFontMetrics();
        int timestampWidth = fm.stringWidth(timestamp);
        int canvasWidth = config.getCanvasConfig().getWidth();
        // 在右下角显示时间戳
        g2d.drawString(timestamp, canvasWidth - timestampWidth - 20, config.getCanvasConfig().getHeight() - 20);
    }

    /**
     * 创建自定义配置示例
     */
    private static SequenceDiagramConfig createCustomConfig() {
        SequenceDiagramConfig config = new SequenceDiagramConfig();
        // 自定义画布
        config.getCanvasConfig().setWidth(1400);
        config.getCanvasConfig().setHeight(900);
        config.getCanvasConfig().setBackgroundColor(new Color(250, 250, 250));
        // 自定义标题
        config.setTitle("微服务调用时序图");
        // 自定义参与者
        config.getParticipants().clear();
        config.addParticipant("客户端");
        config.addParticipant("API网关");
        config.addParticipant("用户服务");
        config.addParticipant("订单服务");
        config.addParticipant("商品服务");
        config.addParticipant("库存服务");
        config.addParticipant("支付服务");
        // 自定义消息
        config.getMessages().clear();
        config.addMessage(0, 1, "下单请求", 1, "POST /api/order");
        config.addMessage(1, 2, "验证用户", 2, "token: abc123");
        config.addMessage(2, 1, "用户有效", 3, "user_id: 1001");
        config.addMessage(1, 3, "创建订单", 4, "订单数据");
        config.addMessage(3, 1, "订单创建", 5, "order_id: 5001");
        config.addMessage(1, 4, "查询商品", 6, "product_ids: [101,102]");
        config.addMessage(4, 1, "商品详情", 7, "products: {...}");
        config.addMessage(1, 5, "检查库存", 8, "库存查询");
        config.addMessage(5, 1, "库存充足", 9, "stock_ok: true");
        config.addMessage(1, 6, "发起支付", 10, "支付请求");
        config.addMessage(6, 1, "支付成功", 11, "payment_id: 8001");
        config.addMessage(1, 0, "返回结果", 12, "下单成功");
        // 自定义颜色
        config.getColorConfig().setRequestLineColor(new Color(70, 130, 180)); // 钢蓝色
        config.getColorConfig().setResponseLineColor(new Color(34, 139, 34)); // 森林绿
        // 自定义字体
        config.getFontConfig().setFontFamily("微软雅黑");
        config.getFontConfig().setTitleSize(22);
        config.getFontConfig().setParticipantSize(16);
        // 自定义布局
        config.getLayoutConfig().setParticipantWidth(150);
        config.getLayoutConfig().setVerticalSpacing(60);
        config.getLayoutConfig().setLegendX(1000);
        config.getLayoutConfig().setLegendY(100);
        return config;
    }
}
