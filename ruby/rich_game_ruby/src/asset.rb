require_relative '../src/places/estate'
class Asset

  attr_reader :fund, :estates, :points

  def initialize(initialFund)
    @fund = initialFund
    @estates = {}
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

  def sellTool(tool)
    @tools[tool] -= 1
    @points += tool.value
  end

  def addPoints(plusPoints)
    @points += plusPoints
  end

  def buyEstate(estate)
    @fund -= estate.emptyPrice
    @estates[estate.level] = [] if !@estates.include? estate.level
    @estates[estate.level].push(estate)
  end

  def hasEstate(estate)
    @estates[estate.level] = [] if !@estates.include? estate.level
    @estates[estate.level].include? estate
  end

  def sellEstate(estate)
    @estates[estate.level].delete(estate)
    @fund += estate.sellPrice
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

  def reportAsString
    Array[Estate::Level::EMPTY, Estate::Level::THATCH, Estate::Level::FOREIGN_STYLE, Estate::Level::SKYSCRAPER].each {
        |level| @estates[level] = [] if !@estates.include? level
    }
    Array[Tool::BLOCK, Tool::BOMB, Tool::ROBOT_DULL].each {
        |tool| @tools[tool] = 0 if (@tools[tool] == nil)
    }
    res = "资金: #{@fund}元\n"
    res += "点数: #{@points}点\n"
    res += "地产: 空地#{@estates[Estate::Level::EMPTY].length}处, 茅屋#{@estates[Estate::Level::THATCH].length}处, 洋房#{@estates[Estate::Level::FOREIGN_STYLE].length}处, 摩天楼#{@estates[Estate::Level::SKYSCRAPER].length}处\n"
    res += "道具: 路障#{@tools[Tool::BLOCK]}个, 炸弹#{@tools[Tool::BOMB]}个, 机器娃娃#{@tools[Tool::ROBOT_DULL]}个"
    puts res.to_s
    return res.to_s
  end
end