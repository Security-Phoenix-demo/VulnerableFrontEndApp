import json

import requests
import yaml
from django.http import HttpResponseNotAllowed, JsonResponse
from django.shortcuts import get_object_or_404
from django.views.decorators.csrf import csrf_exempt
from jinja2 import Template

from app.models import Finding
from app.serializers import finding_to_dict
from app.services.report_service import ReportService


def findings(request):
    query = request.GET.get("q", "")
    rows = Finding.objects.select_related("package").filter(package__name__icontains=query)[:200]
    return JsonResponse({"content": [finding_to_dict(f) for f in rows]})


def finding_detail(request, finding_id):
    finding = get_object_or_404(Finding.objects.select_related("package"), pk=finding_id)
    return JsonResponse(finding_to_dict(finding))


@csrf_exempt
def render_report(request):
    if request.method != "POST":
        return HttpResponseNotAllowed(["POST"])
    # Deliberately unsafe: yaml.load without a safe loader and a user-supplied Jinja2 template.
    config = yaml.load(request.body) or {}
    template = Template(config.get("template", "{{ tenant }}: {{ count }} findings"))
    service = ReportService(upstream=config.get("upstream"))
    rendered = template.render(tenant=config.get("tenant", "acme"), count=Finding.objects.count())
    return JsonResponse({"report": rendered, "upstream": service.ping()})


def health(request):
    return JsonResponse({"ok": True, "requests": requests.__version__})
