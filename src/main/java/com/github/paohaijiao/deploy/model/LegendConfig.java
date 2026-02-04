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

import lombok.Data;

import java.util.Arrays;
import java.util.List;

/**
 * 图例配置
 */
@Data
public class LegendConfig {

    private List<LegendItem> items;

    private int width = 350;

    private int height = 140;

    private String title = "组件说明";

    public LegendConfig() {
        /**
         * 默认图例项
         */
        items = Arrays.asList(
                new LegendItem("核心平台", "rootColor"),
                new LegendItem("核心服务", "coreColor"),
                new LegendItem("应用服务", "appColor"),
                new LegendItem("数据处理", "dataColor"),
                new LegendItem("支撑服务", "supportColor"),
                new LegendItem("IP标识", "ipColor")
        );
    }

    public LegendConfig(List<LegendItem> items) {
        this.items = items;
    }


}
