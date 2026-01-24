package com.github.paohaijiao.er.layout;

public class JQuickUmlErPoint {

    public double x;

    public double y;

    public JQuickUmlErPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public JQuickUmlErPoint add(JQuickUmlErPoint other) {
        return new JQuickUmlErPoint(this.x + other.x, this.y + other.y);
    }

    public JQuickUmlErPoint subtract(JQuickUmlErPoint other) {
        return new JQuickUmlErPoint(this.x - other.x, this.y - other.y);
    }

    public JQuickUmlErPoint multiply(double scalar) {
        return new JQuickUmlErPoint(this.x * scalar, this.y * scalar);
    }

    public double distance(JQuickUmlErPoint other) {
        double dx = this.x - other.x;
        double dy = this.y - other.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public double length() {
        return Math.sqrt(x * x + y * y);
    }

    public JQuickUmlErPoint normalize() {
        double len = length();
        if (len == 0) return new JQuickUmlErPoint(0, 0);
        return new JQuickUmlErPoint(x / len, y / len);
    }

    @Override
    public String toString() {
        return String.format("(%.2f, %.2f)", x, y);
    }
}
