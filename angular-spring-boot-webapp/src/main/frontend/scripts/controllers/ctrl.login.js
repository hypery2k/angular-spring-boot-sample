/**
 * @ngdoc function
 * @name ngSpringBootApp.controller:LoginCtrl
 * @description
 * Login controller of the ngSpringBootApp
 */
app.controller('LoginCtrl', function ($rootScope, $scope, $log, $location, $translate, AuthenticationService, feedbackUI) {
    'use strict';

    // internal functions

    function init() {
        $scope.credentials = {};
    }


    // init the controller
    init();

    // view API


    $scope.login = function (loginForm) {
        if (loginForm.$valid) {
            AuthenticationService.login($scope.credentials, function (user) {
                $rootScope.user = user;
                if (user.isValid()) {
                    $log.info('Login was sucessfull for user ' + user.username);
                    $location.path('/home');

                } else {
                    $log.error('Login failure for user ' + $scope.credentials.username);
                    $translate('LOGIN_ERROR').then(function (translatedValue) {
                        feedbackUI.appendErrorMsg(translatedValue);
                    });
                }
            }, function (user) {
                $rootScope.user = user;
                $log.error('Login failure for user ' + $scope.credentials.username);
                $translate('LOGIN_ERROR').then(function (translatedValue) {
                    feedbackUI.appendErrorMsg(translatedValue);
                });
            });
        }
    };
});
