# API REST DOCUMENTATION
All API queries are preceded `/api`
## Users
All User queries are preceded `/users`
### New User

Create a new user. If admin creates a new user, it will be a teacher else it will be a student.

* ##### URL

	< / >

* ##### Method:

	`POST`

* ##### Params
  * Required:
    * `username=[String]`
    * `password=[String]`
    * `repeatPassword=[String]`
    * `userMail=[String]`
 
* ##### Success Response: 201 CREATED
      {
      "userID": 60,
      "username": "pepito",
      "userMail": "pepito@hotmail.com",
      "userFirstName": "",
      "userLastName": "",
      "internalName": "pepito",
      "roles": [
          "ROLE_USER"
      ],
      "admin": false,
      "student": true
      }
* ##### Error Response:

	**Code:** 500 INTERNAL SERVER ERROR (if params are missed or incorrect)

### Update User
Modify user information. You only can update your profile.

* ##### URL

	< /{userInternalName}>

* ##### Method:

	`PUT`

* ##### Request Body:
      {
        "username": "amico4",
        "password": "pass",
        "userMail": "amico@mail.com",
        "userFirstName": "Carlos",
        "userLastName": "Gil",
        "userAddress": "Under the Brooklyn Bridge",
        "city": "New York",
        "country": "United States of America",
        "interests": "Sleeping and watching tv.",
        "urlProfileImage": "null",
        "phoneNumber": 667905555
      }
 
* ##### Success Response: 200 OK
      {
	    "userID": 51,
	    "username": "amico4",
	    "userMail": "amico@mail.com",
	    "userFirstName": "Carlos",
	    "userLastName": "Gil",
	    "userAddress": "Under the Brooklyn Bridge",
	    "city": "New York",
	    "country": "United States of America",
	    "phoneNumber": 667905555,
	    "interests": "Sleeping and watching tv.",
	    "urlProfileImage": "null",
	    "internalName": "amico4",
	    "roles": [
		"ROLE_USER"
	    ],
	    "admin": false,
	    "student": true,
	    "completedCourses": [
		{
		    "courseID": 2,
		    "name": "AI Advanced Tips",
		    "internalName": "ai-advanced-tips",
		    "courseLanguage": "English",
		    "type": "computer-science",
		    "startDateString": "01-03-2021",
		    "endDateString": "01-03-2024",
		    "numberOfUsers": 23,
		    "courseDescription": "Learn all the most advanced stuff related to IA",
		    "originalName": "../img/courses/ai-advanced-tips/ai-advanced-tips.jpeg",
		    "completed": true
		},
		{
		    "courseID": 4,
		    "name": "Cortar con tijeras",
		    "internalName": "cortar-con-tijeras",
		    "courseLanguage": "Español",
		    "type": "handwork",
		    "startDateString": "01-03-2022",
		    "endDateString": "01-03-2022",
		    "numberOfUsers": 21,
		    "courseDescription": "Aprende a cortar con tijeras como un maestro. Este curso intensivo te hará desarollar tu potencial oculto",
		    "originalName": "../img/courses/cortar-con-tijeras/cortar-con-tijeras.jpg",
		    "completed": true
		}
	    ],
	    "currentCourses": [
		{
		    "courseID": 1,
		    "name": "Introduction to AI",
		    "internalName": "introduction-to-ai",
		    "courseLanguage": "English",
		    "type": "computer-science",
		    "startDateString": "31-01-2019",
		    "endDateString": "30-07-2019",
		    "numberOfUsers": 38,
		    "courseDescription": "If you want to learn all about AI, this is our course",
		    "originalName": "../img/courses/introduction-to-ai/introoduction-to-ai.jpg",
		    "completed": false
		},
		{
		    "courseID": 3,
		    "name": "Cocina Moderna",
		    "internalName": "cocina-moderna",
		    "courseLanguage": "Español",
		    "type": "cooking",
		    "startDateString": "01-03-2025",
		    "endDateString": "01-03-2025",
		    "numberOfUsers": 23,
		    "courseDescription": "Si tienes hambre y no sabes ni freir un huevo este es tu curso.",
		    "originalName": "../img/courses/cocina-moderna/cocina-moderna.jpg",
		    "completed": false
		},
		{
		    "courseID": 5,
		    "name": "Matematicas Para Gatos",
		    "internalName": "matematicas-para-gatos",
		    "courseLanguage": "Miau",
		    "type": "maths",
		    "startDateString": "01-03-2022",
		    "endDateString": "01-03-2023",
		    "numberOfUsers": 18,
		    "courseDescription": "Miau miau miau miau ( +, -, *, /, ...) miau miau miau. Miuau miau",
		    "originalName": "../img/courses/matematicas-para-gatos/matematicas-para-gatos.jpg",
		    "completed": false
		},
		{
		    "courseID": 6,
		    "name": "Retrato profesional",
		    "internalName": "retrato-profesional",
		    "courseLanguage": "Español",
		    "type": "photograph",
		    "startDateString": "01-03-2018",
		    "endDateString": "18-03-2018",
		    "numberOfUsers": 3,
		    "courseDescription": "En este curso se abordaran las técnicas más novedosas a la hora de realizar retratos a mano alzada.",
		    "originalName": "../img/courses/retrato-profesional/retrato-profesional.jpg",
		    "completed": false
		}
	    ]
      }
