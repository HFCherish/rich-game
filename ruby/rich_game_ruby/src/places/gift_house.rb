require_relative 'place'
require_relative '../commands/command_factory'
class GiftHouse < Place
  def initialize(*gift)
    @gifts = gift
  end

  def comeHere(player)
    player.moveTo(self)
    # return player.endTurn if !canAffordWith(player.asset.points)
    player.lastResponsiveCommand = CommandFactory::SelectGift
    return player.waitForResponse
  end

end