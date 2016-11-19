require_relative 'place'
class Estate < Place
  attr_accessor :owner
  attr_reader :emptyPrice

  def initialize(emptyPrice)
    @emptyPrice = emptyPrice
    @owner = nil
  end

  def comeHere(player)
    player.moveTo(self)
    typeFor(player).action(player, self)
  end

  def typeFor(player)
    return Type::EMPTY if (@owner == nil)
    return Type::OWNER if (@owner == player)
  end

  class Type
    EMPTY = Type.new

    def EMPTY.action(player, estate)
      return player.endTurn if (player.asset.fund < estate.emptyPrice)
      player.lastResponsiveCommand = CommandFactory.BuyEstate(estate)
      return player.waitForResponse
    end

    OWNER = Type.new

    def OWNER.action(player, estate)
      return player.endTurn if (player.asset.fund < estate.emptyPrice)
      player.lastResponsiveCommand = CommandFactory.UpgradeEstate(estate)
      return player.waitForResponse
    end

    def action(player, estate)
    end

  end
end