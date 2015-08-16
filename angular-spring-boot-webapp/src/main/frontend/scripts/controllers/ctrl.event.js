app.controller('EventCtrl', function ($scope, $log, $translate, ngTableParams, feedbackUI, EventService, Domain) {
        'use strict';

        // internal functions

        function init() {
            $scope.currentEvent = false;
            loadEvents();
        }

        function loadEvents() {
            EventService.listAllEvents(function (events) {
                $scope.allEvents = [];
                if (events) {
                    for (var i = 0; i < events.length; i++) {

                        $scope.allEvents.push(events[i]);
                    }
                }
                // init pagination
                $scope.tableParams = new ngTableParams({ // jshint ignore:line
                    page: 1,            // show first page
                    count: 10           // count per page
                }, {
                    total: $scope.allEvents.length, // length of data
                    getData: function ($defer, params) {
                        $defer.resolve($scope.allEvents.slice((params.page() - 1) * params.count(), params.page() * params.count()));
                    }
                });
            });
        }

        // init the controller
        init();

        // view API

        // datepicker ui bootstrap
        $scope.openStartDate = function ($event) {
            $event.preventDefault();
            $event.stopPropagation();
            $scope.startDateOpened = true;
        };
        $scope.openEndDate = function ($event) {
            $event.preventDefault();
            $event.stopPropagation();
            $scope.endDateOpened = true;
        };

        $scope.getEvent = function (eventId) {
            EventService.getEvent(eventId, function (event) {
                $scope.currentEvent = event;
            });
        };
        $scope.deleteEvent = function (event) {
            EventService.deleteEvent(event, function () {
                // success
                init();
                $translate('EVENT_DELETE_SUCCESS').then(function (translatedValue) {
                    feedbackUI.appendInfoMsg(translatedValue);
                });
            });
        };

        $scope.reset = function () {
            init();
        };

        $scope.createEvent = function () {
            $scope.currentEvent = Domain.Event.build();
        };

        $scope.saveEvent = function (form) {
            // only submit valid from
            if (form.$valid) {
                EventService.saveEvent($scope.currentEvent, function () {
                    // success
                    init();
                    $translate('EVENT_SAVE_SUCCESS').then(function (translatedValue) {
                        feedbackUI.appendInfoMsg(translatedValue);
                    });
                });
            }
        };
    }
);