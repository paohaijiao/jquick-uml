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
public class ColorConfig {
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

    private Color legendBgColor = new Color(249, 249, 249);

}
