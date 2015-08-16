// Karma configuration
// http://karma-runner.github.io/0.12/config/configuration-file.html
// Generated on 2015-06-18 using
// generator-karma 1.0.0

module.exports = function (config) {
    'use strict';

    config.set({
        // enable / disable watching file and executing tests whenever any file changes
        autoWatch: true,

        // base path, that will be used to resolve files and exclude
        basePath: '.',

        // testing framework to use (jasmine/mocha/qunit/...)
        // as well as any additional frameworks (requirejs/chai/sinon/...)
        frameworks: [
            "jasmine"
        ],

        // list of files / patterns to load in the browser
        files: [
            // bower:js
            'bower_components/jquery/dist/jquery.js',
            'bower_components/angular/angular.js',
            'bower_components/angular-animate/angular-animate.js',
            'bower_components/angular-block-ui/dist/angular-block-ui.js',
            'bower_components/angular-bootstrap/ui-bootstrap-tpls.js',
            'bower_components/angular-cookies/angular-cookies.js',
            'bower_components/angular-invocation-handler/dist/angular-invocation-handler.js',
            'bower_components/angular-resource/angular-resource.js',
            'bower_components/angular-route/angular-route.js',
            'bower_components/angular-sanitize/angular-sanitize.js',
            'bower_components/angular-touch/angular-touch.js',
            'bower_components/angular-translate/angular-translate.js',
            'bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/affix.js',
            'bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/alert.js',
            'bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/button.js',
            'bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/carousel.js',
            'bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/collapse.js',
            'bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/dropdown.js',
            'bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/tab.js',
            'bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/transition.js',
            'bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/scrollspy.js',
            'bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/modal.js',
            'bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/tooltip.js',
            'bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/popover.js',
            'bower_components/ng-table/dist/ng-table.min.js',
            'bower_components/angular-mocks/angular-mocks.js',
            // endbower
            "src/main/frontend/scripts/**/*.js",
            "src/test/frontend/mocks/**/*.js",
            "src/test/frontend/spec/**/*.js"
        ],

        // list of files / patterns to exclude
        exclude: [],

        // web server port
        port: 9080,

        // Start these browsers, currently available:
        // - Chrome
        // - ChromeCanary
        // - Firefox
        // - Opera
        // - Safari (only Mac)
        // - PhantomJS
        // - IE (only Windows)
        browsers: [
            "PhantomJS"
        ],

        // Which plugins to enable
        plugins: [
            "karma-phantomjs-launcher",
            "karma-jasmine",
            "karma-junit-reporter"
        ],

        reporters: [
            "progress",
            "junit"
        ],

        junitReporter: {
            outputDir: 'target', // results will be saved as $outputDir/$browserName.xml
            suite: 'ng-spring-boot'
        },

        // Continuous Integration mode
        // if true, it capture browsers, run tests and exit
        singleRun: false,

        colors: true,

        // level of logging
        // possible values: LOG_DISABLE || LOG_ERROR || LOG_WARN || LOG_INFO || LOG_DEBUG
        logLevel: config.LOG_INFO,

        // Uncomment the following lines if you are using grunt's server to run the tests
        // proxies: {
        //   '/': 'http://localhost:9000/'
        // },
        // URL root prevent conflicts with the site root
        // urlRoot: '_karma_'
    });
};
