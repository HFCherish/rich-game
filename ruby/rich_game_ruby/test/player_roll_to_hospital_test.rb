require 'minitest/autorun'
require 'minitest/mock'
require '../src/commands/command_factory'
require '../src/player'
require '../src/places/hospital'
require '../src/assistencePower/tool'

class PlayerRollToHospitalTest < Minitest::Test
  def setup
    @hospital = Hospital.new
    @map = Minitest::Mock.new
    @map.expect(:move, @hospital, [Object, Fixnum])
    @game = Minitest::Mock.new(@map)
    @game.expect(:map, @map)
    @game.expect(:nextPlayer, [])
    @dice = Minitest::Mock.new.expect(:next, 1)
    @rollCommand = CommandFactory.Roll(@dice)
  end

  def test_that_end_turn
    player = Player::create_player_with_game_and_fund_and_command_state(@game)

    player.execute(@rollCommand)

    assert_equal @hospital, player.currentPlace
    assert_equal player.status, Player::Status::WAIT_FOR_TURN
    refute player.isStuck
  end

end