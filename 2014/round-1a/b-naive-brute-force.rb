#!/usr/bin/ruby -w

def solve(adjacency_list)
  num_nodes = adjacency_list.size - 1
  min_deleted_nodes = num_nodes - 1
  initial_nodes = (1..num_nodes).to_a
  delete_options =
    (0...num_nodes).flat_map { |i| initial_nodes.combination(i).to_a }

  delete_options.each do |deleted_nodes|
    new_adjacency_list = delete_nodes_from_graph adjacency_list, *deleted_nodes

    if connected? new_adjacency_list
      if find_root_full_binary_tree new_adjacency_list
        num_deleted_nodes = deleted_nodes.size

        if num_deleted_nodes < min_deleted_nodes
          min_deleted_nodes = num_deleted_nodes
        end
      end
    end
  end

  min_deleted_nodes
end

def connected?(adjacency_list)
  return false if adjacency_list.none?

  visited =
    Array.new(adjacency_list.size) { |node| true unless adjacency_list[node] }
  queue = [adjacency_list.find_index { |nodes| nodes }]

  loop do
    node = queue.shift

    unless visited[node]
      visited[node] = true

      if adjacency_list[node]
        adjacency_list[node].each { |adjacent_node| queue << adjacent_node }
      end
    end

    break if queue.empty? || visited.all?
  end

  visited.all?
end

def find_root_full_binary_tree(adjacency_list)
  initial_nodes =
    adjacency_list.each_with_index.map { |nodes, i| i if nodes }.compact

  initial_nodes.each do |node|
    new_adjacency_list = adjacency_list.map { |nodes| nodes }

    new_adjacency_list.map! do |nodes|
      if nodes
        nodes - [node]
      end
    end

    return node if full_binary_tree_root? node, new_adjacency_list
  end

  nil
end

def full_binary_tree_root?(root, adjacency_list)
  num_childrens = adjacency_list[root].size

  return true if num_childrens == 0

  if num_childrens == 2
    first, second = adjacency_list[root]
    adjacency_list = delete_nodes_from_graph adjacency_list, root

    full_binary_tree_root?(first,
                           delete_nodes_from_graph(adjacency_list, second)) &&
      full_binary_tree_root?(second,
                             delete_nodes_from_graph(adjacency_list, first))
  end
end

def delete_nodes_from_graph(adjacency_list, *deleted_nodes)
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
