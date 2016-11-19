require_relative 'place'
class MagicHouse < Place

  def comeHere(player)
    player.moveTo(self)
    return player.endTurn
  end
end