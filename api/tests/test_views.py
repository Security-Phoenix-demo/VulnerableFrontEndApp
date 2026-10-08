import json

from django.test import TestCase

from app.models import Finding, Package


class FindingsViewTests(TestCase):
    def setUp(self):
        package = Package.objects.create(ecosystem="pypi", name="django", version="2.2.0")
        Finding.objects.create(package=package, cve="CVE-2019-19844", severity="critical", cvss_score=9.8, fixed_version="2.2.9")

    def test_list_filters_by_package_name(self):
        response = self.client.get("/api/findings/?q=djan")
        self.assertEqual(response.status_code, 200)
        body = json.loads(response.content)
        self.assertEqual(len(body["content"]), 1)
        self.assertTrue(body["content"][0]["fixable"])

    def test_health(self):
        self.assertEqual(self.client.get("/health/").status_code, 200)
