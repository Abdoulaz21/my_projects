# Execute this file to have a demonstration of our solution.

import theory.src.drone_no_eulerian as drone

import networkx as ne
import osmnx as ox

print("#### INSTRUCTIONS ####")
print("We get the city graph from OpenStreetMap.")
print("We decided to take the city Collonges-la-Rouge in France.\n")

city = ox.graph_from_place('Collonges-la-Rouge, France', network_type='drive', simplify=True)

print("#### GRAPH ####")
print("Here is the graph of the city:\n")

ox.plot_graph(city)

print("We get the total edge length of the graph:")

totalLength = ox.stats.edge_length_total(city)
print(totalLength, " meters")

print("")

print("After that, we get the data from the graph (nodes, edges, weights).")
print("And we construct a new data according to our algorithms.\n")

def data_from_graph(G):
    nodes_gdfs, edges_gdfs = ox.graph_to_gdfs(G)
    nodes = list(G.nodes())
    edges = list(G.edges())

    weigths = []
    for val in edges_gdfs['length']:
        weigths.append(val)

    return nodes, edges, weigths

nodes, edges, weigths = data_from_graph(city)

def data_for_algo(N, E, W):
    data = []
    i = 0
    while i < len(E):
        n1 = N.index(E[i][0])
        n2 = N.index(E[i][1])
        data.append((n1, n2, W[i]))
        i = i + 1

    nb_nodes = len(N)

    return (nb_nodes, data)

data = data_for_algo(nodes, edges, weigths)

print("#### DRONE ####")
print("Finally we can run our algorithm !")

res = drone.chinese_postman(*data)

print("This is the path we got for the drone:")

print(res)

print("")

print("#### TIME ####")
print("We consider that a snowy have a speed of about 7.5km.")
print("So, we can calculate the total time for one snowy for all the city:")

totalTime = (res[1] / 1000) / 7.5
print(totalTime, " hours")

print("")

print("#### MONTREAL ####")
print("We can now propose a cost model for the city of Montreal.")
print("First, let's get the graph from OpenStreetMap !")

montreal_graph = ox.graph_from_place('Montréal, Canada', network_type='drive', simplify=True)

print("Then we can get the necessary information.\n")

nodes_montreal, edges_montreal, weights_montreal = data_from_graph(montreal_graph)
data_montreal = data_for_algo(nodes_montreal, edges_montreal, weights_montreal)

print("#### COST MODEL ####")

nb_nodes = len(nodes_montreal)

print("There is", nb_nodes, "nodes in Montreal's graph.")

nb_nodes_per_sg = nb_nodes / 197

print("We can divide this number by 197 to obtain 197 sub-graph of", nb_nodes_per_sg,  "nodes each.")

nb_machines = 2200
nb_machines_per_sg = nb_machines / 197

print("We have 2200 machines and an equivalent amount of employees.")
print("So we have about", nb_machines_per_sg, "machines per sub-graph")
print("Then, we can divide each sub-graph into 3 areas with one machine for the road and one machine for each side of the pavement.\n")

print("#### CONCLUSION ####")
print("Finally we have an optimize solution for these operations, 3 hours for each sub-graph.")
print("Each sub-graph with a size almost equivalent to the city of Collonges-la-Rouge.")
