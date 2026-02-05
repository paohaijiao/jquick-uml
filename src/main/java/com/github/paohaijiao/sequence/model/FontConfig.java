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

import java.awt.*;

/**
 * packageName com.github.paohaijiao.sequence.model
 *
 * @author Martin
 * @version 1.0.0
 * @since 2026/2/5
 */
@Data
public class FontConfig {

    private String fontFamily = "Microsoft YaHei";

    private int titleSize = 20;

    private int participantSize = 14;

    private int messageSize = 12;

    private int dataSize = 10;

    private int legendTitleSize = 16;

    private int legendTextSize = 12;

    private int timestampSize = 11;

    public Font getTitleFont() {
        return new Font(fontFamily, Font.BOLD, titleSize);
    }

    public Font getParticipantFont() {
        return new Font(fontFamily, Font.BOLD, participantSize);
    }

    public Font getMessageFont() {
        return new Font(fontFamily, Font.PLAIN, messageSize);
    }

    public Font getDataFont() {
        return new Font("Consolas", Font.PLAIN, dataSize);
    }

    public Font getBoldFont(int size) {
        return new Font(fontFamily, Font.BOLD, size);
    }

    public Font getPlainFont(int size) {
        return new Font(fontFamily, Font.PLAIN, size);
    }
}
