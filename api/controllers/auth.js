var passport = require('passport');
var FacebookTokenStrategy = require('passport-facebook-token');

passport.use(new FacebookTokenStrategy({
    clientID: 1105437202885536,
    clientSecret: 'e0882edcccb66587e77e57cc09217952',
    fbGraphVersion: 'v2.8'
  }, function(accessToken, refreshToken, profile, done) {
    // console.log(profile);
    return done(null, profile);
  }
));

exports.isAuthenticated = passport.authenticate(['facebook-token'], { session : false });
