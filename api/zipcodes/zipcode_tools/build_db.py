#!/usr/bin/python
from database import *

def main():
    db = Database("../data/2016_Gaz_zcta_national.txt")
    db.create_db()
    db.insert_data()