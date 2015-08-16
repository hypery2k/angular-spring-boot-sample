var baseConfig = require('./karma.conf.js');

module.exports = function (config) {
    // Load base config
    baseConfig(config);

    // Override base config
    config.set({
        junitReporter: {
            outputDir: 'target/surefire-reports', // results will be saved as $outputDir/$browserName.xml
            suite: 'ng-spring-boot'
        },
        singleRun: true,
        autoWatch: false
    });
};