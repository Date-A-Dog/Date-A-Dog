#!/usr/bin/python

from find_zipcodes import *
import sys

"""To search, here's what you do!
FindZipcodes' constructor takes two arguments: zipcode and distance (in miles).
Calling get_zipcodes() on the instance will return a list of all matching zipcodes whose centers fall within the region.
"""
def main():
    if len(sys.argv) < 3:
        print "usage: zipcode, distance (mi)"

    zipcode = int(sys.argv[1])
    distance = int(sys.argv[2])

    print FindZipcodes(zipcode, distance).get_zipcodes()

if __name__ == '__main__':
    main()
