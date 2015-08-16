app.factory('UserService', function ($resource) {
    'use strict';

    var userResource = $resource('/api/user/:userId', {userId: '@userId',}, {update: {method: 'PUT'}});

    return {
        saveUser: function (user, callback, errorCallback) {
            if (user.userId) {
                return userResource.update({userId: user.userId}, user, callback, errorCallback);
            } else {
                return userResource.save({}, user, callback, errorCallback);
            }
        },
        deleteUser: function(userId, callback, errorCallback){
        	return userResource.delete({userId: userId}, callback, errorCallback);
        }
    };
});