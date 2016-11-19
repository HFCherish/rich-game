require_relative 'asset'
class Player
  attr_reader :game, :asset, :currentPlace
  attr_accessor :lastResponsiveCommand, :status


  def initialize(game, initialFund=10000)
    @game = game
    @asset = Asset.new
    @asset.addFund(initialFund)
    @status = Status::WAIT_FOR_COMMAND
  end

  def moveTo(place)
    @currentPlace = place
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

    def action(command, player)
    end

  end
end