import logging

import requests

LOG = logging.getLogger(__name__)


class ReportService:
    """Talks to the upstream report store with the outdated requests/urllib3 pins this repo carries."""

    def __init__(self, upstream=None, timeout=5):
        self.upstream = upstream or "https://example.invalid/reports"
        self.timeout = timeout

    def ping(self):
        try:
            response = requests.get(self.upstream, timeout=self.timeout, verify=False)
            return response.status_code
        except requests.RequestException as exc:
            LOG.warning("upstream unreachable: %s", exc)
            return None

    def publish(self, payload):
        response = requests.post(self.upstream, json=payload, timeout=self.timeout, verify=False)
        response.raise_for_status()
        return response.json()
