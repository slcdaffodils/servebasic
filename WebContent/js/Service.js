angular.module("crudApp").service("crudService", function ($http) {
	var serviceUrl = "/servebasic/api"; 
	//get All Users
    this.getUsers = function () {
        return $http.get(serviceUrl + "?query=getallusers");
    };

	this.getUser = function(userId) {
		var response = $http({
			method	: "GET",
			url		: serviceUrl,
			params 	: {
					id : userId
			}
		});
		return response;
	};

	this.updateUser = function (user) {
		var response = $http.put(serviceUrl,user);
		return response;
	};

	this.addUser = function (user) {
		var response = $http.post(serviceUrl,user);
		return response;
	};

	this.deleteUser = function (id) {
		var response = $http({
			method  : "POST",
			url		: serviceUrl+"?query=delete",
			params : {userId : id}
		});

		return response;
	};
	
	this.getNetflowData=function(sourceIp,start,end,from,size){
		var response = $http({
			method	: "GET",
			url		: "http://34.210.155.47:8080/netflow/data",
			params 	: {
					ip : sourceIp,
					start:start,
					end:end,
					from:from,
					size:size
			}
		});
		return response;
		
	}

});