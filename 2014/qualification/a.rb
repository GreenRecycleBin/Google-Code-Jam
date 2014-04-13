#!/usr/bin/ruby -w

require 'set'

def perform_magic_trick(first_set, second_set)
  union_set = first_set & second_set

  if union_set.empty?
    'Volunteer cheated!'
  elsif union_set.size == 1
    union_set.first
  else
    'Bad magician!'
  end
end

def get_set
  row = ARGF.gets.to_i

  set = nil

  4.times do |i|
    if i + 1 == row
      set = Set.new ARGF.gets.split.map!(&:to_i)
    else
      ARGF.gets
    end
  end

  set
end

n = ARGF.gets.to_i

n.times do |i|
  result = perform_magic_trick get_set, get_set

  puts "Case ##{i + 1}: #{result}"
end
