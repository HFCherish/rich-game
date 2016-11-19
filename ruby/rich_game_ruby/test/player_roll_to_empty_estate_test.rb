require 'minitest/autorun'
require 'minitest/mock'
require '../src/commands/command_factory'
require '../src/player'
require '../src/places/estate'

class PlayerRollToEmptyEstateTest < Minitest::Test
  EMPTY_PRICE = 200

  def setup
    @emptyEstate = Estate.new(EMPTY_PRICE)
    @map = Minitest::Mock.new
    @map.expect(:move, @emptyEstate, [Object, Fixnum])
    @game = Minitest::Mock.new(@map)
    @game.expect(:map, @map)
    @dice = Minitest::Mock.new.expect(:next,1)
  end

  def test_that_wait_for_response_if_has_enough_money
    player = Player::create_player_with_game_and_fund_and_command_state(@game, EMPTY_PRICE + 1)

    @rollCommand = CommandFactory.Roll(@dice)
    player.execute(@rollCommand)

    assert_equal @emptyEstate, player.currentPlace
    assert_equal player.status, Player::Status::WAIT_FOR_RESPONSE
    assert_kind_of BuyEstate, player.lastResponsiveCommand
  end

  def test_that_end_turn_if_not_has_enough_money
    player = Player::create_player_with_game_and_fund_and_command_state(@game, EMPTY_PRICE - 1)

    @rollCommand = CommandFactory.Roll(@dice)
    player.execute(@rollCommand)

    assert_equal player.status, Player::Status::WAIT_FOR_TURN
  end

  def test_that_buy_estate_if_sayYes
    player = Player::create_player_with_game_and_fund_and_command_state(@game, EMPTY_PRICE)

    @rollCommand = CommandFactory.Roll(@dice)
    player.execute(@rollCommand)

    player.execute(CommandFactory::Yes)

    assert_equal player.status, Player::Status::WAIT_FOR_TURN
    assert_equal player.asset.fund, 0
    assert_equal player.asset.estates.length, 1
    assert_equal @emptyEstate.typeFor(player), Estate::Type::OWNER
  end

  def test_that_not_buy_estate_if_say_no
    player = Player::create_player_with_game_and_fund_and_command_state(@game, EMPTY_PRICE)

    @rollCommand = CommandFactory.Roll(@dice)
    player.execute(@rollCommand)

    player.execute(CommandFactory::No)

    assert_equal player.status, Player::Status::WAIT_FOR_TURN
    assert_equal player.asset.fund, EMPTY_PRICE
    assert_equal player.asset.estates.length, 0
    assert_equal @emptyEstate.typeFor(player), Estate::Type::EMPTY
  end

end