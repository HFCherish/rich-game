require_relative 'place'
class Mineral < Place

  attr_reader :value

  def initialize(value)
    @value = value
  end

  def comeHere(player)
    player.moveTo(self)
    player.asset.addPoints(@value)
    return player.endTurn
  end
end