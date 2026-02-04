package com.github.paohaijiao.sequence;/*
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
import org.apache.batik.anim.dom.SVGDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.w3c.dom.Document;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class SequenceDiagramGenerator {

    public static void main(String[] args) {
        try {
            Document document = SVGDOMImplementation.getDOMImplementation().createDocument(SVGDOMImplementation.SVG_NAMESPACE_URI, "svg", null);
            SVGGraphics2D svgGenerator = new SVGGraphics2D(document);
            int width = 900;
            int height = 700;
            svgGenerator.setSVGCanvasSize(new Dimension(width, height));
            drawSequenceDiagram(svgGenerator, width, height);
            Writer out = new FileWriter("d://test//sequence-diagram.svg");
            svgGenerator.stream(out, true);
            System.out.println("时序图已生成到 sequence-diagram.svg");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void drawSequenceDiagram(SVGGraphics2D g2d, int width, int height) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(new Color(245, 245, 245));
        g2d.fillRect(0, 0, width, height);
        String[] participants = {"用户", "前端应用", "API网关", "认证服务", "订单服务"};
        int participantCount = participants.length;
        int participantWidth = 130;
        int startX = 80;
        int verticalSpacing = 70;
        int startY = 120;
        g2d.setFont(new Font("Microsoft YaHei", Font.BOLD, 14));
        for (int i = 0; i < participantCount; i++) {
            int x = startX + i * participantWidth;
            FontMetrics fm = g2d.getFontMetrics();
            Rectangle2D textBounds = fm.getStringBounds(participants[i], g2d);
            int textX = x - (int)(textBounds.getWidth() / 2);
            g2d.setColor(new Color(50, 50, 150));
            g2d.drawString(participants[i], textX, 80);
            int lifelineStartY = 90;
            g2d.setColor(new Color(180, 180, 180));
            g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, new float[]{3, 3}, 0));
            g2d.drawLine(x, lifelineStartY, x, height - 80);
            g2d.setColor(new Color(100, 100, 100));
            g2d.fillOval(x - 2, lifelineStartY - 2, 4, 4);
        }
        List<DiagramMessage> messages = new ArrayList<>();
        messages.add(new DiagramMessage(0, 1, "提交订单请求", 1, "orderData"));
        messages.add(new DiagramMessage(1, 2, "POST /api/orders", 2, "{\"order\": {...}}"));
        messages.add(new DiagramMessage(2, 3, "验证令牌", 3, "token: xyz123"));
        messages.add(new DiagramMessage(3, 2, "验证成功", 4, "{\"valid\": true, \"userId\": 123}"));
        messages.add(new DiagramMessage(2, 4, "创建订单", 5, "orderDetails"));
        messages.add(new DiagramMessage(4, 2, "订单创建成功", 6, "{\"orderId\": 456, \"status\": \"success\"}"));
        messages.add(new DiagramMessage(2, 1, "返回订单ID", 7, "{\"orderId\": 456}"));
        messages.add(new DiagramMessage(1, 0, "显示订单确认", 8, "订单创建成功！"));
        g2d.setFont(new Font("Microsoft YaHei", Font.PLAIN, 11));
        for (DiagramMessage msg : messages) {
            int fromX = startX + msg.fromIndex * participantWidth;
            int toX = startX + msg.toIndex * participantWidth;
            int y = startY + msg.sequence * verticalSpacing;
            boolean isReturnMessage = fromX > toX;
            if (isReturnMessage) {         // 返回消息：绿色虚线
                g2d.setColor(new Color(0, 150, 0));
                g2d.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, new float[]{5, 3}, 0));
            } else { // 请求消息：蓝色实线
                g2d.setColor(new Color(0, 100, 200));
                g2d.setStroke(new BasicStroke(1.5f));
            }
            // 绘制带箭头的线
            drawMessageLine(g2d, fromX, y, toX, y, isReturnMessage);
            // 绘制消息标签（纯文本）
            drawMessageText(g2d, fromX, toX, y - 20, msg.text, isReturnMessage);
            // 绘制消息数据（纯文本）
            if (msg.data != null && !msg.data.isEmpty()) {
                drawDataText(g2d, fromX, toX, y + 5, msg.data, isReturnMessage);
            }
        }
        // 添加标题
        g2d.setColor(new Color(50, 50, 100));
        g2d.setFont(new Font("Microsoft YaHei", Font.BOLD, 20));
        FontMetrics fm = g2d.getFontMetrics();
        String title = "订单处理时序图";
        int titleWidth = fm.stringWidth(title);
        g2d.drawString(title, (width - titleWidth) / 2, 40);
        // 添加简单的图例
        addSimpleLegend(g2d, width - 180, 150);
    }

    // 绘制消息线
    private static void drawMessageLine(Graphics2D g2d, int x1, int y, int x2, int y2, boolean isReturnMessage) {
        // 绘制主线
        g2d.drawLine(x1, y, x2, y2);
        // 绘制箭头
        if (isReturnMessage) {
            // 返回箭头：指向fromX方向（向左）
            if (x1 > x2) {
                // 从左向右返回，箭头指向左
                drawArrow(g2d, x2, y2, Math.PI);
            } else {
                // 从右向左返回，箭头指向右
                drawArrow(g2d, x2, y2, 0);
            }
        } else {
            // 请求箭头：指向toX方向（向右）
            if (x1 < x2) {
                // 从左向右请求，箭头指向右
                drawArrow(g2d, x2, y2, 0);
            } else {
                // 从右向左请求，箭头指向左
                drawArrow(g2d, x2, y2, Math.PI);
            }
        }
    }

    // 绘制箭头
    private static void drawArrow(Graphics2D g2d, int x, int y, double angle) {
        int arrowSize = 8;
        // 计算箭头两边的点
        double x1 = x - arrowSize * Math.cos(angle - Math.PI / 6);
        double y1 = y - arrowSize * Math.sin(angle - Math.PI / 6);
        double x2 = x - arrowSize * Math.cos(angle + Math.PI / 6);
        double y2 = y - arrowSize * Math.sin(angle + Math.PI / 6);
        // 绘制箭头
        g2d.drawLine(x, y, (int)x1, (int)y1);
        g2d.drawLine(x, y, (int)x2, (int)y2);
    }

    // 绘制消息文本（纯文本，无背景）
    private static void drawMessageText(Graphics2D g2d, int x1, int x2, int y, String text, boolean isReturnMessage) {
        g2d.setFont(new Font("Microsoft YaHei", Font.PLAIN, 11));
        FontMetrics fm = g2d.getFontMetrics();
        int midX = (x1 + x2) / 2;
        int textWidth = fm.stringWidth(text);
        int textX = midX - textWidth / 2;
        // 使用不同颜色
        if (isReturnMessage) {
            g2d.setColor(new Color(0, 120, 0)); // 返回消息用深绿色
        } else {
            g2d.setColor(new Color(0, 0, 120)); // 请求消息用深蓝色
        }
        g2d.drawString(text, textX, y);
    }

    // 绘制数据文本（纯文本，无背景）
    private static void drawDataText(Graphics2D g2d, int x1, int x2, int y, String data, boolean isReturnMessage) {
        g2d.setFont(new Font("Consolas", Font.PLAIN, 9));
        FontMetrics fm = g2d.getFontMetrics();
        // 截断过长的数据
        String displayText = data;
        if (data.length() > 25) {
            displayText = data.substring(0, 22) + "...";
        }
        int midX = (x1 + x2) / 2;
        int textWidth = fm.stringWidth(displayText);
        int textX = midX - textWidth / 2;
        // 使用不同颜色
        if (isReturnMessage) {
            g2d.setColor(new Color(0, 100, 0)); // 返回数据用绿色
        } else {
            g2d.setColor(new Color(0, 0, 100)); // 请求数据用蓝色
        }
        g2d.drawString(displayText, textX, y);
    }

    // 添加简单图例（只有文本）
    private static void addSimpleLegend(Graphics2D g2d, int x, int y) {
        g2d.setFont(new Font("Microsoft YaHei", Font.PLAIN, 11));
        // 请求消息示例
        g2d.setColor(new Color(0, 100, 200));
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.drawLine(x, y, x + 40, y);
        drawArrow(g2d, x + 40, y, 0);
        g2d.setColor(Color.BLACK);
        g2d.drawString("→ 请求消息", x + 45, y + 5);
        // 响应消息示例
        g2d.setColor(new Color(0, 150, 0));
        g2d.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, new float[]{5, 3}, 0));
        g2d.drawLine(x + 40, y + 20, x, y + 20);
        drawArrow(g2d, x, y + 20, Math.PI);
        g2d.setColor(Color.BLACK);
        g2d.drawString("← 响应消息", x + 45, y + 25);
    }
    static class DiagramMessage {
        int fromIndex;
        int toIndex;
        String text;
        int sequence;
        String data;

        DiagramMessage(int from, int to, String text, int seq, String data) {
            this.fromIndex = from;
            this.toIndex = to;
            this.text = text;
            this.sequence = seq;
            this.data = data;
        }
    }
}