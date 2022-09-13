# Pour trouver le plus court chemin dans le cas d'un cycle eulérien on va tenter de résoudre le problème du postier chinois
# et on obtient le meilleur résultat dans le cas d'un cycle eulérien

import theory.src.utils as utils

def get_undirected_path(n, edges):
    eulerian_path = utils.find_eulerian_cycle_undirected(n, edges)
    eulerian_path.append(eulerian_path[0])
    return utils.get_edges(eulerian_path)
