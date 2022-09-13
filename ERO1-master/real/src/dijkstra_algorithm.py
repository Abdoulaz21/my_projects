import utils as util
import math

def get_adj_list(n, edges):
    res = list()
    for src, dst, weight in edges:
        if src == n:
            res.append((dst, weight))
        elif dst == n:
            res.append((src, weight))
    return res

def get_path_from_prev(pr, node):
    p = list()
    while node is not None:
        p.append(node)
        node = pr[node]
    return util.get_edges(p[::-1])

"""Dijkstra algorithm"""
def dijkstra(n, edges, src, dst):
    q = set()
    dist = dict()
    prev = dict()

    for vertex in range(n):
        dist[vertex] = math.inf
        prev[vertex] = None
        q.add(vertex)

    dist[src] = 0

    while q:
        u = min(q, key=dist.get)
        q.remove(u)

        if dst is not None and u == dst:
            return dist[dst], prev

        for v, w in get_adj_list(u, edges):
            if v in q:
                alt = dist[u] + w
                if alt < dist[v]:
                    dist[v] = alt
                    prev[v] = u

    return dist, prev

def get_dijkstra_cost_and_path(n, edges, src, dst):
    dist, prev = dijkstra(n, edges, src, dst)
    return dist, get_path_from_prev(prev, dst)
