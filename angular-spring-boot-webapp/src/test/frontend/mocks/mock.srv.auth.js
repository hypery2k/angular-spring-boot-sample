mockApp.factory('AuthServicePositiveMock', function () {
        'use strict';
        return {
            login: function (credentials, callback, errorCallback) {
                callback(credentials);
            },
            logout: function (callback, errorCallback) {
                callback();
            }
        };
    }
);