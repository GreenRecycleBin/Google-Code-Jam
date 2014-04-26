#!/usr/bin/ruby -w

require 'set'

OFFSETS = (-1..1).to_a.product (-1..1).to_a
OFFSETS.delete [0, 0]

def solve(num_rows, num_columns, num_bombs)
  i = 0

  bomb_locations_combination(num_rows, num_columns, num_bombs).each do |bomb_locations|
    matrix = generate_one_click_board(num_rows, num_columns, bomb_locations)

    if matrix
      matrix.map! do |row|
        row.map! do |cell|
          cell ||= '.'
        end
      end

      return matrix
    end

    i += 1
  end

  nil
end

def bomb_locations_combination(num_rows, num_columns, num_bombs)
  (0...num_rows).to_a.product((0...num_columns).to_a).combination(num_bombs)
end

def generate_one_click_board(num_rows, num_columns, bomb_locations)
  m = Array.new(num_rows) { Array.new(num_columns) }

  bomb_locations.each { |x, y| m[x][y] = '*' }

  (0...num_rows).to_a.product((0...num_columns).to_a) do |x, y|
    next if m[x][y] == '*' || (neighbor_to_bombs?(m, x, y) && num_rows * num_columns - bomb_locations.size > 1)

    new_m = copy_2d_array m
    is_all_revealed = click new_m, x, y, bomb_locations.size

    return new_m if is_all_revealed
  end

  nil
end

def copy_2d_array(m)
  new_m = []

  m.each { |row| new_m << row.dup }

  new_m
end

def click(m, cell_x, cell_y, num_bombs)
  num_unrevealed_cells = m.size * m[0].size - num_bombs

  m[cell_x][cell_y] = 'c'
  revealing_cells = Set[ [ cell_x, cell_y ] ]

  new_m = copy_2d_array m

  while num_unrevealed_cells > 0 && !revealing_cells.empty?
    cell = revealing_cells.first
    new_m[cell.first][cell.last] = 'o'

    revealing_cells = revealing_cells - [cell]

    unless neighbor_to_bombs?(m, cell.first, cell.last)
      OFFSETS.each do |x, y|
        new_x = cell.first + x

        if (0...m.size).include? new_x
          new_y = cell.last + y

          if (0...m[0].size).include? new_y
            revealing_cells = revealing_cells + [ [ new_x, new_y ] ] unless new_m[new_x][new_y]
          end
        end
      end
    end

    num_unrevealed_cells -= 1
  end

  num_unrevealed_cells.zero?
end

def neighbor_to_bombs?(m, cell_x, cell_y)
  OFFSETS.any? do |x, y|
    new_x = cell_x + x

    if (0...m.size).include? new_x
      new_y = cell_y + y

      if (0...m[0].size).include? new_y
        m[new_x][new_y] == '*'
      end
    end
  end
end

STDOUT.sync = true

n = ARGF.gets.to_i

n.times do |i|
  matrix = solve(* ARGF.gets.split.map!(&:to_i))

  puts "Case ##{i + 1}:"

  if matrix
    matrix.each do |row|
      row.each do |cell|
        print cell
      end

      puts
    end
  else
    puts 'Impossible'
  end
end
