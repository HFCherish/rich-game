require_relative 'roll'
require_relative 'buy_estate'
require_relative 'upgrade_estate'
require_relative 'buy_tool'
require_relative 'get_house_product'
require_relative 'select_gift'
require_relative 'use_tool'
require_relative 'sell_estate'
require_relative 'sell_tool'
require_relative 'query'
class CommandFactory
  Yes = Command.new
  No = Command.new
  BuyTool = BuyTool.new
  SelectGift = SelectGift.new
  Quit = Command.new
  Query = Query.new

  def self.Roll(dice)
    return Roll.new(dice)
  end

  def self.BuyEstate(estate)
    return BuyEstate.new(estate)
  end

  def self.SellEstate(estate)
    return SellEstate.new(estate)
  end

  def self.UpgradeEstate(estate)
    return UpgradeEstate.new(estate)
  end

  def self.GetHouseProduct(product)
    return GetHouseProduct.new(product)
  end

  def self.UseTool(tool, steps)
    return UseTool.new(tool, steps)
  end

  def self.SellTool(tool)
    return SellTool.new(tool)
  end

end