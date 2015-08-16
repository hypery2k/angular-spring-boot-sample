'use strict';

describe('Controller: LoginCtrl', function () {

    beforeEach(function () {
        // load app module
        module('ngSpringBootApp');
        // load app mock module
        module('ngSpringBootAppMock');
    });

    var controller,
        mockedFeedbackUIService,
        mockedLoginService,
        rootScope,
        scope;

    // Initialize the controller and a mock scope
    beforeEach(inject(function ($controller, $rootScope, $translate, AuthServicePositiveMock, feedbackUI) {
        rootScope = $rootScope;
        scope = $rootScope.$new();
        mockedFeedbackUIService = feedbackUI;
        mockedLoginService = AuthServicePositiveMock;
        // init mocks
        controller = $controller('NavCtrl', {
            $scope: scope,
            $translate: $translate,
            feedbackUI: mockedFeedbackUIService,
            // use mock module
            AuthenticationService: mockedLoginService
        });
        spyOn(mockedFeedbackUIService, 'appendErrorMsg');
        spyOn(mockedLoginService, 'login').and.callThrough();
        spyOn(mockedLoginService, 'logout').and.callThrough();
    }));

    it('should logout successfully', function () {
        rootScope.user = {
            username: 'abc',
            password: 'pass'
        };
        scope.logout();
        scope.$apply();
        expect(mockedLoginService.logout).toHaveBeenCalled();
        expect(rootScope.user).not.toBeDefined();
    });
});
