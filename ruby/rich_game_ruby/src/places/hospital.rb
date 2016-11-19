require_relative 'place'
class Hospital < Place
  HOSPITAL_DAYS = 3

  def comeHere(player)
    player.moveTo(self)
    return player.endTurn
  end
end