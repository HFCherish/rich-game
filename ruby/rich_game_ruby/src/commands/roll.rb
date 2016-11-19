require_relative 'command'
class Roll < Command

  def initialize(dice)
    @dice = dice
  end

  def execute(player)
    player.game.map.move(player.currentPlace, @dice.next).comeHere(player)
  end


end