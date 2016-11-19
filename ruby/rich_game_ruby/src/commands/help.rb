require_relative 'command'
class Help < Command

  def execute(player)

    return player.waitForCommand
  end
end