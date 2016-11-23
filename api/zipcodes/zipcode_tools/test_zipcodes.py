import unittest
from find_zipcodes import *
from calc import *

class TestZipcodes(unittest.TestCase):

  def test_small_zipcodes(self):
    zip_small_expected = ['98105', '98115', '98112', '98195', '98102'] 
    zip_small_actual = FindZipcodes(98105, 2).get_zipcodes()
    self.assertEqual(zip_small_expected, zip_small_actual)

  def test_zero_distance(self):
    zip_zero_expected = ['98105']
    zip_zero_actual = FindZipcodes(98105, 0).get_zipcodes()
    self.assertEqual(zip_zero_expected, zip_zero_actual)
  
  def test_leading_zeroes_zipcode(self):
    zips_expected = ['07030', '07310', '07087', '07311', '07307', '07086', 
      '10011', '10014', '07302', '10001', '10199', '10282', '10013', '10012']
    zips_actual = FindZipcodes(07030, 2).get_zipcodes()

  def convert_to_rads_0(self):
    rads_expected = 0.0
    rads_actual = convert_degs_to_rads(0) 
    self.assertEqual(rads_expected, rads_actual)

  def convert_to_rads_90(self):
    rads_expected = 1.57079632679
    rads_actual = convert_degs_to_rads(90)
    self.assertEqual(rads_expected, rads_actual)

  def convert_to_rads_neg_45(self):
    rads_expected = -0.785398163397
    rads_actual = convert_degs_to_rads(-45)
    self.assertEqual(rads_expected, rads_actual)

  def convert_to_rads_180(self):
    rads_expected = math.pi 
    rads_actual = convert_degs_to_rads(180)
    self.assertEqual(rads_expected, rads_actual)



 
if __name__ == '__main__':
  unittest.main()
