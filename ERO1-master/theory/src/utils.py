import math
import numpy as np
from collections import defaultdict

import itertools as it

def odd_vertices(n, edges):
    deg = [0] * n
    for (a,b,_) in edges:
        deg[a] += 1
        deg[b] += 1
    return [a for a in range(n) if deg[a] % 2]

def generate_odd_pairs(vertices):
    res = it.combinations(vertices, 2)
    if len(vertices) <= 2:
        return [(pair1, pair2) for (pair1,pair2) in res]
    res = it.combinations(res, len(vertices)//2)

    new_res = [(pair1,pair2) for (pair1,pair2) in res if common_elements(pair1,pair2) == True]
    return new_res

def common_elements(pair1,pair2):
    return pair1[0] != pair2[0] and pair1[0] != pair2[1] and pair1[1] != pair2[0] and pair1[1] != pair2[1]

def is_edge_connected(n, edges):
    if n == 0 or len(edges) == 0:
        return True

    succ = [[] for a in range(n)]
    for (a,b,_) in edges:
        succ[a].append(b)
        succ[b].append(a)

    touched = [False] * n
    init = edges[0][0]
    touched[init] = True
    todo = [init]
    while todo:
        s = todo.pop()
        for d in succ[s]:
            if touched[d]:
                continue
            touched[d] = True
            todo.append(d)
    for a in range(n):
        if succ[a] and not touched[a]:
            return False
    return True

def is_eulerian(n, edges):
    return is_edge_connected(n, edges) and not odd_vertices(n, edges)

def find_eulerian_cycle_undirected(n, edges):
    if len(edges) == 0:
        return []
    cycle = [edges[0][0]]
    while True:
        rest = []
        for (a, b, weight) in edges:
            if cycle[-1] == a:
                cycle.append(b)
            elif cycle[-1] == b:
                cycle.append(a)
            else:
                rest.append((a,b, weight))
        if not rest:
            assert cycle[0] == cycle[-1]
            return cycle[0:-1]
        edges = rest
        if cycle[0] == cycle[-1]:
            for (a, b, _) in edges:
                if a in cycle:
                    idx = cycle.index(a)
                    cycle = cycle[idx:-1] + cycle[0:idx+1]
                    break

def find_eulerian_cycle_directed(n, edges):
    if len(edges) == 0:
        return []
    cycle = [edges[0][0]] # start somewhere
    while True:
        rest = []
        for a, b, weight in edges:
            if cycle[-1] == a:
                cycle.append(b)
            else:
                rest.append((a,b,weight))
        if not rest:
            assert cycle[0] == cycle[-1]
            return cycle[0:-1]
        edges = rest
        if cycle[0] == cycle[-1]:
            # Rotate the cycle so that the last state
            # has some outgoing edge in EDGES.
            for a, b, weight in edges:
                if a in cycle:
                    idx = cycle.index(a)
                    cycle = cycle[idx:-1] + cycle[0:idx+1]
                    break


def nedge(a, b):
    return (a, b) if a < b else (b, a)

def is_eulerian_cycle(n, edges, cycle):
    if len(edges) != len(cycle):
        return False
    if len(edges) == 0:
        return True
    eset = {}
    for (a, b) in edges:
        s = nedge(a, b)
        if s in eset:
            eset[s] += 1
        else:
            eset[s] = 1
    for (a, b) in zip(cycle, cycle[1:]+cycle[0:1]):
        s = nedge(a, b)
        if s in eset and eset[s] > 0:
            eset[s] -= 1
        else:
            return False
    for val in eset.values():
        if val != 0:
            return False
    return True

def get_weight(edges, src, dst):
    for s, d, w in edges:
        if src == s and dst == d or dst == s and src == d:
            return w
    raise RuntimeError('edge ({}, {}) does not exist'.format(src, dst))

def get_edges(vertices):
    res = []
    for i in range(len(vertices) - 1):
        res.append((vertices[i],vertices[i+1]))
    return res

def compute_cost(eulerian_cycle, edge_list):
    cost = 0
    for src, dest in eulerian_cycle:
        cost += get_weight(edge_list, src, dest)
    return cost

def get_all_weight(edge_list):
    cost = 0
    for src, dest, weight in edge_list:
        cost += weight
    return cost

def compute_in_out_edges(n,edge_list):
    res = [0] * n
    for (a,b,_) in edge_list:
        res[a] += 1
        res[b] -= 1

    arr = np.array(res)
    return np.all((arr == 0))
