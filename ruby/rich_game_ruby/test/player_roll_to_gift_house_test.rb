require 'minitest/autorun'
require 'minitest/mock'
require '../src/commands/command_factory'
require '../src/player'
require '../src/places/estate'
require '../src/places/gift_house'
require '../src/assistencePower/tool'

class PlayerRollToGiftHouseTest < Minitest::Test
  def setup
    @giftHouse = GiftHouse.new(Gift::FUND, Gift::POINT_CARD, Gift::LUCKY_GOD)
    @map = Minitest::Mock.new
    @map.expect(:move, @giftHouse, [Object, Fixnum])
    @game = Minitest::Mock.new(@map)
    @game.expect(:map, @map)
    @dice = Minitest::Mock.new.expect(:next, 1)
    @rollCommand = CommandFactory.Roll(@dice)
  end

  def test_that_wait_for_response
    player = Player::create_player_with_game_and_fund_and_command_state(@game)

    player.execute(@rollCommand)

    assert_equal @giftHouse, player.currentPlace
    assert_equal player.status, Player::Status::WAIT_FOR_RESPONSE
    assert_kind_of SelectGift, player.lastResponsiveCommand
  end


end