* ##### Error Response:

	**Code:** 404 NOT FOUND
  	**Code:** 401 UNAUTHORIZED
  	**Code:** 511 NETWORK AUTHENTICATION REQUIRED
	
### Delete User

Only by admin.

* ##### URL

	< /{userInternalName}>

* ##### Method:

	`DELETE`
 
* ##### Success Response: 200 OK
      {
	    "userID": 52,
	    "username": "amicoTeacher",
	    "userMail": "amicoTeacher@mail.com",
	    "userFirstName": "",
	    "userLastName": "",
	    "internalName": "amicoteacher",
	    "roles": [
		"ROLE_USER"
	    ],
	    "admin": false,
	    "student": false
      }
* ##### Error Response:

	**Code:** 404 NOT FOUND
  	**Code:** 401 UNAUTHORIZED
	
### User Profile

Only for owner.

* ##### URL

	< /myProfile>

* ##### Method:

	`GET`
 
* ##### Success Response: 302 FOUND
      {
	    "userID": 51,
	    "username": "amico4",
	    "userMail": "amico@mail.com",
	    "userFirstName": "Carlos",
	    "userLastName": "Gil",
	    "userAddress": "Under the Brooklyn Bridge",
	    "city": "New York",
	    "country": "United States of America",
	    "phoneNumber": 667905555,
	    "interests": "Sleeping and watching tv.",
	    "urlProfileImage": "null",
	    "internalName": "amico4",
	    "roles": [
		"ROLE_USER"
	    ],
	    "admin": false,
	    "student": true,
	    "completedCourses": [
		{
		    "courseID": 2,
		    "name": "AI Advanced Tips",
		    "internalName": "ai-advanced-tips",
		    "courseLanguage": "English",
		    "type": "computer-science",
		    "startDateString": "01-03-2021",
		    "endDateString": "01-03-2024",
		    "numberOfUsers": 23,
		    "courseDescription": "Learn all the most advanced stuff related to IA",
		    "originalName": "../img/courses/ai-advanced-tips/ai-advanced-tips.jpeg",
		    "completed": true
		},
		{
		    "courseID": 4,
		    "name": "Cortar con tijeras",
		    "internalName": "cortar-con-tijeras",
		    "courseLanguage": "Español",
		    "type": "handwork",
		    "startDateString": "01-03-2022",
		    "endDateString": "01-03-2022",
		    "numberOfUsers": 21,
		    "courseDescription": "Aprende a cortar con tijeras como un maestro. Este curso intensivo te hará desarollar tu potencial oculto",
		    "originalName": "../img/courses/cortar-con-tijeras/cortar-con-tijeras.jpg",
		    "completed": true
		}
	    ],
	    "currentCourses": [
		{
		    "courseID": 1,
		    "name": "Introduction to AI",
		    "internalName": "introduction-to-ai",
		    "courseLanguage": "English",
		    "type": "computer-science",
		    "startDateString": "31-01-2019",
		    "endDateString": "30-07-2019",
		    "numberOfUsers": 38,
		    "courseDescription": "If you want to learn all about AI, this is our course",
		    "originalName": "../img/courses/introduction-to-ai/introoduction-to-ai.jpg",
		    "completed": false
		},
		{
		    "courseID": 3,
		    "name": "Cocina Moderna",
		    "internalName": "cocina-moderna",
		    "courseLanguage": "Español",
		    "type": "cooking",
		    "startDateString": "01-03-2025",
		    "endDateString": "01-03-2025",
		    "numberOfUsers": 23,
		    "courseDescription": "Si tienes hambre y no sabes ni freir un huevo este es tu curso.",
		    "originalName": "../img/courses/cocina-moderna/cocina-moderna.jpg",
		    "completed": false
		},
		{
		    "courseID": 5,
		    "name": "Matematicas Para Gatos",
		    "internalName": "matematicas-para-gatos",
		    "courseLanguage": "Miau",
		    "type": "maths",
		    "startDateString": "01-03-2022",
		    "endDateString": "01-03-2023",
		    "numberOfUsers": 18,
		    "courseDescription": "Miau miau miau miau ( +, -, *, /, ...) miau miau miau. Miuau miau",
		    "originalName": "../img/courses/matematicas-para-gatos/matematicas-para-gatos.jpg",
		    "completed": false
		},
		{
		    "courseID": 6,
		    "name": "Retrato profesional",
		    "internalName": "retrato-profesional",
		    "courseLanguage": "Español",
		    "type": "photograph",
		    "startDateString": "01-03-2018",
		    "endDateString": "18-03-2018",
		    "numberOfUsers": 3,
		    "courseDescription": "En este curso se abordaran las técnicas más novedosas a la hora de realizar retratos a mano alzada.",
		    "originalName": "../img/courses/retrato-profesional/retrato-profesional.jpg",
		    "completed": false
		}
	    ]
      }
