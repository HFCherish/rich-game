require 'minitest/autorun'
require '../src/places/starting'
require '../src/places/estate'
require '../src/game_map'
require '../src/assistencePower/tool'

class MapTest < Minitest::Test
  def test_that_move_to_another_place
    @starting = Starting.new
    @estate = Estate.new(10)
    @estate1 = Estate.new(10)
    @estate2 = Estate.new(10)

    @map = GameMap.new(2, 2, @starting, @estate, @estate1, @estate2)
    assert_equal @map.move(@starting, 1), @estate

    assert_equal @map.move(@starting, 5), @estate

    assert_equal @map.move(@starting, -1), @estate2

    assert_equal @map.move(@starting, -5), @estate2
  end

  def test_that_stop_if_pass_block_or_bomb_when_move
    @starting = Starting.new
    @estate = Estate.new(10)
    @estate.tool = Tool::BLOCK
    @estate1 = Estate.new(10)
    @estate2 = Estate.new(10)

    @map = GameMap.new(2, 2, @starting, @estate, @estate1, @estate2)

    assert_equal @map.move(@starting, 3), @estate

    assert_equal @map.move(@starting, -5), @estate
  end


end