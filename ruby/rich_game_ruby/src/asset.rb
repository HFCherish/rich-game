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

  # def addFund(bonus)
  #   @fund += bonus
  # end
end