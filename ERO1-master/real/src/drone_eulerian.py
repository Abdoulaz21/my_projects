# Pour trouver le plus court chemin dans le cas d'un cycle eulérien on va tenter de résoudre le problème du postier chinois
# et on obtient le meilleur résultat dans le cas d'un cycle eulérien

import utils as util

def get_path(n, edges):
    eulerian_path = util.find_eulerian_cycle(n, edges)
    eulerian_path.append(eulerian_path[0])
    return util.get_edges(eulerian_path)


"""TEST"""
"""
graph1 = (6, [(0,1,1),(0,2,2),(1,3,3),(2,3,4),(3,4,5),(3,5,6),(4,5,7)])
print('Path graph 1:', get_path(*graph1))

graph2 = (7, [(0,1,1),(0,4,2),(0,5,3),(0,6,4),(1,2,5),(2,3,6),(2,5,7),(2,6,8),(3,4,9),(4,5,1),(4,6,2),(5,6,3)])
print('Path graph 2:', get_path(*graph2))
"""