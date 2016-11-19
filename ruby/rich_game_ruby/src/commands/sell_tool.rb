require_relative 'command'
class SellTool < Command
  def initialize(tool)
    @tool = tool
  end

  def execute(player)
    if(player.asset.hasTool(@tool)) then
      player.asset.sellTool(@tool)
    end
    return player.waitForCommand
  end


end