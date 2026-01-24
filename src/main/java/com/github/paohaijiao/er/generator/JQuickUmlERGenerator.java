package com.github.paohaijiao.er.generator;

import com.github.paohaijiao.config.ThemeConfig;
import com.github.paohaijiao.enums.JQuickUmlErRelationshipType;
import com.github.paohaijiao.er.layout.JQuickUmlErPoint;
import com.github.paohaijiao.er.model.JQuickUmlErColumn;
import com.github.paohaijiao.er.model.JQuickUmlERDiagram;
import com.github.paohaijiao.er.model.JQuickUmlErTable;
import com.github.paohaijiao.er.relation.JQuickUmlErRelationship;
import lombok.Data;
import org.apache.batik.svggen.SVGGraphics2D;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

@Data
public class JQuickUmlERGenerator {

    private ThemeConfig themeConfig;

    private String watermark;

    private String diagramName;

    private Date generationDate;

    private boolean showDate = true;

    private boolean showWatermark = true;

    private boolean showDiagramName = true;

    private double tableSpacing = 200;
    /**
     *  最小距离
     */
    private double minDistance = 150;

    public JQuickUmlERGenerator() {
        this.themeConfig = new ThemeConfig();
        this.generationDate = new Date();
    }

    public JQuickUmlERGenerator(ThemeConfig themeConfig) {
        this.themeConfig = themeConfig;
        this.generationDate = new Date();
    }

    /**
     * 生成ER图的SVG
     */
    public void generateSVG(JQuickUmlERDiagram diagram, String outputPath) throws Exception {
        if (diagramName != null && !diagramName.isEmpty()) {
            diagram.setTitle(diagramName);
        }
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.newDocument();
        SVGGraphics2D svgGenerator = new SVGGraphics2D(document);
        Dimension size = diagram.getSize();
        svgGenerator.setSVGCanvasSize(size);
        drawBackground(svgGenerator, size);
        applyGridLayout(diagram);// 使用网格布局而不是力导向布局
        calculateAllRelationshipPaths(diagram);// 重新计算关系路径（使用正交连线）
        List<JQuickUmlErTable> sortedJQuickUmlErTables = new ArrayList<>(diagram.getTables().values());// 按zIndex排序表格
        sortedJQuickUmlErTables.sort(Comparator.comparingInt(JQuickUmlErTable::getZIndex));
        for (JQuickUmlErRelationship rel : diagram.getJQuickUmlErRelationships()) {// 先绘制所有关系线
            drawRelationship(svgGenerator, rel);
        }
        for (JQuickUmlErTable JQuickUmlErTable : sortedJQuickUmlErTables) {// 再绘制所有表格（确保关系线在表格下方）
            drawTable(svgGenerator, JQuickUmlErTable);
        }
        drawTitle(svgGenerator, diagram, size);// 最后绘制标题、水印和元信息
        if (showWatermark) {
            drawWatermark(svgGenerator, size);
        }
        if (showDate) {
            drawGenerationDate(svgGenerator, size);
        }// 保存SVG文件
        try (Writer out = new OutputStreamWriter(new FileOutputStream(outputPath), StandardCharsets.UTF_8)) {
            svgGenerator.stream(out, true);
        }
        svgGenerator.dispose();
    }

    /**
     * 应用网格布局
     */
    private void applyGridLayout(JQuickUmlERDiagram diagram) {
        Map<String, JQuickUmlErTable> tables = diagram.getTables();
        if (tables.isEmpty()) return;
        List<JQuickUmlErTable> JQuickUmlErTableList = new ArrayList<>(tables.values());
        Dimension canvasSize = diagram.getSize();
        int cols = (int) Math.ceil(Math.sqrt(JQuickUmlErTableList.size()));// 根据表格数量确定网格布局
        int rows = (int) Math.ceil((double) JQuickUmlErTableList.size() / cols);
        int cellWidth = (canvasSize.width - 200) / cols; // 计算每个网格单元的大小
        int cellHeight = (canvasSize.height - 200) / rows;
        for (int i = 0; i < JQuickUmlErTableList.size(); i++) {// 设置表格位置（网格布局）
            JQuickUmlErTable JQuickUmlErTable = JQuickUmlErTableList.get(i);
            int row = i / cols;
            int col = i % cols;
            int x = 100 + col * cellWidth + (cellWidth - JQuickUmlErTable.getSize().width) / 2; // 计算表格位置（在网格单元中居中）
            int y = 100 + row * cellHeight + (cellHeight - JQuickUmlErTable.getSize().height) / 2;
            JQuickUmlErTable.setPosition(x, y);
        }
    }

