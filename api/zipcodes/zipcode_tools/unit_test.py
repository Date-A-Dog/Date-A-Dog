import unittest
from find_zipcodes import *

class TestZipcodes(unittest.TestCase):
    zip_small = FindZipcodes(98105, 1).get_zipcodes()
    zip_small = FindZipcodes(10001, 1).get_zipcodes()
