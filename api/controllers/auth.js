var passport = require('passport');
var FacebookTokenStrategy = require('passport-facebook-token');

passport.use(new FacebookTokenStrategy({
    clientID: 1105437202885536,
    clientSecret: 'e0882edcccb66587e77e57cc09217952',
    fbGraphVersion: 'v2.8'
  }, function(accessToken, refreshToken, profile, done) {
    // console.log(profile);
    var user = {};
    user.id = profile._json.id;
    user.name = profile._json.name;
    user.last_name = profile._json.last_name;
    user.first_name = profile._json.first_name;
    return done(null, user);
  }
));

exports.isAuthenticated = passport.authenticate(['facebook-token'], { session : false });
