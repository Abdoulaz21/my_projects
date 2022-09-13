import theory.src.utils as utils

def get_directed_path(n, edges):
    eulerian_path = utils.find_eulerian_cycle_directed(n, edges)
    eulerian_path.append(eulerian_path[0])
    return utils.get_edges(eulerian_path)
