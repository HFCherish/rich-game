require 'minitest/autorun'
require 'minitest/mock'
require '../src/commands/command_factory'
require '../src/player'
require '../src/places/estate'

class PlayerRollToOwnEstateTest < Minitest::Test
  EMPTY_PRICE = 200

  def setup
    @ownEstate = Estate.new(EMPTY_PRICE)
    @map = Minitest::Mock.new
    @map.expect(:move, @ownEstate, [Object, Fixnum])
    @game = Minitest::Mock.new(@map)
    @game.expect(:map, @map)
    @dice = Minitest::Mock.new.expect(:next, 1)
  end

  def test_that_wait_for_response_if_has_enough_money
    player = Player::create_player_with_game_and_fund_and_command_state(@game, EMPTY_PRICE + 1)
    @ownEstate.owner = player

    @rollCommand = CommandFactory.Roll(@dice)
    player.execute(@rollCommand)

    assert_equal player.status, Player::Status::WAIT_FOR_RESPONSE
    assert_kind_of UpgradeEstate, player.lastResponsiveCommand
  end

  def test_that_end_turn_if_not_has_enough_money
    player = Player::create_player_with_game_and_fund_and_command_state(@game, EMPTY_PRICE - 1)
    @ownEstate.owner = player

    @rollCommand = CommandFactory.Roll(@dice)
    player.execute(@rollCommand)

    assert_equal player.status, Player::Status::WAIT_FOR_TURN
  end

  def test_that_upgrade_estate_if_sayYes
    player = Player::create_player_with_game_and_fund_and_command_state(@game, EMPTY_PRICE)
    @ownEstate.owner = player

    @rollCommand = CommandFactory.Roll(@dice)
    player.execute(@rollCommand)

    player.execute(CommandFactory::Yes)

    assert_equal player.status, Player::Status::WAIT_FOR_TURN
    assert_equal player.asset.fund, 0
    assert_equal @ownEstate.level, Estate::Level::THATCH
  end

  def test_that_not_upgrade_estate_if_say_no
    player = Player::create_player_with_game_and_fund_and_command_state(@game, EMPTY_PRICE)
    @ownEstate.owner = player

    @rollCommand = CommandFactory.Roll(@dice)
    player.execute(@rollCommand)

    player.execute(CommandFactory::No)

    assert_equal player.status, Player::Status::WAIT_FOR_TURN
    assert_equal player.asset.fund, EMPTY_PRICE
    assert_equal @ownEstate.level, Estate::Level::EMPTY
  end

end