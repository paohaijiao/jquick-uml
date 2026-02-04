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


import com.github.paohaijiao.deploy.component.ServiceComponent;
import lombok.Data;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 服务层配置
 */
@Data
public class ServiceLayer {
    /**
     * 层名称，如"核心服务层"
     */
    private String name;
    /**
     * 标题Y坐标
     */
    private int titleY;
    /**
     * 组件起始Y坐标
     */
    private int boxStartY;
    /**
     * 该层组件的颜色
     */
    private Color color;

    private List<ServiceComponent> services = new ArrayList<>();

    public ServiceLayer(String name, int titleY, int boxStartY, Color color) {
        this.name = name;
        this.titleY = titleY;
        this.boxStartY = boxStartY;
        this.color = color;
    }

    public ServiceLayer addService(ServiceComponent service) {
        services.add(service);
        return this;
    }

    public ServiceLayer addServices(ServiceComponent... services) {
        Collections.addAll(this.services, services);
        return this;
    }
}