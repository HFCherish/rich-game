require_relative 'command'
require_relative '../../src/game_help'
class Help < Command

  def execute(player)
    GameHelp.helpAsString
    return player.waitForCommand
  end
end