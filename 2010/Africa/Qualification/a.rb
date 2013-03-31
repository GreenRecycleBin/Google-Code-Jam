#!/usr/bin/ruby

# https://code.google.com/codejam/contest/351101/dashboard

# Return [a, b] where array[a] + array[b] == value
# or [] if there is no such pair
def sum3(array, value)
  sorted_array_with_original_index = array.each_with_index.sort
  i, j = 0, sorted_array_with_original_index.size - 1

  while i < j
    sum = sorted_array_with_original_index[i].first +
      sorted_array_with_original_index[j].first

    if sum == value
      break
    elsif sum < value
      i += 1
    else
      j -= 1
    end
  end

  if i < j
    [sorted_array_with_original_index[i].last,
     sorted_array_with_original_index[j].last]
  else
    []
  end
end

n = ARGF.gets.to_i

n.times do |i|
  value = ARGF.gets.to_i
  num_items = ARGF.gets.to_i
  array = ARGF.gets.split(' ').map(&:to_i)

  raise 'Wrong number of items' unless array.size == num_items

  indices = sum3(array, value).sort

  puts "Case ##{i + 1}: #{indices.first + 1} #{indices.last + 1}"
end
