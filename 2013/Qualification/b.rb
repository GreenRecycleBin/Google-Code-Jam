#!/usr/bin/ruby

# http://code.google.com/codejam/contest/2270488/dashboard#s=p1

def possible?(pattern)
  num_row = pattern.size
  num_column = pattern[0].size

  grid = Array.new(num_row) { |i| [pattern[i].max] * num_column }

  column_maximums = pattern[0].dup

  num_column.times do |j|
    (1...num_row).each do |i|
      column_maximums[j] = [column_maximums[j], pattern[i][j]].max
    end
  end

  num_row.times do |i|
    num_column.times do |j|
      grid[i][j] = column_maximums[j] if grid[i][j] > column_maximums[j]
    end
  end

  grid == pattern ? 'YES' : 'NO'
end

n = ARGF.gets.to_i

n.times do |i|
  num_row = ARGF.gets.split(' ').map(&:to_i).first
  pattern = []

  num_row.times do |j|
    pattern << ARGF.gets.split(' ').map(&:to_i)
  end

  puts "Case ##{i + 1}: #{possible? pattern}"
end
