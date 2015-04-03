(function() {
  'use strict';

  var module = angular.module('MovieDatabase', ['MovieDatabase.services',
    'MovieDatabase.controllers',
    'ngAnimate',
    'ngRoute'
  ]);

  module.constant('BASE_URL', '/movies');
  module.constant('API_MOVIES_BASE',
    'http://moviedatabase.com/shop-rest/movies');

  module.config(['$routeProvider',
    '$httpProvider',
    'BASE_URL',
    function($routeProvider, $httpProvider,
      BASE_URL) {
      $routeProvider
        .when(BASE_URL, {
          templateUrl: 'partials/list.html',
          controller: 'ListCtrl',
          controllerAs: 'ctrl',
          resolve: {
            movies: ['MovieService', function(MovieService) {
              return MovieService.getAll();
            }]
          }
        })
        .when(BASE_URL + '/:movieId', {
          templateUrl: 'partials/form.html',
          controller: 'EditCtrl',
          controllerAs: 'ctrl',
          resolve: {
            movie: ['MovieService', '$route', function(MovieService,
              $route) {
              return MovieService.get($route.current.params.movieId);
            }]
          }
        })
        .otherwise({
          redirectTo: BASE_URL
        });

      function addDefaultHeader(key, value) {
        $httpProvider.defaults.headers.common[key] = value;
      }
      addDefaultHeader('Accept', 'application/json, application/hal+json');
      $httpProvider.interceptors.push('HttpInterceptor');

    }
  ]);

})();
