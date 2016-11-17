require 'minitest/autorun'
require '../src/parking_lot'
require '../src/car'

class ParkingLotTest < MiniTest::Test
  def test_that_can_park_when_available
    lot = ParkingLot.new(1)
    assert_equal lot.park(Car.new), true
    assert !lot.isAvailable
  end

  def test_that_can_not_park_when_not_available
    lot = ParkingLot.new(0)
    assert_equal lot.park(Car.new), false
    assert !lot.isAvailable
  end

  def test_that_can_unpark_when_not_available
    lot = ParkingLot.new(1)
    car = Car.new
    lot.park(car)
    assert_equal lot.unpark(car), true
    assert lot.isAvailable
  end

end