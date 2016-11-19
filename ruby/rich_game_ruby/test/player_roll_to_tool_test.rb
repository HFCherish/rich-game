require 'minitest/autorun'
require 'minitest/mock'
require '../src/commands/command_factory'
require '../src/player'
require '../src/places/prison'
require '../src/assistencePower/tool'

class PlayerRollToToolTest < Minitest::Test
  def setup
    @prison = Prison.new
    @map = Minitest::Mock.new
    @map.expect(:move, @prison, [Object, Fixnum])
    @game = Minitest::Mock.new(@map)
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

end