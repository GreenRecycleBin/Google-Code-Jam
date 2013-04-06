#!/usr/bin/ruby

# https://code.google.com/codejam/contest/351101/dashboard#s=p2

class MobileKeyboard
  CHAR_TO_KEY_PRESSES = {
    'a' => '2',
    'b' => '22',
    'c' => '222',
    'd' => '3',
    'e' => '33',
    'f' => '333',
    'g' => '4',
    'h' => '44',
    'i' => '444',
    'j' => '5',
    'k' => '55',
    'l' => '555',
    'm' => '6',
    'n' => '66',
    'o' => '666',
    'p' => '7',
    'q' => '77',
    'r' => '777',
    's' => '7777',
    't' => '8',
    'u' => '88',
    'v' => '888',
    'w' => '9',
    'x' => '99',
    'y' => '999',
    'z' => '9999',
    ' ' => '0'
  }

  def self.key_presses(s)
    new(s).key_presses()
  end

  def initialize(s)
    @string = s
    @last_key = nil
  end

  def key_presses()
    reset
    result = ''

    @string.each_char do |c|
      char_to_key_presses = CHAR_TO_KEY_PRESSES[c]

      raise 'Invalid character in string' unless char_to_key_presses

      result << if char_to_key_presses[0] == @last_key
        " #{char_to_key_presses}"
      else
        char_to_key_presses
      end

      @last_key = char_to_key_presses[0]
    end

    result
  end

  def reset
    @last_key = nil
  end
end

n = ARGF.gets.to_i

n.times do |i|
  puts "Case ##{i + 1}: #{MobileKeyboard.key_presses(ARGF.gets.chomp)}"
end
