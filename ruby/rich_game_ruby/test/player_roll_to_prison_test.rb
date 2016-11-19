require 'minitest/autorun'
require 'minitest/mock'
require '../src/commands/command_factory'
require '../src/player'
require '../src/places/estate'
require '../src/places/prison'
require '../src/assistencePower/tool'

class PlayerRollToPrisonTest < Minitest::Test
  def setup
    @prison = Prison.new
    @map = Minitest::Mock.new
    @map.expect(:move, @prison, [Object, Fixnum])
    @game = Minitest::Mock.new(@map)
    @game.expect(:map, @map)
    @dice = Minitest::Mock.new.expect(:next, 1)
    @rollCommand = CommandFactory.Roll(@dice)
  end

  def test_that_get_into_prison_and_end_turn
    player = Player::create_player_with_game_and_fund_and_command_state(@game)

    player.execute(@rollCommand)

    assert_equal @prison, player.currentPlace
    assert_equal player.status, Player::Status::WAIT_FOR_TURN
    assert player.isStuck
  end

  # def test_that_get_lucky_god_is_valid_for_5_turn
  #   player = Player::create_player_with_game_and_fund_and_command_state(@game)
  #
  #   player.getLuckyGod
  #   player.endTurn
  #   assert player.isLucky
  #
  #   4.times {player.inTurn
  #   player.endTurn
  #   assert player.isLucky}
  #
  #   player.inTurn
  #   player.endTurn
  #   refute player.isLucky
  # end


end