class Game
  attr_reader :map

  def initialize(map)
    @map = map
    @players = []
  end

  def initPlayers(*players)
    @players = players
    @players.each {
      |player| player.moveTo(map.starting)
    }
    @players[0].inTurn
    map.initPlayers(@players)
    @currentPlayerIndex = 0
  end

  def currentPlayer
    return @players[@currentPlayerIndex]
  end
end