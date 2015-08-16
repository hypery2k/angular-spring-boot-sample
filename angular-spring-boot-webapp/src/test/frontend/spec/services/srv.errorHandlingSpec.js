describe('Service: ErrorHandling', function () {
    'use strict';

    // load the controller's module
    beforeEach(module('ngSpringBootApp'));

    var service, rootScope;

    // Initialize the service
    beforeEach(inject(function ($rootScope, ErrorHandlingService) {
        rootScope = $rootScope;
        service = ErrorHandlingService;
    }));

    it('should build validation messages for the http code 400', function (done) {
        service.resolve({
            error: {
                data: [{'messageTemplate': 'validation.date.range_error'}]
            },
            status: 500
        }, function (msg) {
            if (msg) {
                done();
            }
        });
        rootScope.$apply();
    });

    it('should build default error messages for the http code 403', function (done) {
        service.resolve({error: {}, status: 403}, function (msg) {
            if (msg) {
                done();
            }
        });
        rootScope.$apply();
    });

    it('should build default error messages for the http code 404', function (done) {
        service.resolve({error: {}, status: 404}, function (msg) {
            if (msg) {
                done();
            }
        });
        rootScope.$apply();
    });

    it('should build default error messages for the http code 405', function (done) {
        service.resolve({error: {}, status: 405}, function (msg) {
            if (msg) {
                done();
            }
        });
        rootScope.$apply();
    });

    it('should build default error messages for the http code 409', function (done) {
        service.resolve({error: {}, status: 409}, function (msg) {
            if (msg) {
                done();
            }
        });
        rootScope.$apply();
    });

    it('should build default error messages for the http code 500', function (done) {
        service.resolve({error: {}, status: 500}, function (msg) {
            if (msg) {
                done();
            }
        });
        rootScope.$apply();
    });
});
