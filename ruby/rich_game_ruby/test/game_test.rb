require 'minitest/autorun'
require 'minitest/mock'
require '../src/game'
require '../src/places/starting'
require '../src/places/estate'
require '../src/player'
class GameTest < Minitest::Test

  def setup
    @starting = Starting.new
    @map = GameMap.new(2, 2, @starting, Estate.new(10), Estate.new(10), Estate.new(10))
  end

  def test_the_first_player_is_in_turn
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
end