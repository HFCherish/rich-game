class Gift

  attr_reader :value

  def initialize(value)
    @value = value
  end

  LUCKY_GOD = Gift.new(5)
  FUND = Gift.new(2000)
  POINT_CARD = Gift.new(200)
end