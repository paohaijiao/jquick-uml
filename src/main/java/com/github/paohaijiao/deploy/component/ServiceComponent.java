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
package com.github.paohaijiao.deploy.component;

/**
 * packageName com.github.paohaijiao.deploy.component
 *
 * @author Martin
 * @version 1.0.0
 * @since 2026/2/4
 */

import com.github.paohaijiao.deploy.model.ColorConfig;
import lombok.Data;

import java.awt.*;

/**
 * 服务组件
 */
@Data
public class ServiceComponent {
    /**
     * 服务名称，如"共享交换平台"
     */
    private String name;
    /**
     * 服务描述，如"lesgxjhpt"
     */
    private String desc;
    /**
     * IP段，如"148"
     */
    private String ipSegment;

    private ColorConfig colorConfig;

    public ServiceComponent(String name, String desc, String ipSegment, ColorConfig colorConfig) {
        this.name = name;
        this.desc = desc;
        this.ipSegment = ipSegment;
        this.colorConfig = colorConfig;
    }

    public String getDisplayIp() {
        return "IP: " + ipSegment;
    }

    public Color getColor() {
        if (name.contains("内部数据库")) {
            return colorConfig.getInnerDbColor();
        }
        return colorConfig.getCoreColor();
    }

    public Color getIpColor() {
        return colorConfig.getIpColor();
    }
}
