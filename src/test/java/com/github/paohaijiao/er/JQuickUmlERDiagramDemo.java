package com.github.paohaijiao.er;

import com.github.paohaijiao.config.ThemeConfig;
import com.github.paohaijiao.enums.JQuickUmlErRelationshipType;
import com.github.paohaijiao.er.generator.JQuickUmlERGenerator;
import com.github.paohaijiao.er.model.JQuickUmlErColumn;
import com.github.paohaijiao.er.model.JQuickUmlERDiagram;
import com.github.paohaijiao.er.model.JQuickUmlErTable;
import com.github.paohaijiao.er.relation.JQuickUmlErRelationship;

import java.util.Date;

public class JQuickUmlERDiagramDemo {
    public static void main(String[] args) {
        try {
            /**
             * 创建ER图
             */
            JQuickUmlERDiagram diagram = new JQuickUmlERDiagram();
            diagram.setTitle("订单系统数据库设计");
            diagram.setSize(1600, 1000); // 增大画布尺寸

            /**
             * 创建表格
             */
            JQuickUmlErTable usersJQuickUmlErTable = new JQuickUmlErTable("用户表");
            usersJQuickUmlErTable.setComment("系统用户信息");
            usersJQuickUmlErTable.addColumn(new JQuickUmlErColumn("用户ID", "INT"));
            usersJQuickUmlErTable.addColumn(new JQuickUmlErColumn("用户名", "VARCHAR(50)"));
            usersJQuickUmlErTable.addColumn(new JQuickUmlErColumn("邮箱", "VARCHAR(100)"));
            usersJQuickUmlErTable.addColumn(new JQuickUmlErColumn("密码", "VARCHAR(100)"));
            usersJQuickUmlErTable.addColumn(new JQuickUmlErColumn("创建时间", "DATETIME"));
            usersJQuickUmlErTable.getColumn("用户ID");//.setPrimaryKey(true);
            usersJQuickUmlErTable.getColumn("用户名");//.setNullable(false);
            usersJQuickUmlErTable.getColumn("邮箱");////.setNullable(false);

            JQuickUmlErTable productsJQuickUmlErTable = new JQuickUmlErTable("商品表");
            productsJQuickUmlErTable.setComment("商品信息");
            productsJQuickUmlErTable.addColumn(new JQuickUmlErColumn("商品ID", "INT"));
            productsJQuickUmlErTable.addColumn(new JQuickUmlErColumn("商品名称", "VARCHAR(100)"));
            productsJQuickUmlErTable.addColumn(new JQuickUmlErColumn("价格", "DECIMAL(10,2)"));
            productsJQuickUmlErTable.addColumn(new JQuickUmlErColumn("库存", "INT"));
            productsJQuickUmlErTable.addColumn(new JQuickUmlErColumn("分类", "VARCHAR(50)"));
            productsJQuickUmlErTable.getColumn("商品ID");//.setPrimaryKey(true);
            productsJQuickUmlErTable.getColumn("商品名称");//.setNullable(false);
            productsJQuickUmlErTable.getColumn("价格");//.setNullable(false);

            JQuickUmlErTable ordersJQuickUmlErTable = new JQuickUmlErTable("订单表");
            ordersJQuickUmlErTable.setComment("用户订单");
            ordersJQuickUmlErTable.addColumn(new JQuickUmlErColumn("订单ID", "INT"));
            ordersJQuickUmlErTable.addColumn(new JQuickUmlErColumn("用户ID", "INT"));
            ordersJQuickUmlErTable.addColumn(new JQuickUmlErColumn("订单金额", "DECIMAL(10,2)"));
            ordersJQuickUmlErTable.addColumn(new JQuickUmlErColumn("状态", "VARCHAR(20)"));
            ordersJQuickUmlErTable.addColumn(new JQuickUmlErColumn("下单时间", "DATETIME"));
            ordersJQuickUmlErTable.getColumn("订单ID").setPrimaryKey(true);
            ordersJQuickUmlErTable.getColumn("用户ID").setForeignKey(true);
            ordersJQuickUmlErTable.getColumn("订单金额").setNullable(false);
            ordersJQuickUmlErTable.getColumn("下单时间").setNullable(false);

            JQuickUmlErTable orderItemsJQuickUmlErTable = new JQuickUmlErTable("订单明细表");
            orderItemsJQuickUmlErTable.setComment("订单商品明细");
            orderItemsJQuickUmlErTable.addColumn(new JQuickUmlErColumn("明细ID", "INT"));
            orderItemsJQuickUmlErTable.addColumn(new JQuickUmlErColumn("订单ID", "INT"));
            orderItemsJQuickUmlErTable.addColumn(new JQuickUmlErColumn("商品ID", "INT"));
            orderItemsJQuickUmlErTable.addColumn(new JQuickUmlErColumn("数量", "INT"));
            orderItemsJQuickUmlErTable.addColumn(new JQuickUmlErColumn("单价", "DECIMAL(10,2)"));
            orderItemsJQuickUmlErTable.getColumn("明细ID").setPrimaryKey(true);
            orderItemsJQuickUmlErTable.getColumn("订单ID").setForeignKey(true);
            orderItemsJQuickUmlErTable.getColumn("商品ID").setForeignKey(true);
            orderItemsJQuickUmlErTable.getColumn("数量").setNullable(false);

            /**
             * 添加到ER图
             */
            diagram.addTable(usersJQuickUmlErTable);
            diagram.addTable(productsJQuickUmlErTable);
            diagram.addTable(ordersJQuickUmlErTable);
            diagram.addTable(orderItemsJQuickUmlErTable);
            /**
             * 创建关系
             */
            JQuickUmlErRelationship rel1 = new JQuickUmlErRelationship("订单表", "用户ID", "用户表", "用户ID", JQuickUmlErRelationshipType.ONE_TO_MANY);
            rel1.setLabel("1:N");
            JQuickUmlErRelationship rel2 = new JQuickUmlErRelationship("订单明细表", "订单ID", "订单表", "订单ID", JQuickUmlErRelationshipType.ONE_TO_MANY);
            rel2.setLabel("1:N");
            JQuickUmlErRelationship rel3 = new JQuickUmlErRelationship("订单明细表", "商品ID", "商品表", "商品ID", JQuickUmlErRelationshipType.ONE_TO_MANY);
            rel3.setLabel("1:N");
            /**
             * 添加到ER图
             */
            diagram.addRelationship(rel1);
            diagram.addRelationship(rel2);
            diagram.addRelationship(rel3);

            JQuickUmlERGenerator generator = new JQuickUmlERGenerator();
            /**
             * 配置生成器
             */
            generator.setDiagramName("数据库ER图");
            generator.setWatermark("Generated by ER Diagram Tool");
            generator.setGenerationDate(new Date());
            generator.setTableSpacing(200); // 增加表格间距
            /**
             * 自定义主题
             */
            ThemeConfig theme = new ThemeConfig();
            /**
             * 调整表格大小，确保文字能完整显示
             */
            usersJQuickUmlErTable.setSize(320, 180);
            productsJQuickUmlErTable.setSize(320, 180);
            ordersJQuickUmlErTable.setSize(320, 180);
            orderItemsJQuickUmlErTable.setSize(320, 180);
            generator.setThemeConfig(theme);
            generator.generateSVG(diagram, "d://test//er_diagram_fixed_v2.svg");
            System.out.println("修复版ER图生成成功: er_diagram_fixed_v2.svg");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
