
""" Creates a database of US zipcodes and their center coordinates.
Also provides a query runner utility, reducing redundancy. Note that this
isn't sanitizing input; in this case it's not a problem because I'm the
only client of the code, but it would be unwise to do this when interacting with
user inputs.
"""

import psycopg2

class Database:
    def __init__(self, filepath):
        assert db_name != ""
        self.filepath = filepath  # may be left blank if not inserting data from a file.
        self.db_name = db_name

    def create_db(self):
        """Creates the zipcode database table."""
        query = "CREATE TABLE IF NOT EXISTS zipcodes \
              (zipcode INT PRIMARY KEY, \
              latitude DOUBLE, \
              longitude DOUBLE)"
        self.run_query(query)

    def insert_data(self):
        """Concatenates insert rows into one query;
        It works fine for this dataset, but if it were larger I'd need to be
        careful not to run out of memory (batch processing)
        """
        assert self.filepath != ""
        query = "INSERT INTO zipcodes VALUES "

        try:
            with open(self.filepath) as f:
                next(f)  # skip header row
                for line in f:
                    row = line.split("\t")
                    query += "(" + row[0] + ", " + row[5] + ", " + row[6] + "),"
            query = query[:-1]  # remove last comma

            self.run_query(query)

            f.close()
        except IOError:
            print "Filepath " + self.filepath + " is invalid; insert failed."

    def run_query(self, query):
        """Connects and runs query passed as an argument, returning results, if any,
        as a list of tuples (each representing selected fields for a single row).
        Here's another place with the potential for running out of memory.
        """
        conn = psycopg2.connect(database="dateadog", user="dadadmin", password="zOg8sUs87TOu",
	        host="dad-postgres.clcyrikoceop.us-west-2.rds.amazonaws.com")
        c = conn.cursor()
        c.execute(query)
        rows = c.fetchall()
        conn.commit()
        c.close()
        conn.close()
        return rows
