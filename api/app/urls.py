from django.contrib import admin
from django.urls import path

from app import views

urlpatterns = [
    path("admin/", admin.site.urls),
    path("api/findings/", views.findings, name="findings"),
    path("api/findings/<int:finding_id>/", views.finding_detail, name="finding-detail"),
    path("api/reports/render/", views.render_report, name="render-report"),
    path("health/", views.health, name="health"),
]
