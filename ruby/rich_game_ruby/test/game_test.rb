require 'minitest/autorun'
require 'minitest/mock'
require '../src/game'
require '../src/places/starting'
require '../src/places/estate'
require '../src/player'
require '../src/game_map'
class GameTest < Minitest::Test

  def setup
    @starting = Starting.new
    @map = GameMap.new(2, 2, @starting, Estate.new(10), Estate.new(10), Estate.new(10))
  end

  def test_the_first_player_is_in_turn_and_the_starting_place_is_set
    game = Game.new(@map)
    player = Player.create_player_with_game_and_fund(game)
    player1 = Player.create_player_with_game_and_fund(game)
    game.initPlayers(player, player1)

    assert_equal game.currentPlayer, player
    assert_equal player.currentPlace, @starting
    assert_equal player1.currentPlace, @starting
    assert_equal player.status, Player::Status::WAIT_FOR_COMMAND
    assert_equal player1.status, Player::Status::WAIT_FOR_TURN
  end

  def test_that_can_let_the_current_player_execute_command
    game = Game.new(@map)
    player = Player.create_player_with_game_and_fund(game)
    player1 = Player.create_player_with_game_and_fund(game)
    game.initPlayers(player, player1)

    command = Minitest::Mock.new
    command.expect(:execute, Player::Status::WAIT_FOR_TURN, [Object])
    game.execute(command)

    assert_equal player.status, Player::Status::WAIT_FOR_TURN
  end

  def test_that_can_shift_to_next_player
    game = Game.new(@map)
    player = Player.create_player_with_game_and_fund(game)
    player1 = Player.create_player_with_game_and_fund(game)
    game.initPlayers(player, player1)

    game.nextPlayer()

    assert_equal game.currentPlayer, player1
    assert_equal player1.status, Player::Status::WAIT_FOR_COMMAND
  end

  def test_that_should_shift_to_next_player_if_currentPlayer_end_turn
    game = Game.new(@map)
    player = Player.create_player_with_game_and_fund(game)
    player1 = Player.create_player_with_game_and_fund(game)
    game.initPlayers(player, player1)

    player.endTurn

    assert_equal game.currentPlayer, player1
    assert_equal player1.status, Player::Status::WAIT_FOR_COMMAND
    assert_equal player.status, Player::Status::WAIT_FOR_TURN
  end

  def test_that_shift_to_player_after_next_player_if_next_player_bankrupt
    game = Game.new(@map)
    player = Player.create_player_with_game_and_fund(game)
    player1 = Player.create_player_with_game_and_fund(game)
    player2 = Player.create_player_with_game_and_fund(game)
    game.initPlayers(player, player1, player2)
    player1.bankrupt

    player.endTurn

    assert_equal game.currentPlayer, player2
    assert_equal player2.status, Player::Status::WAIT_FOR_COMMAND
    assert_equal player.status, Player::Status::WAIT_FOR_TURN
    assert_equal player1.status, Player::Status::BANKRUPT
  end

  def test_that_shift_to_player_after_next_player_if_next_player_in_hospital_or_prison
    game = Game.new(@map)
    player = Player.create_player_with_game_and_fund(game)
    player1 = Player.create_player_with_game_and_fund(game)
    player2 = Player.create_player_with_game_and_fund(game)
    game.initPlayers(player, player1, player2)
    player1.stuckFor(1)

    player.endTurn

    assert_equal game.currentPlayer, player2
    assert_equal player2.status, Player::Status::WAIT_FOR_COMMAND
    assert_equal player.status, Player::Status::WAIT_FOR_TURN
    assert_equal player1.status, Player::Status::WAIT_FOR_TURN
    refute player1.isStuck
  end

end