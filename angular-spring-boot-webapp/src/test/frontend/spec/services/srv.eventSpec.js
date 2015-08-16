describe('Service: EventService', function () {
    'use strict';

    // load the controller's module
    beforeEach(module('ngSpringBootApp'));

    var service,
        domain,
        scope,
        httpBackend;

    // Initialize the service
    beforeEach(inject(function (EventService, $rootScope, $httpBackend, Domain) {
        scope = $rootScope.$new();
        service = EventService;
        httpBackend = $httpBackend;
        domain = Domain;
    }));

    describe('error handling', function () {
        it('should not call normal handler', function (done) {
            httpBackend.expectGET('/api/events').respond(400);
            service.listAllEvents(function () {
                fail();
            }, function () {
                done();
            });
            httpBackend.flush();
        });
    });

    describe('normal handling', function () {

        describe('read', function () {

            it('should list events', function (done) {
                var events;
                httpBackend.expectGET('/api/events').respond(200, [{
                    'insertDate': '2015-07-07',
                    'deleted': false,
                    'eventId': '144',
                    'eventDescription': 'Beschreibung',
                    'startDate': '2015-07-17',
                    'endDate': '2015-07-17'
                }, {
                    'insertDate': '2015-07-07',
                    'deleted': false,
                    'eventId': '145',
                    'eventDescription': 'Beschreibung',
                    'startDate': '2015-07-28',
                    'endDate': '2015-07-30'
                }]);
                service.listAllEvents(function (response) {
                    events = response;
                    expect(events.length).toBe(2);
                    expect(events[0].eventId).toBe('144');
                    expect(events[1].eventId).toBe('145');
                    done();
                });
                httpBackend.flush();
            });

            it('get event by eventId for customer', function (done) {
                var event;
                httpBackend.expectGET('/api/events/1').respond(200, {
                    'insertDate': '2015-07-07',
                    'deleted': false,
                    'eventId': '145',
                    'eventDescription': 'Beschreibung',
                    'startDate': '2015-07-28',
                    'endDate': '2015-07-30'
                });
                service.getEvent('1', function (response) {
                    event = response;
                    expect(event.eventId).toBe('145');
                    done();
                });
                httpBackend.flush();
            });
        });

        describe('write', function () {

            it('should create event', function (done) {
                httpBackend.expectPOST('/api/events').respond(200);
                service.saveEvent(domain.Event.build({
                    insertDate: new Date(),
                    eventDescription: 'event',
                    startDate: new Date(),
                    endDate: new Date()
                }), function () {
                    done();
                });
                httpBackend.flush();
            });

            it('should update event', function (done) {
                httpBackend.expectPUT('/api/events/1').respond(200);
                service.saveEvent(domain.Event.build({
                    insertDate: new Date(),
                    eventId: 1,
                    eventDescription: 'event',
                    startDate: new Date(),
                    endDate: new Date()
                }), function () {
                    done();
                });
                httpBackend.flush();
            });

        });
    });
});
