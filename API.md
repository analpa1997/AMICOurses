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

### GET Profile Photo

* ##### URL

	< img/{userInternalName}>

* ##### Method:

	`GET`
 
* ##### Success Response: IMAGE 200 OK 
      
* ##### Error Response:

	**Code:** 404 NOT FOUND

### PUT Profile Photo

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

  ## Moodle

  ### Modules (Only teachers can use them)
  
   #### Add Module

   Adds a new module within a subject

  * ##### URL

	< /moodle/{courseInternalName}/{subjectInternalName}/module >

* ##### Method:

	`POST`

* ##### Params
    * Required:
    * `courseInternalName=[String]`
    * `subjectInternalName=[String]`
 
* ##### Success Response: 200 OK
      {
          "subjectID":1,
          "name":"History of AI",
          "description":"A short history for the AI",
          "internalName":"history-of-ai",
          "numberModules":4,
          "users":[]
      }
* ##### Error Response:

	**Code:** 404 NOT FOUND

 #### Delete Module

Deletes a module from a subjec

* ##### URL
< /moodle/{courseInternalName}/{subjectInternalName}/module/{module} >

* ##### Method:

	`DELETE`

* ##### Params
  * Required:
    * `courseInternalName=[String]`
    * `subjectInternalName=[String]`
    * `moduleNumber=[Integer]`
 
* ##### Success Response: 200 OK
      {
        "subjectID": 1,
        "name": "History of AI",
        "description": "A short history for the AI",
        "internalName": "history-of-ai",
        "numberModules": 3,
        "users" : [...]
      }
* ##### Error Response:

	**Code:** 400 or 404
	
  ### StudyItems
  
  (Type can be studyItem or practice)
  
   #### Gets all the Items/practices from a subject

  * ##### URL

	< /moodle/{courseInternalName}/{subjectInternalName}/{type}/all >

* ##### Method:

	`GET`

* ##### Params
    * Required:
    * `courseInternalName=[String]`
    * `subjectInternalName=[String]`
    * `type=[String]`
 
* ##### Success Response: 200 OK
      {
          "content": [
             {
                "studyItemID": 1,
                "type": "file-pdf",
                "name": "Theme 1",
                "fileName": "studyItem-1",
                "module": 1,
                "originalName": "theme-1.txt",
                "extension": null,
                "icon": "pdf",
                "isPractice": false,
                "practice": false
            }, ... ],
            "last": true,
            "totalPages": 1,
            "totalElements": 9,
            "size": 10,
            "number": 0,
            "sort": null,
            "first": true,
            "numberOfElements": 9
      }
* ##### Error Response:

	**Code:** 404 

   #### Gets all the Items from a module of a subject

  * ##### URL

	< /moodle/{courseInternalName}/{subjectInternalName}/studyItem/module/{module} >

* ##### Method:

	`GET`

* ##### Params
      * Required:
    * `courseInternalName=[String]`
    * `subjectInternalName=[String]`
    * `module=[Integer]`
 
* ##### Success Response: 200 OK
      {
        "content": [
        {
            "studyItemID": 1,
            "type": "file-pdf",
            "name": "Theme 1",
            "fileName": "studyItem-1",
            "module": 1,
            "originalName": "theme-1.txt",
            "extension": null,
            "icon": "pdf",
            "isPractice": false,
            "practice": false
        }, {...}
          ],
          "last": true,
          "totalPages": 1,
          "totalElements": 5,
          "size": 10,
          "number": 0,
          "sort": null,
          "first": true,
          "numberOfElements": 5
      }
* ##### Error Response:

	**Code:** 404 
	
	 #### Gets a studyItem/practice from a subject

  * ##### URL

	< /moodle/{courseInternalName}/{subjectInternalName}/{type}/one/{ID} >

* ##### Method:

	`GET`

* ##### Params
     * Required:
    * `courseInternalName=[String]`
    * `subjectInternalName=[String]`
    * `type=[String]`
    * `ID=[Long]`
 
* ##### Success Response: 200 OK
      {
          "studyItemID": 1,
          "type": "file-pdf",
          "name": "Theme 1",
          "fileName": "studyItem-1",
          "subject": {
              "subjectID": 1,
              "name": "History of AI",
              "description": "A short history for the AI",
              "internalName": "history-of-ai",
              "numberModules": 3,
              "users": [...]
          },
          "module": 1,
          "originalName": "theme-1.txt",
          "extension": null,
          "icon": "pdf",
          "isPractice": false,
          "practices": [],
          "practice": false
      }
