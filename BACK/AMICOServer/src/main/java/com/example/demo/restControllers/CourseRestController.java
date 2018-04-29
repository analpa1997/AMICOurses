package com.example.demo.restControllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.course.Course;
import com.example.demo.course.CourseRepository;
import com.example.demo.course.CourseService;
import com.example.demo.skill.Skill;
import com.example.demo.subject.Subject;
import com.example.demo.user.SessionUserComponent;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
@RequestMapping(value = "/api/courses")
public class CourseRestController {

	interface CourseBasicInformation extends Course.BasicCourse  {
	}

	interface CourseDetail extends Course.BasicCourse, Course.UserInformation, User.BasicUser {
	}

	interface SkillBasicInformation extends Skill.BasicSkill {
	}

	interface SubjectsDetail
			extends Subject.SubjectsBasicInformation, Subject.CourseBasicInformation, Course.BasicCourse {
	}

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SessionUserComponent sessionUserComponent;

	@Autowired
	private CourseService courseService;

	private String message;

	@RequestMapping(value = { "/id/{courseID}/add", "/name/{internalName}/add" }, method = RequestMethod.PUT)
	public ResponseEntity<String> addCourseToUser(@PathVariable(required = false) String internalName,
			@PathVariable(required = false) Long courseID) {

		Course course;
		User user = null;
		int courseInscribedCounter;
		int courseCompletedCounter;
		List<User> uInscribedList = null; // Users list
		List<Course> cInscribedUsers = null; // Courses list
		List<Course> cCompletedList = null;

		// check if the visitor is registered
		if (!sessionUserComponent.isLoggedUser()) {
			message = "To register for a course it is necessary to be logged into the system. Press AMICOURSES to return to the main screen and be able to register in the system ";
			return new ResponseEntity<>(message, HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
		} else { // registered. Get the user data

			// System.out.println ("adding a new course to a user");

			if (courseID == null)
				course = courseRepository.findByInternalName(internalName);
			else
				course = courseRepository.getOne(courseID);
			user = userRepository.findByUserID(sessionUserComponent.getLoggedUser().getUserID());

			if (!user.isStudent()) {
				message = "Users who aren't students cannot be registered into a course";
				return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);

			} else {
				if (course.getInscribedUsers().size() > 0)
					uInscribedList = course.getInscribedUsers(); // get list of user subscribed to course
				else
					uInscribedList = new ArrayList<>(); // initialize array to add user

				if (user.getInscribedCourses().size() > 0)
					cInscribedUsers = user.getInscribedCourses(); // get the list of courses to which the user is
																	// subscribed
				else
					cInscribedUsers = new ArrayList<>(); // initialize array to add users

				if (user.getCompletedCourses().size() > 0)
					cCompletedList = user.getCompletedCourses(); // get the list of courses to which the user is
																	// subscribed
				else
					cCompletedList = new ArrayList<>(); // initialize array to add users

				courseInscribedCounter = 0;
				while (courseInscribedCounter < cInscribedUsers.size()
						&& cInscribedUsers.get(courseInscribedCounter).getCourseID() != course.getCourseID())
					courseInscribedCounter++;

				courseCompletedCounter = 0;
				while (courseCompletedCounter < cCompletedList.size()
						&& cCompletedList.get(courseCompletedCounter).getCourseID() != course.getCourseID())
					courseCompletedCounter++;

				if (courseCompletedCounter != cCompletedList.size()) {
					message = "You have already completed the course " + course.getInternalName() + ".";
					return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
				} else if (courseInscribedCounter != cInscribedUsers.size()) {
					message = "You are already registered in the course" + course.getInternalName() + ".";
					return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
				} else { // user can register in the course

					cInscribedUsers.add(course);
					user.setInscribedCourses(cInscribedUsers);
					userRepository.save(user); // update user

					uInscribedList.add(user);
					course.setInscribedUsers(uInscribedList);
					course.setNumberOfUsers(course.getNumberOfUsers() + 1);
					courseRepository.save(course);// update courses

					// System.out.println ("course added");

					// go to user profile

					message = "User added successfully to course " + course.getInternalName() + ".";
					return new ResponseEntity<>(HttpStatus.OK);
				}
			}
		}

		// go to same page to show the message

	}

