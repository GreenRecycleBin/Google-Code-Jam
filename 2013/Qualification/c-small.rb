#!/usr/bin/ruby -w

require 'set'

def num_fair_and_square_palindromes(a, b)
  a.upto(b).count { |i| fair_and_square_palindrome? i }
end

def fair_and_square_palindrome?(n)
  square?(n) && palindrome?(n) && palindrome?(Math.sqrt(n).to_i)
end

def square?(n)
  sqrt = Math.sqrt(n).to_i

  n == sqrt ** 2
end

def palindrome?(n)
  s = n.to_s

  s == s.reverse
end

n = ARGF.gets.to_i

n.times do |i|
  a, b = ARGF.gets.split(' ').map(&:to_i)

  puts "Case ##{i + 1}: #{num_fair_and_square_palindromes a, b}"
end
