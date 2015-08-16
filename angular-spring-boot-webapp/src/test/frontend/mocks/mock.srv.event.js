mockApp.factory('EventServicePositiveMock', function () {
        'use strict';
        return {
            listAllEvents: function (callback, errorCallback) {
                callback();
            },
            getEvent: function (eventId, callback, errorCallback) {
                callback({eventCategory: 'eventCategory'});
            },
            saveEvent: function (event, callback, errorCallback) {
                callback();
            },
            deleteEvent: function (event, callback, errorCallback) {
                callback();
            }
        };
    }
);