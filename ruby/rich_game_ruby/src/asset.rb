class Asset

  attr_reader :fund, :estates

  def initialize(initialFund)
    @fund = initialFund
    @estates = []
  end

  def buyEstate(estate)
    @fund -= estate.emptyPrice
    @estates.push(estate)
  end

  def upgradeEstate(estate)
    @fund -= estate.emptyPrice
  end

  def chargeToll(estate)
    @fund -= estate.toll
  end

  def earnToll(estate)
    @fund += estate.toll
  end

  # def addFund(bonus)
  #   @fund += bonus
  # end
end