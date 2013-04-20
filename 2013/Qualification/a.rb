#!/usr/bin/ruby

# http://code.google.com/codejam/contest/2270488/dashboard

def calculate_status(board)
  ['X', 'O'].each do |symbol|
    has_four_on_diagonals = [true] * 2

    4.times do |i|
      has_four_on_horizontal = true
      has_four_on_vertical = true

      4.times do |j|
        if board[i][j] != symbol && board[i][j] != 'T'
          has_four_on_horizontal = false
        end

        if board[j][i] != symbol && board[j][i] != 'T'
          has_four_on_vertical = false
        end
      end

      return "#{symbol} won" if has_four_on_horizontal || has_four_on_vertical

      if board[i][i] != symbol && board[i][i] != 'T'
        has_four_on_diagonals[0] = false
      end

      if board[i][3 - i] != symbol && board[i][3 - i] != 'T'
        has_four_on_diagonals[1] = false
      end
    end

    return "#{symbol} won" if has_four_on_diagonals.any?
  end

  if board.any? do |row|
      row.each_char.any? { |symbol| symbol == '.' }
    end
    return "Game has not completed"
  end

  "Draw"
end

n = ARGF.gets.to_i

n.times do |i|
  board = 4.times.with_object([]) do |j, b|
    b << ARGF.gets.chomp
  end

  ARGF.gets

  puts "Case ##{i + 1}: #{calculate_status board}"
end
