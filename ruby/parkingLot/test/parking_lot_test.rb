require 'minitest/autorun'
require '../src/parking_lot'
require '../src/car'

class ParkingLotTest < MiniTest::Test
  def test_that_can_park_when_available
    lot = ParkingLot.new(1)
    assert_equal lot.park(Car.new), true
    assert !lot.isAvailable
  end
end