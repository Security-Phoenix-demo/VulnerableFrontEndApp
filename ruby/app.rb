require 'json'
require 'rack'
require 'nokogiri'
require 'loofah'

require_relative 'lib/importer'
require_relative 'lib/sanitizer'

# Minimal Rack application that imports scanner XML reports and serves sanitized summaries.
class FindingsApp
  def call(env)
    request = Rack::Request.new(env)
    case [request.request_method, request.path]
    when ['POST', '/import']
      findings = Importer.new.parse(request.body.read)
      [200, { 'Content-Type' => 'application/json' }, [JSON.generate(count: findings.size, findings: findings)]]
    when ['POST', '/sanitize']
      [200, { 'Content-Type' => 'text/html' }, [Sanitizer.clean(request.body.read)]]
    when ['GET', '/health']
      [200, { 'Content-Type' => 'application/json' }, ['{"ok":true}']]
    else
      [404, { 'Content-Type' => 'text/plain' }, ['not found']]
    end
  end
end
