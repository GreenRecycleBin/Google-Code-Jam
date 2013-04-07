#!/usr/bin/ruby

# https://code.google.com/codejam/contest/438101/dashboard

require 'set'

n = ARGF.gets.to_i

n.times do |i; set|
  ARGF.gets

  set = Set.new
  codes =  ARGF.gets.chomp.split(' ').map(&:to_i)

  codes.each { |code| set.add(code) unless set.delete? code }

  puts "Case ##{i + 1}: #{set.to_a.first}"
end
