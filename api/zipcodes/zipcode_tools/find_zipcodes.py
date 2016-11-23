
"""Given a valid zipcode and distance in miles, returns all zipcodes
within the requested area (based on the zipcode's center point).
"""

from calc import *
from database import *
from operator import itemgetter
from decimal import *

class FindZipcodes(object):
    # global database object.
    db = Database("")

    def __init__(self, zipcode, distance):
        """zipcode: center point of search region
        distance: how far, in miles, to search for adjacent zip codes
        asserts verify that valid inputs are provided for zipcode and distance:
        non-negative numbers of the appropriate length.
        """
        self.zipcode = zipcode
        self.distance = distance

    def get_coordinates(self):
        """Get coordinates for the provided zipcode; returns tuple of the form
        (latitude, longitude)
        """
        query = "SELECT latitude, longitude FROM zipcodes WHERE zipcode = " + str(self.zipcode)
        result = self.db.run_query(query)
        return result[0]

    def get_bounding_coords(self, lat, lon):
        """Get bounding coordinates; calculates great circle for longitude, providing more accurate results
        than if we were to use the circle of latitude as reference. (Doesn't account for anomalies at the poles or along
        the 180th meridian--fine for US Zipcodes, but another application might require an adjustment.
        Returns a list of the bounding coordinates.
        """
        lat_radians = convert_degs_to_rads(lat)
        lon_radians = convert_degs_to_rads(lon)
        km = convert_miles_to_km(self.distance)

        angular_radius = calc_angular_radius(km)
        delta_lon = calc_delta_longitude(angular_radius, lat_radians)

        # Convert back into degrees to be used as coordinates
        min_lat = convert_rads_to_degrees(lat_radians - angular_radius)
        max_lat = convert_rads_to_degrees(lat_radians + angular_radius)
        min_lon = convert_rads_to_degrees(lon_radians - delta_lon)
        max_lon = convert_rads_to_degrees(lon_radians + delta_lon)

        return [min_lat, max_lat, min_lon, max_lon]

    def get_zipcodes(self):
        """Get coordinates for request zipcode, then calculate directional bounds based on distance.
        Based on the bounding coordinates, return a list of zipcodes within the bound, in order of geodesic distance.
        """
        center_coords = self.get_coordinates()
        lat = center_coords[0]
        lon = center_coords[1]

        bounds = self.get_bounding_coords(lat, lon)

        query = "SELECT * FROM zipcodes \
                WHERE latitude BETWEEN " + str(bounds[0]) + " AND " + str(bounds[1]) + \
                " AND longitude BETWEEN " + str(bounds[2]) + " AND " + str(bounds[3])

        distances = []

        result = self.db.run_query(query)

        # attribute a relative weight distance
        for row in result:
            zipcode = '%05d' % row[0]
            tup = (zipcode, (math.fabs(row[1] - lat) + math.fabs(row[2] - lon)))
            distances.append(tup)

        distances.sort(key=itemgetter(1))

        # Strips out the ranking numbers and just passes back the zipcodes in order of proximity
        return [tup[0] for tup in distances]
	
