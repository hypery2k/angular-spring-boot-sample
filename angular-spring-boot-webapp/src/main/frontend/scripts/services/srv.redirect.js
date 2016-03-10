app.factory('RedirectService', function ($window) {
    'use strict';

    return {
        redirect: function (path) {
            $window.location.href = path;
        }
    };
});