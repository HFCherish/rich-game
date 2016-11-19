class Gift

  attr_reader :value

  def initialize(value)
    @value = value
  end

  LUCKY_GOD = Gift.new(5)
end