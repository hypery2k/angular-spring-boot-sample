describe('Service: Domain', function () {
    'use strict';

    // load the controller's module
    beforeEach(module('ngSpringBootApp'));

    var service;

    // Initialize the service
    beforeEach(inject(function (Domain) {
        service = Domain;
    }));

    describe('Event', function () {

        it('should convert dates to strings to DTO', function () {
            var eventDTO = service.Event.build({
                'eventDescription': 'ss',
                'startDate': new Date(2015, 7, 13, 0, 0, 0, 0),
                'endDate': new Date(2014, 1, 11, 0, 0, 0, 0)
            }).convertToDTO();
            expect(eventDTO.startDate.getTime()).toBe(new Date(2015, 7, 13, 12, 0, 0, 0).getTime());
            expect(eventDTO.endDate.getTime()).toBe(new Date(2014, 1, 11, 12, 0, 0, 0).getTime());
        });

        // TODO more tests

    });
});