    /**
     * 计算所有关系的路径（使用正交连线）
     */
    private void calculateAllRelationshipPaths(JQuickUmlERDiagram diagram) {
        Map<String, JQuickUmlErTable> tables = diagram.getTables();
        for (JQuickUmlErRelationship rel : diagram.getJQuickUmlErRelationships()) {
            rel.clearPathPoints();
            JQuickUmlErTable source = tables.get(rel.getSourceTable());
            JQuickUmlErTable target = tables.get(rel.getTargetTable());
            if (source == null || target == null) continue;
            JQuickUmlErPoint sourcePos = source.getPosition();// 获取表格位置和大小
            Dimension sourceSize = source.getSize();
            JQuickUmlErPoint targetPos = target.getPosition();
            Dimension targetSize = target.getSize();
            // 计算表格中心
            JQuickUmlErPoint sourceCenter = new JQuickUmlErPoint(sourcePos.x + sourceSize.width / 2, sourcePos.y + sourceSize.height / 2);
            JQuickUmlErPoint targetCenter = new JQuickUmlErPoint(targetPos.x + targetSize.width / 2, targetPos.y + targetSize.height / 2);
            // 计算连接点（在表格边框上）
            JQuickUmlErPoint sourceJQuickUmlErPoint = getOrthogonalConnectionPoint(source, targetCenter);
            JQuickUmlErPoint targetJQuickUmlErPoint = getOrthogonalConnectionPoint(target, sourceCenter);
            // 创建正交连线路径（横平竖直）
            List<JQuickUmlErPoint> pathJQuickUmlErPoints = new ArrayList<>();
            pathJQuickUmlErPoints.add(sourceJQuickUmlErPoint);
            // 添加中间点，形成L形或Z形路径
            if (isHorizontalConnection(sourceJQuickUmlErPoint, targetJQuickUmlErPoint)) {
                // 水平连接：先水平再垂直
                JQuickUmlErPoint middle1 = new JQuickUmlErPoint(targetJQuickUmlErPoint.x, sourceJQuickUmlErPoint.y);
                pathJQuickUmlErPoints.add(middle1);
            } else if (isVerticalConnection(sourceJQuickUmlErPoint, targetJQuickUmlErPoint)) {
                // 垂直连接：先垂直再水平
                JQuickUmlErPoint middle1 = new JQuickUmlErPoint(sourceJQuickUmlErPoint.x, targetJQuickUmlErPoint.y);
                pathJQuickUmlErPoints.add(middle1);
            } else {
                // 对角线连接：使用两个中间点形成Z形路径
                double dx = Math.abs(targetJQuickUmlErPoint.x - sourceJQuickUmlErPoint.x);
                double dy = Math.abs(targetJQuickUmlErPoint.y - sourceJQuickUmlErPoint.y);
                if (dx > dy) {
                    // 水平距离更大，先水平再垂直再水平
                    JQuickUmlErPoint middle1 = new JQuickUmlErPoint((sourceJQuickUmlErPoint.x + targetJQuickUmlErPoint.x) / 2, sourceJQuickUmlErPoint.y);
                    JQuickUmlErPoint middle2 = new JQuickUmlErPoint((sourceJQuickUmlErPoint.x + targetJQuickUmlErPoint.x) / 2, targetJQuickUmlErPoint.y);
                    pathJQuickUmlErPoints.add(middle1);
                    pathJQuickUmlErPoints.add(middle2);
                } else {
                    // 垂直距离更大，先垂直再水平再垂直
                    JQuickUmlErPoint middle1 = new JQuickUmlErPoint(sourceJQuickUmlErPoint.x, (sourceJQuickUmlErPoint.y + targetJQuickUmlErPoint.y) / 2);
                    JQuickUmlErPoint middle2 = new JQuickUmlErPoint(targetJQuickUmlErPoint.x, (sourceJQuickUmlErPoint.y + targetJQuickUmlErPoint.y) / 2);
                    pathJQuickUmlErPoints.add(middle1);
                    pathJQuickUmlErPoints.add(middle2);
                }
            }
            pathJQuickUmlErPoints.add(targetJQuickUmlErPoint);
            rel.setPathJQuickUmlErPoints(pathJQuickUmlErPoints);
            // 计算标签位置（在路径的中点）
            JQuickUmlErPoint labelPos = calculateLabelPosition(pathJQuickUmlErPoints);
            rel.setLabelPosition(labelPos);
        }
    }

