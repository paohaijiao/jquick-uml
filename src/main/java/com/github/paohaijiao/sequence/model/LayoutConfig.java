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

import lombok.Data;

/**
 * packageName com.github.paohaijiao.sequence.model
 *
 * @author Martin
 * @version 1.0.0
 * @since 2026/2/5
 */
@Data
public class LayoutConfig {

    /**
     * 参与者配置
     */
    private int participantWidth = 130;

    private int participantStartX = 80;

    private int participantTitleY = 80;

    private int lifelineStartY = 90;

    /**
     * 消息配置
     */
    private int messageStartY = 120;

    private int verticalSpacing = 70;

    private float messageLineWidth = 1.5f;

    /**
     * 边距
     */
    private int leftMargin = 80;

    private int rightMargin = 80;

    private int topMargin = 40;

    private int bottomMargin = 80;

    /**
     * 图例
     */
    private int legendWidth = 200;

    private int legendHeight = 120;

    private int legendX = 900;

    private int legendY = 150;

    /**
     * 箭头
     */
    private int arrowSize = 8;

    /**
     * 需要在外部设置
     *
     * @return
     */
    public int getParticipantCount() {
        return 0;
    }

    /**
     * 可以在这里动态调整布局
     *
     * @param count
     */
    public void setParticipantCount(int count) {
    }
}
