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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 集群组件
 */
@Data
public class ClusterComponent {
    /**
     * 集群名称
     */
    private String name;
    /**
     * IP段
     */
    private String ipSegment;
    /**
     * 进程列表
     */
    private List<String> processes;

    private ColorConfig colorConfig;

    public ClusterComponent(String name, String ipSegment, ColorConfig colorConfig) {
        this.name = name;
        this.ipSegment = ipSegment;
        this.colorConfig = colorConfig;
        this.processes = new ArrayList<>();
    }


    public String getDisplayIp() {
        return "IP: " + ipSegment;
    }

    public ClusterComponent addProcess(String process) {
        processes.add(process);
        return this;
    }

    public ClusterComponent addProcesses(String... processes) {
        Collections.addAll(this.processes, processes);
        return this;
    }

    public Color getBorderColor() {
        if (name.contains("插件") || name.contains("备份") || name.contains("可视化")) {
            return colorConfig.getSupportColor();
        }
        return colorConfig.getDataColor();
    }

    public Color getInnerColor() {
        if (name.contains("插件") || name.contains("备份") || name.contains("可视化")) {
            return colorConfig.getSupportClusterColor();
        }
        return colorConfig.getDataClusterColor();
    }
}
