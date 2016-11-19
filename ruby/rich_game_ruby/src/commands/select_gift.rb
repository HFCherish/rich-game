require_relative 'command'
class SelectGift < Command

  def respond(response, player)
    if (response.product != nil) then
      response.product.action(player)
    end
    return player.endTurn
  end
end