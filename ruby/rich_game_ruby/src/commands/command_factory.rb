require_relative 'roll'
require_relative 'buy_estate'
require_relative 'upgrade_estate'
require_relative 'buy_tool'
require_relative 'get_house_product'
require_relative 'select_gift'
class CommandFactory
  Yes = Command.new
  No = Command.new
  BuyTool = BuyTool.new
  SelectGift = SelectGift.new
  Quit = Command.new

  def self.Roll(dice)
    return Roll.new(dice)
  end

  def self.BuyEstate(estate)
    return BuyEstate.new(estate)
  end

  def self.UpgradeEstate(estate)
    return UpgradeEstate.new(estate)
  end

  def self.GetHouseProduct(product)
    return GetHouseProduct.new(product)
  end

end