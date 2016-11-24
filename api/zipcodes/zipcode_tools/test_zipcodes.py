import unittest
from find_zipcodes import *
from calc import *

class TestZipcodes(unittest.TestCase):

  def test_small_zipcodes(self):
    zip_small_expected = ['98105', '98115', '98112', '98195', '98102'] 
    zip_small_actual = FindZipcodes('98105', 2).get_zipcodes()
    self.assertEqual(zip_small_expected, zip_small_actual, "2 miles from 98105 produces invalid results.")

  def test_zero_distance(self):
    zip_zero_expected = ['98105']
    zip_zero_actual = FindZipcodes('98105', 0).get_zipcodes()
    self.assertEqual(zip_zero_expected, zip_zero_actual, "Zero distance doesn't match expected.")
  
  def test_leading_zeroes_zipcode(self):
    zips_expected = ['07030']
    zips_actual = FindZipcodes('07030', 0).get_zipcodes()
    self.assertEqual(zips_expected, zips_actual)

if __name__ == '__main__':
  unittest.main()
