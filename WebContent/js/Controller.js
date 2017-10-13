app.controller("crudController", function ($scope, crudService) {
	debugger;
	
	$scope.UserFormContainer = false;
	$scope.itemShowCount = ['5','10','20', '30'];
	$scope.typeList = [1,2,3,4,5,6,7,8,9,10];
	$scope.date = new Date();
	$scope.pageNumber=1;
	GetAllUsers();
	
	//To Get all users list
	function GetAllUsers () {
		
		var getUserData = crudService.getUsers();
		getUserData.then(function (user) {
			$scope.users = user.data.userList;

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
			$scope.UserId = user.id
			$scope.UserFirstname	= user.firstname;
			$scope.UserLastname		= user.lastname;
			$scope.UserType	= user.type;
			var isActive = (user.active == 1) ? true : false;
			$scope.UserActiveChecked = isActive ;
			
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
        $scope.UserId = "";
        $scope.UserFirstname = "";
        $scope.UserLastname = "";
        $scope.UserType = "";
        $scope.UserActive = "";
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
				firstname	: $scope.UserFirstname,
				lastname	: $scope.UserLastname,
				type		: $scope.UserType,
				active		: ( ($scope.UserActive) ? "1" : "0" )
				};
		
		var getUserAction = $scope.Action;
		
		if(getUserAction == "Update"){
			user.userid = $scope.UserId;
			var getUserData = crudService.updateUser(user);
			
			getUserData.then (function (response) {
								GetAllUsers();
								var msg = response.data.msg;
								alert(msg);
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
				alert(msg); 
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