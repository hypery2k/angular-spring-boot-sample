app.factory('EventService', function ($resource, Domain) {
    'use strict';

    var eventResource = $resource('/api/events/:eventId', {eventId: '@eventId'}, {update: {method: 'PUT'}});

    return {
        listAllEvents: function (callback, errorCallback) {
            return eventResource.query({}, function (events) {
                var result = [];
                if (events && events.length > 0) {
                    for (var i = 0; i < events.length; i++) {
                        result.push(Domain.Event.build(events[i]).convertFromDTO());
                    }
                }
                callback(result);
            }, errorCallback);
        },
        getEvent: function (eventId, callback, errorCallback) {
            return eventResource.get({eventId: eventId}, function (event) {
                var result = null;
                if (event) {
                    result = Domain.Event.build(event).convertFromDTO();
                }
                callback(result);
            }, errorCallback);
        },
        saveEvent: function (event, callback, errorCallback) {
            if (event.eventId) {
                return eventResource.update({eventId: event.eventId}, event.convertToDTO(), callback, errorCallback);
            } else {
                return eventResource.save({}, event.convertToDTO(), callback, errorCallback);
            }
        },
        deleteEvent: function (event, callback, errorCallback) {
            return eventResource.delete({eventId: event.eventId}, event.convertToDTO(), callback, errorCallback);
        }
    };
});