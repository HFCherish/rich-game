require 'minitest/autorun'
require 'minitest/mock'
require '../src/commands/command_factory'
require '../src/player'
require '../src/places/estate'
require '../src/places/tool_house'
require '../src/assistencePower/tool'

class PlayerRollToToolHouseTest < Minitest::Test
  def setup
    @toolHouse = ToolHouse.new(Tool::BLOCK, Tool::BOMB, Tool::ROBOT_DULL)
    @map = Minitest::Mock.new
    @map.expect(:move, @toolHouse, [Object, Fixnum])
    @game = Minitest::Mock.new(@map)
    @game.expect(:map, @map)
    @dice = Minitest::Mock.new.expect(:next, 1)
  end

  def test_that_wait_for_response_if_has_enough_points
    player = Player::create_player_with_game_and_fund_and_command_state(@game)
    player.asset.addPoints(Tool::BOMB.value)

    @rollCommand = CommandFactory.Roll(@dice)
    player.execute(@rollCommand)

    assert_equal @toolHouse, player.currentPlace
    assert_equal player.status, Player::Status::WAIT_FOR_RESPONSE
    assert_equal player.asset.points, Tool::BOMB.value
    assert_kind_of BuyTool, player.lastResponsiveCommand
  end

  def test_that_end_turn_if_has_no_enough_points
    player = Player::create_player_with_game_and_fund_and_command_state(@game)

    @rollCommand = CommandFactory.Roll(@dice)
    player.execute(@rollCommand)

    assert_equal @toolHouse, player.currentPlace
    assert_equal player.status, Player::Status::WAIT_FOR_TURN
  end

  def test_that_buy_tool_and_wait_for_response_after_select_tool_and_still_has_points
    player = Player::create_player_with_game_and_fund_and_command_state(@game)
    player.asset.addPoints(Tool::BOMB.value * 2)

    @rollCommand = CommandFactory.Roll(@dice)
    player.execute(@rollCommand)

    player.execute(CommandFactory.GetHouseProduct(Tool::BOMB))

    assert_equal @toolHouse, player.currentPlace
    assert_equal player.status, Player::Status::WAIT_FOR_RESPONSE
    assert_equal player.asset.points, Tool::BOMB.value
    assert_equal player.asset.hasTool(Tool::BOMB), true
    assert_kind_of BuyTool, player.lastResponsiveCommand
  end

  def test_that_buy_tool_and_end_turn_after_select_tool_and_has_no_enough_points
    player = Player::create_player_with_game_and_fund_and_command_state(@game)
    player.asset.addPoints(Tool::BOMB.value)

    @rollCommand = CommandFactory.Roll(@dice)
    player.execute(@rollCommand)

    player.execute(CommandFactory.GetHouseProduct(Tool::BOMB))

    assert_equal @toolHouse, player.currentPlace
    assert_equal player.status, Player::Status::WAIT_FOR_TURN
    assert_equal player.asset.points, 0
    assert_equal player.asset.hasTool(Tool::BOMB), true
    assert_kind_of BuyTool, player.lastResponsiveCommand
  end


end