    /**
     * 获取正交连接点（在表格边框上）
     */
    private JQuickUmlErPoint getOrthogonalConnectionPoint(JQuickUmlErTable JQuickUmlErTable, JQuickUmlErPoint targetCenter) {
        JQuickUmlErPoint tableCenter = JQuickUmlErTable.getCenter();
        Rectangle bounds = JQuickUmlErTable.getBounds();
        // 计算从表格中心到目标中心的方向
        double dx = targetCenter.x - tableCenter.x;
        double dy = targetCenter.y - tableCenter.y;
        // 确定连接点在哪条边上
        double left = bounds.getX();
        double right = left + bounds.getWidth();
        double top = bounds.getY();
        double bottom = top + bounds.getHeight();
        // 优先考虑水平或垂直方向
        if (Math.abs(dx) > Math.abs(dy)) {
            // 水平方向为主
            if (dx > 0) {
                // 目标在右侧，连接点在右边界
                return new JQuickUmlErPoint(right, tableCenter.y);
            } else {
                // 目标在左侧，连接点在左边界
                return new JQuickUmlErPoint(left, tableCenter.y);
            }
        } else {
            // 垂直方向为主
            if (dy > 0) {
                // 目标在下侧，连接点在下边界
                return new JQuickUmlErPoint(tableCenter.x, bottom);
            } else {
                // 目标在上侧，连接点在上边界
                return new JQuickUmlErPoint(tableCenter.x, top);
            }
        }
    }

    /**
     * 检查是否可以直接水平连接
     */
    private boolean isHorizontalConnection(JQuickUmlErPoint p1, JQuickUmlErPoint p2) {
        // 如果y坐标接近相同，可以直接水平连接
        return Math.abs(p1.y - p2.y) < 20;
    }

    /**
     * 检查是否可以直接垂直连接
     */
    private boolean isVerticalConnection(JQuickUmlErPoint p1, JQuickUmlErPoint p2) {
        // 如果x坐标接近相同，可以直接垂直连接
        return Math.abs(p1.x - p2.x) < 20;
    }

    /**
     * 计算标签位置
     */
    private JQuickUmlErPoint calculateLabelPosition(List<JQuickUmlErPoint> pathJQuickUmlErPoints) {
        if (pathJQuickUmlErPoints.size() < 2) {
            return pathJQuickUmlErPoints.get(0);
        }
        // 找到最长的线段，将标签放在其中点
        double maxLength = 0;
        int maxIndex = 0;
        for (int i = 0; i < pathJQuickUmlErPoints.size() - 1; i++) {
            JQuickUmlErPoint p1 = pathJQuickUmlErPoints.get(i);
            JQuickUmlErPoint p2 = pathJQuickUmlErPoints.get(i + 1);
            double length = Math.sqrt(Math.pow(p2.x - p1.x, 2) + Math.pow(p2.y - p1.y, 2));
            if (length > maxLength) {
                maxLength = length;
                maxIndex = i;
            }
        }

        JQuickUmlErPoint p1 = pathJQuickUmlErPoints.get(maxIndex);
        JQuickUmlErPoint p2 = pathJQuickUmlErPoints.get(maxIndex + 1);
        return new JQuickUmlErPoint((p1.x + p2.x) / 2, (p1.y + p2.y) / 2);
    }

