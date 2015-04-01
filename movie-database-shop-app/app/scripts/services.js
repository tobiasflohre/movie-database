(function() {
  'use strict';

  var module = angular.module('MovieDatabase.services', []);

  module.service('MovieService', ['InMemoryDatabase', function MovieService(
    InMemoryDatabase) {

    function getMovie(id) {
      var movies = InMemoryDatabase.load();
      for (var i = 0; i < movies.length; i++) {
        if (movies[i].id === id) {
          return movies[i];
        }
      }
      throw new Error('Movie (id=' + id + ') does not exist.');
    }

    this.getAll = function() {
      return InMemoryDatabase.load();
    };

    this.add = function(movie) {
      var movies = InMemoryDatabase.load();
      movies.push(movie);
      InMemoryDatabase.store(movies);
    };

    this.remove = function(id) {
      var movies = InMemoryDatabase.load();
      movies = movies.filter(function(movie) {
        return movie.id !== id;
      });
      InMemoryDatabase.store(movies);
    };

    this.edit = function(movie) {
      var originalMovie = getMovie(movie.id);
      originalMovie.title = movie.title;
      originalMovie.description = movie.description;
    };

    this.get = function(id) {
      return angular.copy(getMovie(id));
    };
  }]);

  module.service('InMemoryDatabase', function InMemoryDatabase() {
    var data = null;
    this.load = function() {
      if (data === null) {
        return [{
          id: '1',
          quantity: '11',
          price: '12,99 Euro',
          title: 'The Lord of the Rings: The Fellowship of the Ring',
          description: 'A meek hobbit of The Shire and eight companions set ' +
            'out on a journey to Mount Doom to destroy the One Ring and the ' +
            'dark lord Sauron.'
        }, {
          id: '2',
          quantity: '11',
          price: '12,99 Euro',
          title: 'The Lord of the Rings: The Two Towers',
          description: 'While Frodo and Sam edge closer to Mordor with the ' +
            'help of the shifty Gollum, the divided fellowship makes a stand ' +
            'against Sauron\'s new ally, Saruman, and his hordes of Isengard.'
        }];
      }
      return angular.copy(data);
    };
    this.store = function(dataToStore) {
      data = angular.copy(dataToStore);
    };
  });

})();
