import theory.src.utils as utils
import theory.src.snowy_eulerian as snowy_eulerian
import unittest

graph1 = (6, [(2,1,3),(0,2,4),(3,4,5),(4,0,6),(1,0,1),(0,3,2)])
print('Path graph 1:', snowy_eulerian.get_directed_path(*graph1))

graph2 = (5, [(2,3,3),(1,2,4),(4,3,5),(1,4,6),(0,1,1),(3,0,2),(3,1,15)])
print('Path graph 2:', snowy_eulerian.get_directed_path(*graph2))

class TestDroneEulerianMethods(unittest.TestCase):

    def test_length_graph1(self):
        self.assertEqual(len(graph1[1]), len(snowy_eulerian.get_directed_path(*graph1)))

    def test_cost_graph1(self):
        self.assertEqual(utils.get_all_weight(graph1[1]), utils.compute_cost(snowy_eulerian.get_directed_path(*graph1), graph1[1]))

    def test_good_eulerian_graph1(self):
        self.assertTrue(utils.compute_in_out_edges(*graph1))

    def test_graph1_is_eulerian(self):
        self.assertTrue(utils.is_eulerian(*graph1))

    def test_length_graph2(self):
        self.assertEqual(len(graph2[1]), len(snowy_eulerian.get_directed_path(*graph2)))

    def test_cost_graph2(self):
        self.assertEqual(utils.get_all_weight(graph2[1]), utils.compute_cost(snowy_eulerian.get_directed_path(*graph2), graph2[1]))

    def test_good_eulerian_graph2(self):
        self.assertTrue(utils.compute_in_out_edges(*graph2))

    def test_graph2_is_eulerian(self):
        self.assertTrue(utils.is_eulerian(*graph2))

if __name__ == '__main__':
    unittest.main()