    /**
     * 绘制背景
     */
    private void drawBackground(SVGGraphics2D g2d, Dimension size) {
        g2d.setPaint(themeConfig.background);// 纯色背景
        g2d.fillRect(0, 0, size.width, size.height);
    }

    /**
     * 绘制表格
     */
    private void drawTable(SVGGraphics2D g2d, JQuickUmlErTable JQuickUmlErTable) {
        JQuickUmlErPoint position = JQuickUmlErTable.getPosition();
        Dimension size = JQuickUmlErTable.getSize();
        int x = (int) position.x;
        int y = (int) position.y;
        int width = size.width;
        int height = size.height;
        // 绘制表格主体
        RoundRectangle2D tableRect = new RoundRectangle2D.Double(x, y, width, height, 8, 8);
        // 表格填充色
        g2d.setPaint(JQuickUmlErTable.isSelected() ? themeConfig.tableSelectedBackground : themeConfig.tableBackground);
        g2d.fill(tableRect);
        // 表格边框
        g2d.setColor(JQuickUmlErTable.isSelected() ? themeConfig.tableSelectedBorder : themeConfig.tableBorder);
        g2d.setStroke(new BasicStroke(JQuickUmlErTable.isSelected() ? 2.0f : 1.0f));
        g2d.draw(tableRect);
        // 绘制表头
        drawTableHeader(g2d, JQuickUmlErTable, x, y, width);
        // 绘制列
        drawTableColumns(g2d, JQuickUmlErTable, x, y, width, height);
    }

    /**
     * 绘制表头
     */
    private void drawTableHeader(SVGGraphics2D g2d, JQuickUmlErTable JQuickUmlErTable, int x, int y, int width) {
        int headerHeight = 35;
        // 绘制表头背景
        g2d.setPaint(themeConfig.tableHeaderBackground);
        g2d.fillRect(x, y, width, headerHeight);
        // 绘制表头底部边框
        g2d.setColor(themeConfig.tableBorder);
        g2d.setStroke(new BasicStroke(1.0f));
        g2d.drawLine(x, y + headerHeight, x + width, y + headerHeight);
        // 绘制表名
        g2d.setFont(themeConfig.tableNameFont);
        g2d.setColor(themeConfig.tableNameColor);
        FontMetrics fm = g2d.getFontMetrics();
        String tableName = JQuickUmlErTable.getName();
        // 处理表名过长的情况
        int maxWidth = width - 20;
        String displayName = truncateText(g2d, tableName, maxWidth);
        // 居中显示表名
        int textWidth = fm.stringWidth(displayName);
        int textX = x + (width - textWidth) / 2;
        int textY = y + headerHeight / 2 + fm.getAscent() / 2 - 2;
        g2d.drawString(displayName, textX, textY);
        // 绘制注释（如果存在）
        if (JQuickUmlErTable.getComment() != null && !JQuickUmlErTable.getComment().isEmpty()) {
            g2d.setFont(themeConfig.commentFont);
            g2d.setColor(themeConfig.commentColor);
            String comment = "(" + JQuickUmlErTable.getComment() + ")";
            comment = truncateText(g2d, comment, maxWidth);
            int commentWidth = g2d.getFontMetrics().stringWidth(comment);
            int commentX = x + (width - commentWidth) / 2;
            int commentY = textY + fm.getHeight() + 2;

            g2d.drawString(comment, commentX, commentY);
        }
    }

