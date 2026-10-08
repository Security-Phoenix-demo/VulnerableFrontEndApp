import yaml
import requests
from flask import Flask, request, jsonify

app = Flask(__name__)


@app.route("/config", methods=["POST"])
def load_config():
    # Deliberately unsafe: yaml.load without a safe loader, as in the real app this imitates.
    config = yaml.load(request.data)
    upstream = requests.get(config.get("upstream", "https://example.invalid"), timeout=5)
    return jsonify({"status": upstream.status_code, "keys": sorted(config.keys())})


@app.route("/health")
def health():
    return jsonify({"ok": True})
