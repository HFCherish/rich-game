class Gift

  attr_reader :value

  def initialize(value)
    @value = value
  end

  LUCKY_GOD = Gift.new(5)
  def LUCKY_GOD.action(player)
    player.getLuckyGod
  end

  FUND = Gift.new(2000)
  def FUND.action(player)
    player.asset.addFunds(self.value)
  end

  POINT_CARD = Gift.new(200)
  def POINT_CARD.action(player)
    player.asset.addPoints(self.value)
  end

  def action(player)
  end
end