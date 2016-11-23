module.exports = {
    getZipcodes: function (zipcode, distance) {
        var PythonShell = require('python-shell');

        var options = {
            mode: 'text',
            pythonPath: '/usr/bin/python',
            scriptPath: '../zipcodes/zipcode_tools',
            args: [zipcode, distance]
        };

        PythonShell.run('main.py', options, function(err, res){
            if(err) throw err;
            var query_zip_str = "AND ";

            res.forEach(function(zipcode) {
                query_zip_str += "zipcode = " + zipcode + " OR ";
            });

            query_zip_str = query_zip_str.slice(0, -3);

            return query_zip_str;
        });
    }
};