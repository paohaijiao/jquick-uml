package com.github.paohaijiao.er;

import com.github.paohaijiao.config.ThemeConfig;
import com.github.paohaijiao.enums.JQuickUmlErRelationshipType;
import com.github.paohaijiao.er.generator.JQuickUmlERGenerator;
import com.github.paohaijiao.er.model.JQuickUmlErColumn;
import com.github.paohaijiao.er.model.JQuickUmlERDiagram;
import com.github.paohaijiao.er.model.JQuickUmlErTable;
import com.github.paohaijiao.er.relation.JQuickUmlErRelationship;

import java.awt.*;
import java.util.Date;
import java.util.Random;

public class JQuickUmlErComplexERDiagramDemo {
    private static final Random random = new Random();

    public static void main(String[] args) {
        try {
            /**
             * 创建ER图 - 使用更大的画布
             */
            JQuickUmlERDiagram diagram = new JQuickUmlERDiagram();
            diagram.setTitle("电商系统完整数据库设计");
            diagram.setAuthor("ER Diagram Generator");
            diagram.setSize(2400, 1600);

            /**
             * 创建所有表格（总共约30个表）
             */
            System.out.println("开始创建表格...");

            /**
             * 1. 用户模块表格
             */
            JQuickUmlErTable usersJQuickUmlErTable = createTable("用户表", "系统用户信息", 350, 200);
            addColumn(usersJQuickUmlErTable, "user_id", "BIGINT", true, false, false);
            addColumn(usersJQuickUmlErTable, "username", "VARCHAR(50)", false, false, false);
            addColumn(usersJQuickUmlErTable, "password", "VARCHAR(100)", false, false, false);
            addColumn(usersJQuickUmlErTable, "email", "VARCHAR(100)", false, false, false);
            addColumn(usersJQuickUmlErTable, "mobile", "VARCHAR(20)", false, false, true);
            addColumn(usersJQuickUmlErTable, "real_name", "VARCHAR(50)", false, false, true);
            addColumn(usersJQuickUmlErTable, "id_card", "VARCHAR(18)", false, false, true);
            addColumn(usersJQuickUmlErTable, "avatar", "VARCHAR(200)", false, false, true);
            addColumn(usersJQuickUmlErTable, "gender", "TINYINT", false, false, true);
            addColumn(usersJQuickUmlErTable, "birthday", "DATE", false, false, true);
            addColumn(usersJQuickUmlErTable, "register_time", "DATETIME", false, false, false);
            addColumn(usersJQuickUmlErTable, "last_login_time", "DATETIME", false, false, true);
            addColumn(usersJQuickUmlErTable, "status", "TINYINT", false, false, false);

            JQuickUmlErTable userAddressJQuickUmlErTable = createTable("用户地址表", "用户收货地址", 320, 180);
            addColumn(userAddressJQuickUmlErTable, "address_id", "BIGINT", true, false, false);
            addColumn(userAddressJQuickUmlErTable, "user_id", "BIGINT", false, true, false);
            addColumn(userAddressJQuickUmlErTable, "receiver_name", "VARCHAR(50)", false, false, false);
            addColumn(userAddressJQuickUmlErTable, "receiver_phone", "VARCHAR(20)", false, false, false);
            addColumn(userAddressJQuickUmlErTable, "province", "VARCHAR(50)", false, false, false);
            addColumn(userAddressJQuickUmlErTable, "city", "VARCHAR(50)", false, false, false);
            addColumn(userAddressJQuickUmlErTable, "district", "VARCHAR(50)", false, false, false);
            addColumn(userAddressJQuickUmlErTable, "detail_address", "VARCHAR(200)", false, false, false);
            addColumn(userAddressJQuickUmlErTable, "is_default", "TINYINT", false, false, false);
            addColumn(userAddressJQuickUmlErTable, "create_time", "DATETIME", false, false, false);

            JQuickUmlErTable userBalanceJQuickUmlErTable = createTable("用户余额表", "用户账户余额", 300, 150);
            addColumn(userBalanceJQuickUmlErTable, "balance_id", "BIGINT", true, false, false);
            addColumn(userBalanceJQuickUmlErTable, "user_id", "BIGINT", false, true, false);
            addColumn(userBalanceJQuickUmlErTable, "balance", "DECIMAL(10,2)", false, false, false);
            addColumn(userBalanceJQuickUmlErTable, "frozen_balance", "DECIMAL(10,2)", false, false, false);
            addColumn(userBalanceJQuickUmlErTable, "update_time", "DATETIME", false, false, false);

            JQuickUmlErTable userLevelJQuickUmlErTable = createTable("用户等级表", "用户会员等级", 280, 130);
            addColumn(userLevelJQuickUmlErTable, "level_id", "INT", true, false, false);
            addColumn(userLevelJQuickUmlErTable, "level_name", "VARCHAR(50)", false, false, false);
            addColumn(userLevelJQuickUmlErTable, "min_points", "INT", false, false, false);
            addColumn(userLevelJQuickUmlErTable, "discount_rate", "DECIMAL(3,2)", false, false, false);
            addColumn(userLevelJQuickUmlErTable, "description", "VARCHAR(200)", false, false, true);

            JQuickUmlErTable userPointsJQuickUmlErTable = createTable("用户积分表", "用户积分信息", 300, 150);
            addColumn(userPointsJQuickUmlErTable, "points_id", "BIGINT", true, false, false);
            addColumn(userPointsJQuickUmlErTable, "user_id", "BIGINT", false, true, false);
            addColumn(userPointsJQuickUmlErTable, "total_points", "INT", false, false, false);
            addColumn(userPointsJQuickUmlErTable, "available_points", "INT", false, false, false);
            addColumn(userPointsJQuickUmlErTable, "update_time", "DATETIME", false, false, false);

            /**
             * 2. 商品模块表格
             */
            JQuickUmlErTable categoryJQuickUmlErTable = createTable("商品分类表", "商品分类信息", 320, 180);
            addColumn(categoryJQuickUmlErTable, "category_id", "BIGINT", true, false, false);
            addColumn(categoryJQuickUmlErTable, "category_name", "VARCHAR(100)", false, false, false);
            addColumn(categoryJQuickUmlErTable, "parent_id", "BIGINT", false, true, true);
            addColumn(categoryJQuickUmlErTable, "level", "TINYINT", false, false, false);
            addColumn(categoryJQuickUmlErTable, "sort_order", "INT", false, false, false);
            addColumn(categoryJQuickUmlErTable, "status", "TINYINT", false, false, false);
            addColumn(categoryJQuickUmlErTable, "icon", "VARCHAR(200)", false, false, true);
            addColumn(categoryJQuickUmlErTable, "description", "TEXT", false, false, true);

            JQuickUmlErTable brandJQuickUmlErTable = createTable("品牌表", "商品品牌信息", 300, 150);
            addColumn(brandJQuickUmlErTable, "brand_id", "BIGINT", true, false, false);
            addColumn(brandJQuickUmlErTable, "brand_name", "VARCHAR(100)", false, false, false);
            addColumn(brandJQuickUmlErTable, "logo", "VARCHAR(200)", false, false, true);
            addColumn(brandJQuickUmlErTable, "description", "TEXT", false, false, true);
            addColumn(brandJQuickUmlErTable, "sort_order", "INT", false, false, false);
            addColumn(brandJQuickUmlErTable, "status", "TINYINT", false, false, false);

            JQuickUmlErTable goodsJQuickUmlErTable = createTable("商品表", "商品基本信息", 350, 200);
            addColumn(goodsJQuickUmlErTable, "goods_id", "BIGINT", true, false, false);
            addColumn(goodsJQuickUmlErTable, "goods_name", "VARCHAR(200)", false, false, false);
            addColumn(goodsJQuickUmlErTable, "goods_sn", "VARCHAR(50)", false, false, false);
            addColumn(goodsJQuickUmlErTable, "category_id", "BIGINT", false, true, false);
            addColumn(goodsJQuickUmlErTable, "brand_id", "BIGINT", false, true, true);
            addColumn(goodsJQuickUmlErTable, "market_price", "DECIMAL(10,2)", false, false, false);
            addColumn(goodsJQuickUmlErTable, "shop_price", "DECIMAL(10,2)", false, false, false);
            addColumn(goodsJQuickUmlErTable, "cost_price", "DECIMAL(10,2)", false, false, true);
            addColumn(goodsJQuickUmlErTable, "stock_quantity", "INT", false, false, false);
            addColumn(goodsJQuickUmlErTable, "warn_quantity", "INT", false, false, false);
            addColumn(goodsJQuickUmlErTable, "goods_weight", "DECIMAL(8,3)", false, false, true);
            addColumn(goodsJQuickUmlErTable, "goods_brief", "VARCHAR(500)", false, false, true);
            addColumn(goodsJQuickUmlErTable, "goods_desc", "TEXT", false, false, true);
            addColumn(goodsJQuickUmlErTable, "is_on_sale", "TINYINT", false, false, false);
            addColumn(goodsJQuickUmlErTable, "is_hot", "TINYINT", false, false, false);
            addColumn(goodsJQuickUmlErTable, "is_new", "TINYINT", false, false, false);
            addColumn(goodsJQuickUmlErTable, "is_recommend", "TINYINT", false, false, false);
            addColumn(goodsJQuickUmlErTable, "sort_order", "INT", false, false, false);
            addColumn(goodsJQuickUmlErTable, "create_time", "DATETIME", false, false, false);
            addColumn(goodsJQuickUmlErTable, "update_time", "DATETIME", false, false, false);

            JQuickUmlErTable goodsSpecJQuickUmlErTable = createTable("商品规格表", "商品规格信息", 320, 180);
            addColumn(goodsSpecJQuickUmlErTable, "spec_id", "BIGINT", true, false, false);
            addColumn(goodsSpecJQuickUmlErTable, "goods_id", "BIGINT", false, true, false);
            addColumn(goodsSpecJQuickUmlErTable, "spec_name", "VARCHAR(100)", false, false, false);
            addColumn(goodsSpecJQuickUmlErTable, "spec_value", "VARCHAR(100)", false, false, false);
            addColumn(goodsSpecJQuickUmlErTable, "price_adjust", "DECIMAL(10,2)", false, false, true);
            addColumn(goodsSpecJQuickUmlErTable, "stock_adjust", "INT", false, false, true);

            JQuickUmlErTable goodsImageJQuickUmlErTable = createTable("商品图片表", "商品图片信息", 300, 150);
            addColumn(goodsImageJQuickUmlErTable, "image_id", "BIGINT", true, false, false);
            addColumn(goodsImageJQuickUmlErTable, "goods_id", "BIGINT", false, true, false);
            addColumn(goodsImageJQuickUmlErTable, "image_url", "VARCHAR(200)", false, false, false);
            addColumn(goodsImageJQuickUmlErTable, "image_desc", "VARCHAR(100)", false, false, true);
            addColumn(goodsImageJQuickUmlErTable, "sort_order", "INT", false, false, false);
            addColumn(goodsImageJQuickUmlErTable, "is_main", "TINYINT", false, false, false);

            JQuickUmlErTable goodsAttributeJQuickUmlErTable = createTable("商品属性表", "商品属性信息", 320, 180);
            addColumn(goodsAttributeJQuickUmlErTable, "attribute_id", "BIGINT", true, false, false);
            addColumn(goodsAttributeJQuickUmlErTable, "goods_id", "BIGINT", false, true, false);
            addColumn(goodsAttributeJQuickUmlErTable, "attribute_name", "VARCHAR(100)", false, false, false);
            addColumn(goodsAttributeJQuickUmlErTable, "attribute_value", "VARCHAR(200)", false, false, false);
            addColumn(goodsAttributeJQuickUmlErTable, "sort_order", "INT", false, false, false);

            /**
             * 3. 订单模块表格
             */
            JQuickUmlErTable orderJQuickUmlErTable = createTable("订单表", "订单主表", 380, 220);
            addColumn(orderJQuickUmlErTable, "order_id", "BIGINT", true, false, false);
            addColumn(orderJQuickUmlErTable, "order_sn", "VARCHAR(50)", false, false, false);
            addColumn(orderJQuickUmlErTable, "user_id", "BIGINT", false, true, false);
            addColumn(orderJQuickUmlErTable, "order_status", "TINYINT", false, false, false);
            addColumn(orderJQuickUmlErTable, "shipping_status", "TINYINT", false, false, false);
            addColumn(orderJQuickUmlErTable, "pay_status", "TINYINT", false, false, false);
            addColumn(orderJQuickUmlErTable, "consignee", "VARCHAR(50)", false, false, false);
            addColumn(orderJQuickUmlErTable, "mobile", "VARCHAR(20)", false, false, false);
            addColumn(orderJQuickUmlErTable, "address", "VARCHAR(200)", false, false, false);
            addColumn(orderJQuickUmlErTable, "goods_amount", "DECIMAL(10,2)", false, false, false);
            addColumn(orderJQuickUmlErTable, "shipping_fee", "DECIMAL(10,2)", false, false, false);
            addColumn(orderJQuickUmlErTable, "order_amount", "DECIMAL(10,2)", false, false, false);
            addColumn(orderJQuickUmlErTable, "discount_amount", "DECIMAL(10,2)", false, false, false);
            addColumn(orderJQuickUmlErTable, "pay_amount", "DECIMAL(10,2)", false, false, false);
            addColumn(orderJQuickUmlErTable, "pay_time", "DATETIME", false, false, true);
            addColumn(orderJQuickUmlErTable, "shipping_time", "DATETIME", false, false, true);
            addColumn(orderJQuickUmlErTable, "confirm_time", "DATETIME", false, false, true);
            addColumn(orderJQuickUmlErTable, "create_time", "DATETIME", false, false, false);
            addColumn(orderJQuickUmlErTable, "update_time", "DATETIME", false, false, false);

            JQuickUmlErTable orderGoodsJQuickUmlErTable = createTable("订单商品表", "订单商品明细", 350, 200);
            addColumn(orderGoodsJQuickUmlErTable, "order_goods_id", "BIGINT", true, false, false);
            addColumn(orderGoodsJQuickUmlErTable, "order_id", "BIGINT", false, true, false);
            addColumn(orderGoodsJQuickUmlErTable, "goods_id", "BIGINT", false, true, false);
            addColumn(orderGoodsJQuickUmlErTable, "goods_name", "VARCHAR(200)", false, false, false);
            addColumn(orderGoodsJQuickUmlErTable, "goods_sn", "VARCHAR(50)", false, false, false);
            addColumn(orderGoodsJQuickUmlErTable, "goods_number", "INT", false, false, false);
            addColumn(orderGoodsJQuickUmlErTable, "market_price", "DECIMAL(10,2)", false, false, false);
            addColumn(orderGoodsJQuickUmlErTable, "goods_price", "DECIMAL(10,2)", false, false, false);
            addColumn(orderGoodsJQuickUmlErTable, "spec_info", "VARCHAR(200)", false, false, true);
            addColumn(orderGoodsJQuickUmlErTable, "is_real", "TINYINT", false, false, false);
            addColumn(orderGoodsJQuickUmlErTable, "is_gift", "TINYINT", false, false, false);

            JQuickUmlErTable orderActionJQuickUmlErTable = createTable("订单操作日志表", "订单操作日志", 330, 180);
            addColumn(orderActionJQuickUmlErTable, "action_id", "BIGINT", true, false, false);
            addColumn(orderActionJQuickUmlErTable, "order_id", "BIGINT", false, true, false);
            addColumn(orderActionJQuickUmlErTable, "action_user", "VARCHAR(50)", false, false, false);
            addColumn(orderActionJQuickUmlErTable, "order_status", "TINYINT", false, false, false);
            addColumn(orderActionJQuickUmlErTable, "shipping_status", "TINYINT", false, false, false);
            addColumn(orderActionJQuickUmlErTable, "pay_status", "TINYINT", false, false, false);
            addColumn(orderActionJQuickUmlErTable, "action_note", "VARCHAR(500)", false, false, true);
            addColumn(orderActionJQuickUmlErTable, "log_time", "DATETIME", false, false, false);

            /**
             * 4. 购物车模块
             */
            JQuickUmlErTable cartJQuickUmlErTable = createTable("购物车表", "用户购物车", 320, 180);
            addColumn(cartJQuickUmlErTable, "cart_id", "BIGINT", true, false, false);
            addColumn(cartJQuickUmlErTable, "user_id", "BIGINT", false, true, false);
            addColumn(cartJQuickUmlErTable, "goods_id", "BIGINT", false, true, false);
            addColumn(cartJQuickUmlErTable, "goods_sn", "VARCHAR(50)", false, false, false);
            addColumn(cartJQuickUmlErTable, "goods_name", "VARCHAR(200)", false, false, false);
            addColumn(cartJQuickUmlErTable, "market_price", "DECIMAL(10,2)", false, false, false);
            addColumn(cartJQuickUmlErTable, "goods_price", "DECIMAL(10,2)", false, false, false);
            addColumn(cartJQuickUmlErTable, "goods_number", "INT", false, false, false);
            addColumn(cartJQuickUmlErTable, "spec_info", "VARCHAR(200)", false, false, true);
            addColumn(cartJQuickUmlErTable, "selected", "TINYINT", false, false, false);
            addColumn(cartJQuickUmlErTable, "add_time", "DATETIME", false, false, false);
            addColumn(cartJQuickUmlErTable, "update_time", "DATETIME", false, false, false);

            /**
             * 5. 支付模块
             */
            JQuickUmlErTable paymentJQuickUmlErTable = createTable("支付表", "支付信息", 350, 200);
            addColumn(paymentJQuickUmlErTable, "payment_id", "BIGINT", true, false, false);
            addColumn(paymentJQuickUmlErTable, "payment_sn", "VARCHAR(50)", false, false, false);
            addColumn(paymentJQuickUmlErTable, "order_id", "BIGINT", false, true, false);
            addColumn(paymentJQuickUmlErTable, "user_id", "BIGINT", false, true, false);
            addColumn(paymentJQuickUmlErTable, "payment_method", "TINYINT", false, false, false);
            addColumn(paymentJQuickUmlErTable, "payment_amount", "DECIMAL(10,2)", false, false, false);
            addColumn(paymentJQuickUmlErTable, "payment_status", "TINYINT", false, false, false);
            addColumn(paymentJQuickUmlErTable, "pay_time", "DATETIME", false, false, true);
            addColumn(paymentJQuickUmlErTable, "payment_note", "VARCHAR(500)", false, false, true);
            addColumn(paymentJQuickUmlErTable, "create_time", "DATETIME", false, false, false);

            JQuickUmlErTable refundJQuickUmlErTable = createTable("退款表", "退款信息", 330, 180);
            addColumn(refundJQuickUmlErTable, "refund_id", "BIGINT", true, false, false);
            addColumn(refundJQuickUmlErTable, "refund_sn", "VARCHAR(50)", false, false, false);
            addColumn(refundJQuickUmlErTable, "order_id", "BIGINT", false, true, false);
            addColumn(refundJQuickUmlErTable, "user_id", "BIGINT", false, true, false);
            addColumn(refundJQuickUmlErTable, "refund_amount", "DECIMAL(10,2)", false, false, false);
            addColumn(refundJQuickUmlErTable, "refund_status", "TINYINT", false, false, false);
            addColumn(refundJQuickUmlErTable, "refund_reason", "VARCHAR(500)", false, false, true);
            addColumn(refundJQuickUmlErTable, "refund_time", "DATETIME", false, false, true);
            addColumn(refundJQuickUmlErTable, "create_time", "DATETIME", false, false, false);

            /**
             *  6. 物流模块
             */
            JQuickUmlErTable shippingJQuickUmlErTable = createTable("配送方式表", "物流配送方式", 300, 150);
            addColumn(shippingJQuickUmlErTable, "shipping_id", "BIGINT", true, false, false);
            addColumn(shippingJQuickUmlErTable, "shipping_name", "VARCHAR(50)", false, false, false);
            addColumn(shippingJQuickUmlErTable, "shipping_code", "VARCHAR(50)", false, false, false);
            addColumn(shippingJQuickUmlErTable, "shipping_desc", "VARCHAR(200)", false, false, true);
            addColumn(shippingJQuickUmlErTable, "base_price", "DECIMAL(10,2)", false, false, false);
            addColumn(shippingJQuickUmlErTable, "is_enabled", "TINYINT", false, false, false);
            addColumn(shippingJQuickUmlErTable, "sort_order", "INT", false, false, false);

            JQuickUmlErTable deliveryJQuickUmlErTable = createTable("发货单表", "订单发货信息", 350, 200);
            addColumn(deliveryJQuickUmlErTable, "delivery_id", "BIGINT", true, false, false);
            addColumn(deliveryJQuickUmlErTable, "order_id", "BIGINT", false, true, false);
            addColumn(deliveryJQuickUmlErTable, "delivery_sn", "VARCHAR(50)", false, false, false);
            addColumn(deliveryJQuickUmlErTable, "shipping_id", "BIGINT", false, true, false);
            addColumn(deliveryJQuickUmlErTable, "shipping_name", "VARCHAR(50)", false, false, false);
            addColumn(deliveryJQuickUmlErTable, "consignee", "VARCHAR(50)", false, false, false);
            addColumn(deliveryJQuickUmlErTable, "mobile", "VARCHAR(20)", false, false, false);
            addColumn(deliveryJQuickUmlErTable, "address", "VARCHAR(200)", false, false, false);
            addColumn(deliveryJQuickUmlErTable, "shipping_fee", "DECIMAL(10,2)", false, false, false);
            addColumn(deliveryJQuickUmlErTable, "shipping_time", "DATETIME", false, false, true);
            addColumn(deliveryJQuickUmlErTable, "status", "TINYINT", false, false, false);
            addColumn(deliveryJQuickUmlErTable, "create_time", "DATETIME", false, false, false);

            /**
             * 7. 营销模块
             */
            JQuickUmlErTable couponJQuickUmlErTable = createTable("优惠券表", "优惠券信息", 350, 200);
            addColumn(couponJQuickUmlErTable, "coupon_id", "BIGINT", true, false, false);
            addColumn(couponJQuickUmlErTable, "coupon_name", "VARCHAR(100)", false, false, false);
            addColumn(couponJQuickUmlErTable, "coupon_type", "TINYINT", false, false, false);
            addColumn(couponJQuickUmlErTable, "coupon_amount", "DECIMAL(10,2)", false, false, false);
            addColumn(couponJQuickUmlErTable, "min_amount", "DECIMAL(10,2)", false, false, false);
            addColumn(couponJQuickUmlErTable, "start_time", "DATETIME", false, false, false);
            addColumn(couponJQuickUmlErTable, "end_time", "DATETIME", false, false, false);
            addColumn(couponJQuickUmlErTable, "use_scope", "TINYINT", false, false, false);
            addColumn(couponJQuickUmlErTable, "total_quantity", "INT", false, false, false);
            addColumn(couponJQuickUmlErTable, "used_quantity", "INT", false, false, false);
            addColumn(couponJQuickUmlErTable, "status", "TINYINT", false, false, false);
            addColumn(couponJQuickUmlErTable, "create_time", "DATETIME", false, false, false);

            JQuickUmlErTable userCouponJQuickUmlErTable = createTable("用户优惠券表", "用户领取的优惠券", 330, 180);
            addColumn(userCouponJQuickUmlErTable, "user_coupon_id", "BIGINT", true, false, false);
            addColumn(userCouponJQuickUmlErTable, "user_id", "BIGINT", false, true, false);
            addColumn(userCouponJQuickUmlErTable, "coupon_id", "BIGINT", false, true, false);
            addColumn(userCouponJQuickUmlErTable, "coupon_sn", "VARCHAR(50)", false, false, false);
            addColumn(userCouponJQuickUmlErTable, "order_id", "BIGINT", false, true, true);
            addColumn(userCouponJQuickUmlErTable, "use_status", "TINYINT", false, false, false);
            addColumn(userCouponJQuickUmlErTable, "use_time", "DATETIME", false, false, true);
            addColumn(userCouponJQuickUmlErTable, "start_time", "DATETIME", false, false, false);
            addColumn(userCouponJQuickUmlErTable, "end_time", "DATETIME", false, false, false);
            addColumn(userCouponJQuickUmlErTable, "create_time", "DATETIME", false, false, false);

            JQuickUmlErTable promotionJQuickUmlErTable = createTable("促销活动表", "商品促销活动", 350, 200);
            addColumn(promotionJQuickUmlErTable, "promotion_id", "BIGINT", true, false, false);
            addColumn(promotionJQuickUmlErTable, "promotion_name", "VARCHAR(100)", false, false, false);
            addColumn(promotionJQuickUmlErTable, "promotion_type", "TINYINT", false, false, false);
            addColumn(promotionJQuickUmlErTable, "discount_rate", "DECIMAL(3,2)", false, false, true);
            addColumn(promotionJQuickUmlErTable, "discount_amount", "DECIMAL(10,2)", false, false, true);
            addColumn(promotionJQuickUmlErTable, "start_time", "DATETIME", false, false, false);
            addColumn(promotionJQuickUmlErTable, "end_time", "DATETIME", false, false, false);
            addColumn(promotionJQuickUmlErTable, "status", "TINYINT", false, false, false);
            addColumn(promotionJQuickUmlErTable, "create_time", "DATETIME", false, false, false);

            /**
             *  8. 评价模块
             */
            JQuickUmlErTable commentJQuickUmlErTable = createTable("商品评价表", "用户商品评价", 350, 200);
            addColumn(commentJQuickUmlErTable, "comment_id", "BIGINT", true, false, false);
            addColumn(commentJQuickUmlErTable, "order_id", "BIGINT", false, true, false);
            addColumn(commentJQuickUmlErTable, "user_id", "BIGINT", false, true, false);
            addColumn(commentJQuickUmlErTable, "goods_id", "BIGINT", false, true, false);
            addColumn(commentJQuickUmlErTable, "content", "TEXT", false, false, false);
            addColumn(commentJQuickUmlErTable, "score", "TINYINT", false, false, false);
            addColumn(commentJQuickUmlErTable, "is_anonymous", "TINYINT", false, false, false);
            addColumn(commentJQuickUmlErTable, "is_show", "TINYINT", false, false, false);
            addColumn(commentJQuickUmlErTable, "create_time", "DATETIME", false, false, false);

            JQuickUmlErTable commentImageJQuickUmlErTable = createTable("评价图片表", "评价上传的图片", 300, 150);
            addColumn(commentImageJQuickUmlErTable, "image_id", "BIGINT", true, false, false);
            addColumn(commentImageJQuickUmlErTable, "comment_id", "BIGINT", false, true, false);
            addColumn(commentImageJQuickUmlErTable, "image_url", "VARCHAR(200)", false, false, false);
            addColumn(commentImageJQuickUmlErTable, "sort_order", "INT", false, false, false);

            /**
             * 9. 系统管理模块
             */
            JQuickUmlErTable adminJQuickUmlErTable = createTable("管理员表", "系统管理员", 320, 180);
            addColumn(adminJQuickUmlErTable, "admin_id", "BIGINT", true, false, false);
            addColumn(adminJQuickUmlErTable, "username", "VARCHAR(50)", false, false, false);
            addColumn(adminJQuickUmlErTable, "password", "VARCHAR(100)", false, false, false);
            addColumn(adminJQuickUmlErTable, "real_name", "VARCHAR(50)", false, false, true);
            addColumn(adminJQuickUmlErTable, "mobile", "VARCHAR(20)", false, false, true);
            addColumn(adminJQuickUmlErTable, "email", "VARCHAR(100)", false, false, true);
            addColumn(adminJQuickUmlErTable, "avatar", "VARCHAR(200)", false, false, true);
            addColumn(adminJQuickUmlErTable, "status", "TINYINT", false, false, false);
            addColumn(adminJQuickUmlErTable, "last_login_time", "DATETIME", false, false, true);
            addColumn(adminJQuickUmlErTable, "create_time", "DATETIME", false, false, false);

            JQuickUmlErTable roleJQuickUmlErTable = createTable("角色表", "管理员角色", 280, 130);
            addColumn(roleJQuickUmlErTable, "role_id", "BIGINT", true, false, false);
            addColumn(roleJQuickUmlErTable, "role_name", "VARCHAR(50)", false, false, false);
            addColumn(roleJQuickUmlErTable, "role_desc", "VARCHAR(200)", false, false, true);
            addColumn(roleJQuickUmlErTable, "status", "TINYINT", false, false, false);
            addColumn(roleJQuickUmlErTable, "create_time", "DATETIME", false, false, false);

            JQuickUmlErTable permissionJQuickUmlErTable = createTable("权限表", "系统权限", 300, 150);
            addColumn(permissionJQuickUmlErTable, "permission_id", "BIGINT", true, false, false);
            addColumn(permissionJQuickUmlErTable, "permission_name", "VARCHAR(100)", false, false, false);
            addColumn(permissionJQuickUmlErTable, "permission_code", "VARCHAR(100)", false, false, false);
            addColumn(permissionJQuickUmlErTable, "parent_id", "BIGINT", false, true, true);
            addColumn(permissionJQuickUmlErTable, "permission_type", "TINYINT", false, false, false);
            addColumn(permissionJQuickUmlErTable, "sort_order", "INT", false, false, false);

            JQuickUmlErTable operationLogJQuickUmlErTable = createTable("操作日志表", "系统操作日志", 350, 200);
            addColumn(operationLogJQuickUmlErTable, "log_id", "BIGINT", true, false, false);
            addColumn(operationLogJQuickUmlErTable, "admin_id", "BIGINT", false, true, true);
            addColumn(operationLogJQuickUmlErTable, "admin_name", "VARCHAR(50)", false, false, false);
            addColumn(operationLogJQuickUmlErTable, "operation", "VARCHAR(100)", false, false, false);
            addColumn(operationLogJQuickUmlErTable, "method", "VARCHAR(10)", false, false, false);
            addColumn(operationLogJQuickUmlErTable, "params", "TEXT", false, false, true);
            addColumn(operationLogJQuickUmlErTable, "ip", "VARCHAR(50)", false, false, false);
            addColumn(operationLogJQuickUmlErTable, "user_agent", "VARCHAR(500)", false, false, true);
            addColumn(operationLogJQuickUmlErTable, "result", "TINYINT", false, false, false);
            addColumn(operationLogJQuickUmlErTable, "error_msg", "TEXT", false, false, true);
            addColumn(operationLogJQuickUmlErTable, "operation_time", "DATETIME", false, false, false);

            /**
             * 10. 仓储模块
             */
            JQuickUmlErTable warehouseJQuickUmlErTable = createTable("仓库表", "商品仓库", 300, 150);
            addColumn(warehouseJQuickUmlErTable, "warehouse_id", "BIGINT", true, false, false);
            addColumn(warehouseJQuickUmlErTable, "warehouse_name", "VARCHAR(100)", false, false, false);
            addColumn(warehouseJQuickUmlErTable, "warehouse_code", "VARCHAR(50)", false, false, false);
            addColumn(warehouseJQuickUmlErTable, "address", "VARCHAR(200)", false, false, false);
            addColumn(warehouseJQuickUmlErTable, "contact", "VARCHAR(50)", false, false, true);
            addColumn(warehouseJQuickUmlErTable, "phone", "VARCHAR(20)", false, false, true);
            addColumn(warehouseJQuickUmlErTable, "status", "TINYINT", false, false, false);

            JQuickUmlErTable stockJQuickUmlErTable = createTable("库存表", "商品库存", 320, 180);
            addColumn(stockJQuickUmlErTable, "stock_id", "BIGINT", true, false, false);
            addColumn(stockJQuickUmlErTable, "goods_id", "BIGINT", false, true, false);
            addColumn(stockJQuickUmlErTable, "warehouse_id", "BIGINT", false, true, false);
            addColumn(stockJQuickUmlErTable, "stock_quantity", "INT", false, false, false);
            addColumn(stockJQuickUmlErTable, "available_quantity", "INT", false, false, false);
            addColumn(stockJQuickUmlErTable, "locked_quantity", "INT", false, false, false);
            addColumn(stockJQuickUmlErTable, "update_time", "DATETIME", false, false, false);

            JQuickUmlErTable stockLogJQuickUmlErTable = createTable("库存日志表", "库存变更日志", 350, 200);
            addColumn(stockLogJQuickUmlErTable, "log_id", "BIGINT", true, false, false);
            addColumn(stockLogJQuickUmlErTable, "goods_id", "BIGINT", false, true, false);
            addColumn(stockLogJQuickUmlErTable, "warehouse_id", "BIGINT", false, true, false);
            addColumn(stockLogJQuickUmlErTable, "change_type", "TINYINT", false, false, false);
            addColumn(stockLogJQuickUmlErTable, "change_quantity", "INT", false, false, false);
            addColumn(stockLogJQuickUmlErTable, "before_quantity", "INT", false, false, false);
            addColumn(stockLogJQuickUmlErTable, "after_quantity", "INT", false, false, false);
            addColumn(stockLogJQuickUmlErTable, "order_id", "BIGINT", false, true, true);
            addColumn(stockLogJQuickUmlErTable, "operator", "VARCHAR(50)", false, false, false);
            addColumn(stockLogJQuickUmlErTable, "remark", "VARCHAR(500)", false, false, true);
            addColumn(stockLogJQuickUmlErTable, "create_time", "DATETIME", false, false, false);

            /**
             *  11. 内容管理模块
             */
            JQuickUmlErTable articleJQuickUmlErTable = createTable("文章表", "系统文章", 320, 180);
            addColumn(articleJQuickUmlErTable, "article_id", "BIGINT", true, false, false);
            addColumn(articleJQuickUmlErTable, "title", "VARCHAR(200)", false, false, false);
            addColumn(articleJQuickUmlErTable, "category_id", "BIGINT", false, true, false);
            addColumn(articleJQuickUmlErTable, "content", "TEXT", false, false, false);
            addColumn(articleJQuickUmlErTable, "author", "VARCHAR(50)", false, false, true);
            addColumn(articleJQuickUmlErTable, "source", "VARCHAR(100)", false, false, true);
            addColumn(articleJQuickUmlErTable, "keywords", "VARCHAR(200)", false, false, true);
            addColumn(articleJQuickUmlErTable, "description", "VARCHAR(500)", false, false, true);
            addColumn(articleJQuickUmlErTable, "click_count", "INT", false, false, false);
            addColumn(articleJQuickUmlErTable, "is_show", "TINYINT", false, false, false);
            addColumn(articleJQuickUmlErTable, "is_top", "TINYINT", false, false, false);
            addColumn(articleJQuickUmlErTable, "sort_order", "INT", false, false, false);
            addColumn(articleJQuickUmlErTable, "create_time", "DATETIME", false, false, false);
            addColumn(articleJQuickUmlErTable, "update_time", "DATETIME", false, false, false);
            /**
             * 将所有表格添加到ER图
             */
            System.out.println("添加表格到ER图...");
            JQuickUmlErTable[] allJQuickUmlErTables = {
                    usersJQuickUmlErTable, userAddressJQuickUmlErTable, userBalanceJQuickUmlErTable, userLevelJQuickUmlErTable, userPointsJQuickUmlErTable,
                    categoryJQuickUmlErTable, brandJQuickUmlErTable, goodsJQuickUmlErTable, goodsSpecJQuickUmlErTable, goodsImageJQuickUmlErTable, goodsAttributeJQuickUmlErTable,
                    orderJQuickUmlErTable, orderGoodsJQuickUmlErTable, orderActionJQuickUmlErTable,
                    cartJQuickUmlErTable,
                    paymentJQuickUmlErTable, refundJQuickUmlErTable,
                    shippingJQuickUmlErTable, deliveryJQuickUmlErTable,
                    couponJQuickUmlErTable, userCouponJQuickUmlErTable, promotionJQuickUmlErTable,
                    commentJQuickUmlErTable, commentImageJQuickUmlErTable,
                    adminJQuickUmlErTable, roleJQuickUmlErTable, permissionJQuickUmlErTable, operationLogJQuickUmlErTable,
                    warehouseJQuickUmlErTable, stockJQuickUmlErTable, stockLogJQuickUmlErTable,
                    articleJQuickUmlErTable
            };

            for (JQuickUmlErTable JQuickUmlErTable : allJQuickUmlErTables) {
                diagram.addTable(JQuickUmlErTable);
            }
            /**
             * 创建关系
             */
            System.out.println("创建表关系...");

            /**
             * 用户模块关系
             */
            createRelationship(diagram, "用户地址表", "user_id", "用户表", "user_id", JQuickUmlErRelationshipType.ONE_TO_MANY, "1:N");
            createRelationship(diagram, "用户余额表", "user_id", "用户表", "user_id", JQuickUmlErRelationshipType.ONE_TO_ONE, "1:1");
            createRelationship(diagram, "用户积分表", "user_id", "用户表", "user_id", JQuickUmlErRelationshipType.ONE_TO_ONE, "1:1");

            /**
             * 商品模块关系
             */
            createRelationship(diagram, "商品表", "category_id", "商品分类表", "category_id", JQuickUmlErRelationshipType.ONE_TO_MANY, "1:N");
            createRelationship(diagram, "商品表", "brand_id", "品牌表", "brand_id", JQuickUmlErRelationshipType.ONE_TO_MANY, "1:N");
            createRelationship(diagram, "商品规格表", "goods_id", "商品表", "goods_id", JQuickUmlErRelationshipType.ONE_TO_MANY, "1:N");
            createRelationship(diagram, "商品图片表", "goods_id", "商品表", "goods_id", JQuickUmlErRelationshipType.ONE_TO_MANY, "1:N");
            createRelationship(diagram, "商品属性表", "goods_id", "商品表", "goods_id", JQuickUmlErRelationshipType.ONE_TO_MANY, "1:N");

            /**
             * 订单模块关系
             */
            createRelationship(diagram, "订单表", "user_id", "用户表", "user_id", JQuickUmlErRelationshipType.ONE_TO_MANY, "1:N");
            createRelationship(diagram, "订单商品表", "order_id", "订单表", "order_id", JQuickUmlErRelationshipType.ONE_TO_MANY, "1:N");
            createRelationship(diagram, "订单商品表", "goods_id", "商品表", "goods_id", JQuickUmlErRelationshipType.ONE_TO_MANY, "1:N");
            createRelationship(diagram, "订单操作日志表", "order_id", "订单表", "order_id", JQuickUmlErRelationshipType.ONE_TO_MANY, "1:N");

            /**
             * 购物车关系
             */
            createRelationship(diagram, "购物车表", "user_id", "用户表", "user_id", JQuickUmlErRelationshipType.ONE_TO_MANY, "1:N");
            createRelationship(diagram, "购物车表", "goods_id", "商品表", "goods_id", JQuickUmlErRelationshipType.ONE_TO_MANY, "1:N");

            /**
             * 支付模块关系
             */
            createRelationship(diagram, "支付表", "order_id", "订单表", "order_id", JQuickUmlErRelationshipType.ONE_TO_ONE, "1:1");
            createRelationship(diagram, "支付表", "user_id", "用户表", "user_id", JQuickUmlErRelationshipType.ONE_TO_MANY, "1:N");
            createRelationship(diagram, "退款表", "order_id", "订单表", "order_id", JQuickUmlErRelationshipType.ONE_TO_ONE, "1:1");
            createRelationship(diagram, "退款表", "user_id", "用户表", "user_id", JQuickUmlErRelationshipType.ONE_TO_MANY, "1:N");

            /**
             * 物流模块关系
             */
            createRelationship(diagram, "发货单表", "order_id", "订单表", "order_id", JQuickUmlErRelationshipType.ONE_TO_ONE, "1:1");
            createRelationship(diagram, "发货单表", "shipping_id", "配送方式表", "shipping_id", JQuickUmlErRelationshipType.ONE_TO_MANY, "1:N");

            /**
             * 营销模块关系
             */
            createRelationship(diagram, "用户优惠券表", "user_id", "用户表", "user_id", JQuickUmlErRelationshipType.ONE_TO_MANY, "1:N");
            createRelationship(diagram, "用户优惠券表", "coupon_id", "优惠券表", "coupon_id", JQuickUmlErRelationshipType.ONE_TO_MANY, "1:N");
            createRelationship(diagram, "用户优惠券表", "order_id", "订单表", "order_id", JQuickUmlErRelationshipType.ONE_TO_MANY, "1:N");

            /**
             * 评价模块关系
             */
            createRelationship(diagram, "商品评价表", "order_id", "订单表", "order_id", JQuickUmlErRelationshipType.ONE_TO_ONE, "1:1");
            createRelationship(diagram, "商品评价表", "user_id", "用户表", "user_id", JQuickUmlErRelationshipType.ONE_TO_MANY, "1:N");
            createRelationship(diagram, "商品评价表", "goods_id", "商品表", "goods_id", JQuickUmlErRelationshipType.ONE_TO_MANY, "1:N");
            createRelationship(diagram, "评价图片表", "comment_id", "商品评价表", "comment_id", JQuickUmlErRelationshipType.ONE_TO_MANY, "1:N");

            /**
             * 仓储模块关系
             */
            createRelationship(diagram, "库存表", "goods_id", "商品表", "goods_id", JQuickUmlErRelationshipType.ONE_TO_MANY, "1:N");
            createRelationship(diagram, "库存表", "warehouse_id", "仓库表", "warehouse_id", JQuickUmlErRelationshipType.ONE_TO_MANY, "1:N");
            createRelationship(diagram, "库存日志表", "goods_id", "商品表", "goods_id", JQuickUmlErRelationshipType.ONE_TO_MANY, "1:N");
            createRelationship(diagram, "库存日志表", "warehouse_id", "仓库表", "warehouse_id", JQuickUmlErRelationshipType.ONE_TO_MANY, "1:N");
            createRelationship(diagram, "库存日志表", "order_id", "订单表", "order_id", JQuickUmlErRelationshipType.ONE_TO_MANY, "1:N");
            createRelationship(diagram, "角色表", "role_id", "权限表", "permission_id", JQuickUmlErRelationshipType.ONE_TO_MANY, "1:N");

            /**
             * 内容模块关系
             */
            createRelationship(diagram, "文章表", "category_id", "商品分类表", "category_id", JQuickUmlErRelationshipType.ONE_TO_MANY, "1:N");

            /**
             * 创建SVG生成器
             */
            System.out.println("创建SVG生成器...");
            JQuickUmlERGenerator generator = new JQuickUmlERGenerator();

            /**
             * 配置生成器
             */
            generator.setDiagramName("电商系统数据库ER图");
            generator.setWatermark("Generated by ER Diagram Generator - 电商系统");
            generator.setGenerationDate(new Date());
            generator.setTableSpacing(250); // 增加表格间距

            /**
             * 自定义主题
             */
            ThemeConfig theme = new ThemeConfig();
            theme.background = new Color(248, 249, 250);
            theme.tableBackground = Color.WHITE;
            theme.tableHeaderBackground = new Color(240, 241, 242);
            theme.tableBorder = new Color(222, 226, 230);

            /**
             * 使用更小的字体以容纳更多内容
             */
            theme.titleFont = new Font("Microsoft YaHei", Font.BOLD, 20);
            theme.tableNameFont = new Font("Microsoft YaHei", Font.BOLD, 12);
            theme.columnFont = new Font("Microsoft YaHei", Font.PLAIN, 10);
            theme.commentFont = new Font("Microsoft YaHei", Font.ITALIC, 9);
            theme.relationshipFont = new Font("Microsoft YaHei", Font.PLAIN, 9);

            generator.setThemeConfig(theme);

            /**
             * 生成SVG
             */
            System.out.println("生成SVG文件...");
            generator.generateSVG(diagram, "d://test//complex_er_diagram.svg");

            System.out.println("复杂ER图生成成功: complex_er_diagram.svg");
            System.out.println("总表格数量: " + allJQuickUmlErTables.length + " 个");
            System.out.println("总关系数量: " + diagram.getJQuickUmlErRelationships().size() + " 个");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建表格的辅助方法
     */
    private static JQuickUmlErTable createTable(String name, String comment, int width, int height) {
        JQuickUmlErTable JQuickUmlErTable = new JQuickUmlErTable(name);
        JQuickUmlErTable.setComment(comment);
        JQuickUmlErTable.setSize(width, height);
        return JQuickUmlErTable;
    }

    /**
     * 添加列的辅助方法
     */
    private static void addColumn(JQuickUmlErTable JQuickUmlErTable, String name, String dataType, boolean isPrimaryKey, boolean isForeignKey, boolean nullable) {
        JQuickUmlErColumn JQuickUmlErColumn = new JQuickUmlErColumn(name, dataType);
        JQuickUmlErColumn.setPrimaryKey(isPrimaryKey);
        JQuickUmlErColumn.setForeignKey(isForeignKey);
        JQuickUmlErColumn.setNullable(nullable);
        JQuickUmlErTable.addColumn(JQuickUmlErColumn);
    }

    /**
     * 创建关系的辅助方法
     */
    private static void createRelationship(JQuickUmlERDiagram diagram, String sourceTable, String sourceColumn, String targetTable, String targetColumn, JQuickUmlErRelationshipType type, String label) {
        JQuickUmlErRelationship rel = new JQuickUmlErRelationship(sourceTable, sourceColumn, targetTable, targetColumn, type);
        rel.setLabel(label);
        diagram.addRelationship(rel);
    }
}