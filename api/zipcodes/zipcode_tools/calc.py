
""" Mathematical conversion and helper functions. """

import math

def convert_degs_to_rads(deg):
    """convert degrees into radians"""
    return deg * (math.pi / 180.0)


def convert_rads_to_degrees(rads):
    """convert radians into degrees"""
    return rads * (180.0 / math.pi)


def convert_miles_to_km(miles):
    """convert miles into kilometers"""
    return miles / 0.62137


def calc_angular_radius(km):
    """calculate the angular radius for a given distance in kilometers"""
    earth_radius = 6371.0  # in km
    return km / earth_radius


def calc_delta_longitude(angular_rad, lat):
    """calculate the delta for longitude considering the angular radius (for
    the given distance and earth) and the latitude in radians
    """
    return math.asin(math.sin(angular_rad) / math.cos(lat))