* ##### Error Response:

	**Code:** 404 NOT FOUND
  	**Code:** 511 NETWORK AUTHENTICATION REQUIRED
	
### FIND
Find users.
#### Find one user

* ##### URL

	< /{userInternalName}>

* ##### Method:

	`GET`
 
* ##### Success Response: 302 FOUND
      {
	    "userID": 52,
	    "username": "amicoTeacher",
	    "userMail": "amicoTeacher@mail.com",
	    "userFirstName": "",
	    "userLastName": "",
	    "internalName": "amicoteacher",
	    "roles": [
		"ROLE_USER"
	    ],
	    "admin": false,
	    "student": false
      }
* ##### Error Response:

	**Code:** 404 NOT FOUND
	
#### Find in course

* ##### URL

	< {couseID}/{userInternalName}>

* ##### Method:

	`GET`
 
* ##### Success Response: 302 FOUND
      {
	    "userID": 52,
	    "username": "amicoTeacher",
	    "userMail": "amicoTeacher@mail.com",
	    "userFirstName": "",
	    "userLastName": "",
	    "internalName": "amicoteacher",
	    "roles": [
		"ROLE_USER"
	    ],
	    "admin": false,
	    "student": false
      }
* ##### Error Response:

	**Code:** 404 NOT FOUND
	
#### Find all users
Paged from ten to ten.
* ##### URL

	< /all>

* ##### URL Params:

	* `page=[int]`
    	* `isStudent=[Boolean]`
	
* ##### URL Example: `/api/users/all?page=5&isStudent=true`

* ##### Method:

	`GET`
 
* ##### Success Response: 302 FOUND
      {
	    "content": [
		{
		    "userID": 51,
		    "username": "amico",
		    "userMail": "amicourses@mail.com",
		    "userFirstName": "Amico",
		    "userLastName": "Fernandez",
		    "internalName": "amico",
		    "roles": [
			"ROLE_USER"
		    ],
		    "admin": false,
		    "student": true
		}
	    ],
	    "totalPages": 6,
	    "totalElements": 51,
	    "last": true,
	    "size": 10,
	    "number": 5,
	    "sort": null,
	    "first": false,
	    "numberOfElements": 1
      }
* ##### Error Response:

	**Code:** 404 NOT FOUND

### PUT Profile Photo

* ##### URL

	< img/{userInternalName}>

* ##### Method:

	`GET`
 
* ##### Success Response: IMAGE 200 OK 
      
* ##### Error Response:

	**Code:** 404 NOT FOUND

### GET Profile Photo

* ##### URL

	< img/{userInternalName}>
	
* ##### Params:
	* Required:
	    * `profileImage=[MultipartFile]`

* ##### Method:

	`PUT`
 
* ##### Success Response: 200 OK 
      {
	    "userID": 52,
	    "username": "amicoTeacher",
	    "userMail": "amicoTeacher@mail.com",
	    "userFirstName": "",
	    "userLastName": "",
	    "internalName": "amicoteacher",
	    "roles": [
		"ROLE_USER"
	    ],
	    "admin": false,
	    "student": false
      }
	   
* ##### Error Response:

	**Code:** 500 INTERNAL SERVER ERROR
