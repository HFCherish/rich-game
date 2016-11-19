require_relative 'place'
class Starting < Place

  def comeHere(player)
    player.moveTo(self)
    return player.endTurn
  end
end