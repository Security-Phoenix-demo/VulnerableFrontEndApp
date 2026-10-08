require 'minitest/autorun'
require_relative '../lib/importer'
require_relative '../lib/report_builder'

class ImporterTest < Minitest::Test
  XML = <<~XML
    <report>
      <finding><package>rails</package><version>5.2.0</version><cve>CVE-2019-5418</cve><severity>High</severity><fixed>5.2.2.1</fixed></finding>
      <finding><package>nokogiri</package><version>1.10.0</version><cve>CVE-2019-11068</cve><severity>Critical</severity></finding>
    </report>
  XML

  def test_parses_every_finding
    findings = Importer.new.parse(XML)
    assert_equal 2, findings.size
    assert_equal 'rails', findings.first[:package]
    assert_equal '5.2.2.1', findings.first[:fixedVersion]
  end

  def test_groups_worst_first
    grouped = ReportBuilder.new(Importer.new.parse(XML)).grouped
    assert_equal 'nokogiri', grouped.first[:package]
    assert_equal 'critical', grouped.first[:worst]
  end
end
