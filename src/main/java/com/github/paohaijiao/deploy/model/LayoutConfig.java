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

/**
 * 组件布局配置
 */
@Data
public class LayoutConfig {
    /**
     * 圆角配置
     */
    private int defaultArc = 8;

    private int smallArc = 5;

    private int rootArc = 10;

    private int clusterArc = 8;

    /**
     * 间距配置
     */
    private int lineSpacing = 22;

    private int clusterContentSpacing = 18;

    private int ipBoxWidth = 120;

    private int ipBoxHeight = 26;

    private int ipBoxTextOffset = 72;

    /**
     * 组件尺寸
     */
    private int rootBoxWidth = 400;

    private int rootBoxHeight = 80;

    private int serviceBoxWidth = 220;

    private int serviceBoxHeight = 90;

    private int clusterBoxWidth = 340;

    private int clusterBoxHeight = 130;

    private int supportClusterBoxHeight = 100;

    private int clusterTitleBoxHeight = 50;

    /**
     * 组件间距
     */
    private int serviceBoxGap = 40;

    private int clusterBoxGap = 30;

    /**
     * 边距
     */
    private int leftMargin = 100;

    private int rightMargin = 100;

    private int topMargin = 70;


    public int getServiceBoxSpacing() {
        return serviceBoxWidth + serviceBoxGap;
    }

    public int getClusterBoxSpacing() {
        return clusterBoxWidth + clusterBoxGap;
    }

    public int getRootBoxX(int canvasWidth) {
        return (canvasWidth - rootBoxWidth) / 2;
    }
}
