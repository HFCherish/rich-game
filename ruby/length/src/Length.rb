class Length

  attr_accessor :unit, :quantity

  def initialize(quantity, unit)
    @quantity = quantity
    @unit = unit
  end

  def ==(otherObject)
    return true if @quantity == otherObject.quantity and @unit == otherObject.unit
    if @unit == 'm' and otherObject.unit == 'dm'
      return @quantity * 100 == otherObject.quantity * 10
    end
    if @unit == 'dm' and otherObject.unit == 'm'
      return @quantity * 10 == otherObject.quantity * 100
    end
    return false
  end
end