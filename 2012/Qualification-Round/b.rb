#!/usr/bin/ruby

# https://code.google.com/codejam/contest/1460488/dashboard#s=p1

def can_achieve_best_score?(total_score, best_score, is_suprising_set_allowed)
  if best_score < 0
    return false
  end

  if total_score == 0 && best_score != 0
    return false
  end

  average = total_score / 3

  if best_score <= average
    return true
  end

  remainder = total_score % 3

  case remainder
  when 0
    return best_score - average == 1 && is_suprising_set_allowed

  when 1
    return best_score - average == 1

  when 2
    if is_suprising_set_allowed
      return 0 < best_score - average && best_score - average <= 2
    else
      return best_score - average == 1
    end
  end

  return false
end

def solve(num_people, num_suprising_sets, best_score, scores)
  count = 0

  scores.each do |total_score|
    if can_achieve_best_score?(total_score, best_score, false)
      count = count.next
    elsif num_suprising_sets > 0 && can_achieve_best_score?(total_score, best_score, true)
      count = count.next
      num_suprising_sets -= 1
    end
  end

  count
end

n = STDIN.gets.chomp.to_i

1.upto(n) do |id|
  input = STDIN.gets.chomp.split(' ').map! { |x| x.to_i }

  num_people = input[0]
  num_suprising_sets = input[1]
  best_score = input[2]
  scores = input[3...input.size]

  result = solve(num_people, num_suprising_sets, best_score, scores)

  puts "Case ##{id}: #{result}"
end