	@JsonView(CourseBasicInformation.class)
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<Page<Course>> allCourses(Pageable pages,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "sort", defaultValue = "courseID") String nameField,
			@RequestParam(value = "type", defaultValue = "all") String type,
			@RequestParam(value = "name", defaultValue = "") String nameCourse) {
		Page<Course> pageCourse;

		if (!nameField.equals("")) {
			if (nameField.equals("numberOfUsers"))
				if (type.equals("all"))
					if (nameCourse.equals(""))
						pageCourse = courseRepository.findAll(new PageRequest(page, 10, Sort.Direction.DESC,
								nameField)); /*
												 * Return the page selected without any filter, only sorted by
												 * numberOfUsers
												 */
					else
						pageCourse = courseRepository.findByInternalNameContaining(nameCourse, new PageRequest(page, 10,
								Sort.Direction.DESC, nameField)); /*
																	 * Return the page selected filtered by partial name
																	 * and sorted by numberOfUsers
																	 */
				else if (nameCourse.equals(""))
					pageCourse = courseRepository.findByType(type, new PageRequest(page, 10, Sort.Direction.DESC,
							nameField)); /* Return the page filtered by type and sorted by numberOfUsers */
				else
					pageCourse = courseRepository.findByTypeAndInternalNameContaining(type, nameCourse,
							new PageRequest(page, 10, Sort.Direction.DESC,
									nameField)); /*
													 * Return the page filtered by type AND partial name of the course,
													 * and sorted by numberOfUsers
													 */
			else if (type.equals("all"))
				if (nameCourse.equals(""))
					pageCourse = courseRepository.findAll(new PageRequest(page, 10, Sort.Direction.ASC,
							nameField)); /* Return the page only sorted by another field distinct of numberOfUsers */
				else
					pageCourse = courseRepository.findByInternalNameContaining(nameCourse, new PageRequest(page, 10,
							Sort.Direction.ASC, nameField)); /*
																 * Return the page filtered by partial name and sorted
																 * by another field distinct of numberOfUsers
																 */
			else if (nameCourse.equals(""))
				pageCourse = courseRepository.findByType(type, new PageRequest(page, 10, Sort.Direction.ASC,
						nameField)); /*
										 * Return the page filtered by type and sorted by another field distinct of
										 * numberOfUsers
										 */
			else
				pageCourse = courseRepository.findByTypeAndInternalNameContaining(type, nameCourse,
						new PageRequest(page, 10, Sort.Direction.ASC,
								nameField)); /*
												 * Return the page filtered by type AND partial name, and sorted by
												 * another field distinct of numberOfUsers
												 */
		} else if (type.equals("all"))
			if (nameCourse.equals(""))
				pageCourse = courseRepository
						.findAll(new PageRequest(page, 10)); /* Return the page with all filter and sort by default */
			else
				pageCourse = courseRepository.findByInternalNameContaining(nameCourse, new PageRequest(page,
						10)); /* Return the page only filtered by partial name, sorted by default */
		else if (nameCourse.equals(""))
			pageCourse = courseRepository.findByType(type,
					new PageRequest(page, 10)); /* Return the page filtered only by type, sorted by default */
		else
			pageCourse = courseRepository.findByTypeAndInternalNameContaining(type, nameCourse, new PageRequest(page,
					10)); /* Return the page filtered by Type AND partial name, sorted by default */
		if (pageCourse != null)
			return new ResponseEntity<>(pageCourse, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public Course createCourse(@RequestBody Course course) {
		return courseService.createCourse(course);

	}

	@JsonView(CourseBasicInformation.class)
	@RequestMapping(value = { "/id/{courseID}/", "/name/{internalName}/" }, method = RequestMethod.DELETE)
	public ResponseEntity<Course> deleteCourse(@PathVariable(required = false) Long courseID,
			@PathVariable(required = false) String internalName) {
		if (!sessionUserComponent.isLoggedUser())
			return new ResponseEntity<>(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
		else if (!sessionUserComponent.getLoggedUser().isAdmin())
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		else {
			Course deletedCourse;
			if (internalName == null)
				deletedCourse = courseRepository.getOne(courseID);
			else
				deletedCourse = courseRepository.findByInternalName(internalName);
			if (deletedCourse != null) {
				courseService.deleteCourse(deletedCourse);
				return new ResponseEntity<>(deletedCourse, HttpStatus.OK);
			} else
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@JsonView(CourseBasicInformation.class)
	@RequestMapping(value = "/", method = RequestMethod.PUT)
	public ResponseEntity<Course> editCourse(@RequestBody Course newCourse) {
		if (sessionUserComponent.isLoggedUser())
			if (sessionUserComponent.getLoggedUser().isAdmin()) {
				Course editedCourse = courseService.editCourse(newCourse);
				if (editedCourse != null)
					return new ResponseEntity<>(editedCourse, HttpStatus.OK);
				else
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} else
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		else
			return new ResponseEntity<>(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);

	}

	/* Get profile photo */
	@RequestMapping(value = "/img/{courseID}", method = RequestMethod.GET)
	public void getProfilePhoto(@PathVariable Long courseID, HttpServletResponse res) throws IOException {

		Course course = courseRepository.findOne(courseID);

		if (course != null)
			try {
				Path image = courseService.getImgPath(course);
				res.setContentType("image/jpeg");
				res.setContentLength((int) image.toFile().length());
				FileCopyUtils.copy(Files.newInputStream(image), res.getOutputStream());

			} catch (IOException exception) {
				res.sendError(404);
				exception.printStackTrace();
			}
		else
			res.sendError(404);
	}

	@RequestMapping(value = "/types/", method = RequestMethod.GET)
	public ResponseEntity<List<String>> getTypes() {
		List<String> result = new ArrayList<>();
		for (Course c : courseRepository.findAll())
			if (!result.contains(c.getType()))
				result.add(c.getType());
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@JsonView(CourseBasicInformation.class)
	@RequestMapping(value = { "/id/{courseID}/", "/name/{internalName}/" }, method = RequestMethod.GET)
	public ResponseEntity<Course> oneCourse(@PathVariable(required = false) Long courseID,
			@PathVariable(required = false) String internalName) {
		Course course;
		if (internalName == null)
			course = courseRepository.findOne(courseID);
		else
			course = courseRepository.findByInternalName(internalName);
		if (course != null)
			return new ResponseEntity<>(course, HttpStatus.OK);
		else
			return new ResponseEntity(HttpStatus.NOT_FOUND);
	}
	
	interface FullCourse extends Course.BasicCourse, Course.ExtendedCourse, Subject.SubjectsBasicInformation, User.BasicUser {}
	@JsonView(FullCourse.class)
	@RequestMapping(value = { "/id/{courseID}/full", "/name/{internalName}/full" }, method = RequestMethod.GET)
	public ResponseEntity<Course> oneCourseFull(@PathVariable(required = false) Long courseID,
			@PathVariable(required = false) String internalName) {
		Course course;
		if (internalName == null)
			course = courseRepository.findOne(courseID);
		else
			course = courseRepository.findByInternalName(internalName);
		if (course != null)
			return new ResponseEntity<>(course, HttpStatus.OK);
		else
			return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	@JsonView(SkillBasicInformation.class)
	@RequestMapping(value = { "/id/{courseID}/skills/", "/name/{internalName}/skills/" }, method = RequestMethod.GET)
	public ResponseEntity<List<Skill>> skills(@PathVariable(required = false) String internalName,
			@PathVariable(required = false) Long courseID) {

		Course course = null;
		if (internalName == null)
			course = courseRepository.findOne(courseID);
		else
			course = courseRepository.findByInternalName(internalName);
		if (course != null) {

			List<Skill> skillList;
			skillList = course.getSkills();

			return new ResponseEntity<>(skillList, HttpStatus.OK);
		} else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@JsonView(SubjectsDetail.class)
	@RequestMapping(value = { "/id/{courseID}/subjects/",
			"/name/{internalName}/subjects/" }, method = RequestMethod.GET)
	public ResponseEntity<List<Subject>> subjects(@PathVariable(required = false) String internalName,
			@PathVariable(required = false) Long courseID) {

		Course course = null;

		if (internalName == null)
			course = courseRepository.findOne(courseID);
		else
			course = courseRepository.findByInternalName(internalName);

		if (course != null) {

			List<Subject> subjectsList;
			subjectsList = course.getSubjects();

			return new ResponseEntity<>(subjectsList, HttpStatus.OK);
		} else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	/* Put a profile photo */
	@JsonView(CourseBasicInformation.class)
	@RequestMapping(value = "/img/{courseID}", method = RequestMethod.PUT)
	public ResponseEntity<Course> uploadProfilePhoto(User userUpdated, @PathVariable Long courseID,
			@RequestParam("courseImage") MultipartFile file) {

		User user = sessionUserComponent.getLoggedUser();

		if (user != null && user.isAdmin()) {
			/* Image uploading controll. If a profile image exists, it is overwritten */

			Course course = courseRepository.findOne(courseID);
			if (course != null) {
				courseService.addImg(course, file);
				return new ResponseEntity<>(course, HttpStatus.OK);
			} else
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
