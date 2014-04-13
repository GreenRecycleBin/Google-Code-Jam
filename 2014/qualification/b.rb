#!/usr/bin/ruby -w

COOKIES_PER_SECOND = 2
DECIMAL_PLACES = 7

def solve(c, f, x)
  total_time = 0.0

  r = COOKIES_PER_SECOND
  current_time_no_buy = x / r
  time_to_buy = c / r
  next_time_no_buy = x / (r + f)

  until current_time_no_buy <= time_to_buy + next_time_no_buy do
    total_time += time_to_buy
    r += f

    current_time_no_buy = next_time_no_buy
    time_to_buy = c / r
    next_time_no_buy = x / (r + f)
  end

  total_time += current_time_no_buy
end

n = ARGF.gets.to_i

n.times do |i|
  result = solve(* ARGF.gets.split.map!(&:to_f))

  puts "Case ##{i + 1}: #{result.round(DECIMAL_PLACES)}"
end
