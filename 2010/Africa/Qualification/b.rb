#!/usr/bin/ruby

# https://code.google.com/codejam/contest/351101/dashboard#s=p1

def reverse_words(s)
  s.split(' ').reverse.join(' ')
end

n = ARGF.gets.to_i

n.times do |i|
  s = ARGF.gets

  puts "Case ##{i + 1}: #{reverse_words(s)}"
end
