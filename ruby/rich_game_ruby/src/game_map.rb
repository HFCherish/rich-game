class GameMap
  def initialize(height, width, *places)
    @height = height
    @width = width
    @places = places
  end

  def move(start, steps)
    startIndex = @places.index(start)
    direction = steps > 0 ? 1 : -1
    i = 0
    while i.abs <= @places.length && i.abs <= steps.abs
      nextIndex = nextIndex(startIndex, i)
      return @places[nextIndex] if(@places[nextIndex].tool != nil)
      i += direction
    end
    return @places[nextIndex(startIndex, steps)]
  end

  def setTool(tool, starting, steps)
    return false if steps.abs > 10
    target = @places[nextIndex(@places.index(starting), steps)]
    return false if target.tool != nil
    target.tool = tool
  end

  def useRobot(starting)

  end

  def nextIndex(startIndex, steps)
    return (@places.length + (startIndex+steps) % @places.length) % @places.length
  end

  private :nextIndex
end