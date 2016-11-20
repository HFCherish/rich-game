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
    if current_player.isBankrupt then
      self.nextPlayer
    else
      current_player.inTurn
      current_player.endTurn if current_player.isStuck
    end
  end


  def currentPlayer
    return @players[@currentPlayerIndex]
  end

end