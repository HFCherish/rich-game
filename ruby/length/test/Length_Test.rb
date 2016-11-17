require'minitest/autorun'
require '../src/Length'

class LengthTest < Minitest::Test
  def test_that_length_with_same_quantity_and_unit_be_equal
    oneMeter = Length.new(1, Unit::M)
    anotherOneMeter = Length.new(1, Unit::M)
    assert_equal(oneMeter, anotherOneMeter)
  end

  def test_that_length_with_diff_quantity_and_diff_unit_be_equal_if_convert_equal
    oneMeter = Length.new(1, Unit::M)
    tenDelimeter = Length.new(10, Unit::DM)
    assert_equal(oneMeter, tenDelimeter)
  end
end