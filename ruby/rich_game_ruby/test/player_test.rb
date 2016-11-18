require '../src/asset'
require '../src/command'
require '../src/player'
require 'minitest/autorun'

class PlayerTest < MiniTest::Test
  def setup
    @game = MiniTest::Mock.new
  end

  def test_that_player_will_execute_the_current_command_if_waiting_for_command
    command = Command.new

    def command.execute(player)
      return Player::Status::WAIT_FOR_RESPONSE
    end

    player = Player.create_player_with_game_and_fund_and_command_state(@game)

    assert_equal player.status, Player::Status::WAIT_FOR_COMMAND

    player.execute(command)

    assert_equal player.status, Player::Status::WAIT_FOR_RESPONSE
  end

  def test_that_player_will_record_the_last_command_if_it_is_responsive
    command = Command.new

    def command.execute(player)
      player.lastResponsiveCommand = Command.new
      return Player::Status::WAIT_FOR_RESPONSE
    end

    player = Player.create_player_with_game_and_fund_and_command_state(@game)

    assert_equal player.status, Player::Status::WAIT_FOR_COMMAND
    assert_nil player.lastResponsiveCommand

    player.execute(command)

    assert_equal player.status, Player::Status::WAIT_FOR_RESPONSE
    refute_nil player.lastResponsiveCommand
  end

  def test_that_player_will_respond_to_last_responsive_command_if_waiting_for_response
    player = Player.create_player_with_game_and_fund_and_command_state(@game)

    command = Command.new

    def command.execute(player)
      responsiveCommand = Command.new
      player.lastResponsiveCommand = responsiveCommand
      def responsiveCommand.respond(response, aplayer)
        return Player::Status::WAIT_FOR_TURN
      end
      return Player::Status::WAIT_FOR_RESPONSE
    end

    player.execute(command)


    player.execute(Command.new)

    assert_equal player.status, Player::Status::WAIT_FOR_TURN
  end


end