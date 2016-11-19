require_relative 'place'
class Estate < Place
  attr_reader :owner
  def initialize(emptyPrice)
    @emptyPrice = emptyPrice
    @owner = nil
  end

  def comeHere(player)
    player.moveTo(self)
    typeFor(player).action(player, self)
  end

  def typeFor(player)
    return Type::EMPTY if(@owner == nil)
  end

  class Type
    EMPTY = Type.new

    def EMPTY.action(player, estate)
      player.waitForResponse
    end

    def action(player, estate)
    end

  end

end