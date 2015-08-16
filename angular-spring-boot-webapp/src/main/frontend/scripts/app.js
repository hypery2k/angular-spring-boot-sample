/**
 * @ngdoc overview
 * @name ngSpringBootApp
 * @description
 *
 * Main module of the application.
 */

/*global app: true*/
var app = angular.module('ngSpringBootApp', [
    'ngAnimate',
    'ngCookies',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ui.bootstrap',
    'blockUI',
    'pascalprecht.translate',
    'ngTouch',
    'ngTable',
    'ngIH.core',
    'ngIH.ui'
]);


app.config(function ($provide, ngIHServiceProvider, ngIHConfig) {
    'use strict';

    // enable UI feedback attach
    ngIHConfig.feedbackAttach = true;
    ngIHConfig.customErrorHandler = 'ErrorHandlingService';
    // decorate the mentioned [services] with automatic error handling.
    ngIHServiceProvider.decorate($provide, ['EventService']);
    ngIHServiceProvider.decorate($provide, ['UserService']);
});


app.config(function ($routeProvider) {
    'use strict';

    $routeProvider
        .when('/home', {
            templateUrl: 'views/main.html',
            controller: 'MainCtrl'
        })
        .when('/event', {
            templateUrl: 'views/event.html',
            controller: 'EventCtrl'
        })
        .when('/loginArea', {
            templateUrl: 'views/login.html',
            controller: 'LoginCtrl'
        })
        .otherwise({
            redirectTo: '/loginArea'
        });
});

app.config(function ($translateProvider) {
    'use strict';

    $translateProvider.translations('de_DE', {
        GENERAL_LOADING: 'Lade',
        GENERAL_TITLE: 'ngSpringBoot',
        GENERAL_EDIT: 'Bearbeiten',
        GENERAL_DELETE: 'Löschen',
        GENERAL_ALL: 'Alle',
        GENERAL_NEW: 'Neu',
        GENERAL_SAVE: 'Speichern',
        GENERAL_CANCEL: 'Abbrechen',
        GENERAL_CLOSE: 'Schließen',
        GENERAL_TODAY: 'Heute',
        GENERAL_BEGIN: 'Beginn',
        GENERAL_END: 'Ende',
        GENERAL_YES: 'Ja',
        GENERAL_NO: 'Nein',
        GENERAL_CHOOSE: 'Bitte auswählen',
        GENERAL_ADD: 'Neuer Eintrag',
        GENERAL_UPLOAD: 'Hochladen',

        MAIN_HEADLINE: 'AngularJS Spring Boot Demo',
        MAIN_GREETING: 'Demo Anwendung die die Verwendung von AngularJS und SpringBoot zeigt',
        MAIN_FIRST_HEADLINE: 'AngularJS',
        MAIN_FIRST_TEXT: 'AngularJS – oft einfach als Angular bezeichnet – ist ein Open-Source-Framework des US-amerikanischen Unternehmens Google Inc. Mit AngularJS kann man in HTML und JavaScript Single-page-Webanwendungen nach einem Model View ViewModel-Muster erstellen. Die Softwareentwicklung und das Komponententesten können damit vereinfacht werden.',
        MAIN_SECOND_HEADLINE: 'SpringBoot',
        MAIN_SECOND_TEXT: 'Spring Boot heißt das neue Projekt der Entwickler des Spring Frameworks für die Java-Plattform. Spring Boot soll also eine Art Einstiegspunkt für Entwickler bieten, von dem aus sie auf die Funktionen von Spring zugreifen können, um damit Anwendungen und Dienste zu erstellen.',
        MAIN_THIRD_HEADLINE: 'Swagger',
        MAIN_THIRD_TEXT: 'm Funktionalität und Möglichkeiten einer API zu überschauen, ist die Qualität der API-Dokumentation ausschlaggebend. Mit Swagger UI behalten wir immer den Überblick, können andere Personen die API bereitstellen oder häufig genutzte Schnittstellen dokumentieren.',

        EVENT_HEADLINE: 'Eventdaten',
        EVENT_SELECTION_HEADING: 'Eventdaten auswählen',
        EVENT_CREATION_DATE: 'Eintragung',
        EVENT_DESCRIPTION: 'Ereignis',
        EVENT_DESCRIPTION_REQUIRED: 'Die Ereignisbezeichnung ist erforderlich',
        EVENT_START_DATE: 'Beginn',
        EVENT_END_DATE: 'Ende',
        EVENT_EDIT_HEADING: 'Eventdaten bearbeiten',
        EVENT_DELETE_SUCCESS: 'Event wurde gelöscht.',
        EVENT_SAVE_SUCCESS: 'Event wurde erfolgreich gespeichert.',

        LOGIN_HEADLINE: 'Willkommen zur Angular SpringBoot Demo',
        LOGIN_GREETING: 'Melden Sie sich an um das Portal zu nutzen.',
        LOGIN_FORM_USERNAME: 'Nutzer',
        LOGIN_FORM_PASSWORD: 'Passwort',
        LOGIN_FORM_SUBMIT: 'Anmelden',
        LOGIN_ERROR: 'Anmeldefehler. Bitte versuchen Sie erneut sich anzumelden.',
        LOGIN_DENIED: 'Anmeldefehler. Der angebene Nutzer ist nicht berechtigt für die Oberfläche.',
        NAV_EVENTS: 'Events',
        NAV_LOGOUT: 'Abmelden',

        LOGOUT_ERROR: 'Fehler beim Abmelden',

        HTTP_STATUS_CODE_0: 'Der Server antwortet nicht.',
        HTTP_STATUS_CODE_400: 'Validierungsfehler.',
        HTTP_STATUS_CODE_403: 'Zugriff verweigert.',
        HTTP_STATUS_CODE_404: 'Dieser Datensatz existiert nicht.',
        HTTP_STATUS_CODE_405: 'Ungültige Anfrage.',
        HTTP_STATUS_CODE_409: 'Es besteht ein Datenkonflikt.',
        HTTP_STATUS_CODE_500: 'Unbekannter Serverfehler.',

        // bean validation messages
        'VALIDATION_ERROR_validation.jpa.unique': 'Ein ähnlicher Datensatz existiert bereits',
        'VALIDATION_ERROR_validation.jpa.unknown': 'Prüfen Sie ihre Daten',
        'VALIDATION_ERROR_validation.passwords_not_match': 'Die Passwordbestätigung ist nicht korrekt',
        'VALIDATION_ERROR_validation.date.range_error': 'Das Startdatum liegt nicht vor dem Enddatum.',
        'VALIDATION_ERROR_validation.time.range_error': 'Der Beginn der Uhrzeit liegt nicht vor dem Ende.',
        'VALIDATION_ERROR_javax.validation.constraints.NotNull.message': 'Bitte füllen Sie alle Pflichtfelder aus.   ',
        'VALIDATION_ERROR_javax.validation.constraints.Size.message': 'Die maximale Länge wurde überschritten.',
    });

    $translateProvider.preferredLanguage('de_DE');
    $translateProvider.useSanitizeValueStrategy('escape');
});

app.config(function (blockUIConfig) {
    'use strict';

    // Change the default overlay message
    blockUIConfig.message = 'Lade ...';

    // Change the default delay to 100ms before the blocking is visible
    blockUIConfig.delay = 10;
});

app.run(function ($rootScope, AuthenticationService) {
    'use strict';

    // register listener to watch route changes
    $rootScope.$on('$routeChangeStart', function (event, next, current) {
        AuthenticationService.validateUser(event, next, current);
    });
});
