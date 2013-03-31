#!/usr/bin/ruby

# https://code.google.com/codejam/contest/1460488/dashboard

char_to_char = {}

input = 'abcdefghijklmnopqrstuvwxyz '
output = 'yhesocvxduiglbkrztnwjpfmaq '

0.upto(input.size - 1) do |i|
  char_to_char[input[i].chr] = output[i].chr
end

n = STDIN.gets.chomp.to_i

1.upto(n) do |id|
  line = STDIN.gets.chomp

  print "Case #%d: " %[id]

  line.each_char do |c|
    print char_to_char[c]
  end

  puts ""
end
