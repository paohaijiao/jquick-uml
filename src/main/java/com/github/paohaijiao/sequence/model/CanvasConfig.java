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
public class CanvasConfig {

    private  int width = 1200;

    private  int height = 800;


    private  Color backgroundColor = new Color(245, 245, 245);

    private  String title = "时序图";

    public Dimension getSize() {
        return new Dimension(width, height);
    }

    public int getCenterX() {
        return width / 2;
    }
}
