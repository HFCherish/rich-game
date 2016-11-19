require 'minitest/autorun'
require 'minitest/mock'
require '../src/commands/command_factory'
require '../src/player'
require '../src/places/estate'
require '../src/places/gift_house'
require '../src/assistencePower/tool'

class NonFinalCommandTest < Minitest::Test
  def setup
    @map = Minitest::Mock.new
    @map.expect(:move, @giftHouse, [Object, Fixnum])
    @map.expect(:setTool, true, [Object, Fixnum])
    @game = Minitest::Mock.new(@map)
    2.times {@game.expect(:map, @map)}
    @dice = Minitest::Mock.new.expect(:next, 1)
  end

  def test_that_can_use_tool_if_has
    player = Player::create_player_with_game_and_fund_and_command_state(@game)
    player.asset.addPoints(Tool::BLOCK.value)
    player.asset.buyTool(Tool::BLOCK)

    player.execute(CommandFactory.UseTool(Tool::BLOCK, 1))

    assert_equal player.status, Player::Status::WAIT_FOR_COMMAND
    refute player.asset.hasTool(Tool::BLOCK)
  end

  def test_that_can_sell_estate_if_has
    emptyPrice = 50
    player = Player::create_player_with_game_and_fund_and_command_state(@game, emptyPrice)
    estate = Estate.new(emptyPrice)
    player.asset.buyEstate(estate)
    estate.owner =player

    player.execute(CommandFactory.SellEstate(estate))

    assert_equal player.status, Player::Status::WAIT_FOR_COMMAND
    refute player.asset.hasEstate(estate)
    assert_equal player.asset.fund, emptyPrice * 2
  end

  def test_that_can_sell_estate_if_has
    player = Player::create_player_with_game_and_fund_and_command_state(@game)
    player.asset.addPoints(Tool::BLOCK.value)
    player.asset.buyTool(Tool::BLOCK)

    player.execute(CommandFactory.SellTool(Tool::BLOCK))

    assert_equal player.status, Player::Status::WAIT_FOR_COMMAND
    refute player.asset.hasTool(Tool::BLOCK)
  end

  def test_that_can_query
    player = Player::create_player_with_game_and_fund_and_command_state(@game)

    player.execute(CommandFactory::Query)

    assert_equal player.status, Player::Status::WAIT_FOR_COMMAND
  end

  def test_that_can_query
    player = Player::create_player_with_game_and_fund_and_command_state(@game)

    player.execute(CommandFactory::Help)

    assert_equal player.status, Player::Status::WAIT_FOR_COMMAND
  end

end