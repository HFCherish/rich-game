class Asset

  attr_reader :fund

  def initialize
    @fund = 0
  end

  def addFund(bonus)
    @fund += bonus
  end
end