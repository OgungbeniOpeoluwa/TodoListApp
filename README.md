# ****TODOLIST APPLICATION****

_This Todo list web application is designed to help users organise and manage tasks efficiently.it allows 
users create,edit,update,set due date and also delete task they have completed.This application is built using 
java Maven springboot and mongodb database._

## **Table of content**
1. [Features](#Features)
2. [EndPoints](#EndPoint)
3. [SetUp](#SetUp)
4. [ Pre-requites](#Pre-requites)

# Pre-requites
* JDK 21
* Postman
* MongoDb
* Maven

# SetUp
1. _Create an account with git._
2. _From your terminal/command prompt clone the repository using this git command_ 
   * git clone <https://github.com/OgungbeniOpeoluwa/TodoListApp.git>.
3. _Ensure all dependencies in the project are well injected in your pom.xml._
     * To download and build the project you can run this command on the terminal: _mvn clean install_
4. _Ensure  mongodb database is configure for proper database connection._
5. _To start the application from your IDE run the application main class.Alternatively you can run this command on the terminal._
      * mvn spring-boot:run
6. _install postman to test the application end-points by providing the necessary url and body requests if necessary._

# Features
* _User Registration_
* _User Login_
* _Create a task in todo_ 
* _View All tasks in todo list_
* _View A day tasks in todo list_
* _View a task in todo list_
* _Update a task in todo_ 
* _Update due date of a task_
* _Delete all task in todo list_
* _Delete a task in todo list_


# ENDPOINT

## **Register Request**

_This end point create a new user.it allows the user create an account. it accepts the username and the password, 
which is saved in the database._

_The password criteria:_
* Minimum length: 8 characters.
* Must start with atleast one uppercase letter.
* Followed by any combination of at least 7 and at most 19 characters (total length between 8 and 20 characters)

### **Request**

* Url : localhost:6060/api/user/users
* Method : POST
* Header : Content-Type : application/json 
* Body:

    ```
  {
    "username":"opeoluwa",
    "password":"Opeyemoi@1
    }
  ```

* Fields

   * Username(required,String):The username of the user

   * password(required,String):The password of the user


## **Response 1:**
_successful request_

    status code: 202 Accepted
  * Body
```
{
"data"{
"message":"Account Created"
}
"successful": "true"
}
```

## **Response 2:**
_unsuccessful request due to creating a user with a username that already exist in the database_

    Status Code: 400 Bad Request
  * Body:
```
{
"data" :{
"message":"user already exist"
},
"successful": false
}
```

## **Response 3**
_unsuccessful request due to creating with wrong password format_

    Status Code: 400 Bad Request
   * Body
```
{
"data":{
"message ": "Password is too weak,must start with a capital letter,and any character with at least 8 length");
}
"successful":false
}
```

# **Login Request**

_This end point verify user before the accessing the todo list features,it takes in username and password 
 and verify if the details exist in the database and if the given details are valid._

### **Request**
* Url: http:\\localhost:6060/api/user/login
* Method: Post
* Header: Content-type:application/json
* Body:
```
{
"username":"Ope",
"password":Opemip@1"
}
```
### Fields:
* username(required,String) the username of the user
* password(required,String) The password of the user

### **Response 1**
_successful request to login_

    Status code: 202 Accepted
```
{
"data"{
"message":"You don login"
},
"isSuccesful":true
}
```


### **Response 2:**
_unsuccessful login request due to incorrect password or username._

    Status Code: 400 Not Found
* Body:
```
{
"data":{
"message" : "Invalid details"
}
"isSuccessful": false
}
```

# **AddTodo Request**
_This end point create task for user in the todo list,it takes in username,Message, the due date of the task and also the creation date(Which is optional).if it is not given the date it was created is given by default._

### **Request:**
* Url: http:\localhost:9005/api/user/create
* Method: POST
* Header: Content type: application\json
* Body:
   * Request 1:
      This request automatically uses the day the task was created as the start date.

```
{
"username": "Ope",
"message": "Clean the garage",
"dueDateTime":{
"date":{
"year": 2024,
"month": 10,
"day": 21
},
"hour": 9,
"minute": 30
}
}
```
* Request 2: 

   _This request allow user to set the date of creation of todo._
```
{
"username": "Ope",
"message": "make my hair",
"dueDateTime":{
"date":{
"year": 2024,
"month": 10,
"day": 20
},
"hour": 9,
"minute": 30
},
"creationDate":{
"year": 2023,
"month":12,
"day": 30
}
}
```


## **Fields**
* username(required, String) the username of the user

* message/description(required, String) the message of the task the user want to create

* dueDateTime(required, int) the due date for the task to be executed
* creationDate(int) this is optional field that allow user specify the date todo was created.

## **Response 1**
_successful request_

    Status code: 201 Created
* Body:

```
{
"data": {
"message": "todo task has be created"
},
"isSuccessful": true
```


## **Response 2**
_unsuccessful Request due to Account Locked_

    Status Code: 400 Bad Request
* Body:

```
{
"data": {
"message": "Account is Locked"
},
"isSuccessful": false
```
## **Response 3**
_unsuccessful request due Invalid username_

    Status Code: 400 Bad Request
* Body:

```
{
"data": {
"message": "User doesn't Exit"
},
"isSuccessful": false
}
```

## **Response 4**
_unsuccessful Request due to same message existing on the same day_

    Status Code: 400 Bad Request
* Body:

```
{
"data": {
"message": "Task already exist"
},
"isSuccessful": false
}
```


# **UpdateTodo**
_This end point makes changes to an existing task, such as modifying its description.This process helps users keep their task information accurate and current. An update requires users to specify task they want to modify and provide the new task and also takes in the date the task was created._

### Request:
* Url: http://localhost:6060:api/user/update
* Method: Put
* Header : Content - type: application/json
* Body:
```
{
"username": "delighted",
"oldMessage":"clean the garage",
"newMessage": "I need to make my hair",
"date":{
"year":2023,
"month":12,
"day":19
}
}
```
### **Fields**
* username(required, String) the username of the user

* old message/task(required, String) the task that existed before

* new message/ task(required,String):the update task

* date(required, int) the date the task was created

### **Response 1**
_successful request_

    Status code: Ok
* Body:
```
{
"data"{
"message": "update sucessfully"
},
"isSuccessful": true,
}
```

### **Response 2**
_unsuccessful Request due to invalid username_

    Status Code : Not_Found
* Body:
```
{
"data"{
"message": "User doesn't exist"
},
"isSuccessful": false,
}
```


### **Response 3**
_unsuccessful request due to Invalid details_

    Status Code: Not_Found
* Body:

```
{
"data"{
"message": "Task not Found"
},
"isSuccessful": false,
}
```

### **Response 4**
_unsuccessful request due to new message/task already existing in todo list for that date_

    Status Code: Not_Found
* Body:
```
{
"data"{
"message": "Task Already Exist"
},
"isSuccessful": false,
}
```

## **View All Task Request**

_This end point allow the user to view all their existing task in the todo list. it takes in their username and return the list of all their task._

### **Request**
* Url: http://:localhost/6060:api/user/ViewAll

* Method: Get

* Header:Content-type:application/json

* Body:

```
{
"username":"Ope"
}
```

### **Field**

* Username:(require,String):The user username

### **Response 1**
_successful Request_

    Status Code:202 Accepted
* Body:
```
{
"data":{
"username":"Ope"
}
"successful":true
}
``` 


### Response 2
_unsuccessful request due to not account not unlocked_

    Status Code: Not_Found
* Body:
```
{
"data":{
"message": "Account is Locked"
}
"successful":false
}
```

### **Response 3**
_unsuccessful request due to invalid username_

    Status code: Not_found
* Body:
```
{
"data" :{
"message":"user doesn't exit"
}
"successful":false
}
```

## **Response 4**
_unsuccessful request due to user not having any task in todolist_

    Status code: Not_Found
* Body:
```
{
"data":{
"message": "No Tasks available with the username
}
```

### **View A Day Todo Request**
_This end point view all the task created for a particular day. it collects the username and the date the task as created.it returns the list of task for that date._

### **Request**
* Url:http://localhost/6060/api/user/viewADay
* Method: Get
* Header: Content-type/application/json
* Body:
```
{
"username": "Ope",
"date" :
"year":2023,
"month":12,
"day":23
}
```

### **Fields**

* username(required,String):The username of the user

* Date(required,int):The date the tasks was a created

### **Response 1**
_Successful request_

    Status Code: 202 Accepted
* Body:
```
{
"data": {
"response": [
{
"id": "6586149010296d1c43d6be44",
"localDate": "2023-12-22",
"message": "clean the toilet",
"todoId": "658611eb10296d1c43d6be43",
"dueDate": "2024-01-03T12:00:00"
}
]
},
"successful": true
}
```

### **Response 2**
_unsuccessful request due to account still locked_

    Status Code :Not_Found
* Body:
```
{
"data":{
"message": "Account is Locked"
}
"successful" :false
}
```

#### **Response 3**
_unsuccessful request due to Invalid username_

    Status Code:Not_Found
* Body:
```
{
"data":{
"message": "User doesn't exist"
}
"successful" :false
}
```

### **Response 4**
_unsuccessful request due to no task existing for that date_

    Status Code:Not_Found
* Body:
```
{
"data":{
"message": "No Task Available For that date"
}
"successful" :false
}
```


## **View A Todo Request**
_This end point give view of a particular task that has been created my the user. it takes in the username,date the task was created and task message and ut returns the tasks._

### **Request**
* Url:http://localhost/6060/api/user/viewTask
* Method:Get
* Header:Content-type:application/json
* Body:
```
{
"username":"ope"

"task":"clean the garage",

"date":

"year":2023,

"month":12,

"day":23
}
```

### **Fields**

* username(require,String):The username of the user

* Date(required,int):The date it was created

* Message/task(required,String):The task the user want to view

### **Response 1**
_Successful request_

    Status Code: 202 Accepted
* Body:
```
{
"data": {

"task": {

"id": "6586149010296d1c43d6be44",

"localDate": "2023-12-22",

"message": "clean the toilet",

"todoId": "658611eb10296d1c43d6be43",

"dueDate": "2024-01-03T12:00:00"
}

},
"successful": true
}
```

**### Response 2**
_unsuccessful request.Failed to log in before calling the request_

    Status Code: Not_Found
* Body:
```{
"data":{
"message": "Account is Locked"
}
"successful":false
}
```


### **Response 3**

__unsuccessful request due to invalid username__

    Status code: Not_found
* Body:
```
{
"data" :{
"message":"user doesn't exit"
}
"successful":false
}
```

### **Response 4**

_unsuccessful request due to invalid details(message/task and date)_

    Status Code: Not_Found
* Body:
```
{
"data":{

"message":"Task doesn't exist"
}
"successful": false
}
```

## **Delete All Todo**

_This End point delete all the todo list that the user as created. it takes in the password._

### **Request**
* Url:http://localhost/6060/api/user/deleteAll/Ope
* Method: Delete

* Header: Content_type:application/json

* Parmeter:
   > username:String

### **Response 1**
_Successful request_

    Status Code 202 Accepted
* Body:
```
{
"data": {
"message": "All Todo Has Been Deleted"
},
"successful": true
}
```

### **Response 2**

_unsuccessful request due to not account not unlocked_

    Status Code: Not_Found

* Body:
```
{
"data":{
"message": "Account is Locked"
}
"successful":false
}
```

### **Response 3**
_unsuccessful request due to invalid username_

    Status code: Not_found
* Body:
```
{
"data" :{
"message":"user doesn't exit"
}
"successful":false
}
```

### **Response 4**
_unsuccessful request due to user not having any task in todolist_

    Status code: Not_Found
* Body:
```
{
"data":{
"message": "No Tasks found"
}
"successful" :false
}
```


### **Delete A Todo Request**

_This end point delete a particular task for the user. it takes the username,date the task as created and the task message/description._

### **Request**
* Url:http://:localhost/6060/api/user/delete
* Method: Delete
* Header: Content-type/application/json
* Body:
```
{
"username":"jenny",

"message":"clean the garage",

"date":{

"year":2023,

"month":12,

"day":23
},
}
```

## **Fields**

* Username: (required,String):The username of the user

* Date(required,int): The date it was created

* Message/task(required,String):The task the user intend to delete

### **Response 1**
_Successful request_

    Status Code:202 Accepted
* Body:
```
{
"data": {
"message": "Task Has Been Deleted"
},
"successful": true
}
```

### **Response 2**
_unsuccessful request due to account still locked_

    Status Code :Not_Found

* Body:
```
{
"data":{
"message": "Account is Locked"
}
"successful" :false
}
```

### **Response 3**

_unsuccessful request due to Invalid username_

    Status Code:Not_Found
* Body:
```
{
"data":{
"message": "User doesn't exist"
}
"successful" :false
}
```

### **Response 4**
_unsuccessful request due to no task existing for that date_

    Status Code:Not_Found
* Body:
```
{
"data":{
"message": "Task Not found"
}
"successful" :false
}
```


## **Delete Todo Account Request**

_This end point delete the account of an existing user from the data base.it takes in the username._

### **Request**
* Url:http://:localhost/6060/api/request/deleteAccount/jenny
* Method: Delete
* Header: Content-type/application/json

### **Fields**
* Username:(require,String):The username of the user

### *** Response 1**
_Successful request_

    Status Code: 202 Accepted
* Body:
```
{
"data": {
"message": "your account has been deleted"
},
"successful": true
}
```

### **Response 2**
_unsuccessful request due not logging in before performing an action_

    Status Code: Not_Found
* Body:
```
{
"data":{
"message": "Account is Locked"
}
"successful":false
}
```

### **Response 3**
_unsuccessful request due to invalid username_

    Status code: Not_found
* Body:
```
{
"data" :{
"message":"user doesn't exit"
}
"successful":false
}
```


## **Update the Due Date Of a Todo Request**

_This end point modify the due date of an existing task .it takes the username, date created, the task message/description and the update due date._

### **Request:**
* Url:http://localhost/6060/api/user/updateDate
* Method: Put
* Header: Content-type:application/json
* Body:
```
{
    "username":"Tobi",
    "task":"clean the toilet",
    "date":{
        "year":2023,
        "month":12,
        "day":22
    },
    "dueDate":{
        "date":{
            "year":2024,
            "month":1,
            "day":25
        }
            }
}
```
### **Fields:**
* username:(required,String):The username of the user

* Date:(required,int):The date the task was created

* message:(required,String):The task message

* Due Date(required, int):The due date that the user want to update the task deadline to.

### **Response 1:**
_Successful request_

    Status Code:202 Accepted
* Body:
```
{
"data": {
"message": "Due Date Has Been Updated"
},
"successful": true
}
```
### **Response 2**
_unsuccessful request due to account still locked_

    Status Code :Not_Found
* Body:
```
{
"data":{
"message": "Account is Locked"
}
"successful" :false
}
```

### **Response 3**
_unsuccessful request due to Invalid username_

    Status Code:Not_Found
* Body:
```
{
"data":{
"message": "User doesn't exist"
}
"successful" :false
}
```

### **Response 4**
_unsuccessful request due to no task existing for that date._

    Status Code:Not_Found
* Body:
```
{
"data":{
"message": "Task not Found"
}
"successful" :false
}
```













































