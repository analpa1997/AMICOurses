
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
      {"subjectID":1,
      "name":"History of AI",
      "description":"A short history for the AI",
      "internalName":"history-of-ai",
      "numberModules":4,
      "users":[]}
* ##### Error Response:

	**Code:** 404 NOT FOUND

 ### Delete Module

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

	< /api/moodle/{courseInternalName}/{subjectInternalName}/{type}/one/{ID} >

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

	< /api/moodle/{courseInternalName}/{subjectInternalName}/{type}/file/{ID} >

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

	< /api/moodle/{courseInternalName}/{subjectInternalName}/studyItem/module/{module} >

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

	< /api/moodle/{courseInternalName}/{subjectInternalName}/practice >

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

	< /api/moodle/{courseInternalName}/{subjectInternalName}/{type}/file/{studyItemID} >

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

	< /api/moodle/{courseInternalName}/{subjectInternalName}/{type}/{ID} >

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

	< /api/moodle/{courseInternalName}/{subjectInternalName}/{type}/file/{studyItemID} >

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

