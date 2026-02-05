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

import com.github.paohaijiao.sequence.enums.MessageType;
import lombok.Data;

/**
 * packageName com.github.paohaijiao.sequence.model
 *
 * @author Martin
 * @version 1.0.0
 * @since 2026/2/5
 */
@Data
public class DiagramMessage {

    private int fromIndex;

    private int toIndex;

    private String text;

    private int sequence;

    private String data;

    private MessageType type = MessageType.REQUEST;

    public DiagramMessage(int from, int to, String text, int seq, String data) {
        this.fromIndex = from;
        this.toIndex = to;
        this.text = text;
        this.sequence = seq;
        this.data = data;
        this.type = from > to ? MessageType.RESPONSE : MessageType.REQUEST;
    }

    public boolean isReturnMessage() {
        return type == MessageType.RESPONSE;
    }
}
