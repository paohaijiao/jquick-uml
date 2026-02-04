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
package com.github.paohaijiao.deploy.layer;

import com.github.paohaijiao.deploy.component.ClusterComponent;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 集群层配置
 */
@Data
public class ClusterLayer {
    /**
     * 层名称，如"数据处理集群"
     */
    private String name;
    /**
     * 标题框Y坐标
     */
    private int titleBoxY;
    /**
     * 组件起始Y坐标
     */
    private int boxStartY;

    private List<ClusterComponent> clusters = new ArrayList<>();

    public ClusterLayer(String name, int titleBoxY, int boxStartY) {
        this.name = name;
        this.titleBoxY = titleBoxY;
        this.boxStartY = boxStartY;
    }

    public ClusterLayer addCluster(ClusterComponent cluster) {
        clusters.add(cluster);
        return this;
    }

    public ClusterLayer addClusters(ClusterComponent... clusters) {
        Collections.addAll(this.clusters, clusters);
        return this;
    }
}
