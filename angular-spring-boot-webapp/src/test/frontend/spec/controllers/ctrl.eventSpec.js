'use strict';

describe('Controller: EventCtrl', function () {
    // load the controller's module

    beforeEach(function () {
        // load app module
        module('ngSpringBootApp');
        // load app mock module
        module('ngSpringBootAppMock');
    });

    var ctrl,
        scope,
        mockedFeedbackUIService,
        mockedEventService,
        mockedAuthService;

    // Initialize the controller and a mock scope
    beforeEach(inject(function ($controller, $rootScope, $translate, EventServicePositiveMock, AuthServicePositiveMock, feedbackUI) {
        scope = $rootScope.$new();
        mockedFeedbackUIService = feedbackUI;
        mockedEventService = EventServicePositiveMock;
        mockedAuthService = AuthServicePositiveMock;
        // init mocks
        ctrl = $controller('EventCtrl', {
            $scope: scope,
            $translate: $translate,
            feedbackUI: feedbackUI,
            // use mock module
            EventService: mockedEventService,
            AuthenticationService: mockedAuthService
        });
    }));

    it('should get event', function () {
        spyOn(mockedEventService, 'getEvent');
        scope.getEvent('1');
        scope.$apply();
        expect(mockedEventService.getEvent).toHaveBeenCalled();
    });

    it('should not allow transfer invalid values', function () {
        spyOn(mockedEventService, 'saveEvent');
        scope.saveEvent({$valid: false});
        scope.$apply();
        expect(mockedEventService.saveEvent).not.toHaveBeenCalled();
    });

    it('should not allow transfer valid values', function () {
        spyOn(mockedEventService, 'saveEvent');
        scope.saveEvent({$valid: true});
        scope.$apply();
        expect(mockedEventService.saveEvent).toHaveBeenCalled();
    });

    it('should delete event', function () {
        spyOn(mockedEventService, 'deleteEvent');
        spyOn(mockedEventService, 'getEvent').and.callThrough();
        scope.deleteEvent();
        scope.$apply();
        expect(mockedEventService.deleteEvent).toHaveBeenCalled();
    });

    it('should list all events for customer during init', function () {
        spyOn(mockedEventService, 'listAllEvents');
        scope.reset();
        expect(mockedEventService.listAllEvents).toHaveBeenCalled();
    });
});
