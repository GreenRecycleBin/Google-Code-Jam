#!/usr/bin/ruby -v

MAX_L = 40

def num_flicks(initials, targets, n, l)
  first = targets.first
  min_mask_count_1s = MAX_L + 1

  initials.each do |initial|
    mask = first ^ initial
    actuals = initials.map { |outlet| outlet ^ mask }

    if actuals.sort! == targets
      mask_count_1s = count_1s mask

      min_mask_count_1s = mask_count_1s if mask_count_1s < min_mask_count_1s
    end
  end

  if min_mask_count_1s == MAX_L + 1
    -1
  else
    min_mask_count_1s
  end
end

def count_1s(n)
  count = 0

  while n > 0
    n &= n - 1
    count += 1
  end

  count
end

t = ARGF.gets.to_i

t.times do |i|
  n, l = ARGF.gets.split.map(&:to_i)

  initials = ARGF.gets.split.map { |s| Integer(s, 2) }
  targets = ARGF.gets.split.map { |s| Integer(s, 2) }

  result = num_flicks initials, targets.sort, n, l

  print "Case ##{i + 1}: "

  puts result == -1 ? 'NOT POSSIBLE' : result
end
