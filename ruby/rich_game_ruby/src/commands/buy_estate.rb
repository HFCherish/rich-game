require_relative 'command'
require_relative 'command_factory'
class BuyEstate < Command
  def initialize(estate)
    @estate = estate
  end

  def respond(response, player)
    if response == CommandFactory::Yes
      player.asset.buyEstate(@estate)
      @estate.owner = player
      player.endTurn
    end
    end
  end