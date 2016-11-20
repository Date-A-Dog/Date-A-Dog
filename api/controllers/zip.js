function getZipcodes(zipcode, distance) {
    var PythonShell = require('python-shell');

    var options = {
        mode: 'text',
        pythonPath: '/usr/bin/python',
        scriptPath: '../zipcodes/zipcode_tools',
        args: [zipcode, distance]
    };

    PythonShell.run('main.py', options, function(err, res){
        if(err) throw err;
        return res;
    });
}