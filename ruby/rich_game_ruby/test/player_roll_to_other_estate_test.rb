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
    @game.expect(:nextPlayer, [])

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

  def test_that_bankrupt_if_not_has_enough_money
    @game.expect(:inform,[], [Object])
    player = Player::create_player_with_game_and_fund_and_command_state(@game, 0)
    anotherPlayer = Player::create_player_with_game_and_fund_and_command_state(@game, 0)
    @otherEstate.owner = anotherPlayer

    @rollCommand = CommandFactory.Roll(@dice)
    player.execute(@rollCommand)

    assert_equal @otherEstate, player.currentPlace
    assert_equal player.status, Player::Status::BANKRUPT
  end

  def test_that_end_turn_if_not_has_enough_money_but_with_lucky_god
    player = Player::create_player_with_game_and_fund_and_command_state(@game, 0)
    player.getLuckyGod
    anotherPlayer = Player::create_player_with_game_and_fund_and_command_state(@game, 0)
    @otherEstate.owner = anotherPlayer

    @rollCommand = CommandFactory.Roll(@dice)
    player.execute(@rollCommand)

    assert_equal @otherEstate, player.currentPlace
    assert_equal player.status, Player::Status::WAIT_FOR_TURN
    assert_equal player.asset.fund, 0
  end

  def test_that_end_turn_if_the_owner_of_estate_is_in_hospital_or_prison
    player = Player::create_player_with_game_and_fund_and_command_state(@game, 0)
    player.getLuckyGod
    anotherPlayer = Player::create_player_with_game_and_fund_and_command_state(@game, 0)
    @otherEstate.owner = anotherPlayer
    anotherPlayer.stuckFor(1)

    @rollCommand = CommandFactory.Roll(@dice)
    player.execute(@rollCommand)

    assert_equal @otherEstate, player.currentPlace
    assert_equal player.status, Player::Status::WAIT_FOR_TURN
    assert_equal player.asset.fund, 0
  end

end