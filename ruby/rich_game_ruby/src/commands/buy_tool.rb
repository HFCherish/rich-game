require_relative 'command'
class BuyTool < Command

  def respond(response, player)
    if(response.instance_of? GetHouseProduct) then
      player.asset.buyTool(response.product)
      return player.waitForResponse if(player.currentPlace.canAffordWith(player.asset.points))
      return player.endTurn
    end
  end
end