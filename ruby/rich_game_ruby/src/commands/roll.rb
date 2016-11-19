require_relative 'command'
class Roll < Command

  def initialize(dice)
    @dice = dice
  end

  def execute(player)
    targetPlace = player.game.map.move(player.currentPlace, @dice.next)
    return targetPlace.tool.passOn(targetPlace, player) if targetPlace.tool != nil
    return targetPlace.comeHere(player)
  end


end