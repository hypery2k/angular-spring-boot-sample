mockApp.factory('UserServicePositiveMock', function (Domain) {
        'use strict';

        return {
            saveUser: function (user, callback, errorCallback) {
            	callback();
            }, 
            getControllerByCustomer: function (customerId, callback, errorCallback){
            	callback();
            },
            getPlanerByBranch: function(branchId, callback, errorCallback){
            	callback(Domain.UserProfile.build());
            },
            deleteUser: function(userId, callback, errorCallback){
            	callback();
            }
        };
    }
);