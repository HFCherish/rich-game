class Unit
  def initialize(centimeters)
    @centimeters = centimeters
  end

  M = Unit.new(100.0)
  DM = Unit.new(10.0)

  def to_centimeters
    @centimeters
  end
end