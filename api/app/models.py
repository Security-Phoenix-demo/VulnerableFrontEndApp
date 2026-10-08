from django.conf import settings
from django.db import models


class Package(models.Model):
    ecosystem = models.CharField(max_length=32)
    name = models.CharField(max_length=255)
    version = models.CharField(max_length=64)

    class Meta:
        unique_together = ("ecosystem", "name", "version")

    def __str__(self):
        return f"{self.name}@{self.version}"


class Finding(models.Model):
    SEVERITIES = [("critical", "Critical"), ("high", "High"), ("medium", "Medium"), ("low", "Low")]

    package = models.ForeignKey(Package, on_delete=models.CASCADE, related_name="findings")
    cve = models.CharField(max_length=32, db_index=True)
    severity = models.CharField(max_length=16, choices=SEVERITIES)
    cvss_score = models.DecimalField(max_digits=3, decimal_places=1)
    fixed_version = models.CharField(max_length=64, blank=True)
    reported_by = models.ForeignKey(settings.AUTH_USER_MODEL, null=True, blank=True, on_delete=models.SET_NULL)
    created_at = models.DateTimeField(auto_now_add=True)

    class Meta:
        ordering = ["-cvss_score", "cve"]

    @property
    def is_fixable(self):
        return bool(self.fixed_version)
