#!/usr/bin/ruby -w

require 'set'

def fair_and_square_palindromes_up_to(n)
  fair_and_square_palindromes(palindromes_up_to Math.sqrt(n))
end

def palindromes_up_to(n)
  palindromes = Set.new 0..9

  num_digits_of_n = n.to_s.size

  1.upto(10 ** num_digits_of_n - 1) do |i|
    s = i.to_s
    ss = s + s.reverse

    ssi = ss.to_i

    break if ssi > n

    palindromes.add ssi

    sjsi = nil

    0.upto(9) do |j|
      sjs = s + j.to_s + s.reverse

      sjsi = sjs.to_i

      break if sjsi > n

      palindromes.add sjsi
    end

    break if sjsi > n
  end

  palindromes
end

def fair_and_square_palindromes(palindromes)
  result = Set[]

  palindromes.each do |p|
    pp = p ** 2

    if palindrome? pp
      result.add pp
    end
  end

  result
end

def palindrome?(n)
  s = n.to_s

  s == s.reverse
end

FAIR_AND_SQUARE_PALINDROMES = fair_and_square_palindromes_up_to(10 ** 14).delete 0

n = ARGF.gets.to_i

n.times do |i|
  a, b = ARGF.gets.split(' ').map(&:to_i)
  range = a..b

  puts "Case ##{i + 1}: #{FAIR_AND_SQUARE_PALINDROMES.count { |j| range.include? j }}"
end
