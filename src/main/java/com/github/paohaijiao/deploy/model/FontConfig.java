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

import lombok.Getter;

import java.awt.*;

/**
 * 字体配置
 */
@Getter
public class FontConfig {
    private String fontFamily = "微软雅黑";
    private int titleSize = 24;
    private int clusterTitleSize = 18;
    private int layerTitleSize = 18;
    private int serverSize = 16;
    private int descSize = 13;
    private int ipSize = 12;
    private int smallSize = 12;
    private int legendTitleSize = 16;
    private int legendTextSize = 12;
    private int timeSize = 11;
    private int rootTitleSize = 22; // 根平台标题比主标题小2

    private Font titleFont;
    private Font clusterTitleFont;
    private Font layerTitleFont;
    private Font serverFont;
    private Font descFont;
    private Font ipFont;
    private Font smallFont;
    private Font legendTitleFont;
    private Font legendTextFont;
    private Font timeFont;
    private Font rootTitleFont;

    public FontConfig() {
        updateFonts();
    }

    private void updateFonts() {
        titleFont = new Font(fontFamily, Font.BOLD, titleSize);
        clusterTitleFont = new Font(fontFamily, Font.BOLD, clusterTitleSize);
        layerTitleFont = new Font(fontFamily, Font.BOLD, layerTitleSize);
        serverFont = new Font(fontFamily, Font.BOLD, serverSize);
        descFont = new Font(fontFamily, Font.PLAIN, descSize);
        ipFont = new Font(fontFamily, Font.BOLD, ipSize);
        smallFont = new Font(fontFamily, Font.PLAIN, smallSize);
        legendTitleFont = new Font(fontFamily, Font.BOLD, legendTitleSize);
        legendTextFont = new Font(fontFamily, Font.PLAIN, legendTextSize);
        timeFont = new Font(fontFamily, Font.PLAIN, timeSize);
        rootTitleFont = new Font(fontFamily, Font.BOLD, rootTitleSize);
    }

    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
        updateFonts();
    }

    public void setTitleSize(int titleSize) {
        this.titleSize = titleSize;
        updateFonts();
    }

    public void setClusterTitleSize(int clusterTitleSize) {
        this.clusterTitleSize = clusterTitleSize;
        updateFonts();
    }

    public void setLayerTitleSize(int layerTitleSize) {
        this.layerTitleSize = layerTitleSize;
        updateFonts();
    }

    public void setServerSize(int serverSize) {
        this.serverSize = serverSize;
        updateFonts();
    }

    public void setDescSize(int descSize) {
        this.descSize = descSize;
        updateFonts();
    }

    public void setIpSize(int ipSize) {
        this.ipSize = ipSize;
        updateFonts();
    }

    public void setSmallSize(int smallSize) {
        this.smallSize = smallSize;
        updateFonts();
    }

    public void setLegendTitleSize(int legendTitleSize) {
        this.legendTitleSize = legendTitleSize;
        updateFonts();
    }

    public void setLegendTextSize(int legendTextSize) {
        this.legendTextSize = legendTextSize;
        updateFonts();
    }

    public void setTimeSize(int timeSize) {
        this.timeSize = timeSize;
        updateFonts();
    }

    public void setRootTitleSize(int rootTitleSize) {
        this.rootTitleSize = rootTitleSize;
        updateFonts();
    }

    public Font getBoldFont(int size) {
        return new Font(fontFamily, Font.BOLD, size);
    }

    public Font getPlainFont(int size) {
        return new Font(fontFamily, Font.PLAIN, size);
    }
}
