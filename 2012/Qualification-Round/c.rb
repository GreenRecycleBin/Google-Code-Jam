#!/usr/bin/ruby

# https://code.google.com/codejam/contest/1460488/dashboard#s=p2

def permute(s)
  permutation = [s.to_i]

  (s.size - 1).times do
    tmp = s[1..s.size - 1] << s[0]
    permutation << tmp.to_i
    s = tmp
  end

  permutation.uniq.sort
end

permutation_map = {}

n = gets.chomp.to_i

1.upto(n) do |id|
  input = gets.chomp.split(' ')
  a = input[0].to_i
  b = input[1].to_i

  a.upto(b) do |i|
    next if permutation_map[i] != nil

    permutations = permute(i.to_s)
    permutations.delete_if { |permutation| permutation < i }
    permutation_map[i] = permutations
    permutations.each { |x| permutation_map[x] = permutations }
  end

  sum = 0

  a.upto(b) do |j|
    permutations = permutation_map[j]
    start_index = permutations.index(j)
    end_index = permutations.index { |x| x > b }
    end_index = permutations.size if end_index == nil
    size = end_index - start_index
    sum += size - 1 if size > 1
  end

  puts "Case ##{id}: #{sum}"
end
