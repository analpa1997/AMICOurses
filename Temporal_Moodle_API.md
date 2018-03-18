
# API REST DOCUMENTATION
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



