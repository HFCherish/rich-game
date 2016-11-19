require_relative 'roll'
require_relative 'buy_estate'
class CommandFactory
  Yes = Command.new

  def self.Roll(dice)
    return Roll.new(dice)
  end

  def self.BuyEstate(estate)
    return BuyEstate.new(estate)
  end

end