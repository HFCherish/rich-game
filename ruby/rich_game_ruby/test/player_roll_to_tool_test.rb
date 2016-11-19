require 'minitest/autorun'
require 'minitest/mock'
require '../src/commands/command_factory'
require '../src/player'
require '../src/places/prison'
require '../src/places/hospital'
require '../src/assistencePower/tool'
require '../src/game_map'

class PlayerRollToToolTest < Minitest::Test
  def setup
    @prison = Prison.new
    @hospital = Hospital.new
    @map = Minitest::Mock.new
    @map.expect(:move, @prison, [Object, Fixnum])
    @map.expect(:hospital, @hospital)
    @game = Minitest::Mock.new(@map)
    @game.expect(:map, @map)
    @game.expect(:map, @map)
    @dice = Minitest::Mock.new.expect(:next, 1)
    @rollCommand = CommandFactory.Roll(@dice)
  end

  def test_that_get_into_prison_and_end_turn_if_pass_block_on_a_prison
    player = Player::create_player_with_game_and_fund_and_command_state(@game)
    @prison.tool = Tool::BLOCK

    player.execute(@rollCommand)

    assert_equal @prison, player.currentPlace
    assert_equal player.status, Player::Status::WAIT_FOR_TURN
    assert player.isStuck
    assert_nil @prison.tool
  end

  def test_that_get_into_hospital_for_3_days_and_end_turn_if_pass_bomb
    player = Player::create_player_with_game_and_fund_and_command_state(@game)
    @prison.tool = Tool::BOMB

    player.execute(@rollCommand)

    assert_equal @hospital, player.currentPlace
    assert_equal player.status, Player::Status::WAIT_FOR_TURN
    assert player.isStuck
    assert_nil @prison.tool

    2.times {
      player.inTurn
      player.endTurn
      assert player.isStuck
    }

    player.inTurn
    player.endTurn
    refute player.isStuck
  end

end