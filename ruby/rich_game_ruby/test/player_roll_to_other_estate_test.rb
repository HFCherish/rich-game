require 'minitest/autorun'
require 'minitest/mock'
require '../src/commands/command_factory'
require '../src/player'
require '../src/places/estate'

class PlayerRollToOtherEstateTest < Minitest::Test
  EMPTY_PRICE = 200

  def setup
    @otherEstate = Estate.new(EMPTY_PRICE)
    @map = Minitest::Mock.new
    @map.expect(:move, @otherEstate, [Object, Fixnum])
    @game = Minitest::Mock.new(@map)
    @game.expect(:map, @map)
    @dice = Minitest::Mock.new.expect(:next, 1)
  end

  def test_that_charge_and_end_turn_if_has_enough_money
    player = Player::create_player_with_game_and_fund_and_command_state(@game, EMPTY_PRICE)
    anotherPlayer = Player::create_player_with_game_and_fund_and_command_state(@game, 0)
    @otherEstate.owner = anotherPlayer
    @otherEstate.upgrade

    @rollCommand = CommandFactory.Roll(@dice)
    player.execute(@rollCommand)

    assert_equal @otherEstate, player.currentPlace
    assert_equal player.status, Player::Status::WAIT_FOR_TURN
    assert_equal player.asset.fund, 0
    assert_equal anotherPlayer.asset.fund, EMPTY_PRICE
  end
  #
  # def test_that_end_turn_if_not_has_enough_money
  #   player = Player::create_player_with_game_and_fund_and_command_state(@game, EMPTY_PRICE - 1)
  #   @otherEstate.owner = player
  #
  #   @rollCommand = CommandFactory.Roll(@dice)
  #   player.execute(@rollCommand)
  #
  #   assert_equal player.status, Player::Status::WAIT_FOR_TURN
  # end
  #
  # def test_that_end_turn_if_has_enough_money_but_estate_highest_level
  #   player = Player::create_player_with_game_and_fund_and_command_state(@game, EMPTY_PRICE)
  #   @otherEstate.owner = player
  #   @otherEstate.upgrade
  #   @otherEstate.upgrade
  #   @otherEstate.upgrade
  #
  #   @rollCommand = CommandFactory.Roll(@dice)
  #   player.execute(@rollCommand)
  #
  #   assert_equal player.status, Player::Status::WAIT_FOR_TURN
  # end
  #
  # def test_that_upgrade_estate_if_sayYes
  #   player = Player::create_player_with_game_and_fund_and_command_state(@game, EMPTY_PRICE)
  #   @otherEstate.owner = player
  #
  #   @rollCommand = CommandFactory.Roll(@dice)
  #   player.execute(@rollCommand)
  #
  #   player.execute(CommandFactory::Yes)
  #
  #   assert_equal player.status, Player::Status::WAIT_FOR_TURN
  #   assert_equal player.asset.fund, 0
  #   assert_equal @otherEstate.level, Estate::Level::THATCH
  # end
  #
  # def test_that_not_upgrade_estate_if_say_no
  #   player = Player::create_player_with_game_and_fund_and_command_state(@game, EMPTY_PRICE)
  #   @otherEstate.owner = player
  #
  #   @rollCommand = CommandFactory.Roll(@dice)
  #   player.execute(@rollCommand)
  #
  #   player.execute(CommandFactory::No)
  #
  #   assert_equal player.status, Player::Status::WAIT_FOR_TURN
  #   assert_equal player.asset.fund, EMPTY_PRICE
  #   assert_equal @otherEstate.level, Estate::Level::EMPTY
  # end

end