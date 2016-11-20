require 'minitest/autorun'
require 'minitest/mock'
require '../src/commands/command_factory'
require '../src/player'
require '../src/places/mineral'
require '../src/assistencePower/tool'

class PlayerRollToMineralTest < Minitest::Test
  def setup
    @mineral = Mineral.new(10)
    @map = Minitest::Mock.new
    @map.expect(:move, @mineral, [Object, Fixnum])
    @game = Minitest::Mock.new(@map)
    @game.expect(:map, @map)
    @game.expect(:nextPlayer, [])
    @dice = Minitest::Mock.new.expect(:next, 1)
    @rollCommand = CommandFactory.Roll(@dice)
  end

  def test_that_end_turn
    player = Player::create_player_with_game_and_fund_and_command_state(@game)

    player.execute(@rollCommand)

    assert_equal @mineral, player.currentPlace
    assert_equal player.status, Player::Status::WAIT_FOR_TURN
    assert_equal player.asset.points, @mineral.value
  end

end