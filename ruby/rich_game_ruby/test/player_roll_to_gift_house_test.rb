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
    @game.expect(:nextPlayer, [])
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

  def test_that_get_lucky_god_and_end_turn_after_select
    player = Player::create_player_with_game_and_fund_and_command_state(@game)

    player.execute(@rollCommand)

    player.execute(CommandFactory.GetHouseProduct(Gift::LUCKY_GOD))

    assert_equal @giftHouse, player.currentPlace
    assert_equal player.status, Player::Status::WAIT_FOR_TURN
    assert player.isLucky
  end

  def test_that_get_points_and_end_turn_after_select
    player = Player::create_player_with_game_and_fund_and_command_state(@game)

    player.execute(@rollCommand)

    player.execute(CommandFactory.GetHouseProduct(Gift::POINT_CARD))

    assert_equal @giftHouse, player.currentPlace
    assert_equal player.status, Player::Status::WAIT_FOR_TURN
    assert_equal player.asset.points, Gift::POINT_CARD.value
  end

  def test_that_get_lucky_god_is_valid_for_5_turn
    5.times {@game.expect(:nextPlayer, [])}
    player = Player::create_player_with_game_and_fund_and_command_state(@game)

    player.getLuckyGod
    player.endTurn
    assert player.isLucky

    4.times {player.inTurn
    player.endTurn
    assert player.isLucky}

    player.inTurn
    player.endTurn
    refute player.isLucky
  end


end