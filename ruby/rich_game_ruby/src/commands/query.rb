require_relative 'command'
class Query < Command

  def execute(player)
    player.asset.reportAsString
    return player.waitForCommand
  end
end