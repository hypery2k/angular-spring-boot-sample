/**
 * @ngdoc function
 * @name ngSpringBootApp.controller:NavCtrl
 * @description
 * Login Controller of the navigation bar
 */
app.controller('NavCtrl', function ($rootScope, $scope, $log, $translate, $location, feedbackUI, AuthenticationService) {
    'use strict';

    $scope.isActive = function (path) {
        if ($rootScope.user) {
            if ($location.path().substr(0, path.length) === path) {
                return true;
            }
            // add user check here
        } else {
            return false;
        }
        return false;
    };
    $scope.logout = function () {
        AuthenticationService.logout(function (response) {
            $rootScope.user = response;
            $log.info('Logout complete.');
            $location.path('/');
        }, function () {
            $log.error('Logout failure for user.');
            $translate('LOGOUT_ERROR').then(function (translatedValue) {
                feedbackUI.appendErrorMsg(translatedValue);
            });
        });
    };
});
