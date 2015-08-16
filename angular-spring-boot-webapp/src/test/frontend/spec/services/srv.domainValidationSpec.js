describe('Service: Domain', function () {
    'use strict';

    // load the controller's module
    beforeEach(module('ngSpringBootApp'));

    var service;

    // Initialize the service
    beforeEach(inject(function (Domain) {
        service = Domain;
    }));

    describe('user validation and security tests', function () {

        it('should validate user', function () {
            var validUser = service.User.build('user', '1', ['role1']);
            expect(validUser.isValid()).toBeTruthy();
            var invalidUser1 = service.User.build();
            expect(invalidUser1.isValid()).not.toBeTruthy();
            var invalidUser2 = service.User.build('anonymousUser');
            expect(invalidUser2.isValid()).not.toBeTruthy();
        });
    });
});
