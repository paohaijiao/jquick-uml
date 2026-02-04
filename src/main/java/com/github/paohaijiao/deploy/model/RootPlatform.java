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
 * 根平台配置
 */
@Data
public class RootPlatform {

    private String name = "广元市公共信用信息处理平台 (ROOT)";

    private String ipSegment = "148";

    private int boxY = 70;

    private int titleY = 110;

    private int ipY = 130;

    public String getDisplayIp() {
        return "IP: " + ipSegment + ".xxx.xxx.xxx";
    }
}
