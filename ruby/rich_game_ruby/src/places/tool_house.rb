require_relative 'place'
require_relative '../commands/command_factory'
class ToolHouse < Place
  def initialize(*tool)
    @tools = tool
  end

  def comeHere(player)
    player.moveTo(self)
    # player.endTurn if !canAffordWith(player.asset.points)
    player.lastResponsiveCommand = CommandFactory::BuyTool
    player.waitForResponse
  end


end