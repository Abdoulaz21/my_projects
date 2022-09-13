import theory.src.utils as utils
import theory.src.drone_no_eulerian as drone_no_eulerian
import unittest

graph1 = (6, [(0,1,8),(0,2,9),(1,3,7),(2,3,6),(3,4,8),(3,5,11),(4,5,10),(1,3,6)])
print("Path graph 1: ", drone_no_eulerian.chinese_postman(*graph1))

graph2 = (10, [(0,1,8),(0,2,5),(0,3,6),(1,3,7),(2,3,9),(3,4,12),(3,5,6),(3,6,13),(4,6,8),(4,7,9),(5,6,4),(6,7,14),(6,8,3),(6,9,3),(7,8,13),(8,9,1)])
print("Path graph 2: ", drone_no_eulerian.chinese_postman(*graph2))

graph3 = (8, [(0,1,50),(0,2,50),(0,3,50),(1,3,50),(1,4,70),(1,5,50),(2,3,70),(2,6,70),(2,7,120),(3,5,60),(4,5,70),(5,7,60),(6,7,70)])
#print("Path graph 3: ", drone_no_eulerian.chinese_postman(*graph3))

graph3copy = (8, [(0,1,50),(0,2,50),(0,3,50),(1,3,50),(1,4,70),(1,5,50),(2,3,70),(2,6,70),(2,7,120),(3,5,60),(4,5,70),(5,7,60),(6,7,70)])

graph4 = (6, [(0,1,50),(1,2,30),(2,3,70),(2,4,120),(4,5,50)])
print("Path graph 4: ", drone_no_eulerian.chinese_postman(*graph4))

graph5 = (6, [(0,1,2),(1,2,14),(2,4,1000),(4,5,8),(2,3,36)])
print("Path graph 5: ", drone_no_eulerian.chinese_postman(*graph5))

class TestDroneEulerianMethods(unittest.TestCase):

    """ def test_graph1_is_eulerian(self):
         self.assertFalse(utils.is_eulerian(*graph1))

     def test_cost_graph1(self):
         self.assertEqual(utils.get_all_weight(graph1[1]), utils.compute_cost(drone_no_eulerian.chinese_postman(*graph1), graph1[1]))

     def test_graph2_is_eulerian(self):
         self.assertFalse(utils.is_eulerian(*graph2))

     def test_cost_graph2(self):
         self.assertEqual(utils.get_all_weight(graph2[1]), utils.compute_cost(drone_no_eulerian.chinese_postman(*graph2), graph2[1]))
     """
    def test_graph3_is_eulerian(self):
        self.assertFalse(utils.is_eulerian(*graph3copy))

    def test_cost_graph3(self):
        self.assertEqual(1000, drone_no_eulerian.chinese_postman(*graph3)[1])

if __name__ == '__main__':
    unittest.main()
