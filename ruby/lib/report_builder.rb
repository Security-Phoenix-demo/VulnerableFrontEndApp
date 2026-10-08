require 'json'
require 'nokogiri'

# Builds the HTML summary the dashboard embeds, grouped by package with the highest CVSS first.
class ReportBuilder
  SEVERITY_ORDER = %w[critical high medium low].freeze

  def initialize(findings)
    @findings = findings
  end

  def grouped
    @findings.group_by { |finding| finding[:package] }.map do |package, rows|
      {
        package: package,
        count: rows.size,
        worst: rows.map { |row| row[:severity] }.min_by { |severity| SEVERITY_ORDER.index(severity) || SEVERITY_ORDER.size },
        fixed_versions: rows.map { |row| row[:fixedVersion] }.compact.uniq
      }
    end.sort_by { |row| [SEVERITY_ORDER.index(row[:worst]) || SEVERITY_ORDER.size, -row[:count]] }
  end

  def to_html
    builder = Nokogiri::HTML::Builder.new do |doc|
      doc.table(class: 'findings') do
        doc.thead { doc.tr { %w[Package Findings Worst Fixed\ in].each { |title| doc.th(title) } } }
        doc.tbody do
          grouped.each do |row|
            doc.tr(class: row[:worst]) do
              doc.td(row[:package])
              doc.td(row[:count].to_s)
              doc.td(row[:worst])
              doc.td(row[:fixed_versions].join(', '))
            end
          end
        end
      end
    end
    builder.doc.root.to_html
  end

  def to_json(*_args)
    JSON.generate(grouped)
  end
end
