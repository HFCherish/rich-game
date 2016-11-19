class Tool
  attr_reader :value

  def initialize(value)
    @value = value
  end

  BLOCK = Tool.new(50)
  BOMB = Tool.new(50)
  ROBOT_DULL = Tool.new(30)
end