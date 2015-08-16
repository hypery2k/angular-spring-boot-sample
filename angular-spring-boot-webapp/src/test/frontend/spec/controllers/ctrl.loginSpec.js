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
        mockedAuthService,
        rootScope,
        scope;

    // Initialize the controller and a mock scope
    beforeEach(inject(function ($controller, $rootScope, $translate, AuthServicePositiveMock, feedbackUI) {
        scope = $rootScope.$new();
        rootScope = $rootScope;
        mockedFeedbackUIService = feedbackUI;
        mockedAuthService = AuthServicePositiveMock;
        // init mocks
        controller = $controller('LoginCtrl', {
            $scope: scope,
            $translate: $translate,
            // use mock module
            feedbackUI: mockedFeedbackUIService,
            AuthenticationService: mockedAuthService
        });
        spyOn(mockedFeedbackUIService, 'appendErrorMsg');
        spyOn(mockedAuthService, 'login').and.callThrough();
        spyOn(mockedAuthService, 'logout').and.callThrough();
    }));

    it('should login successfully', function () {
        scope.credentials = {
            username: 'abc',
            password: 'pass',
            isValid: function () {
                return true;
            },
            hasRight: function () {
                return true;
            }
        };
        scope.login({$valid: true});
        scope.$apply();
        expect(mockedAuthService.login).toHaveBeenCalled();
        expect(rootScope.user.username).toBe('abc');
    });

    it('should not login without required fields', function () {
        scope.login({$valid: false});
        scope.$apply();
        expect(mockedAuthService.login).not.toHaveBeenCalled();
    });
});
