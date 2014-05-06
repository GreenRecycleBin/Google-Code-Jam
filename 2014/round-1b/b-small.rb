#!/usr/bin/ruby -w

def solve(a, b, k)
  a = a.times.to_a
  b = b.times.to_a
  k = 0...k

  a.product(b).map { |x, y| x & y }.count { |n| k.include? n }
end

t = ARGF.gets.to_i

t.times do |i|
  puts "Case ##{i + 1}: #{solve(*ARGF.gets.split.map(&:to_i))}"
end
