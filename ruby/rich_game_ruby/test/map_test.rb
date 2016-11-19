require 'minitest/autorun'
require 'minitest/mock'
require '../src/places/starting'
require '../src/places/estate'
require '../src/game_map'
require '../src/assistencePower/tool'

class MapTest < Minitest::Test
  def test_that_move_to_another_place
    starting = Starting.new
    estate = Estate.new(10)
    estate1 = Estate.new(10)
    estate2 = Estate.new(10)

    map = GameMap.new(2, 2, starting, estate, estate1, estate2)
    assert_equal map.move(starting, 1), estate

    assert_equal map.move(starting, 5), estate

    assert_equal map.move(starting, -1), estate2

    assert_equal map.move(starting, -5), estate2
  end

  def test_that_stop_if_pass_block_or_bomb_when_move
    starting = Starting.new
    estate = Estate.new(10)
    estate.tool = Tool::BLOCK
    estate1 = Estate.new(10)
    estate2 = Estate.new(10)

    map = GameMap.new(2, 2, starting, estate, estate1, estate2)

    assert_equal map.move(starting, 3), estate

    assert_equal map.move(starting, -5), estate
  end

  def test_that_can_set_block_or_bomb_on_map
    starting = Starting.new
    estate = Estate.new(10)
    estate1 = Estate.new(10)
    estate2 = Estate.new(10) 

    map = GameMap.new(2, 2, starting, estate, estate1, estate2)
    game = Minitest::Mock.new
    player = Player.create_player_with_game_and_fund_and_command_state(game)
    player.moveTo(estate1)
    map.initPlayers(player)

    refute map.setTool(Tool::BOMB, starting, 2)

    refute map.setTool(Tool::BOMB, starting, 15)

    assert map.setTool(Tool::BOMB, starting, 1)

    refute map.setTool(Tool::BOMB, starting, 1)

    assert map.setTool(Tool::BLOCK, starting, -5)

    refute_nil estate.tool
    refute_nil estate2.tool
  end

  def test_that_can_set_block_or_bomb_on_map
    starting = Starting.new
    estate = Estate.new(10)
    estate1 = Estate.new(10)
    estate2 = Estate.new(10)

    map = GameMap.new(2, 2, starting, estate, estate1, estate2)

    assert map.useRobot(starting)

  end


end