    /**
     * 绘制表格列
     */
    private void drawTableColumns(SVGGraphics2D g2d, JQuickUmlErTable JQuickUmlErTable, int x, int y, int width, int height) {
        int headerHeight = 35;
        int columnStartY = y + headerHeight;
        int rowHeight = 28;
        List<JQuickUmlErColumn> JQuickUmlErColumns = JQuickUmlErTable.getJQuickUmlErColumns();
        if (JQuickUmlErColumns.isEmpty()) return;
        // 计算实际需要的行数
        int visibleRows = Math.min(JQuickUmlErColumns.size(), (height - headerHeight) / rowHeight - 1);
        g2d.setFont(themeConfig.columnFont);
        FontMetrics fm = g2d.getFontMetrics();
        int fontAscent = fm.getAscent();
        int fontHeight = fm.getHeight();
        for (int i = 0; i < Math.min(JQuickUmlErColumns.size(), visibleRows); i++) {
            JQuickUmlErColumn JQuickUmlErColumn = JQuickUmlErColumns.get(i);
            int rowY = columnStartY + i * rowHeight;
            // 绘制列分隔线
            if (i < visibleRows - 1) {
                g2d.setColor(themeConfig.tableBorder);
                g2d.setStroke(new BasicStroke(0.5f));
                g2d.drawLine(x, rowY + rowHeight, x + width, rowY + rowHeight);
            }
            // 设置列文本颜色 - 所有字段使用同一种颜色
            g2d.setColor(themeConfig.columnNameColor);
            // 绘制列文本
            String columnText = getColumnDisplayText(JQuickUmlErColumn);
            int textX = x + 12;
            // 精确计算垂直位置，确保每行文字在行内垂直居中
            int textY = rowY + (rowHeight - fontHeight) / 2 + fontAscent;
            // 处理文本溢出
            int maxTextWidth = width - 60; // 为图标留出空间
            if (fm.stringWidth(columnText) > maxTextWidth) {
                columnText = truncateText(g2d, columnText, maxTextWidth);
            }
            g2d.drawString(columnText, textX, textY);
            // 绘制列类型图标
            drawColumnIcons(g2d, JQuickUmlErColumn, x + width - 35, rowY + 6);
        }

        // 如果列数超过可见行数，显示"..."
        if (JQuickUmlErColumns.size() > visibleRows) {
            int moreY = columnStartY + visibleRows * rowHeight;
            g2d.setColor(themeConfig.commentColor);
            int textX = x + 12;
            int textY = moreY + (rowHeight - fontHeight) / 2 + fontAscent;
            g2d.drawString("...", textX, textY);
        }
    }

    /**
     * 获取列的显示文本
     */
    private String getColumnDisplayText(JQuickUmlErColumn JQuickUmlErColumn) {
        StringBuilder sb = new StringBuilder();
        sb.append(JQuickUmlErColumn.getName());
        if (JQuickUmlErColumn.getDataType() != null && !JQuickUmlErColumn.getDataType().isEmpty()) {
            sb.append(" : ").append(JQuickUmlErColumn.getDataType());
            if (JQuickUmlErColumn.getLength() != null) {
                sb.append("(").append(JQuickUmlErColumn.getLength());
                if (JQuickUmlErColumn.getPrecision() != null) {
                    sb.append(",").append(JQuickUmlErColumn.getPrecision());
                }
                sb.append(")");
            }
        }
        if (!JQuickUmlErColumn.isNullable()) {
            sb.append(" *");
        }
        return sb.toString();
    }

