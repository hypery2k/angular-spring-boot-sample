app.factory('ErrorHandlingService', function ($log, $translate, blockUI) {
    'use strict';

    function buildValidationMessages(error, status, msg, callback, i) {
        var errorDetails = error.data[i];
        $translate('VALIDATION_ERROR_' + errorDetails.messageTemplate).then(function (translatedValue) {
            msg = msg + ' ' + translatedValue;

            // replace placeholder if set
            if (errorDetails.propertyList) {
                msg = msg.format(errorDetails.propertyList);
            }

            // callback when complete
            if (i === error.data.length - 1) {
                $log.debug(status + '=>' + msg);
                callback(msg);
            }
        }, function (err) {
            $log.error(err);
            callback(msg);
        });
    }

    return {
        resolve: function (details, callback) {
            if (details.error) {
                var error = details.error;
                var status = details.status;
                // read by http code
                $translate('HTTP_STATUS_CODE_' + status).then(function (translatedValue) {
                    var msg = translatedValue;
                    // handle violation errors
                    if (status === 400 && error.data && error.data.length) {
                        for (var i = 0; i < error.data.length; i++) {
                            blockUI.stop();
                            buildValidationMessages(error, status, msg, callback, i);
                        }
                    } else {
                        blockUI.stop();
                        $log.debug('Got unkown error status ' + status + ':' + msg);
                        callback(msg);
                    }
                });
            }
        }
    };
});