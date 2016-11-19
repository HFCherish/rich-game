require_relative 'place'
class Prison < Place
  PRISON_DAYS = 2

  def comeHere(player)
    player.moveTo(self)
    player.stuckFor(PRISON_DAYS)
    return player.endTurn
  end
end