    /**
     * 绘制列图标
     */
    private void drawColumnIcons(SVGGraphics2D g2d, JQuickUmlErColumn JQuickUmlErColumn, int x, int y) {
        // 主键图标
        if (JQuickUmlErColumn.isPrimaryKey()) {
            Ellipse2D pkIcon = new Ellipse2D.Double(x, y, 16, 16);
            g2d.setColor(themeConfig.primaryKeyColor);
            g2d.fill(pkIcon);
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 9));
            FontMetrics fm = g2d.getFontMetrics();
            int pkWidth = fm.stringWidth("PK");
            int pkX = x + (16 - pkWidth) / 2;
            int pkY = y + 16 - (16 - fm.getAscent()) / 2 - 1;
            g2d.drawString("PK", pkX, pkY);
        }

        // 外键图标
        if (JQuickUmlErColumn.isForeignKey()) {
            Ellipse2D fkIcon = new Ellipse2D.Double(x + 20, y, 16, 16);
            g2d.setColor(themeConfig.foreignKeyColor);
            g2d.fill(fkIcon);
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 9));
            FontMetrics fm = g2d.getFontMetrics();
            int fkWidth = fm.stringWidth("FK");
            int fkX = x + 20 + (16 - fkWidth) / 2;
            int fkY = y + 16 - (16 - fm.getAscent()) / 2 - 1;
            g2d.drawString("FK", fkX, fkY);
        }
    }

    /**
     * 绘制关系线（使用正交连线）
     */
    private void drawRelationship(SVGGraphics2D g2d, JQuickUmlErRelationship JQuickUmlErRelationship) {
        List<JQuickUmlErPoint> pathJQuickUmlErPoints = JQuickUmlErRelationship.getPathJQuickUmlErPoints();
        if (pathJQuickUmlErPoints.size() < 2) return;
        // 设置关系线样式 - 统一使用黑色虚线
        g2d.setColor(Color.BLACK);
        // 设置线条样式 - 统一使用虚线
        Stroke stroke = new BasicStroke(1.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[]{5.0f, 5.0f}, 0.0f);
        if (JQuickUmlErRelationship.isSelected()) {
            stroke = new BasicStroke(2.0f);
        }
        g2d.setStroke(stroke);
        // 绘制正交连线 - 确保横平竖直
        JQuickUmlErPoint prev = pathJQuickUmlErPoints.get(0);
        for (int i = 1; i < pathJQuickUmlErPoints.size(); i++) {
            JQuickUmlErPoint current = pathJQuickUmlErPoints.get(i);
            // 确保线段是水平或垂直的
            if (Math.abs(current.x - prev.x) < Math.abs(current.y - prev.y)) {
                // 垂直线段
                g2d.drawLine((int) prev.x, (int) prev.y, (int) prev.x, (int) current.y);
            } else {
                // 水平线段
                g2d.drawLine((int) prev.x, (int) prev.y, (int) current.x, (int) prev.y);
            }
            prev = current;
        }

        // 绘制最后一段线段
        JQuickUmlErPoint last = pathJQuickUmlErPoints.get(pathJQuickUmlErPoints.size() - 1);
        JQuickUmlErPoint secondLast = pathJQuickUmlErPoints.get(pathJQuickUmlErPoints.size() - 2);
        if (Math.abs(last.x - secondLast.x) < Math.abs(last.y - secondLast.y)) {
            // 垂直线段
            g2d.drawLine((int) secondLast.x, (int) secondLast.y, (int) secondLast.x, (int) last.y);
        } else {
            // 水平线段
            g2d.drawLine((int) secondLast.x, (int) secondLast.y, (int) last.x, (int) secondLast.y);
        }
        // 绘制箭头
        drawRelationshipArrow(g2d, JQuickUmlErRelationship);
        // 绘制标签
        drawRelationshipLabel(g2d, JQuickUmlErRelationship);
    }

    /**
     * 绘制关系箭头
     */
    private void drawRelationshipArrow(SVGGraphics2D g2d, JQuickUmlErRelationship JQuickUmlErRelationship) {
        List<JQuickUmlErPoint> pathJQuickUmlErPoints = JQuickUmlErRelationship.getPathJQuickUmlErPoints();
        if (pathJQuickUmlErPoints.size() < 2) return;
        JQuickUmlErRelationshipType type = JQuickUmlErRelationship.getType();
        // 获取最后两个点来计算箭头方向
        JQuickUmlErPoint lastJQuickUmlErPoint = pathJQuickUmlErPoints.get(pathJQuickUmlErPoints.size() - 1);
        JQuickUmlErPoint secondLastJQuickUmlErPoint = pathJQuickUmlErPoints.get(pathJQuickUmlErPoints.size() - 2);
        // 绘制终点箭头
        drawOrthogonalArrow(g2d, lastJQuickUmlErPoint, secondLastJQuickUmlErPoint, JQuickUmlErRelationship);
        // 对于多对多关系，在起点也绘制箭头
        if (type == JQuickUmlErRelationshipType.MANY_TO_MANY) {
            JQuickUmlErPoint firstJQuickUmlErPoint = pathJQuickUmlErPoints.get(0);
            JQuickUmlErPoint secondJQuickUmlErPoint = pathJQuickUmlErPoints.get(1);
            drawOrthogonalArrow(g2d, firstJQuickUmlErPoint, secondJQuickUmlErPoint, JQuickUmlErRelationship);
        }
    }

    /**
     * 绘制正交箭头（适用于横平竖直的连线）
     */
    private void drawOrthogonalArrow(SVGGraphics2D g2d, JQuickUmlErPoint arrowJQuickUmlErPoint, JQuickUmlErPoint prevJQuickUmlErPoint, JQuickUmlErRelationship JQuickUmlErRelationship) {
        // 计算箭头方向
        double dx = arrowJQuickUmlErPoint.x - prevJQuickUmlErPoint.x;
        double dy = arrowJQuickUmlErPoint.y - prevJQuickUmlErPoint.y;
        // 确定是水平箭头还是垂直箭头
        boolean isHorizontal = Math.abs(dx) > Math.abs(dy);
        // 箭头大小
        int arrowSize = 10;
        // 创建箭头多边形
        Polygon arrow = new Polygon();
        if (isHorizontal) {
            // 水平箭头
            if (dx > 0) {
                // 指向右边
                arrow.addPoint((int) arrowJQuickUmlErPoint.x, (int) arrowJQuickUmlErPoint.y);
                arrow.addPoint((int) arrowJQuickUmlErPoint.x - arrowSize, (int) arrowJQuickUmlErPoint.y - arrowSize / 2);
                arrow.addPoint((int) arrowJQuickUmlErPoint.x - arrowSize, (int) arrowJQuickUmlErPoint.y + arrowSize / 2);
            } else {
                // 指向左边
                arrow.addPoint((int) arrowJQuickUmlErPoint.x, (int) arrowJQuickUmlErPoint.y);
                arrow.addPoint((int) arrowJQuickUmlErPoint.x + arrowSize, (int) arrowJQuickUmlErPoint.y - arrowSize / 2);
                arrow.addPoint((int) arrowJQuickUmlErPoint.x + arrowSize, (int) arrowJQuickUmlErPoint.y + arrowSize / 2);
            }
        } else {
            // 垂直箭头
            if (dy > 0) {
                // 指向下边
                arrow.addPoint((int) arrowJQuickUmlErPoint.x, (int) arrowJQuickUmlErPoint.y);
                arrow.addPoint((int) arrowJQuickUmlErPoint.x - arrowSize / 2, (int) arrowJQuickUmlErPoint.y - arrowSize);
                arrow.addPoint((int) arrowJQuickUmlErPoint.x + arrowSize / 2, (int) arrowJQuickUmlErPoint.y - arrowSize);
            } else {
                // 指向上边
                arrow.addPoint((int) arrowJQuickUmlErPoint.x, (int) arrowJQuickUmlErPoint.y);
                arrow.addPoint((int) arrowJQuickUmlErPoint.x - arrowSize / 2, (int) arrowJQuickUmlErPoint.y + arrowSize);
                arrow.addPoint((int) arrowJQuickUmlErPoint.x + arrowSize / 2, (int) arrowJQuickUmlErPoint.y + arrowSize);
            }
        }

        // 填充箭头 - 使用黑色
        g2d.setColor(Color.BLACK);
        g2d.fill(arrow);
        // 绘制箭头边框
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(1.0f));
        g2d.draw(arrow);
    }

    /**
     * 绘制关系标签
     */
    private void drawRelationshipLabel(SVGGraphics2D g2d, JQuickUmlErRelationship JQuickUmlErRelationship) {
        JQuickUmlErPoint labelPos = JQuickUmlErRelationship.getLabelPosition();
        if (labelPos == null) return;
        String label = JQuickUmlErRelationship.getLabel();
        g2d.setFont(themeConfig.relationshipFont);
        FontMetrics fm = g2d.getFontMetrics();
        int labelWidth = fm.stringWidth(label);
        int labelHeight = fm.getHeight();
        // 绘制标签背景
        int bgX = (int) labelPos.x - labelWidth / 2 - 4;
        int bgY = (int) labelPos.y - labelHeight / 2 - 2;
        int bgWidth = labelWidth + 8;
        int bgHeight = labelHeight + 4;
        g2d.setColor(Color.WHITE);
        g2d.fillRect(bgX, bgY, bgWidth, bgHeight);
        // 绘制标签边框
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(1.0f));
        g2d.drawRect(bgX, bgY, bgWidth, bgHeight);
        // 绘制标签文本
        int textX = (int) labelPos.x - labelWidth / 2;
        int textY = (int) labelPos.y + fm.getAscent() / 2 - 2;
        g2d.setColor(Color.BLACK);
        g2d.drawString(label, textX, textY);
    }

    /**
     * 绘制标题
     */
    private void drawTitle(SVGGraphics2D g2d, JQuickUmlERDiagram diagram, Dimension size) {
        if (!showDiagramName) return;
        String title = diagram.getTitle();
        if (title == null || title.isEmpty()) return;
        g2d.setFont(themeConfig.titleFont);
        g2d.setColor(themeConfig.titleColor);
        FontMetrics fm = g2d.getFontMetrics();
        int titleWidth = fm.stringWidth(title);
        // 居中绘制标题
        int titleX = (size.width - titleWidth) / 2;
        int titleY = 50;
        g2d.drawString(title, titleX, titleY);
    }

    /**
     * 绘制水印
     */
    private void drawWatermark(SVGGraphics2D g2d, Dimension size) {
        if (watermark == null || watermark.isEmpty()) return;
        g2d.setFont(themeConfig.watermarkFont);
        g2d.setColor(themeConfig.watermarkColor);
        FontMetrics fm = g2d.getFontMetrics();
        int watermarkWidth = fm.stringWidth(watermark);
        // 在右下角绘制水印
        int watermarkX = size.width - watermarkWidth - 30;
        int watermarkY = size.height - 30;
        g2d.drawString(watermark, watermarkX, watermarkY);
    }

    /**
     * 绘制生成日期
     */
    private void drawGenerationDate(SVGGraphics2D g2d, Dimension size) {
        if (generationDate == null) return;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateStr = sdf.format(generationDate);
        g2d.setFont(themeConfig.dateFont);
        g2d.setColor(themeConfig.dateColor);
        // 在左下角绘制日期
        int dateX = 30;
        int dateY = size.height - 30;
        g2d.drawString(dateStr, dateX, dateY);
    }

    /**
     * 截断文本以适应宽度
     */
    private String truncateText(Graphics2D g2d, String text, int maxWidth) {
        FontMetrics fm = g2d.getFontMetrics();
        if (fm.stringWidth(text) <= maxWidth) {
            return text;
        }
        String ellipsis = "...";
        for (int i = text.length() - 1; i > 0; i--) {
            String truncated = text.substring(0, i) + ellipsis;
            if (fm.stringWidth(truncated) <= maxWidth) {
                return truncated;
            }
        }

        return ellipsis;
    }


}