* ##### Error Response:

	**Code:** 404
	

   #### Gets a studyItem/practice file from a subject

  * ##### URL

	< /moodle/{courseInternalName}/{subjectInternalName}/{type}/file/{ID} >

* ##### Method:

	`GET`

* ##### Params
     * Required:
    * `courseInternalName=[String]`
    * `subjectInternalName=[String]`
    * `type=[String]`
 
* ##### Success Response: 200 OK
	
	The file

* ##### Error Response:

	**Code:** 404 409
	
 #### Creates a studyItem
	
* ##### URL

	< /moodle/{courseInternalName}/{subjectInternalName}/studyItem/module/{module} >

* ##### Method:

	`POST`

* ##### Params
     * Required:
    * `courseInternalName=[String]`
    * `subjectInternalName=[String]`
    * `module=[Integer]`
    * JSON:
    * `name=[String]`
    * `type=[String]`
 
* ##### Success Response: 200 OK
      {
          "studyItemID": 14,
          "type": "file-pdf",
          "name": "newOne",
          "fileName": null,
          "subject": {
              "subjectID": 1,
              "name": "History of AI",
              "description": "A short history for the AI",
              "internalName": "history-of-ai",
              "numberModules": 3,
              "users": [..]
          },
          "module": 1,
          "originalName": null,
          "extension": null,
          "icon": null,
          "isPractice": false,
          "practices": [],
          "practice": false
      }

* ##### Error Response:

	**Code:** 404
	
#### Creates a Practice
	
* ##### URL

	< /moodle/{courseInternalName}/{subjectInternalName}/practice >

* ##### Method:

	`POST`

* ##### Params
     * Required:
    * `courseInternalName=[String]`
    * `subjectInternalName=[String]`
    * JSON:
    * `name=[String]`
    * `icon=[String]`
 
* ##### Success Response: 200 OK
      {
          "studyItemID": 15,
          "type": "file-pdf",
          "name": "newOne",
          "fileName": null,
          "subject": {
              "subjectID": 1,
              "name": "History of AI",
              "description": "A short history for the AI",
              "internalName": "history-of-ai",
              "numberModules": 3,
              "users": [...]
          },
          "module": -3,
          "originalName": null,
          "extension": null,
          "icon": null,
          "isPractice": true,
          "practices": [],
          "practice": true
      }

* ##### Error Response:

	**Code:** 404
	
	
	
 #### Uploads a Practice/studyItem file
	
* ##### URL

	< /moodle/{courseInternalName}/{subjectInternalName}/{type}/file/{studyItemID} >

* ##### Method:

	`POST`

* ##### Params
     * Required:
    * `courseInternalName=[String]`
    * `subjectInternalName=[String]`
    * `type=[String]`
    * `studyItemID=[Long]`
    
    * Data
    * `itemFile=[MultipartFile]`
 
* ##### Success Response: 200 OK
      {
          "studyItemID": 14,
          "type": "file-pdf",
          "name": "newOne",
          "fileName": "studyItem-14.txt",
          "subject": {
              "subjectID": 1,
              "name": "History of AI",
              "description": "A short history for the AI",
              "internalName": "history-of-ai",
              "numberModules": 3,
              "users": [..]
          },
          "module": 1,
          "originalName": "new file.txt",
          "extension": "txt",
          "icon": null,
          "isPractice": false,
          "practices": [],
          "practice": false
      }

* ##### Error Response:

	**Code:** 404

#### Modifies a practice/studyItem
	
* ##### URL

	< /moodle/{courseInternalName}/{subjectInternalName}/{type}/{ID} >

* ##### Method:

	`PUT`

* ##### Params
     * Required:
    * `courseInternalName=[String]`
    * `subjectInternalName=[String]`
    * `type=[String]`
    * `ID=[Long]`
    * JSON
    * `name=[String]`
    * if studyItem : `type=[String]` if practice : `icon=[String]`
 
* ##### Success Response: 200 OK
      {
          "studyItemID": 12,
          "type": "file-word",
          "name": "newNam33e",
          "fileName": "studyItem-12",
          "subject": {
              "subjectID": 1,
              "name": "History of AI",
              "description": "A short history for the AI",
              "internalName": "history-of-ai",
              "numberModules": 3,
              "users": [...]
           },
          "module": -1,
          "originalName": "Practice 1.txt",
          "extension": null,
          "icon": "word",
          "isPractice": true,
          "practices": [...],
          "practice": true
      }

