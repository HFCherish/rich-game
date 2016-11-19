require_relative 'roll'
class CommandFactory
  def self.Roll(dice)
    return Roll.new(dice)
  end
end