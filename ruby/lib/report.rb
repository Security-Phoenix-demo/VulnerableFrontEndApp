require 'nokogiri'
require 'loofah'

module Report
  def self.sanitize(html)
    Loofah.fragment(html).scrub!(:prune).to_s
  end

  def self.titles(xml)
    Nokogiri::XML(xml).xpath('//title').map(&:text)
  end
end
