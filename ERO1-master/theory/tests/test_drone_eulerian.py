import theory.src.utils as utils
import theory.src.drone_eulerian as drone_eulerian
import unittest

graph1 = (6, [(0,1,1),(0,2,2),(1,3,3),(2,3,4),(3,4,5),(3,5,6),(4,5,7)])
print('Path graph 1:', drone_eulerian.get_undirected_path(*graph1))

graph2 = (7, [(0,1,1),(0,4,2),(0,5,3),(0,6,4),(1,2,5),(2,3,6),(2,5,7),(2,6,8),(3,4,9),(4,5,1),(4,6,2),(5,6,3)])
print('Path graph 2:', drone_eulerian.get_undirected_path(*graph2))

class TestDroneEulerianMethods(unittest.TestCase):

    def test_graph1_is_eulerian(self):
        self.assertTrue(utils.is_eulerian(*graph1))

    def test_length_graph1(self):
        self.assertEqual(len(graph1[1]), len(drone_eulerian.get_undirected_path(*graph1)))

    def test_cost_graph1(self):
        self.assertEqual(utils.get_all_weight(graph1[1]), utils.compute_cost(drone_eulerian.get_undirected_path(*graph1), graph1[1]))

    def test_graph2_is_eulerian(self):
        self.assertTrue(utils.is_eulerian(*graph2))

    def test_length_graph2(self):
        self.assertEqual(len(graph2[1]), len(drone_eulerian.get_undirected_path(*graph2)))

    def test_cost_graph2(self):
        self.assertEqual(utils.get_all_weight(graph2[1]), utils.compute_cost(drone_eulerian.get_undirected_path(*graph2), graph2[1]))


if __name__ == '__main__':
    unittest.main()

