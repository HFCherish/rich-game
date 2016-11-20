require_relative 'asset'
require_relative '../src/assistencePower/gift'
class Player
  attr_reader :game, :asset, :currentPlace
  attr_accessor :lastResponsiveCommand, :status


  def initialize(game, initialFund=10000)
    @game = game
    @asset = Asset.new(initialFund)
    @status = Status::WAIT_FOR_TURN
    @luckyDays = 0
    @stuckDays = 0
  end

  def moveTo(place)
    @currentPlace = place
  end

  def self.create_player_with_game_and_fund(game, initialFund = 10000)
    return Player.new(game, initialFund)
  end

  def self.create_player_with_game_and_fund_and_command_state(game, initialFund=10000)
    player = Player.new(game, initialFund)
    player.status = Status::WAIT_FOR_COMMAND
    return player
  end

  def execute(command)
    @status=@status.action(command, self)
  end

  def waitForResponse
    @status = Status::WAIT_FOR_RESPONSE
    return @status
  end

  def waitForCommand
    @status = Status::WAIT_FOR_COMMAND
    return @status
  end

  def inTurn
    @status = Status::WAIT_FOR_COMMAND
    return @status
  end

  def endTurn
    @luckyDays -= 1 if(@luckyDays > 0)
    @stuckDays -= 1 if(@stuckDays > 0)
    @status = Status::WAIT_FOR_TURN
    game.nextPlayer
    return @status
  end

  def bankrupt
    @status = Status::BANKRUPT
    return @status
  end

  def getLuckyGod
    @luckyDays = Gift::LUCKY_GOD.value + 1
  end

  def isLucky
    @luckyDays > 0
  end

  def stuckFor(days)
    @stuckDays = days + 1
  end

  def isStuck
    @stuckDays > 0
  end

  class Status
    WAIT_FOR_COMMAND = Status.new

    def WAIT_FOR_COMMAND.action(command, player)
      command.execute(player)
    end

    WAIT_FOR_RESPONSE = Status.new

    def WAIT_FOR_RESPONSE.action(command, player)
      return player.lastResponsiveCommand.respond(command, player)
    end

    WAIT_FOR_TURN = Status.new

    BANKRUPT = Status.new

    def action(command, player)
    end

  end

end