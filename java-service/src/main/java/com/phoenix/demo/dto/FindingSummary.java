package com.phoenix.demo.dto;

public class FindingSummary {
    private final String packageName;
    private final long count;
    private final double maxScore;

    public FindingSummary(String packageName, long count, double maxScore) {
        this.packageName = packageName;
        this.count = count;
        this.maxScore = maxScore;
    }

    public String getPackageName() { return packageName; }
    public long getCount() { return count; }
    public double getMaxScore() { return maxScore; }
}
