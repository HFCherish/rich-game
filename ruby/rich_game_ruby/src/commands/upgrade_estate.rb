require_relative 'command'
require_relative 'command_factory'
class UpgradeEstate < Command
  def initialize(estate)
    @estate = estate
  end

  def respond(response, player)
    if response == CommandFactory::Yes
      player.asset.upgradeEstate(@estate)
      @estate.upgrade
    end
    player.endTurn
  end
  end