require_relative 'command'
class UseTool < Command
  def initialize(tool, steps)
    @steps = steps
    @tool = tool
  end

  def execute(player)
    if (player.asset.hasTool(@tool)) then
      player.asset.useTool(@tool) if @tool.useBy(player, @steps)
    end
    return player.waitForCommand
  end
end