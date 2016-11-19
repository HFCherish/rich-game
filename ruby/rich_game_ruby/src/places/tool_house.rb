require_relative 'place'
require_relative '../commands/command_factory'
class ToolHouse < Place
  def initialize(*tool)
    @tools = tool
  end

  def comeHere(player)
    player.moveTo(self)
    return player.endTurn if !canAffordWith(player.asset.points)
    player.lastResponsiveCommand = CommandFactory::BuyTool
    return player.waitForResponse
  end

  def canAffordWith(points)
    @tools.each {|tool| return true if tool.value <= points}
    return false
  end


end