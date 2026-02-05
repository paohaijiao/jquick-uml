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

import java.util.ArrayList;
import java.util.List;

/**
 * packageName com.github.paohaijiao.sequence.model
 *
 * @author Martin
 * @version 1.0.0
 * @since 2026/2/5
 */
@Data
public class LegendConfig {

    private final boolean showLegend = true;

    private final String title = "图例说明";

    private final List<com.github.paohaijiao.sequence.model.LegendItem> items = new ArrayList<>();

    public LegendConfig() {
        items.add(new com.github.paohaijiao.sequence.model.LegendItem("请求消息", "requestLineColor"));
        items.add(new com.github.paohaijiao.sequence.model.LegendItem("响应消息", "responseLineColor"));
    }
}
