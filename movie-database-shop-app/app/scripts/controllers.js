(function() {
  'use strict';

  var module = angular.module('MovieDatabase.controllers', ['ngRoute']);

  module.controller('ListCtrl', ['MovieService',
    'BASE_URL',
    'movies',
    function ListCtrl(MovieService,
      BASE_URL,
      movies) {
      var ctrl = this;
      ctrl.baseUrl = BASE_URL;
      ctrl.movies = movies;

    }
  ]);


  module.controller('EditCtrl', ['MovieService',
    '$location',
    'BASE_URL',
    'movie',
    function EditCtrl(MovieService,
      $location,
      BASE_URL,
      movie) {
      var ctrl = this;
      ctrl.movie = movie;

      ctrl.save = function() {
        MovieService.edit(ctrl.movie);
      };

      ctrl.back = function() {
        $location.path(BASE_URL);
      };

    }
  ]);

})();
