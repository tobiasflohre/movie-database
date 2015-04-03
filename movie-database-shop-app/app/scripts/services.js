(function() {
  'use strict';

  var module = angular.module('MovieDatabase.services', []);

  module.service('MovieService', ['$http', 'API_MOVIES_BASE',
    function MovieService($http, API_MOVIES_BASE) {

      function getDetailUrl(id) {
        return API_MOVIES_BASE + '/' + encodeURIComponent(id);
      }

      this.get = function(id) {
        return $http.get(getDetailUrl(id))
          .then(function(response) {
            return response.data;
          });
      };

      this.getAll = function() {
        return $http.get(API_MOVIES_BASE)
          .then(function(response) {
            return response.data;
          });
      };

      this.edit = function(movie) {
        return $http({
          method: 'put',
          url: getDetailUrl(movie.id),
          data: angular.toJson({
            id: movie.id,
            quantity: movie.quantity,
            price: movie.price
          }),
          headers: {
            'Content-Type': 'application/json'
          }
        });
      };

    }
  ]);

  module.service('HttpInterceptor', function($rootScope, $q) {
    this.responseError = function(rejection) {
      console.error('Response Error', arguments);
      $rootScope.$broadcast('network:error', rejection);
      return $q.reject(rejection);
    };
  });


})();
