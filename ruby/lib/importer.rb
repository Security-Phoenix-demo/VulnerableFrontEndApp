require 'nokogiri'

# Parses the XML scanner export into plain hashes. Entity expansion is left enabled on purpose.
class Importer
  Finding = Struct.new(:package, :version, :cve, :severity, :fixed_version) do
    def to_h
      { package: package, version: version, cve: cve, severity: severity, fixedVersion: fixed_version }
    end
  end

  def parse(xml)
    document = Nokogiri::XML(xml) { |config| config.noent }
    document.xpath('//finding').map do |node|
      Finding.new(
        node.at_xpath('package')&.text,
        node.at_xpath('version')&.text,
        node.at_xpath('cve')&.text,
        node.at_xpath('severity')&.text&.downcase || 'medium',
        node.at_xpath('fixed')&.text
      ).to_h
    end
  end
end
