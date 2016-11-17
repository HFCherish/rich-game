
class ParkingLot

  def initialize(capacity)
    @capacity = capacity
    @cars = Array.new
  end

  def isAvailable()
    @capacity - @cars.length > 0
  end

  def park(car)
    @cars << car
    return true
  end
end