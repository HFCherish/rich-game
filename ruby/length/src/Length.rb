class Length

  attr_accessor :quantity, :unit

  def initialize(quantity, unit)
    @quantity = quantity
    @unit = unit
  end

  def ==(otherObject)
    @unit.to_centimeters * @quantity == otherObject.unit.to_centimeters * otherObject.quantity
  end


end
require_relative 'unit.rb'

