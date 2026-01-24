package com.github.paohaijiao.er.model;

import lombok.Data;

import java.awt.*;
@Data
public class JQuickUmlErColumn {

    private String name;

    private String dataType;

    private Integer length;

    private Integer precision;

    private boolean nullable = true;

    private String comment;

    private boolean primaryKey = false;

    private boolean foreignKey = false;

    private Color color;

    public JQuickUmlErColumn() {
    }

    public JQuickUmlErColumn(String name, String dataType) {
        this.name = name;
        this.dataType = dataType;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (primaryKey) sb.append("🔑 ");
        if (foreignKey) sb.append("🔗 ");
        sb.append(name).append(" : ").append(dataType);
        if (length != null) {
            sb.append("(").append(length);
            if (precision != null) {
                sb.append(",").append(precision);
            }
            sb.append(")");
        }
        if (!nullable) {
            sb.append(" NOT NULL");
        }
        return sb.toString();
    }

    public String getDisplayString() {
        return toString();
    }
}
