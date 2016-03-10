describe('Service: authenticationService', function () {
    'use strict';


    // load the controller's module
    beforeEach(module('ngSpringBootAppMock'));

    var service,
        redirectService,
        rootScope,
        httpBackend;

    // Initialize the service
    beforeEach(inject(function (_$rootScope_,
                                _$httpBackend_,
                                RedirectService,
                                AuthenticationService) {
        rootScope = _$rootScope_;
        redirectService = RedirectService;
        service = AuthenticationService;
        httpBackend = _$httpBackend_;
        spyOn(redirectService, 'redirect').and.callFake(function () {
        });
    }));

    describe('error handling', function () {

        it('should not login with spring errors', function (done) {
            httpBackend.expectPOST('./login').respond(400);
            service.login({username: 'abc', password: '123'}, function () {
                fail();
            }, function (user) {
                expect(user.isValid()).not.toBeTruthy();
                done();
            });
            httpBackend.flush();
        });

        it('should login with user errors', function (done) {
            httpBackend.expectPOST('./login').respond(200);
            httpBackend.expectGET('./api/login/user').respond(400, {
                username: 'abcUser',
                userRoles: ['role1', 'roles2']
            });
            service.login({username: 'abc', password: '123'}, function () {
                fail();
            }, function (user) {
                expect(user.isValid()).not.toBeTruthy();
                done();
            });
            httpBackend.flush();
        });

        it('should logout with errors', function (done) {
            httpBackend.expectPOST('./logout').respond(400);
            service.logout(function () {
                    fail();
                }, function (user) {
                    expect(user.isValid()).not.toBeTruthy();
                    done();
                }
            );
            httpBackend.flush();
        });
    });

    describe('normal handling', function () {

        it('should login successfully', function (done) {
            httpBackend.expectPOST('./login').respond(200);
            httpBackend.expectGET('./api/login/user').respond(200, {
                username: 'abcUser',
                userRoles: ['role1', 'roles2']
            });
            service.login({username: 'abc', password: '123'}, function (user) {
                expect(user.isValid()).toBeTruthy();
                expect(user.username).toBe('abcUser');
                done();
            });
            httpBackend.flush();
        });

        it('should revalidate existing user session', function (done) {
            httpBackend.expectGET('./api/login/user').respond(200, {
                username: 'abcUser',
                userRoles: ['role1', 'roles2']
            });
            service.checkUser(function (user) {
                expect(user.isValid()).toBeTruthy();
                done();
            }, function () {
                fail();
            });
            httpBackend.flush();
        });

        it('should logout successfully', function (done) {
            httpBackend.expectPOST('./logout').respond(200);
            service.logout(function (user) {
                expect(user.isValid()).not.toBeTruthy();
                done();
            });
            httpBackend.flush();
        });

        it('should invalidate non existing user session', function (done) {
            httpBackend.expectGET('./api/login/user').respond(400);
            service.checkUser(function () {
                fail();
            }, function (user) {
                expect(user.isValid()).not.toBeTruthy();
                done();
            });
            httpBackend.flush();
        });

        it('should handle invalid user details', function (done) {
            httpBackend.expectGET('./api/login/user').respond(200, {
                user: 'abcUser'
            });
            service.checkUser(function (user) {
                expect(user.isValid()).not.toBeTruthy();
                done();
            }, function () {
                fail();
            });
            httpBackend.flush();
        });
    });
});
