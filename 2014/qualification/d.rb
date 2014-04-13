#!/usr/bin/ruby -w

# Assume both players sorts their blocks ascendingly
def play_war(naomi_blocks, ken_blocks)
  ken_blocks = ken_blocks.each_with_index.to_a
  naomi_point = 0

  until naomi_blocks.empty?
    naomi_block = naomi_blocks.shift

    # ken_block = [n, i] where n >= naomi_block and ken_blocks[i] == n
    # or nil
    ken_block = ken_blocks.bsearch { |n, _| n >= naomi_block }

    if ken_block
      ken_blocks.delete_at ken_block.last
    else
      ken_block = ken_blocks.shift
      naomi_point += 1
    end

    ken_block.last.upto(ken_blocks.size - 1) { |i| ken_blocks[i][1] -= 1}
  end

  naomi_point
end

def play_deceitful_war(naomi_blocks, ken_blocks)
  naomi_point = 0;

  until naomi_blocks.empty?
    while !naomi_blocks.empty? && naomi_blocks.first < ken_blocks.first
      naomi_blocks.shift
      ken_blocks.delete_at(ken_blocks.size - 1)
    end

    while !naomi_blocks.empty? && naomi_blocks.first > ken_blocks.first
      naomi_blocks.shift
      ken_blocks.shift
      naomi_point += 1
    end
  end

  naomi_point
end

n = ARGF.gets.to_i

n.times do |i|
  ARGF.gets
  naomi_blocks = ARGF.gets.split.map!(&:to_f)
  ken_blocks = ARGF.gets.split.map!(&:to_f)

  naomi_blocks.sort!
  ken_blocks.sort!

  naomi_point_play_deceitful_war = play_deceitful_war naomi_blocks.dup, ken_blocks.dup
  naomi_point_play_war = play_war naomi_blocks, ken_blocks

  puts "Case ##{i + 1}: #{naomi_point_play_deceitful_war} #{naomi_point_play_war}"
end
