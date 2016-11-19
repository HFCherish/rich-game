class Tool
  attr_reader :value

  def initialize(value)
    @value = value
  end

  BLOCK = Tool.new(50)
  def passOn(place, player)
    place.removeTool
    return place.comeHere(player)
  end

  BOMB = Tool.new(50)
  ROBOT_DULL = Tool.new(30)
end