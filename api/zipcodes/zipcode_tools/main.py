from find_zipcodes import *

"""To search, here's what you do!
FindZipcodes' constructor takes two arguments: zipcode and distance (in miles).
Calling get_zipcodes() on the instance will return a list of all matching zipcodes whose centers fall within the region.
"""
def main(argv):
    if len(argv) < 3:
        print "usage: zipcode, distance (mi)"

    zipcode = int(argv[1])
    distance = int(argv[2])

    print FindZipcodes(zipcode, distance).get_zipcodes()
