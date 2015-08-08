app.factory('Domain', function (moment) {
    'use strict';

    // reference for base
    var referenceBaseDateHours = new Date(1970, 1, 2, 0, 0, 0, 0);

    // HELPER functions
    function convertDateToTimeString(date) {
        if (date && date >= referenceBaseDateHours) {
            return moment(date).format('HH:mm');
        } else {
            return null;
        }
    }

    function convertTimeStringToDate(time) {
        if (time) {
            var date = new Date(referenceBaseDateHours.getTime()),
                res = time.split(':');
            date.setHours(res[0]);
            date.setMinutes(res[1]);
            return date;
        } else {
            return null;
        }
    }

    /**
     * User Object
     */
    function User(username, permissions, customers) {
        this.username = username;
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

    User.prototype.getActiveCustomer = function () {
        // TODO error check
        return this.customers[0];
    };

    User.prototype.hasRight = function (userRight) {
        if (!userRight || !this.isValid() || !this.permissions || this.permissions.length === 0) {
            return false;
        } else {
            var position = this.permissions.indexOf(userRight);
            if (position > -1) {
                return true;
            } else {
                return false;
            }
        }
    };


    // Static method, assigned to class, e.g. builders
    // Instance ('this') is not available in static context
    User.build = function (username, permissions, customers) {
        return new User(username, permissions, customers);

    };
    /**
     * Branch Object
     */
    function Branch() {
        this.branchId = null;
        this.branchNumber = null;
        // TODO default customerId
        this.customerId = '1';
        this.branchDescription = null;
        this.zipCode = null;
        this.countryId = null;
        this.federalStateId = null;
        this.city = null;
        this.address = null;
        this.comparableBranchId = null;
        this.comparableBranchName = null;
        this.debitReturn = null;
        this.openingDate = null;
        this.holidayOpening = false;
        this.openingStartTimeNormal = new Date(referenceBaseDateHours.getTime());
        this.openingEndTimeNormal = new Date(referenceBaseDateHours.getTime());
        this.openingStartTimeSaturday = new Date(referenceBaseDateHours.getTime());
        this.openingEndTimeSaturday = new Date(referenceBaseDateHours.getTime());
        this.openingStartTimeSunday = new Date(referenceBaseDateHours.getTime());
        this.openingEndTimeSunday = new Date(referenceBaseDateHours.getTime());
        this.showOpeningTimeNormal = false;
        this.showOpeningTimeSaturday = false;
        this.showOpeningTimeSunday = false;
    }

    /**
     * Public object methods, assigned to prototype
     */
    Branch.prototype.convertTimeStringsToDates = function () {
        // easy way to get a clean copy
        var copy = Branch.build(this);
        // convert times
        copy.openingStartTimeNormal = convertTimeStringToDate(this.openingStartTimeNormal);
        copy.openingEndTimeNormal = convertTimeStringToDate(this.openingEndTimeNormal);
        if (copy.openingStartTimeNormal) {
            copy.showOpeningTimeNormal = true;
        }
        copy.openingStartTimeSaturday = convertTimeStringToDate(this.openingStartTimeSaturday);
        copy.openingEndTimeSaturday = convertTimeStringToDate(this.openingEndTimeSaturday);
        if (copy.openingStartTimeSaturday) {
            copy.showOpeningTimeSaturday = true;
        }
        copy.openingStartTimeSunday = convertTimeStringToDate(this.openingStartTimeSunday);
        copy.openingEndTimeSunday = convertTimeStringToDate(this.openingEndTimeSunday);
        if (copy.openingStartTimeSunday) {
            copy.showOpeningTimeSunday = true;
        }
        return copy;
    };

    Branch.prototype.convertDatesToTimeStrings = function () {
        // easy way to get a clean copy
        var copy = angular.extend({}, this);
        // convert times
        if (copy.showOpeningTimeNormal) {
            copy.openingStartTimeNormal = convertDateToTimeString(this.openingStartTimeNormal);
            copy.openingEndTimeNormal = convertDateToTimeString(this.openingEndTimeNormal);
        } else {
            copy.openingStartTimeNormal = null;
            copy.openingEndTimeNormal = null;
        }
        if (copy.showOpeningTimeSaturday) {
            copy.openingStartTimeSaturday = convertDateToTimeString(this.openingStartTimeSaturday);
            copy.openingEndTimeSaturday = convertDateToTimeString(this.openingEndTimeSaturday);
        } else {
            copy.openingStartTimeSaturday = null;
            copy.openingEndTimeSaturday = null;
        }
        if (copy.showOpeningTimeSunday) {
            copy.openingStartTimeSunday = convertDateToTimeString(this.openingStartTimeSunday);
            copy.openingEndTimeSunday = convertDateToTimeString(this.openingEndTimeSunday);
        } else {
            copy.openingStartTimeSunday = null;
            copy.openingEndTimeSunday = null;
        }
        return copy;
    };


    Branch.prototype.convertToDTO = function () {
        return this.convertDatesToTimeStrings();
    };

    Branch.prototype.convertFromDTO = function () {
        return this.convertTimeStringsToDates();
    };


    // Static method, assigned to class, e.g. builders
    // Instance ('this') is not available in static context
    Branch.build = function (data) {
        if (!data) {
            return new Branch();
        } else {
            var branch = new Branch();
            for (var key in data) {
                if (data.hasOwnProperty(key)) {
                    branch[key] = data[key];
                }
            }
            return branch;
        }
    };

    /**
     * Event Object
     */
    function Order() {
        this.orderId = null;
        this.orderDate = new Date();
        this.customerId = null;
        this.orderState = 'new';
    }


    Order.prototype.convertToDTO = function () {
        return this;
    };

    Order.prototype.convertFromDTO = function () {
        return this;
    };

    /**
     * Event Object
     */
    function Event() {
        this.eventId = null;
        this.eventDescription = null;
        this.branchId = null;
        this.eventCategoryId = null;
        this.eventSubCategoryId = null;
        this.insertDate = null;
        this.startDate = null;
        this.endDate = null;
    }


    Event.prototype.convertToDTO = function () {
        this.branchId = this.branch.branchId;
        this.eventCategoryId = this.eventCategory.eventCategoryId;
        this.eventSubCategoryId = this.eventSubCategory.eventSubCategoryId;
        return this;
    };

    Event.prototype.convertFromDTO = function () {
        return this;
    };
    
    function UserProfile(){
    	this.userName = null;
    	this.password = null;
    	this.passwordConfirmation = null;
    	this.controller = false;
    	this.customerName = null;
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
    
    function Customer(){
    	this.customerId = null;
    	this.customerName = null;
    	this.customerTypeName = null;
    	this.customerTypeId = null;
    }
    
    Customer.prototype.isValid = function () {
    	if(this.custonerName && this.customerTypeId){
    		return true;
    	}
    	return false;
    };
    
    Customer.prototype.convertToDTO = function () {
    	return this;
    };
    
    Customer.prototype.convertFromDTO = function () {
    	return this;
    };
    
    Customer.build = function (data){
    	if(!data) {
    		return new Customer();
    	}else{
    		var customer = new Customer();
    		for(var key in data){
                if (key.charAt(0) !== '$' && data.hasOwnProperty(key)) {
    				customer[key] = data[key];
    			}
    		}
    		return customer;
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

    Order.build = function (data) {
        if (!data) {
            return new Order();
        } else {
            var order = new Order();
            for (var key in data) {
                if (key.charAt(0) !== '$' && data.hasOwnProperty(key)) {
                    order[key] = data[key];
                }
            }
            return order;
        }
    };


    return {
        Branch: Branch,
        User: User,
        Event: Event,
        Order: Order,
        UserProfile: UserProfile,
        Customer: Customer
    };
});