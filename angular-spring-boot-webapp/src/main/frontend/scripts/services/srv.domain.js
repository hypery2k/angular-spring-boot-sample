app.factory('Domain', function () {
    'use strict';

    // reference for base
    var referenceBaseDateHours = new Date(1970, 1, 2, 0, 0, 0, 0),
    // use for date DTO normalization
        normalizerBaseDateHours = new Date(1970, 1, 2, 12, 0, 0, 0);

    // HELPER functions

    function convertDateStringToDate(time) {
        if (time) {
            var date = new Date(referenceBaseDateHours.getTime()),
                res = time.split('-');
            date.setYear(res[0]);
            date.setMonth(res[1]);
            date.setDate(res[2]);
            return date;
        } else {
            return null;
        }
    }

    /**
     * User Object
     */
    function User(username, userId, permissions, customers) {
        this.username = username;
        this.userId = userId;
        this.permissions = permissions;
        this.customers = customers;
    }


    //Public object methods, assigned to prototype


    User.prototype.isValid = function () {
        if (this.username && this.username !== 'anonymousUser') {
            return true;
        } else {
            return false;
        }
    };
    // Static method, assigned to class, e.g. builders
    // Instance ('this') is not available in static context
    User.build = function (username, userId, permissions, customers) {
        return new User(username, userId, permissions, customers);

    };

    /**
     * Event Object
     */
    function Event() {
        this.eventId = null;
        this.eventDescription = null;
        this.insertDate = null;
        this.startDate = null;
        this.endDate = null;
    }


    Event.prototype.convertToDTO = function () {
        var date = new Date(normalizerBaseDateHours.getTime());
        this.startDate.setHours(date.getHours());
        this.startDate.setMinutes(date.getMinutes());
        if (this.endDate) {
            this.endDate.setHours(date.getHours());
            this.endDate.setMinutes(date.getMinutes());
        }
        return this;
    };

    Event.prototype.convertFromDTO = function () {
        // easy way to get a clean copy
        var copy = Event.build(this);
        // convert times
        if (copy.startDate) {
            copy.startDate = convertDateStringToDate(this.startDate);
        }
        if (copy.endDate) {
            copy.endDate = convertDateStringToDate(this.endDate);
        }
        return copy;
    };
    
    function UserProfile(){
    	this.userName = null;
    	this.password = null;
    	this.passwordConfirmation = null;
    	this.customerName = null;
    	this.customerId = null;
    	this.branchNumber = null;
    }
    
    UserProfile.prototype.isValid = function () {
    	if(this.userName && this.password && this.passwordConfirmation && this.customerName && this.password === this.passwordConfirmation){
    		return true;
    	}
    	return false;
    };
    
    UserProfile.build = function(data){
    	if(!data) {
    		return new UserProfile();
    	}else{
    		var userProfile = new UserProfile();
    		for(var key in data){
                if (key.charAt(0) !== '$' && data.hasOwnProperty(key)) {
    				userProfile[key] = data[key];
    			}
    		}
    		return userProfile;
    	}
    };

    // Static method, assigned to class, e.g. builders
    // Instance ('this') is not available in static context
    Event.build = function (data) {
        if (!data) {
            return new Event();
        } else {
            var event = new Event();
            for (var key in data) {
                if (key.charAt(0) !== '$' && data.hasOwnProperty(key)) {
                    event[key] = data[key];
                }
            }
            return event;
        }
    };



    return {
        User: User,
        Event: Event,
        UserProfile: UserProfile
    };
});