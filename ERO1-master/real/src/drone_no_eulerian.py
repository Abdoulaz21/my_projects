import math

import utils as util
import itertools as it
import dijkstra_algorithm as dijkstra


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
                edges.append((src, dst, util.get_weight(edges, src, dst)))
    else:
        dijkstra_path = dijkstras[pairs][1]
        for src, dst in dijkstra_path:
            edges.append((src, dst, util.get_weight(edges, src, dst)))
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

def compute_cost(eulerian_cycle, edge_list):
    cost = 0
    for src, dest in eulerian_cycle:
        cost += util.get_weight(edge_list, src, dest)
    return cost

def chinese_postman(n, edges):
    # List all odd vertices
    odd_vertices = util.odd_vertices(n,edges)

    # List all possible pairings of odd vertices
    pairs = it.combinations(odd_vertices, 2)

    # Get unique pairing between edges
    odds_pairs = util.generate_odd_pairs(odd_vertices)

    dijkstras = get_shortest_paths(n, edges, pairs)
    best_set = get_best_pair_set(odds_pairs, dijkstras)

    add_augmenting(edges, best_set, dijkstras)

    eulerian_nodes = util.find_eulerian_cycle(n, edges)
    eulerian_nodes.append(eulerian_nodes[0])
    eulerian_edges = util.get_edges(eulerian_nodes)

    return util.get_edges(eulerian_nodes), compute_cost(eulerian_edges, edges)
"""
graph1 = (6, [(0,1,8),(0,2,9),(1,3,7),(2,3,6),(3,4,8),(3,5,11),(4,5,10),(1,3,6)])
print("Path graph 1: ", chinese_postman(*graph1))

graph2 = (10, [(0,1,8),(0,2,5),(0,3,6),(1,3,7),(2,3,9),(3,4,12),(3,5,6),(3,6,13),(4,6,8),(4,7,9),(5,6,4),(6,7,14),(6,8,3),(6,9,3),(7,8,13),(8,9,1)])
print("Path graph 2: ", chinese_postman(*graph2))

graph3 = (8, [(0,1,50),(0,2,50),(0,3,50),(1,3,50),(1,4,70),(1,5,50),(2,3,70),(2,6,70),(2,7,120),(3,5,60),(4,5,70),(5,7,60),(6,7,70)])
print("Path graph 3: ", chinese_postman(*graph3))

graph4 = (6, [(0,1,50),(1,2,30),(2,3,70),(2,4,120),(4,5,50)])
print("Path graph 4: ", chinese_postman(*graph4))

graph5 = (6, [(0,1,2),(1,2,14),(2,4,1000),(4,5,8),(2,3,36)])
print("Path graph 5: ", chinese_postman(*graph5))
"""