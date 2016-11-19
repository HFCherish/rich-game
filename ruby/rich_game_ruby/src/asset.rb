class Asset

  attr_reader :fund, :estates, :points

  def initialize(initialFund)
    @fund = initialFund
    @estates = []
    @points = 0
    @tools = {}
  end

  def useTool(tool)
    @tools[tool] -= 1
  end

  def hasTool(tool)
    @tools[tool] = 0 if (@tools[tool] == nil)
    return @tools[tool] > 0
  end

  def buyTool(tool)
    @tools[tool] = 0 if (@tools[tool] == nil)
    @tools[tool] += 1
    @points -= tool.value
  end

  def addPoints(plusPoints)
    @points += plusPoints
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

  def addFunds(bonus)
    @fund += bonus
  end
end