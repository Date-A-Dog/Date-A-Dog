# from database import *
from find_zipcodes import *

"""Creating a new database instance and inserting data, then confirming count."""
# db = Database("../data/2016_Gaz_zcta_national.txt", "zipcodes.db")
# db.create_db()
# db.insert_data()
# rows = db.run_query("SELECT COUNT(*) FROM zipcodes")
# print rows


"""To search, here's what you do!
FindZipcodes' constructor takes two arguments: zipcode and distance (in miles).
Calling get_zipcodes() on the instance will return a list of all matching zipcodes whose centers fall within the region.
"""
print "\n"
print FindZipcodes(98107, 3).get_zipcodes()
print "\n"
print FindZipcodes(98122, 5).get_zipcodes()