require '../src/asset'
require '../src/command'
require '../src/player'
require 'minitest/autorun'

class PlayerTest < MiniTest::Test
  def test_that_player_will_execute_the_current_command_if_waiting_for_command
    game = MiniTest::Mock.new
    command = Command.new
    def command.execute(player)
      puts "I'm in stub command"
      return Player::Status::WAIT_FOR_RESPONSE
    end

    player = Player.create_player_with_game_and_fund_and_command_state(game)

    assert_equal player.status, Player::Status::WAIT_FOR_COMMAND

    player.execute(command)

    assert_equal player.status, Player::Status::WAIT_FOR_RESPONSE

  end
end