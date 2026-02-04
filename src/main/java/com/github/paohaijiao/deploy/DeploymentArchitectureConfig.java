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
package com.github.paohaijiao.deploy;

import com.github.paohaijiao.deploy.component.ClusterComponent;
import com.github.paohaijiao.deploy.component.ServiceComponent;
import com.github.paohaijiao.deploy.layer.ClusterLayer;
import com.github.paohaijiao.deploy.layer.ServiceLayer;
import com.github.paohaijiao.deploy.model.*;
import lombok.Data;

/**
 * 完整的部署架构配置
 */
@Data
public class DeploymentArchitectureConfig {

    private final CanvasConfig canvasConfig = new CanvasConfig();

    private final ColorConfig colorConfig = new ColorConfig();

    private final FontConfig fontConfig = new FontConfig();

    private final LayoutConfig layoutConfig = new LayoutConfig();
    /**
     * 根平台
     */
    private final RootPlatform rootPlatform = new RootPlatform();
    /**
     * 辅助配置
     */
    private final IpDistribution ipDistribution = new IpDistribution();
    private final LegendConfig legendConfig = new LegendConfig();
    /**
     * 服务层
     */
    private ServiceLayer coreServices;
    private ServiceLayer appServices;
    private ClusterLayer dataProcessingClusters;
    private ClusterLayer supportServices;
    /**
     * 位置配置
     */
    private int ipDistributionStartY = 780;

    private int legendStartX = 900;

    private int legendStartY = 780;

    private int timeStampY = 920;

    public DeploymentArchitectureConfig() {
        initializeLayers();
    }

    /**
     * 核心服务层
     */
    private void initializeLayers() {
        coreServices = new ServiceLayer("核心服务层", 170, 190, colorConfig.getCoreColor());
        coreServices.addServices(
                new ServiceComponent("共享交换平台", "lesgxjhpt", "148", colorConfig),
                new ServiceComponent("联合奖惩系统", "gylhjc", "148", colorConfig),
                new ServiceComponent("联合奖惩接口", "gylhjcws", "148", colorConfig),
                new ServiceComponent("内部数据库", "inner_db", "151", colorConfig)
        );
        /**
         * 应用服务层
         */
        appServices = new ServiceLayer("应用服务层", 320, 340, colorConfig.getAppColor());
        appServices.addServices(
                new ServiceComponent("窗口服务系统", "gyckfw", "149", colorConfig),
                new ServiceComponent("移动端服务", "gyyxcxws", "149", colorConfig),
                new ServiceComponent("信用网站", "xywz", "149", colorConfig)
        );

        /**
         * 应用服务层
         */
        dataProcessingClusters = new ClusterLayer("数据处理集群", 430, 490);
        dataProcessingClusters.addClusters(
                new ClusterComponent("数据库对接集群", "146", colorConfig).addProcess("• target_进程4_run_qqDBParser：数据库对接"),
                new ClusterComponent("数据处理集群", "153", colorConfig).addProcesses(
                        "• target_进程1_run_autoprocess：自动数据处理",
                        "• target_进程5_run_dataProcess：数据处理",
                        "• target_进程6_run_ZTKdataProcess：主题库生成"
                ),
                new ClusterComponent("Excel处理集群", "147", colorConfig).addProcesses(
                        "• target_进程2_run_ExcelPreview：文件上报预览",
                        "• target_进程3_run_ExcelParser：文件报送处理"
                )
        );

        // 支撑服务
        supportServices = new ClusterLayer("支撑服务层", 650, 670);
        supportServices.addClusters(new ClusterComponent("插件与备份服务", "152", colorConfig)
                        .addProcesses(
                                "• LhjcPlug：联合奖惩插件",
                                "• 数据备份服务器"
                        ),
                new ClusterComponent("大数据可视化", "150", colorConfig)
                        .addProcesses(
                                "• 大屏展示系统",
                                "• Root_大数据（大屏）"
                        )
        );
    }

    public int getIpDistributionStartX() {
        return layoutConfig.getLeftMargin();
    }

    public int getRootBoxX() {
        return layoutConfig.getRootBoxX(canvasConfig.getWidth());
    }

    public int getClusterTitleBoxWidth() {
        return canvasConfig.getWidth() - 2 * layoutConfig.getLeftMargin();
    }
}
