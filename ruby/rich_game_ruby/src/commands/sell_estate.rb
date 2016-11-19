require_relative 'command'
class SellEstate < Command
  def initialize(estate)
    @estate = estate
  end

  def execute(player)
    if(player.asset.hasEstate(@estate)) then
      player.asset.sellEstate(@estate)
      @estate.sell
    end
    return player.waitForCommand
  end


end