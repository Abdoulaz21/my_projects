import math

import theory.src.utils as utils
import itertools as it
import theory.src.dijkstra_algorithm as dijkstra


def get_shortest_paths(n, edge_list, pairs):
    dijkstras = {}
    for pair in pairs:
        dijkstras[pair] = dijkstra.get_dijkstra_cost_and_path(n, edge_list, pair[0], pair[1])
    return dijkstras

def add_augmenting(edges, pairs, dijkstras):
    if (len(dijkstras) >= 2):
        for pair in pairs:
            dijkstra_path = dijkstras[pair][1]
            for src, dst in dijkstra_path:
                edges.append((src, dst, utils.get_weight(edges, src, dst)))
    else:
        dijkstra_path = dijkstras[pairs][1]
        for src, dst in dijkstra_path:
            edges.append((src, dst, utils.get_weight(edges, src, dst)))
    return edges

def get_weight(edges, src, dst):
    for s, d, w in edges:
        if src == s and dst == d or dst == s and src == d:
            return w
    raise RuntimeError('edge ({}, {}) does not exist'.format(src, dst))

def get_best_pair_set(cbs, dijkstras):
    m = None
    m_cost = math.inf
    for pairs in cbs:
        cost = 0
        if (len(dijkstras) >= 2):
            for pair in pairs:
                cost += dijkstras[pair][0]
        else:
            cost += dijkstras[pairs][0]
        if cost < m_cost:
            m = pairs
            m_cost = cost

    return m


def chinese_postman(n, edges):
    # List all odd vertices
    odd_vertices = utils.odd_vertices(n,edges)

    # List all possible pairings of odd vertices
    pairs = it.combinations(odd_vertices, 2)

    # Get unique pairing between edges
    odds_pairs = utils.generate_odd_pairs(odd_vertices)

    dijkstras = get_shortest_paths(n, edges, pairs)
    best_set = get_best_pair_set(odds_pairs, dijkstras)

    add_augmenting(edges, best_set, dijkstras)

    eulerian_nodes = utils.find_eulerian_cycle_undirected(n, edges)
    eulerian_nodes.append(eulerian_nodes[0])
    eulerian_edges = utils.get_edges(eulerian_nodes)

    return utils.get_edges(eulerian_nodes), utils.compute_cost(eulerian_edges, edges)
