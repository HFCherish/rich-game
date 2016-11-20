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

  def execute(command)
    return currentPlayer.execute(command)
  end

  def nextPlayer
    @currentPlayerIndex = (@currentPlayerIndex + 1) % @players.length
    current_player = currentPlayer
    current_player.inTurn
    return current_player
  end

  def currentPlayer
    return @players[@currentPlayerIndex]
  end
end