* ##### Error Response:

	**Code:** 404
  
 #### Modifies a Practice/studyItem file
	
* ##### URL

	< /moodle/{courseInternalName}/{subjectInternalName}/{type}/file/{studyItemID} >

* ##### Method:

	`PUT`

* ##### Params
     * Required:
    * `courseInternalName=[String]`
    * `subjectInternalName=[String]`
    * `type=[String]`
    * `studyItemID=[Long]`
    
    * Data
    * `itemFile=[MultipartFile]`
 
* ##### Success Response: 200 OK
      {
          "studyItemID": 1,
          "type": "file-pdf",
          "name": "Theme 1",
          "fileName": "studyItem-1.txt",
          "subject": {
              "subjectID": 1,
              "name": "History of AI",
              "description": "A short history for the AI",
              "internalName": "history-of-ai",
              "numberModules": 3,
              "users": [...]
          },
          "module": 1,
          "originalName": "hola.txt",
          "extension": "txt",
          "icon": "pdf",
          "isPractice": false,
          "practices": [],
          "practice": false
      }

* ##### Error Response:

	**Code:** 404
	
  #### Delete a Practice/studyItem
	
* ##### URL

	< /moodle/{courseInternalName}/{subjectInternalName}/{type}/{ID} >

* ##### Method:

	`DELETE`

* ##### Params
     * Required:
    * `courseInternalName=[String]`
    * `subjectInternalName=[String]`
    * `type=[String]`
    * `ID=[Long]`
    
 
* ##### Success Response: 200 OK
      {
          "studyItemID": 1,
          "type": "file-pdf",
          "name": "Theme 1",
          "fileName": "studyItem-1.txt",
          "subject": null,
          "module": 1,
          "originalName": "hola.txt",
          "extension": "txt",
          "icon": "pdf",
          "isPractice": false,
          "practices": null,
          "practice": false
      }

* ##### Error Response:

	**Code:** 404
	
	
#### Deletes all studyItems of a module from a subject
	
* ##### URL

	< /moodle/{courseInternalName}/{subjectInternalName}/studyItem/module/{module} >

* ##### Method:

	`DELETE`

* ##### Params
     * Required:
    * `courseInternalName=[String]`
    * `subjectInternalName=[String]`
    * `module=[Integer]`
    
 
* ##### Success Response: 200 OK
      [
          {
              "studyItemID": 2,
              "type": "file-pdf",
              "name": "Theme 1 exercicies",
              "fileName": "studyItem-2",
              "module": 1,
              "originalName": "theme-1 exercices.txt",
              "extension": null,
              "icon": "pdf",
              "isPractice": false,
              "practice": false
          }, {...}
      ]

* ##### Error Response:

	**Code:** 404
	
### Practices Submissions 
A practice submission is what students submit to a practice statement
	
#### If the user is a student gets his practice submission of a practice or all the practices submissions (from a practice) if the user is a teache
	
* ##### URL

	< /moodle/{courseInternalName}/{subjectInternalName}/submissions/{practiceID}>

* ##### Method:

	`GET`

* ##### Params
     * Required:
    * `courseInternalName=[String]`
    * `subjectInternalName=[String]`
    * `practiceID=[Long]`
    
 
