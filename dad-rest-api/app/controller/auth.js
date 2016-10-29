var passport = require('passport');
var FacebookTokenStrategy = require('passport-facebook-token');

passport.use('facebook-token', new FacebookTokenStrategy({
    clientID: '1831387340406886',
    clientSecret: '0b81472405f89f038be4185f4cf68767'
  }, function(accessToken, refreshToken, profile, done) {
    console.log(profile);

    return done(null, profile); // accessible as `req.profile`
  }
));

exports.isAuthenticated = passport.authenticate(['facebook-token']);
