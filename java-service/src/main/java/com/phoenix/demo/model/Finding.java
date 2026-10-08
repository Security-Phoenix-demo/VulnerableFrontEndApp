package com.phoenix.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Entity
public class Finding {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String packageName;
    private String installedVersion;
    private String fixedVersion;
    private String cve;
    private double cvssScore;

    @Lob
    private String description;

    @ManyToOne
    private User reportedBy;

    public Long getId() { return id; }
    public String getPackageName() { return packageName; }
    public void setPackageName(String packageName) { this.packageName = packageName; }
    public String getInstalledVersion() { return installedVersion; }
    public void setInstalledVersion(String installedVersion) { this.installedVersion = installedVersion; }
    public String getFixedVersion() { return fixedVersion; }
    public void setFixedVersion(String fixedVersion) { this.fixedVersion = fixedVersion; }
    public String getCve() { return cve; }
    public void setCve(String cve) { this.cve = cve; }
    public double getCvssScore() { return cvssScore; }
    public void setCvssScore(double cvssScore) { this.cvssScore = cvssScore; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public User getReportedBy() { return reportedBy; }
    public void setReportedBy(User reportedBy) { this.reportedBy = reportedBy; }
}
