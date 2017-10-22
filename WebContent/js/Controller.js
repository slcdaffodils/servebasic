angular.module("crudApp").filter("ipport",function(){
	  return function(item,what) {
	        var txt = "";
	        var data=item.split(":")
	        if(data.length>what)
	        	{
	        	  txt=data[what];
	        	}
	        else
	        	txt=data[0]
	       
	        return txt;
	    };
})

angular.module("crudApp").controller("crudController", function ($scope, crudService,$timeout) {
	debugger;
	
	$scope.UserFormContainer = false;
	$scope.itemShowCount = ['5','10','20', '30','500'];
	$scope.typeList = [1,2,3,4,5,6,7,8,9,10];
	$scope.date = new Date();
	$scope.netflowData=[];
	$scope.pageNumber=1;
	$scope.inHome=true; 
	$scope.resetHome=resetHome;
	$scope.searchNetflow=searchNetflow;
	$scope.whatUser={};
	$scope.what={};
	getWhatData();
	

	function resetHome(what){
   		$scope.inHome=what;
   		getWhatData();
   }

	
	function getWhatData(){
		if($scope.inHome){
			GetAllUsers();
		}else{
			getNetflowData();
		}
	}

	//TO get NetflowData
	function searchNetflow(){
		getNetflowData();
	}
	function getNetflowData(){
		console.log("Yeah getting it....")
	
		var sourceIp=$scope.what.ip?$scope.what.ip:"82.166.121.2";
		var start=0;
		var end=0;
		var from=0
		var size=500;
		$scope.netflowData=[];
		var netflowData = crudService.getNetflowData(sourceIp,start,end,from,size);
		
			netflowData.then(function (data) {
				$scope.what.ip=sourceIp;
				console.log(data);
				//$scope.users = user.data.userList;
	             $scope.netflowData=data.data.hits.hits;
	             console.log($scope.netflowData);
				//to select first item from ng-option list
				$scope.actItem = $scope.itemShowCount[4];
			}, function() {
				alert('Error in getting users list');
			});
			  
	}
	
	
	//To Get all users list
	function GetAllUsers () {
	   
	        
	        
		var getUserData = crudService.getUsers();
		getUserData.then(function (user) {
			$timeout(function(){
				$scope.users = user.data.userList;	
			});
			

			//to select first item from ng-option list
			$scope.actItem = $scope.itemShowCount[0];
		}, function() {
			alert('Error in getting users list');
		});

	}

	$scope.editUser = function (user) {

        var getUserData = crudService.getUser(user.id);
	
		getUserData.then( function(_user) {
			$scope.user = _user.data;
			$scope.whatUser.UserId = user.id
			$scope.whatUser.UserFirstname	= user.firstname;
			$scope.whatUser.UserLastname		= user.lastname;
			$scope.whatUser.UserType	= user.type;
			var isActive = (user.active == 1) ? true : false;
			$scope.whatUser.UserActiveChecked = isActive ;
			
			$scope.Action = "Update";
			$scope.UserFormContainer = true;
		}, function () {
			alert('Error in getting User record');
		});
	}

	// Hide Add User Form
	$scope.addUser = function () {
		ClearFields();
		$scope.Action = "Add";
		$scope.UserFormContainer = true;	
	}

	function ClearFields() {
//        $scope.UserId = "";
//        $scope.UserFirstname = "";
//        $scope.UserLastname = "";
//        $scope.UserType = "";
//        $scope.UserActive = "";
		$scope.whatUser={};
    }
	
	// Hide Add / Update User Form
	$scope.closeFrmBtn = function () {
		$scope.UserFormContainer = false;
	}

	$scope.Cancel = function () {
		$scope.UserFormContainer = false;
	}

	//Add Update Action 
	$scope.AddUpdateUser = function () {
		var user = {
				firstname	: $scope.whatUser.UserFirstname,
				lastname	: $scope.whatUser.UserLastname,
				type		: $scope.whatUser.UserType,
				active		: ( ($scope.UserActive) ? "1" : "0" )
				};
		
		var getUserAction = $scope.Action;
		
		if(getUserAction == "Update"){
			user.userid = $scope.whatUser.UserId;
			var getUserData = crudService.updateUser(user);
			
			getUserData.then (function (response) {
								GetAllUsers();
								var msg = response.data.msg;
								//alert(msg);
							}, function () {
								alert('Error in updating User record');		
								GetAllUsers();
							});
			
		}else{
			//Add Use Code Come Here
			var addUserData = crudService.addUser(user);
			addUserData.then (function (response) {
								GetAllUsers();
								var msg = response.data.msg;
								alert(msg);
							}, function () {
								alert('Error in adding User record');	
								
							}
			);
		}
		$scope.UserFormContainer = false;
	
	} // end of AddUpdateUser.

	$scope.deleteUser = function (user) {
		//console.log(user.id);
		var ans = confirm('Are you sure to delete it?');
		if(ans) {
			var delUserData = crudService.deleteUser(user.id);
			delUserData.then (function (response) {
				GetAllUsers();
				var msg = response.data.msg;
				//alert(msg); 
			}, function () {
				alert('Error in deleting User record or already deleted...');
				GetAllUsers();
			}
			);
		}

	}
	

	$scope.sort = function(keyname){
        $scope.sortKey = keyname;   //set the sortKey to the param passed
        $scope.reverse = !$scope.reverse; //if true make it false and vice versa
    }

	$scope.activeChange = function() {
		
		$scope.search ={"active": ( ($scope.uActive) ? "1" : "0" )};
	};
	
	
	$scope.reset = function(){
		$scope.uActive=false;
		$scope.search = '';
	};
    $scope.callMe=function(newPageNumber){
    	console.log("data",newPageNumber);
    	$scope.pageNumber=newPageNumber;
    }
});