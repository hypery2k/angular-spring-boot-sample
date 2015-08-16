describe('Service: AuthenticationService', function () {
    'use strict';

    // load the controller's module
    beforeEach(module('ngSpringBootApp'));

    var service,
        scope,
        rootScope,
        location,
        httpBackend;

    // Initialize the service
    beforeEach(inject(function (AuthenticationService, $location, $rootScope, $httpBackend) {
        rootScope = $rootScope;
        scope = $rootScope.$new();
        location = $location;
        service = AuthenticationService;
        httpBackend = $httpBackend;
    }));


    describe('error handling', function () {

        it('should login with spring errors', function (done) {
            httpBackend.expectPOST('/login').respond(400);
            service.login({username: 'abc', password: '123'}, function () {
                fail();
            }, function (user) {
                expect(user.isValid()).not.toBeTruthy();
                done();
            });
            httpBackend.flush();
        });

        it('should login with user errors', function (done) {
            httpBackend.expectPOST('/login').respond(200);
            httpBackend.expectGET('/api/login/user').respond(400, {
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

        it('should logout on server unreachable (HTTP Status 0)', function (done) {
            httpBackend.expectGET('/api/login/user').respond(0);
            rootScope.$broadcast('$routeChangeStart', {});
            httpBackend.flush();
            expect(rootScope.user.isValid()).not.toBeTruthy();
            done();
        });

        it('should logout with errors', function (done) {
            httpBackend.expectPOST('/logout').respond(400);
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
            httpBackend.expectPOST('/login').respond(200);
            httpBackend.expectGET('/api/login/user').respond(200, {
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

        it('should always revalidate session', function (done) {
            httpBackend.expectGET('/api/login/user').respond(200);
            rootScope.$broadcast('$routeChangeStart', {});
            httpBackend.flush();
            expect(rootScope.user.isValid()).not.toBeTruthy();
            done();
        });

        it('should logout successfully', function (done) {
            httpBackend.expectPOST('/logout').respond(200);
            service.logout(function (user) {
                expect(user.isValid()).not.toBeTruthy();
                done();
            });
            httpBackend.flush();
        });

        it('should revalidate existing user session', function (done) {
            httpBackend.expectGET('/api/login/user').respond(200, {
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

        it('should invalidate non existing user session', function (done) {
            httpBackend.expectGET('/api/login/user').respond(400);
            service.checkUser(function () {
                fail();
            }, function (user) {
                expect(user.isValid()).not.toBeTruthy();
                done();
            });
            httpBackend.flush();
        });

        it('should handle invalid user details', function (done) {
            httpBackend.expectGET('/api/login/user').respond(200, {
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

        it('should omit redirection loops after login redirect', function () {
            spyOn(location, 'path');
            httpBackend.expectGET('/api/login/user').respond(400);
            // redirect from index page
            service.validateUser({name: 'test'}, {}, {loadedTemplateUrl: 'index.html'});
            httpBackend.flush();
            expect(rootScope.user.isValid()).not.toBeTruthy();
            // check again (as done by route change in app.js
            httpBackend.expectGET('/api/login/user').respond(400);
            service.validateUser({name: 'test'}, {}, {loadedTemplateUrl: 'views/login.html'});
            httpBackend.flush();
            expect(rootScope.user.isValid()).not.toBeTruthy();
            expect(location.path.calls.count()).toBe(1);
        });
    });
});
