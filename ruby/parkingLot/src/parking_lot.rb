
class ParkingLot

  def initialize(capacity)
    @capacity = capacity
    @cars = Array.new
  end

  def isAvailable()
    @capacity - @cars.length > 0
  end

  def park(car)
    return false if !isAvailable
    @cars << car
    return true
  end

  def unpark(car)
    @cars.delete(car) {return false}
    return true
  end
end