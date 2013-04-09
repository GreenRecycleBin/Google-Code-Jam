#!/usr/bin/ruby

# https://code.google.com/codejam/contest/438101/dashboard#s=p1

class Person
  attr_reader :home_town_id, :passenger_capacity

  def initialize(home_town_id, passenger_capacity)
    @home_town_id = home_town_id
    @passenger_capacity = passenger_capacity
  end

  def <=>(other)
    if @passenger_capacity < other.passenger_capacity
      -1
    elsif @passenger_capacity == other.passenger_capacity
      0
    else
      1
    end
  end
end

class City
  attr_reader :cars

  def initialize(num_towns, work_town_id, people)
    @num_towns = num_towns
    @work_town_id = work_town_id
    @home_town_ids_to_people = Hash.new { |hash, key| hash[key] = [] }

    people.each do |person|
      @home_town_ids_to_people[person.home_town_id] << person
    end
  end

  def has_enough_cars?
    !calculate_cars.empty?
  end

  private

  def calculate_cars
    return @cars if @cars

    @cars = [0] * @num_towns

    @home_town_ids_to_people.sort.each do |home_town_id, people;
      sorted_people, i, j|

      next if home_town_id == @work_town_id

      sorted_people = people.sort.reverse
      i, j = 0, sorted_people.size - 1

      while i <= j do
        person = sorted_people[i]

        if person.passenger_capacity > 0
          j -= person.passenger_capacity - 1
          @cars[home_town_id] += 1
        else
          return @cars.clear
        end

        i += 1
      end
    end

    @cars
  end
end

n = ARGF.gets.to_i

n.times do |i|
  num_towns, work_town_id = ARGF.gets.split(' ').map(&:to_i)
  work_town_id -= 1
  num_people = ARGF.gets.to_i
  people = []

  num_people.times do |j|
    home_town_id, passenger_capacity = ARGF.gets.split(' ').map(&:to_i)
    home_town_id -= 1
    people << Person.new(home_town_id, passenger_capacity)
  end

  city = City.new num_towns, work_town_id, people

  print "Case ##{i + 1}: "

  if city.has_enough_cars?
    puts city.cars.join(' ')
  else
    puts 'IMPOSSIBLE'
  end
end
