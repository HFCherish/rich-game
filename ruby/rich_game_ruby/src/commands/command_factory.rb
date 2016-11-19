require_relative 'roll'
require_relative 'buy_estate'
require_relative 'upgrade_estate'
class CommandFactory
  Yes = Command.new
  No = Command.new

  def self.Roll(dice)
    return Roll.new(dice)
  end

  def self.BuyEstate(estate)
    return BuyEstate.new(estate)
  end

  def self.UpgradeEstate(estate)
    return UpgradeEstate.new(estate)
  end

end