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
package com.github.paohaijiao.sequence.model;

/**
 * packageName com.github.paohaijiao.sequence.model
 *
 * @author Martin
 * @version 1.0.0
 * @since 2026/2/5
 */

import com.github.paohaijiao.deploy.model.ColorConfig;
import com.github.paohaijiao.deploy.model.LayoutConfig;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 时序图配置实体
 * 用于配置时序图的整体样式、参与者、消息流等信息
 */
@Data
public class SequenceDiagramConfig {

    /**
     * 画布配置
     */
    private CanvasConfig canvasConfig = new CanvasConfig();

    /**
     * 字体配置
     */
    private FontConfig fontConfig = new FontConfig();

    /**
     * 颜色配置
     */
    private ColorConfig colorConfig = new ColorConfig();

    /**
     * 布局配置
     */
    private LayoutConfig layoutConfig = new LayoutConfig();

    /**
     * 数据配置
     */
    private List<String> participants = new ArrayList<>();

    private List<DiagramMessage> messages = new ArrayList<>();

    private String title = "时序图";

    /**
     * 图例配置
     */
    private com.github.paohaijiao.sequence.model.LegendConfig legendConfig = new com.github.paohaijiao.sequence.model.LegendConfig();

    /**
     * 时间戳配置
     */
    private boolean showTimestamp = true;

    private String timestampFormat = "yyyy-MM-dd HH:mm:ss";

    public SequenceDiagramConfig() {
        initializeDefaultConfig();
    }

    public static SequenceDiagramConfig createOrderProcessingExample() {
        SequenceDiagramConfig config = new SequenceDiagramConfig();
        config.setTitle("订单处理时序图");
        config.getParticipants().clear();
        config.getMessages().clear();
        config.addParticipant("用户");
        config.addParticipant("前端应用");
        config.addParticipant("API网关");
        config.addParticipant("认证服务");
        config.addParticipant("订单服务");
        config.addParticipant("支付服务");
        config.addParticipant("库存服务");
        config.addMessage(0, 1, "提交订单", 1, "订单数据");
        config.addMessage(1, 2, "POST /api/orders", 2, "{\"order\": {...}}");
        config.addMessage(2, 3, "验证令牌", 3, "token: xyz123");
        config.addMessage(3, 2, "验证成功", 4, "{\"valid\": true, \"userId\": 123}");
        config.addMessage(2, 4, "创建订单", 5, "orderDetails");
        config.addMessage(4, 2, "订单创建成功", 6, "{\"orderId\": 456, \"status\": \"created\"}");
        config.addMessage(2, 5, "调用支付", 7, "支付请求");
        config.addMessage(5, 2, "支付成功", 8, "{\"paymentId\": 789, \"status\": \"paid\"}");
        config.addMessage(2, 6, "扣减库存", 9, "库存扣减请求");
        config.addMessage(6, 2, "库存扣减成功", 10, "{\"stockUpdated\": true}");
        config.addMessage(2, 1, "返回订单结果", 11, "{\"orderId\": 456, \"status\": \"completed\"}");
        config.addMessage(1, 0, "显示成功页面", 12, "订单处理完成！");
        return config;
    }

    /**
     * 构建一个用户登录时序图的配置示例
     */
    public static SequenceDiagramConfig createLoginExample() {

        SequenceDiagramConfig config = new SequenceDiagramConfig();
        config.setTitle("用户登录时序图");
        config.getParticipants().clear();
        config.getMessages().clear();
        config.addParticipant("用户");
        config.addParticipant("Web界面");
        config.addParticipant("认证服务");
        config.addParticipant("用户数据库");
        config.addParticipant("会话服务");
        config.addMessage(0, 1, "输入用户名密码", 1, "user: admin, pwd: ***");
        config.addMessage(1, 2, "POST /auth/login", 2, "登录凭证");
        config.addMessage(2, 3, "查询用户信息", 3, "username: admin");
        config.addMessage(3, 2, "返回用户数据", 4, "用户数据");
        config.addMessage(2, 4, "创建会话", 5, "session data");
        config.addMessage(4, 2, "会话创建成功", 6, "sessionId: abc123");
        config.addMessage(2, 1, "返回认证结果", 7, "{\"token\": \"xyz\", \"user\": {...}}");
        config.addMessage(1, 0, "跳转到主页", 8, "登录成功");
        return config;
    }

    private void initializeDefaultConfig() {
        /**
         * 默认参与者
         */
        participants.add("用户");
        participants.add("前端应用");
        participants.add("API网关");
        participants.add("认证服务");
        participants.add("订单服务");

        /**
         * 默认消息
         */
        messages.add(new DiagramMessage(0, 1, "提交订单请求", 1, "orderData"));
        messages.add(new DiagramMessage(1, 2, "POST /api/orders", 2, "{\"order\": {...}}"));
        messages.add(new DiagramMessage(2, 3, "验证令牌", 3, "token: xyz123"));
        messages.add(new DiagramMessage(3, 2, "验证成功", 4, "{\"valid\": true, \"userId\": 123}"));
        messages.add(new DiagramMessage(2, 4, "创建订单", 5, "orderDetails"));
        messages.add(new DiagramMessage(4, 2, "订单创建成功", 6, "{\"orderId\": 456, \"status\": \"success\"}"));
        messages.add(new DiagramMessage(2, 1, "返回订单ID", 7, "{\"orderId\": 456}"));
        messages.add(new DiagramMessage(1, 0, "显示订单确认", 8, "订单创建成功！"));
    }

    public void addParticipant(String participant) {
        this.participants.add(participant);
    }

    public void addMessage(DiagramMessage message) {
        this.messages.add(message);
    }

    public void addMessage(int from, int to, String text, int seq, String data) {
        this.messages.add(new DiagramMessage(from, to, text, seq, data));
    }

    public int getParticipantCount() {
        return participants.size();
    }

}
