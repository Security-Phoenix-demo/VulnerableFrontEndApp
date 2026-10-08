require 'loofah'

# Strips scripts from user-supplied report HTML before it is embedded in the dashboard.
module Sanitizer
  module_function

  def clean(html)
    Loofah.fragment(html).scrub!(:prune).scrub!(:whitewash).to_s
  end

  def titles(html)
    Loofah.fragment(html).xpath('.//h1 | .//h2').map(&:text)
  end
end
