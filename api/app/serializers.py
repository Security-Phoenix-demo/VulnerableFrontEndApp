from decimal import Decimal


def package_to_dict(package):
    return {"id": package.id, "ecosystem": package.ecosystem, "name": package.name, "version": package.version}


def finding_to_dict(finding):
    return {
        "id": finding.id,
        "package": package_to_dict(finding.package),
        "cve": finding.cve,
        "severity": finding.severity,
        "cvssScore": float(finding.cvss_score or Decimal("0")),
        "fixedVersion": finding.fixed_version or None,
        "fixable": finding.is_fixable,
        "createdAt": finding.created_at.isoformat() if finding.created_at else None,
    }
