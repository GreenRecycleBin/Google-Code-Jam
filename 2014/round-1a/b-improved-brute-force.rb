#!/usr/bin/ruby -w

require 'set'

def solve(adjacency_list)
  num_nodes = adjacency_list.size - 1
  min_deleted_nodes = num_nodes - 1
  initial_nodes = (1..num_nodes).to_a
  delete_options =
    (0...num_nodes).flat_map { |i| initial_nodes.combination(i).to_a }

  delete_options.each do |deleted_nodes|
    new_adjacency_list = delete_nodes_from_graph adjacency_list, deleted_nodes

    if full_binary_tree? new_adjacency_list
      num_deleted_nodes = deleted_nodes.size

      if num_deleted_nodes < min_deleted_nodes
        min_deleted_nodes = num_deleted_nodes
      end
    end
  end

  min_deleted_nodes
end

def delete_nodes_from_graph(adjacency_list, deleted_nodes)
  new_adjacency_list = adjacency_list.map { |nodes| nodes }

  deleted_nodes.each do |deleted_node|
    new_adjacency_list[deleted_node] = nil

    new_adjacency_list.map! do |nodes|
      if nodes
        nodes - [deleted_node]
      end
    end
  end

  new_adjacency_list
end

def full_binary_tree?(adjacency_list)
  num_nodes = adjacency_list.count { |nodes| nodes }

  return true if num_nodes == 1

  num_nodes_with_degree_two = 0
  edges = Set[]

  adjacency_list.each_with_index do |nodes, i|
    next unless nodes

    if nodes.size == 2
      if num_nodes_with_degree_two.zero?
        num_nodes_with_degree_two = 1
      else
        return false
      end
    elsif nodes.size != 1 && nodes.size != 3
      return false
    end

    nodes.each { |node| edges << Edge.new(i, node) if node }
  end

  num_nodes_with_degree_two == 1 && edges.size == num_nodes - 1
end

class Edge
  attr_reader :x, :y
  alias_method :eql?, :==

  def initialize(x, y)
    @x, @y = [x, y].minmax
  end

  def eql?(other)
    other.instance_of?(self.class) && @x == other.x && @y == other.y
  end

  def hash
    [@x, @y].hash
  end
end

t = ARGF.gets.to_i

t.times do |i|
  n = ARGF.gets.to_i

  adjacency_list = Array.new n + 1

  (n - 1).times do
    x, y = ARGF.gets.split.map(&:to_i)

    adjacency_list[x] ||= []
    adjacency_list[y] ||= []

    adjacency_list[x] << y
    adjacency_list[y] << x
  end

  puts "Case ##{i + 1}: #{solve adjacency_list}"
end
