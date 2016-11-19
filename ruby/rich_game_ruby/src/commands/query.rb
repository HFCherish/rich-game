require_relative 'command'
class Query < Command

  def execute(player)
    return player.waitForCommand
  end
end