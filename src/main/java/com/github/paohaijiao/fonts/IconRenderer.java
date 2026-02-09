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
package com.github.paohaijiao.fonts;

import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.bridge.BridgeContext;
import org.apache.batik.bridge.GVTBuilder;
import org.apache.batik.bridge.UserAgent;
import org.apache.batik.bridge.UserAgentAdapter;
import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.Document;

import java.awt.*;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

/**
 * packageName com.github.paohaijiao.fonts
 *
 * @author Martin
 * @version 1.0.0
 * @since 2026/2/9
 */
public class IconRenderer {

    private static final Map<String, GraphicsNode> ICON_CACHE = new HashMap<>();
    private static BridgeContext bridgeContext;

    static {
        try {
            // 初始化Batik桥接上下文
            UserAgent userAgent = new UserAgentAdapter();
            bridgeContext = new BridgeContext(userAgent);
            bridgeContext.setDynamicState(BridgeContext.STATIC);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建指定类型的图标
     */
    public static GraphicsNode createIcon(String iconType, Color color, int size) {
        String cacheKey = iconType + "_" + color.getRGB() + "_" + size;

        if (ICON_CACHE.containsKey(cacheKey)) {
            return ICON_CACHE.get(cacheKey);
        }

        try {
            // 获取SVG路径
            String svgPath = FontAwesomeIcons.getIconPath(iconType);

            // 创建SVG文档
            String svgContent = createSVGDocument(svgPath, color, size);

            // 解析SVG
            String parser = XMLResourceDescriptor.getXMLParserClassName();
            SAXSVGDocumentFactory factory = new SAXSVGDocumentFactory(parser);
            Document document = factory.createDocument(null, new StringReader(svgContent));

            // 构建GraphicsNode
            GVTBuilder builder = new GVTBuilder();
            GraphicsNode graphicsNode = builder.build(bridgeContext, document);

            // 缓存结果
            ICON_CACHE.put(cacheKey, graphicsNode);

            return graphicsNode;

        } catch (Exception e) {
            System.err.println("Failed to create icon: " + iconType);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 创建完整的SVG文档
     */
    private static String createSVGDocument(String svgPath, Color color, int size) {
        String hexColor = String.format("#%02x%02x%02x",
                color.getRed(), color.getGreen(), color.getBlue());

        return String.format(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                        "<svg width=\"%d\" height=\"%d\" viewBox=\"0 0 512 512\" " +
                        "xmlns=\"http://www.w3.org/2000/svg\">" +
                        "<path fill=\"%s\" d=\"%s\"/>" +
                        "</svg>",
                size, size, hexColor, svgPath
        );
    }

    /**
     * 将颜色转换为十六进制表示
     */
    public static String colorToHex(Color color) {
        return String.format("#%02x%02x%02x",
                color.getRed(), color.getGreen(), color.getBlue());
    }

    /**
     * 清除图标缓存
     */
    public static void clearCache() {
        ICON_CACHE.clear();
    }
}
