var MyStuffApp = angular.module("MyStuffApp", []); // MINE

MyStuffApp.controller("AdminController", function($scope, $http, $rootScope){
	
	$scope.restUrl = "http://localhost:8080/stuff.web/rest/admin";
	$scope.name ="";
	$scope.password ="";
	$scope.email = "";
	$scope.description ="";
	$scope.imagePath ="";
	$scope.price ="";
	$scope.protype ="";
	$scope.quantity ="";
	$scope.infomessage = "";
	$scope.errormessage = "";
	$scope.PrintAllProducts  = [] ;
	$scope.printAllCustomers  = [] ;
	$scope.PrintAllOredrs = [] ; 
	$scope.PrintAllCustomerOrder = [] ; 

	
	$scope.login = function(){
		url = $scope.restUrl+"/login/"+ $scope.name +"/" + $scope.password;
		$http.get(url).then(
				function(response){
					$scope.infomessage = response.data.message;
				}, function(response){
					$scope.errormessage = "Server error....";
				});
		
		
	}

	
	$scope.createProduct = function(){
		url = $scope.restUrl+"/createProduct/"+$scope.name+"/"+$scope.description+"/"+$scope.imagePath;+"/"+$scope.price;+"/"+$scope.protype;+"/"+$scope.quantity;
		$http.post(url).then(
				function(response){
					$scope.infomessage = response.data.message;
				}, function(response){
					$scope.errormessage = response.data.message;
				});
		
	}
	
	$scope.updateProduct = function(){
		url = $scope.restUrl+"/removeCompany/"+$scope.productId+"/"+$scope.name+"/"+$scope.description+"/"+$scope.imagePath;+"/"+$scope.price;+"/"+$scope.protype;+"/"+$scope.quantity;
		$http.put(url).then(
				function(response){
					$scope.infomessage = response.data.message;
				}, function(response){
					$scope.errormessage = response.data.message;
				});
		
	};
	
	
	$scope.removeProduct = function(){
		url = $scope.restUrl + "/getProductByName"+"/"+$scope.productId;
		$http.delete(url).then(
				function (response){
					$scope.infomessage = response.data.message;
				}, function(response){
					$scope.errormessage = response.data.message;
				});
		
};



	$scope.getProductById = function(){
		url = $scope.restUrl + "/getProductByName"+"/"+$scope.id;
		$http.get(url).then(
				function (response){
					$scope.infomessage = response.data.message;
				}, function(response){
					$scope.errormessage = response.data.message;
				});
		
};
	
	
	$scope.getAllCutsomers = function(){
		url = $scope.restUrl+"/getAllCutsomers" ; 
		$http.get(url).then(
				function(response){
					$scope.PrintAllCustomers = response.data;
				}, function(response){
					$scope.errormessage = response.data.message;
				});
		
	}
	
	$scope.getCustomerByName = function(){
		url = $scope.restUrl+"/getCustomerByName/"+$scope.name;
		$http.get(url).then(
				function(response){
					$scope.infomessage = response.data.message;
				}, function(response){
					$scope.errormessage = response.data.message;
				});
		
	};
	
	
	$scope.getOrderById = function(){
		url = $scope.restUrl+"/getOrderById"+$scope.if ;
		$http.get(url).then(
					function (response){
						$scope.infomessage = response.data.message;
					}, function(response){
						$scope.errormessage = response.data.message;
					});
			
	};
	
	$scope.removeCustomer= function(){
		url = $scope.restUrl + "/removeCustomer/"+ $scope.id;
		$http.delete(url).then(
				function(response){
					$scope.infomessage = response.data.message;
				}, function(response){
					$scope.errormessage = response.data.message;
				});
		
	};


	
	$scope.getCustomerById = function(){
		url = $scope.restUrl + "/getCustomerById/"+ $scope.id;
		$http.get(url).then(
				function(response){
					$scope.infomessage = response.data.message ;
				}, function(response){
					$scope.errormessage = response.data.message ;
				});
	};
	
	$scope.getCustomerOrderById = function(){
		url = $scope.restUrl + "/getCustomerOrderById/"+ $scope.customerId;
		$http.get(url).then(
				function(response){
					$scope.infomessage = response.data.message ;
				}, function(response){
					$scope.errormessage = response.data.message ;
				});
	};
	

	
	$scope.logout = function(){
		url = $scope.restUrl + "/logout";
		$http.get(url).then(
				function(response){
					$scope.infomessage = response.data.message ;
				}, function(response){
					$scope.errormessage = "Please log in first ! ";
				});
	};
	
	

	});