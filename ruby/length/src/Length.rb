class Length

  attr_accessor :unit, :quantity, :_unit

  def initialize(quantity, unit)
    @quantity = quantity
    @unit = unit
    @_unit = Unit.fromUnit(unit)
  end

  def ==(otherObject)
    @_unit.to_centimeters * @quantity == otherObject._unit.to_centimeters * otherObject.quantity
  end

  class Unit
    def initialize(centimeters)
      @centimeters = centimeters
    end

    M = Unit.new(100.0)
    DM = Unit.new(10.0)

    def to_centimeters
      @centimeters
    end

    def Unit.fromUnit(unit)
      return M if unit.downcase == "m"
      return DM if unit.downcase == "dm"
    end
  end

end