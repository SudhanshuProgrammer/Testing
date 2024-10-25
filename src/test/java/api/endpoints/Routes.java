package api.endpoints;

/* 
 Swagger UI 	: https://petstore.swagger.io
 CreateUser 	: https://petstore.swagger.io/v2/user/
 Get User 		: https://petstore.swagger.io/v2/user/{username}
 Update User 	: https://petstore.swagger.io/v2/user/{username}
 Delete User 	: https://petstore.swagger.io/v2/user/{username}

 Reqres Ui 		: https://reqres.in
 Create user	: https://reqres.in/api/users
 Get user 		: https://reqres.in/api/users/{userid}
 Update user	: https://reqres.in/api/users/{userid}
 Delete user	: https://reqres.in/api/users/{userid}

 public static String base_url = "http://localhost:3000/users";
 public static String post_url = base_url;
 public static String get_url = base_url+"/{userid}";
 public static String update_url = base_url+"/{userid}";
 public static String delete_url = base_url+"/{userid}";
*/

public class Routes {

	public static String base_url = "https://petstore.swagger.io/v2";

//	User module
	public static String post_url = base_url + "/user";
	public static String get_url = base_url + "/user/{username}";
	public static String update_url = base_url + "/user/{username}";
	public static String delete_url = base_url + "/user/{username}";

}
