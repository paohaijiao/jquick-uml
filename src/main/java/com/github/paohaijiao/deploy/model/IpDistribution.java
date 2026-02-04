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
 * IP分布配置
 */
@Data
public class IpDistribution {

    private List<String> distributions;

    public IpDistribution() {
        /**
         * 默认IP分布
         */
        distributions = Arrays.asList(
                "• 核心平台: 148.xxx.xxx.xxx",
                "• 应用服务: 149.xxx.xxx.xxx",
                "• 数据处理: 146.xxx.xxx.xxx / 147.xxx.xxx.xxx / 153.xxx.xxx.xxx",
                "• 支撑服务: 150.xxx.xxx.xxx / 151.xxx.xxx.xxx / 152.xxx.xxx.xxx"
        );
    }

    public IpDistribution(List<String> distributions) {
        this.distributions = distributions;
    }


    public void addDistribution(String distribution) {
        distributions.add(distribution);
    }

    public String[] getDistributionsArray() {
        return distributions.toArray(new String[0]);
    }
}
