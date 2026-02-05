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
package com.github.paohaijiao.deploy.model;

/**
 * packageName com.github.paohaijiao.deploy.model
 *
 * @author Martin
 * @version 1.0.0
 * @since 2026/2/4
 */

import lombok.Data;

import java.awt.*;

/**
 * 颜色配置
 */
@Data
public class ColorConfig {
    /**
     * 核心平台蓝色
     */
    private Color rootColor = new Color(74, 144, 226);
    /**
     * 核心服务橙色
     */
    private Color coreColor = new Color(255, 152, 0);
    /**
     * 应用服务紫色
     */
    private Color appColor = new Color(156, 39, 176);
    /**
     * 数据处理红色
     */
    private Color dataColor = new Color(244, 67, 54);
    /**
     * 支撑服务青色
     */
    private Color supportColor = new Color(0, 150, 136);
    /**
     * 内部数据库红色
     */
    private Color innerDbColor = new Color(244, 67, 54);
    /**
     * 集群绿色
     */
    private Color clusterColor = new Color(124, 179, 66);
    /**
     * 数据集群粉色
     */
    private Color dataClusterColor = new Color(255, 205, 210);
    /**
     * 支撑集群浅青
     */
    private Color supportClusterColor = new Color(178, 223, 219);
    /**
     * IP地址浅蓝
     */
    private Color ipColor = new Color(227, 242, 253);
    /**
     * 图例背景色
     */
    private Color legendBgColor = new Color(249, 249, 249);
    /**
     * 主要文本颜色
     */
    private Color textColor = new Color(60, 60, 60);
    /**
     * 次要文本颜色
     */
    private Color secondaryTextColor = new Color(80, 80, 80);
    /**
     * 浅色文本
     */
    private Color lightTextColor = new Color(150, 150, 150);
    /**
     * 边框颜色
     */
    private Color borderColor = Color.LIGHT_GRAY;
    /**
     * 白色
     */
    private Color white = Color.WHITE;
    /**
     * IP文本颜色
     */
    private Color ipTextColor = new Color(0, 102, 204);
    /**
     * 描述文本颜色
     */
    private Color descTextColor = new Color(240, 240, 240);


    private Color requestLineColor = new Color(0, 100, 200);

    private Color responseLineColor = new Color(0, 150, 0);

    private Color lifelineColor = new Color(180, 180, 180);

    private Color titleColor = new Color(50, 50, 100);

    private Color participantColor = new Color(50, 50, 150);

    private Color requestTextColor = new Color(0, 0, 120);

    private Color responseTextColor = new Color(0, 120, 0);

    private Color requestDataColor = new Color(0, 0, 100);

    private Color responseDataColor = new Color(0, 100, 0);

    private Color timestampColor = new Color(100, 100, 100);

    private Color lifelineDotColor = new Color(100, 100, 100);


    public Color getBorderColorFor(Color baseColor) {
        return new Color(Math.max(baseColor.getRed() - 40, 0), Math.max(baseColor.getGreen() - 40, 0), Math.max(baseColor.getBlue() - 40, 0));
    }
}