* ##### Success Response: 200 OK
      {
          "content": [
              {
                  "practiceID": 1,
                  "practiceName": "practice 1",
                  "calification": 7.3,
                  "studyItem": {
                      "studyItemID": 12,
                      "type": "file-word",
                      "name": "newNam33e",
                      "fileName": "studyItem-12",
                      "subject": { ... },
                  "owner": {
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
                  },
                  "originalName": "practice-1.txt",
                  "presented": true,
                  "corrected": true,
                  "subject": { ...}
              },
              {
                  "practiceID": 2,
                  "practiceName": "practice 1",
                  "calification": 5.25,
                  "studyItem": {
                      "studyItemID": 12,
                      "type": "file-word",
                      "name": "newNam33e",
                      "fileName": "studyItem-12",
                      "subject": { ... },
                  "owner": {
                      "userID": 6,
                      "username": "student-5",
                      "userMail": "user5@mail.com",
                      "userFirstName": "",
                      "userLastName": "",
                      "internalName": "student-5",
                      "roles": [
                          "ROLE_USER"
                      ],
                      "admin": false,
                      "student": true
                  },
                  "originalName": "practice-2.txt",
                  "presented": true,
                  "corrected": true,
                  "subject": { ... }
              }
          ],
          "last": true,
          "totalElements": 2,
          "totalPages": 1,
          "size": 10,
          "number": 0,
          "sort": null,
          "first": true,
          "numberOfElements": 2
      }

* ##### Error Response:

	**Code:** 404 400
	

#### Gets the file of the practice submission
	
* ##### URL

	< /moodle/{courseInternalName}/{subjectInternalName}/submissions/{practiceID}/file/{submissionID}>

* ##### Method:

	`GET`

* ##### Params
     * Required:
    * `courseInternalName=[String]`
    * `subjectInternalName=[String]`
    * `practiceID=[Long]`
    * `submissionID=[Long]`
    
 
* ##### Success Response: 200 OK
    
    The File

* ##### Error Response:

	**Code:** 404 400
	
#### Creates a practice submission. Only students can add submissions to a practice
	
* ##### URL

	< moodle/{courseInternalName}/{subjectInternalName}/submissions/{practiceID}>

* ##### Method:

	`POST`

* ##### Params
     * Required:
    * `courseInternalName=[String]`
    * `subjectInternalName=[String]`
    * `practiceID=[Long]`
    
    * JSON
    * `practiceName=[String]`
 
* ##### Success Response: 200 OK
      {
          "practiceID": 4,
          "practiceName": "New Practice",
          "calification": 0,
          "studyItem": {
              "studyItemID": 13,
              "type": "file-pdf",
              "name": "Practice 2",
              "fileName": "studyItem-13",
              "subject": { ... },
              "module": -1,
              "originalName": "Practice 2.txt",
              "extension": null,
              "icon": "pdf",
              "isPractice": true,
              "practice": true
          },     "owner": {
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
          },
          "originalName": "Not Presented",
          "presented": true,
          "corrected": false,
          "subject" : {...}
         }

* ##### Error Response:

	**Code:** 404 400 226
	

#### Adds a file to a practice submission. Only the owner of the submission can.
	
* ##### URL

	< moodle/{courseInternalName}/{subjectInternalName}/submissions/{practiceID}/file/{submissionID}>

* ##### Method:

	`POST`

* ##### Params
     * Required:
    * `courseInternalName=[String]`
    * `subjectInternalName=[String]`
    * `practiceID=[Long]`
    * `submissionID=[Long]`
    
    * DATA 
    * `file=[MultipartFile]`
 
* ##### Success Response: 200 OK
      {
          "practiceID": 4,
          "practiceName": "New Practice",
          "calification": 0,
          "originalName": "hola.txt",
          "presented": true,
          "corrected": false,
          "subject": {...}
      }

* ##### Error Response:

	**Code:** 404 400 
	
 #### Modifies a practice submission. User can modify the name and the teacher the calification.

* ##### URL

	< moodle/{courseInternalName}/{subjectInternalName}/submissions/{practiceID}/file/{submissionID}>

* ##### Method:

	`PUT`

* ##### Params
     * Required:
    * `courseInternalName=[String]`
    * `subjectInternalName=[String]`
    * `practiceID=[Long]`
    * `submissionID=[Long]`
    
    * JSON
    * if user is student `practiceName=[String]` else if user is teacher `calification=[Double]`
 
* ##### Success Response: 200 OK
      {
          "practiceID": 4,
          "practiceName": "New Practice",
          "calification": 3.2,
          "studyItem": {
              "studyItemID": 13,
              "type": "file-pdf",
              "name": "Practice 2",
              "fileName": "studyItem-13",
              "module": -1,
              "originalName": "Practice 2.txt",
              "extension": null,
              "icon": "pdf",
              "isPractice": true,
              "practice": true
              "subject" : {...}
          },
          "owner": {
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
          },
          "originalName": "hola.txt",
          "presented": true,
          "corrected": true,
          "subject": { ... }
      }

* ##### Error Response:

	**Code:** 404 400 
	
 #### Modifies a practice submission file.

* ##### URL

	< moodle/{courseInternalName}/{subjectInternalName}/submissions/{practiceID}/file/{submissionID} >

* ##### Method:

	`PUT`

* ##### Params
     * Required:
    * `courseInternalName=[String]`
    * `subjectInternalName=[String]`
    * `practiceID=[Long]`
    * `submissionID=[Long]`
    
    * Data
    * `itemFile=[MultipartFile]`
 
* ##### Success Response: 200 OK
      {
          "practiceID": 4,
          "practiceName": "New Practice",
          "calification": 3.2,
          "originalName": "hola.txt",
          "presented": true,
          "corrected": true,
          "subject": { ... }
      }

* ##### Error Response:

	**Code:** 404 400 
	
 #### Deletes a practice submission of a practice.

* ##### URL

	< moodle/{courseInternalName}/{subjectInternalName}/submissions/{practiceID}/{submissionID} >

* ##### Method:

	`DELETE`

* ##### Params
     * Required:
    * `courseInternalName=[String]`
    * `subjectInternalName=[String]`
    * `practiceID=[Long]`
    * `submissionID=[Long]`
 
* ##### Success Response: 200 OK
      {
          "practiceID": 1,
          "practiceName": "practice 1",
          "calification": 7.3,
          "originalName": "practice-1.txt",
          "presented": true,
          "corrected": true,
          "subject": { ... }
      }

* ##### Error Response:

	**Code:** 404 400 

#### Gets the marks from every practice of the subject. If user is a teacher it gets the mean calification per practice and if user the calification

* ##### URL

	< /api/moodle/practicesMarks/{courseInternalName}/{subjectInternalName}/practices/ >

* ##### Method:

	`GET`

* ##### Params
     * Required:
    * `courseInternalName=[String]`
    * `subjectInternalName=[String]`
 
* ##### Success Response: 200 OK
      [
          [
              "newNam33e",
              0
          ],
          [
              "Practice 2",
              3.2
          ],
          [
              "newOne",
              0
          ],
          [
              "newOne",
              0
          ]
      ]

* ##### Error Response:

	**Code:** 404 400 

## Subjects
All Subject queries are preceded by '/courses/{courseInternalName}'
### New Subject
Create a new subject within a course. Only the admin can add new subjects to a current course.

* ##### URL

	</subjects>
	
* ##### Method:

	'POST'

* ##### Params:
   * Required:
   	* `subjectName=[String]`

* ##### Success Response: 201 CREATED

* ##### Error response:
	**Code:** 500 INTERNAL SERVER ERROR (if params are missed or incorrect)
	**Code:** 401 UNAUTHORIZED

### Delete Subject
Delete a subject from given course. Only the admin can remove subjects from a current course.

* ##### URL

	</{subjectInternalName}>

* ##### Method:

	'DELETE'

* ##### Success Response: 201 CREATED

* ##### Error response:
	**Code:** 404 NOT FOUND
	**Code:** 401 UNAUTHORIZED
	
### Udate subject
Modify subject information. Only the admin can modify this data.

* ##### URL

	</{subjectInternalName}>

* ##### Method:

	'PUT'

* ##### Request Body:
	{
		"name":"History of AI"
		"description":"A short history for the AI"
	}
* ##### Success Response: 200 OK

* ##### Error response:
	**Code:** 401 UNAUTHORIZED

### Show all subjects
Shows all the subjects that the current course have

* ##### URL

	</{subjectInternalName}/subjects/all>

* ##### URL Params:

	* `page=[int]`

* ##### URL Example: `/api/courses/matematicas-para-gatos/subjects/all?page=1`

* ##### Method:

	`GET`
	
* ##### Success Response: 302 FOUND

* ##### Error Response:

	**Code:** 404 NOT FOUND

### Show one subject
Shows just the requested subject from the current course

* ##### URL
	</{subjectInternalName}>

* ##### Method:

	`GET`

* ##### Success Response: 302 FOUND

* ##### Error Response:

	**Code:** 404 NOT FOUND

---

## Courses
All User queries are preceded `/courses`
### New course

Create a new course. Only admin can create it.

* ##### URL

	< / >

* ##### Method:

	`POST`

* ##### Body
  * Required:
    * `course=Course`
 
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
* ##### Error Responses:

	**Code:** 511 NETWORK AUTHENTICATION REQUIRED (if you aren't authenticated yet in the application)
	**Code:** 401 UNAUTHORIZED (if you are authenticated but you haven't got permissions to do that action)
	**Code:** 415 UNSUPPORTED MEDIA TYPE (if JSON course request body format are incorrect)
	**Code:** 201 CREATED (if the course is created successfully)
	**Code:** 500 INTERNAL SERVER ERROR (if params are incorrect)

### Update Course

Edit an existing course. Only admin can do it.

* ##### URL

	< / >

* ##### Method:

	`PUT`

* ##### Body
  * Required:
    * `course=Course`
 
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
* ##### Error Responses:

	**Code:** 511 NETWORK AUTHENTICATION REQUIRED (if you aren't authenticated yet in the application)
	**Code:** 401 UNAUTHORIZED (if you are authenticated but you haven't got permissions to do that action)
	**Code:** 415 UNSUPPORTED MEDIA TYPE (if JSON course request body format are incorrect)
	**Code:** 200 OK (if the course is edited successfully)
	**Code:** 404 NOT FOUND (if the course that you want to edit doesn't exist in the BD)
	**Code:** 500 INTERNAL SERVER ERROR (if params are incorrect)
	
### Add user to a Course

The user who is logged into the application is added into the course. Only students can be added.

* ##### URL

	< /id/{courseID} >
	< /name/{internalName} >

* ##### Method:

	`PUT`

* ##### Path Variables
  * Required (only one):
    * `courseID=Long`
    * `internalName=String`
 
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
* ##### Error Responses:

	**Code:** 511 NETWORK AUTHENTICATION REQUIRED (if you aren't authenticated yet in the application)
	**Code:** 401 UNAUTHORIZED (if you are authenticated but you haven't got permissions to do that action)
	**Code:** 415 UNSUPPORTED MEDIA TYPE (if JSON course request body format are incorrect)
	**Code:** 200 OK (if the course is edited successfully)
	**Code:** 404 NOT FOUND (if the course that you want to edit doesn't exist in the BD)
	**Code:** 500 INTERNAL SERVER ERROR (if params are incorrect)
	**Code:** BAD REQUEST (if the user is already inscribed in the course or the user had completed the course before)

### Delete one course

Delete one course to the BD. Only Admin can do that action

* ##### URL

	< /id/{courseID} >
	< /name/{internalName} >
	
* ##### Method:

	`DELETE`

* ##### Path Variables
  * Required (only one):
    * `courseID=Long`
    * `internalName=String`
 
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
* ##### Error Responses:

	**Code:** 302 FOUND (if the courses are found successfully)
	**Code:** 404 NOT FOUND (if no courses are found)
	**Code:** 500 INTERNAL SERVER ERROR (if params are incorrect)
	

### Get one Course

Get the information of one course into the BD.

* ##### URL

	< /id/{courseID} >
	< /name/{internalName} >

* ##### Method:

	`GET`

* ##### Path Variables
  * Required (only one):
    * `courseID=Long`
    * `internalName=String`
 
* ##### Success Response: 302 FOUND
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
* ##### Error Responses:

	**Code:** 302 FOUND (if the course is found successfully)
	**Code:** 404 NOT FOUND (if the course that you want to see doesn't exist in the BD)
	**Code:** 500 INTERNAL SERVER ERROR (if params are incorrect)
	
### Get All Courses

Get the information of all courses into the BD.

* ##### URL

	< / >

* ##### Method:

	`GET`

* ##### Path Variables
  * Required:
    * `page=int (defaultValue=0)`
    * `sort=String (defaultValue='courseID')`
    * `type=String (defaultValue='all')`
    * `name=String (defaultValue='')`
 
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
* ##### Error Responses:

	**Code:** 302 FOUND (if the courses are found successfully)
	**Code:** 404 NOT FOUND (if no courses are found)
	**Code:** 500 INTERNAL SERVER ERROR (if params are incorrect)
	
### Get Course Subjects

Get the information of all courses into the BD.

* ##### URL

	< /id/{courseID}/subjects/ >
	< /name/{internalName}/subjects/ >

* ##### Method:

	`GET`

* ##### Path Variables
  * Required (only one):
    * `courseID=Long`
    * `internalName=String`
 
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
* ##### Error Responses:

	**Code:** 302 FOUND (if the courses are found successfully)
	**Code:** 404 NOT FOUND (if no courses are found)
	**Code:** 500 INTERNAL SERVER ERROR (if params are incorrect)
	
### Get Course Skills

Get the information of all courses into the BD.

* ##### URL

	< /id/{courseID}/skills/ >
	< /name/{internalName}/skills/ >

* ##### Method:

	`GET`

* ##### Path Variables
  * Required (only one):
    * `courseID=Long`
    * `internalName=String`
 
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
* ##### Error Responses:

	**Code:** 302 FOUND (if the courses are found successfully)
	**Code:** 404 NOT FOUND (if no courses are found)
	**Code:** 500 INTERNAL SERVER ERROR (if params are incorrect)
