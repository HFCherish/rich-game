require_relative '../../src/places/hospital'
class Tool
  attr_reader :value

  def initialize(value)
    @value = value
  end

  BLOCK = Tool.new(50)
  def BLOCK.passOn(place, player)
    place.removeTool
    return place.comeHere(player)
  end

  BOMB = Tool.new(50)
  def BOMB.passOn(place, player)
    place.removeTool
    player.stuckFor(Hospital::HOSPITAL_DAYS)
    return player.game.map.hospital.comeHere(player)
  end

  ROBOT_DULL = Tool.new(30)

  def passOn(place, player)
  end
end