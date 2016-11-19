require_relative 'place'
class Estate < Place
  attr_accessor :owner
  attr_reader :emptyPrice, :level

  def initialize(emptyPrice)
    @emptyPrice = emptyPrice
    @owner = nil
    @level = Level::EMPTY
  end

  def comeHere(player)
    player.moveTo(self)
    typeFor(player).action(player, self)
  end

  def typeFor(player)
    return Type::EMPTY if (@owner == nil)
    return Type::OWNER if (@owner == player)
  end

  def upgrade
    @level += 1
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

  module Level
    EMPTY = 0
    THATCH = 1
    FOREIGN_STYLE = 2
    SKYSCRAPER = 3
  end
end