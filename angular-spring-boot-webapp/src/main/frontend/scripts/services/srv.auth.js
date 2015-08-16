app.factory('AuthenticationService', function ($rootScope, $log, $location, $resource, $http, Domain) {
    'use strict';

    var currentUser,
        whiteListedUrls = [],
        resource = $resource('/api/login/user'),
        loginHandlerSpring = '/login',
        logoutHandlerSpring = '/logout';

    function redirectToLoginPage(event, next, current) {
        if (event && next && current) {
            $log.debug('Current event is: ' + event.name);
            // no logged user, we should be going to #login
            if (next.templateUrl === 'views/login.html' || (current && current.loadedTemplateUrl === 'views/login.html')) {
                // already going to #login, no redirect needed
            } else {
                // not going to #login, we should redirect now
                $location.path('/loginPage');
            }
        } else {
            // fallback
            $location.path('/loginPage');
        }
    }

    function validateUser(event, next, current) {
        getUserDetails(function (user) {
            currentUser = user;
            $rootScope.user = user;
            if (user.isValid()) {
                $log.info('Login revalidation was sucessfull for user ' + user.username);
                if (!next || !next.loadedTemplateUrl) {
                    // valid users without resolved template get redirected to home
                    $location.path('/home');
                }
            } else {
                // check white listed URLs
                if (whiteListedUrls.indexOf(next.loadedTemplateUrl) === -1) {
                    redirectToLoginPage(event, next, current);
                }
            }
        }, function (user) {
            $rootScope.user = user;
            redirectToLoginPage(event, next, current);
        });
    }

    function getUserDetails(callback, errorCallback) {
        return resource.get({},
            function (response) {
                $log.info('Got a valid user response');
                callback(Domain.User.build(response.username, response.userId, response.permissions, response.customers));
            },
            function () {
                $log.error('Could not read user details.');
                errorCallback(Domain.User.build());
            });
    }

    return {
        login: function (credentials, callback, errorCallback) {
            $http({
                method: 'POST',
                url: loginHandlerSpring,
                data: credentials,
                transformRequest: function (obj) {
                    var str = [];
                    for (var p in obj) {
                        str.push(encodeURIComponent(p) + '=' + encodeURIComponent(obj[p]));
                    }
                    return str.join('&');
                },
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }).success(function () {
                getUserDetails(callback, errorCallback);
            }).error(function () {
                $log.error('Got a login error');
                errorCallback(Domain.User.build());
            });
        },
        checkUser: function (callback, errorCallback) {
            getUserDetails(callback, errorCallback);
        },
        getCurrentUser: function () {
            if (!currentUser || !currentUser.customers) {
                validateUser();
            }
            if (!currentUser.isValid()) {
                redirectToLoginPage();
            }
            return currentUser;
        },
        validateUser: validateUser,
        logout: function (callback, errorCallback) {
            $http.post(logoutHandlerSpring).
                success(function () {
                    callback(Domain.User.build());
                }).
                error(function () {
                    $log.error('Got a logout error');
                    errorCallback(Domain.User.build());
                });
        }
    };
});