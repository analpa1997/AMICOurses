package com.example.demo.restControllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.course.Course;
import com.example.demo.course.CourseRepository;
import com.example.demo.practices.Practices;
import com.example.demo.practices.PracticesRepository;
import com.example.demo.practices.PracticesService;
import com.example.demo.studyItem.StudyItem;
import com.example.demo.studyItem.StudyItem.Practice;
import com.example.demo.studyItem.StudyItemRepository;
import com.example.demo.studyItem.StudyItemService;
import com.example.demo.subject.Subject;
import com.example.demo.subject.SubjectRepository;
import com.example.demo.subject.SubjectService;
import com.example.demo.user.SessionUserComponent;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
public class MoodleRestController {

	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private StudyItemRepository studyItemRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PracticesRepository practiceSubmissionRepository;

	@Autowired
	private SubjectService subjectService;
	@Autowired
	private StudyItemService studyItemService;
	@Autowired
	private PracticesService practicesSubmissionService;

	@Autowired
	private SessionUserComponent sessionUserComponent;

	/* MODULES */
	/* POST */
	/* Creates a new module within a subject */
	@JsonView(Subject.SubjectsBasicInformation.class)
	@RequestMapping(value = "/api/moodle/{courseInternalName}/{subjectInternalName}/module", method = RequestMethod.POST)
	public ResponseEntity<Subject> addModule(@PathVariable String courseInternalName,
			@PathVariable String subjectInternalName) {

		User user = sessionUserComponent.getLoggedUser();

		if (user != null && !user.isStudent()) {
			Subject subject = subjectService.checkForSubject(user, courseInternalName, subjectInternalName);
			if (subject != null) {
				subjectService.addModule(subject);
				return new ResponseEntity<>(subject, HttpStatus.OK);
			}
		}
		/* If program reaches here is that there has been an error */
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	/* DELETE */
	/* Deletes new module within a subject */
	@JsonView(Subject.SubjectsBasicInformation.class)
	@RequestMapping(value = "/api/moodle/{courseInternalName}/{subjectInternalName}/module/{module}", method = RequestMethod.DELETE)
	public ResponseEntity<Subject> deleteModule(@PathVariable String courseInternalName,
			@PathVariable String subjectInternalName, @PathVariable Integer module) {

		User user = sessionUserComponent.getLoggedUser();

		if (user != null && !user.isStudent()) {
			Subject subject = subjectService.checkForSubject(user, courseInternalName, subjectInternalName);
			if (subject != null) {
				if (subject.getNumberModules() >= module && module > 0) {
					subjectService.deleteModule(subject, module);
					return new ResponseEntity<>(subject, HttpStatus.OK);
				} else {
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}

			}
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	/* STUDY ITEMS / PRACTICES */

	/* GET */
	/* Retrieves all the studyItems/practices from a subject */

	@JsonView(StudyItem.BasicStudyItem.class)
	@RequestMapping(value = "/api/moodle/{courseInternalName}/{subjectInternalName}/{type}/all", method = RequestMethod.GET)
	public ResponseEntity<Page<StudyItem>> getAllStudyItems(Pageable pages, @PathVariable String courseInternalName,
			@PathVariable String subjectInternalName, @PathVariable String type,
			@RequestParam(value = "page", defaultValue = "0") int page) {

		User user = sessionUserComponent.getLoggedUser();

		if (user != null) {
			Subject subject = subjectService.checkForSubject(user, courseInternalName, subjectInternalName);
			if (subject != null) {
				Page<StudyItem> studyItems = null;

				if (type.equals("studyItem")) {
					studyItems = subjectService.getStudyItems(subject, new PageRequest(page, 10));
				} else {
					if (type.equals("practice")) {
						studyItems = subjectService.getPractices(subject, new PageRequest(page, 10));
					}
				}
				if (studyItems != null) {
					return new ResponseEntity<>(studyItems, HttpStatus.OK);
				}
			}
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	/* Retrieves all the studyItems from a module from a subject */
	@JsonView(StudyItem.BasicStudyItem.class)
	@RequestMapping(value = "/api/moodle/{courseInternalName}/{subjectInternalName}/studyItem/module/{module}", method = RequestMethod.GET)
	public ResponseEntity<Page<StudyItem>> getStudyItemsFromModule(Pageable pages,
			@PathVariable String courseInternalName, @PathVariable String subjectInternalName,
			@PathVariable Integer module, @RequestParam(value = "page", defaultValue = "0") int page) {

		User user = sessionUserComponent.getLoggedUser();

		if (user != null) {
			Subject subject = subjectService.checkForSubject(user, courseInternalName, subjectInternalName);
			if (subject != null) {
				Page<StudyItem> studyItems = subjectService.getStudyItems(subject, module, new PageRequest(page, 10));
				return new ResponseEntity<>(studyItems, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	/* Retrieves only one studyItem/practice from a subject */

	interface StudyItemDetailed extends StudyItem.BasicStudyItem, StudyItem.Practice, StudyItem.SubjectOrigin,
			Practices.BasicPractice, Subject.SubjectsBasicInformation {
	}

	@JsonView(StudyItemDetailed.class)
	@RequestMapping(value = "/api/moodle/{courseInternalName}/{subjectInternalName}/{type}/one/{studyItemID}", method = RequestMethod.GET)
	public ResponseEntity<StudyItem> getOneStudyItem(@PathVariable String courseInternalName,
			@PathVariable String subjectInternalName, @PathVariable String type, @PathVariable Long studyItemID) {

		User user = sessionUserComponent.getLoggedUser();

		if (user != null) {
			Subject subject = subjectService.checkForSubject(user, courseInternalName, subjectInternalName);
			if (subject != null) {

				StudyItem studyItem = subjectService.getStudyItem(subject, studyItemID);

				boolean isValidRequest = (studyItem != null && (type.equals("studyItem") && !studyItem.isPractice())
						|| (type.equals("practice") && studyItem.isPractice()));

				if (isValidRequest) {
					return new ResponseEntity<>(studyItem, HttpStatus.OK);
				}

			}
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	/* type can be or studyItem or practice */
	@RequestMapping(value = "/api/moodle/{courseInternalName}/{subjectInternalName}/{type}/file/{studyItemID}", method = RequestMethod.GET)
	public void getStudyItemFile(@PathVariable String courseInternalName, @PathVariable String subjectInternalName,
			@PathVariable String type, @PathVariable Long studyItemID, HttpServletResponse response)
			throws IOException {

		User user = sessionUserComponent.getLoggedUser();

		if (user != null && (type != null && !type.isEmpty())) {
			Subject subject = subjectService.checkForSubject(user, courseInternalName, subjectInternalName);
			if (subject != null && studyItemID != null) {
				StudyItem studyItem = subjectService.getStudyItem(subject, studyItemID);

				/*
				 * If the type and the actual type of the studyItem/practiceEnouncement match
				 */
				boolean isValidRequest = (studyItem != null && ((type.equals("studyItem") && !studyItem.isPractice())
						|| (type.equals("practice") && studyItem.isPractice())));
				if (isValidRequest) {
					Path pathFile = studyItemService.getStudyItemFile(subject.getCourse().getCourseID(),
							subject.getSubjectID(), studyItemID);
					try {
						response.addHeader("Content-Disposition",
								"attachment; filename = " + studyItem.getOriginalName());
						response.setContentType("application/octet-stream");
						response.setContentLength((int) pathFile.toFile().length());
						IOUtils.copy(Files.newInputStream(pathFile), response.getOutputStream());

					} catch (IOException e) {
						e.printStackTrace();
						response.sendError(404);
					}
				} else {
					if (studyItem != null) {
						String realType = studyItem.isPractice() ? "practice" : "studyItem";
						String msg = "The file type is not the type you has requested. (You asked for a " + type
								+ " but the actual type of the file is " + realType;
						response.getWriter().println("Error 404 not found");
						response.sendError(409, msg);
					} else {
						response.sendError(404);
					}
				}
			}
		} else {
			response.sendError(404);
		}
	}

	/* POST */

	/* Creates a studyItem within a subject and a module */
	@JsonView(StudyItemDetailed.class)
	@RequestMapping(value = "/api/moodle/{courseInternalName}/{subjectInternalName}/studyItem/module/{module}", method = RequestMethod.POST)
	public ResponseEntity<StudyItem> createStudyItem(@PathVariable String courseInternalName,
			@PathVariable String subjectInternalName, @PathVariable Integer module,
			@RequestBody StudyItem newStudyItem) {

		User user = sessionUserComponent.getLoggedUser();

		if (user != null && !user.isStudent() && (module != null)) {

			Subject subject = subjectService.checkForSubject(user, courseInternalName, subjectInternalName);
			if (subject != null) {
				if (subject.getTeachers().contains(user) && module <= subject.getNumberModules()) {
					/* If the user is a teacher of the subject can upload the file */

					/* File uploading control. If the file exists, it is overwritten */
					newStudyItem.setModule(module);
					newStudyItem = studyItemService.createStudyItem(subject, newStudyItem, false);
					return new ResponseEntity<>(newStudyItem, HttpStatus.OK);
				}

			}
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	/* Submits a file to a studyItem */
	@JsonView(StudyItemDetailed.class)
	@RequestMapping(value = "/api/moodle/{courseInternalName}/{subjectInternalName}/studyItem/file/{studyItemID}", method = RequestMethod.POST)
	public ResponseEntity<StudyItem> createStudyItemFile(@PathVariable String courseInternalName,
			@PathVariable String subjectInternalName, @PathVariable Long studyItemID,
			@RequestParam("itemFile") MultipartFile file) {

		User user = sessionUserComponent.getLoggedUser();

		if (user != null && !user.isStudent()) {

			Subject subject = subjectService.checkForSubject(user, courseInternalName, subjectInternalName);
			if (subject != null) {
				if (subject.getTeachers().contains(user)) {
					/* If the user is a teacher of the subject can upload the file */

					/* File uploading control. If the file exists, it is overwritten */
					StudyItem studyItem = studyItemRepository.findOne(studyItemID);
					if (subject.getStudyItemsList().contains(studyItem) && !file.isEmpty() && !studyItem.isPractice()) {
						try {
							StudyItem response = studyItemService.modifyStudyItemFile(file, studyItem);
							return new ResponseEntity<>(response, HttpStatus.OK);
						} catch (IOException e) {
							System.out.println(e.getMessage());
						}
					} else {
						return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
					}
				}
			}
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	/* Creates a studyItem within a subject and a module */
	@JsonView(StudyItemDetailed.class)
	@RequestMapping(value = "/api/moodle/{courseInternalName}/{subjectInternalName}/practice", method = RequestMethod.POST)
	public ResponseEntity<StudyItem> createPractice(@PathVariable String courseInternalName,
			@PathVariable String subjectInternalName,
			@RequestBody StudyItem newPractice) {

		User user = sessionUserComponent.getLoggedUser();

		if (user != null && !user.isStudent()){

			Subject subject = subjectService.checkForSubject(user, courseInternalName, subjectInternalName);
			if (subject != null) {
				if (subject.getTeachers().contains(user)) {
					/* If the user is a teacher of the subject can upload the file */

					/* File uploading control. If the file exists, it is overwritten */
					newPractice.setModule(-3);
					newPractice = studyItemService.createStudyItem(subject, newPractice, true);
					return new ResponseEntity<>(newPractice, HttpStatus.OK);
				}

			}
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	/* Submits a file to a studyItem within a subject and a module */
	@JsonView(StudyItemDetailed.class)
	@RequestMapping(value = "/api/moodle/{courseInternalName}/{subjectInternalName}/practice/file/{practiceID}", method = RequestMethod.POST)
	public ResponseEntity<StudyItem> createPracticeFile(@PathVariable String courseInternalName,
			@PathVariable String subjectInternalName, @PathVariable Long practiceID,
			@RequestParam("itemFile") MultipartFile file) {

		User user = sessionUserComponent.getLoggedUser();

		if (user != null && !user.isStudent()) {

			Subject subject = subjectService.checkForSubject(user, courseInternalName, subjectInternalName);
			if (subject != null) {
				if (subject.getTeachers().contains(user)) {
					/* If the user is a teacher of the subject can upload the file */

					/* File uploading control. If the file exists, it is overwritten */
					StudyItem practice = studyItemRepository.findOne(practiceID);
					if (subject.getStudyItemsList().contains(practice) && !file.isEmpty() && practice.isPractice()) {
						try {
							StudyItem response = studyItemService.modifyStudyItemFile(file, practice);
							return new ResponseEntity<>(response, HttpStatus.OK);
						} catch (IOException e) {
							System.out.println(e.getMessage());
						}
					}

				}
			}
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}



	/* UPDATE */

	/* Updates a studyItem/practices */
	@JsonView(StudyItemDetailed.class)
	@RequestMapping(value = "/api/moodle/{courseInternalName}/{subjectInternalName}/{type}/{studyItemID}", method = RequestMethod.PUT)
	public ResponseEntity<StudyItem> updateStudyItem(@PathVariable String courseInternalName,
			@PathVariable String subjectInternalName, @PathVariable String type, @PathVariable Long studyItemID,
			@RequestBody StudyItem newStudyItem) throws IOException {

		User user = sessionUserComponent.getLoggedUser();

		if (user != null && !user.isStudent()) {

			Subject subject = subjectService.checkForSubject(user, courseInternalName, subjectInternalName);
			if (subject != null) {
				if (subject.getTeachers().contains(user)) {
					/* If the user is a teacher of the subject can upload the file */
					/* File uploading control. If the file exists, it is overwritten */

					StudyItem studyItem = subjectService.getStudyItem(subject, studyItemID);
					boolean isValidRequest = (studyItem != null
							&& ((type.equals("studyItem") && !studyItem.isPractice())
									|| (type.equals("practice") && studyItem.isPractice())));
					if (isValidRequest) {

						studyItem = studyItemService.modifyStudyItem(studyItem, newStudyItem);

						return new ResponseEntity<>(studyItem, HttpStatus.OK);
					} else {
						return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
					}
				}
			}
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@JsonView(StudyItemDetailed.class)
	@RequestMapping(value = "/api/moodle/{courseInternalName}/{subjectInternalName}/{type}/file/{studyItemID}", method = RequestMethod.PUT)
	public ResponseEntity<StudyItem> updateStudyItemFile(@PathVariable String courseInternalName,
			@PathVariable String subjectInternalName, @PathVariable String type, @PathVariable Long studyItemID,
			@RequestParam(name = "itemFile") MultipartFile file) throws IOException {

		User user = sessionUserComponent.getLoggedUser();

		if (user != null && !user.isStudent()) {

			Subject subject = subjectService.checkForSubject(user, courseInternalName, subjectInternalName);
			if (subject != null) {
				if (subject.getTeachers().contains(user)) {
					/* If the user is a teacher of the subject can upload the file */
					/* File uploading control. If the file exists, it is overwritten */

					StudyItem studyItem = subjectService.getStudyItem(subject, studyItemID);
					boolean isValidRequest = (studyItem != null && (type.equals("studyItem") && !studyItem.isPractice())
							|| (type.equals("practice") && studyItem.isPractice()));
					if (isValidRequest) {

						studyItem = studyItemService.modifyStudyItemFile(file, studyItem);

						return new ResponseEntity<>(studyItem, HttpStatus.OK);
					}
				}
			}
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	/* DELETE */

	/* Deletes a studyItem/practices */
	@JsonView(StudyItemDetailed.class)
	@RequestMapping(value = "/api/moodle/{courseInternalName}/{subjectInternalName}/{type}/{studyItemID}", method = RequestMethod.DELETE)
	public ResponseEntity<StudyItem> deleteStudyItem(@PathVariable String courseInternalName,
			@PathVariable String subjectInternalName, @PathVariable String type, @PathVariable Long studyItemID) {

		User user = sessionUserComponent.getLoggedUser();

		if (user != null && !user.isStudent()) {

			Subject subject = subjectService.checkForSubject(user, courseInternalName, subjectInternalName);
			if (subject != null) {
				if (subject.getTeachers().contains(user)) {
					/* If the user is a teacher of the subject can upload the file */
					/* File uploading control. If the file exists, it is overwritten */

					StudyItem studyItem = subjectService.getStudyItem(subject, studyItemID);
					boolean isValidRequest = (studyItem != null && (type.equals("studyItem") && !studyItem.isPractice())
							|| (type.equals("practice") && studyItem.isPractice()));
					if (isValidRequest) {

						studyItem = studyItemService.deleteStudyItem(studyItem);

						return new ResponseEntity<>(studyItem, HttpStatus.OK);
					}
				}
			}
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@JsonView(StudyItem.BasicStudyItem.class)
	@RequestMapping(value = "/api/moodle/{courseInternalName}/{subjectInternalName}/studyItem/module/{module}", method = RequestMethod.DELETE)
	public ResponseEntity<List<StudyItem>> deleteFromModuleStudyItems(@PathVariable String courseInternalName,
			@PathVariable String subjectInternalName, @PathVariable Integer module) {

		User user = sessionUserComponent.getLoggedUser();

		if (user != null && !user.isStudent()) {

			Subject subject = subjectService.checkForSubject(user, courseInternalName, subjectInternalName);
			if (subject != null) {
				if (subject.getTeachers().contains(user)) {
					/* If the user is a teacher of the subject can upload the file */
					/* File uploading control. If the file exists, it is overwritten */

					List<StudyItem> deletedStudyItems = new ArrayList<>();

					int pos = 0;
					int size = subject.getStudyItemsList().size();

					StudyItem act;
					while (pos < size) {
						act = subject.getStudyItemsList().get(pos);

						if (!act.isPractice() && act.getModule() == module) {
							deletedStudyItems.add(act);
							studyItemService.deleteStudyItem(act);
							size--;
						} else {
							pos++;
						}
					}
					return new ResponseEntity<>(deletedStudyItems, HttpStatus.OK);
				}
			}
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	/* PRACTICES SUBMISSIONS */

	/* GET */

	/*
	 * Retrieves a practice submission. One if is the user is a student and all if
	 * is teacher
	 */
	/* The practiceID is the id from the statement */

	interface PraticesDetailed extends Practices.BasicPractice, Practices.DetailedPractice, User.BasicUser,
			StudyItem.BasicStudyItem, StudyItem.SubjectOrigin, Subject.SubjectsBasicInformation {
	}

	@JsonView(PraticesDetailed.class)
	@RequestMapping(value = "/api/moodle/{courseInternalName}/{subjectInternalName}/submissions/{practiceID}", method = RequestMethod.GET)
	public ResponseEntity<Page<Practices>> getPracticesSubmissions(Pageable pages,
			@PathVariable String courseInternalName, @PathVariable String subjectInternalName,
			@PathVariable Long practiceID, @RequestParam(value = "page", defaultValue = "0") int page) {

		User user = sessionUserComponent.getLoggedUser();

		if (user != null) {
			Subject subject = subjectService.checkForSubject(user, courseInternalName, subjectInternalName);
			if (subject != null) {
				StudyItem studyItem = studyItemRepository.findOne(practiceID);
				if (studyItem != null) {
					if (studyItem.isPractice()) {
						Page<Practices> response = practicesSubmissionService.getSubmissions(user, studyItem, page);
						return new ResponseEntity<>(response, HttpStatus.OK);
					} else {
						return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
					}
				}
			}
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	/* Requests the file */
	@RequestMapping(value = "/api/moodle/{courseInternalName}/{subjectInternalName}/submissions/{practiceID}/file/{submissionID}", method = RequestMethod.GET)
	public void getPracticeSubmissionFile(Pageable pages, @PathVariable String courseInternalName,
			@PathVariable String subjectInternalName, @PathVariable Long practiceID, @PathVariable Long submissionID,
			HttpServletResponse response) throws IOException {

		User user = sessionUserComponent.getLoggedUser();

		if (user != null) {
			Subject subject = subjectService.checkForSubject(user, courseInternalName, subjectInternalName);
			if (subject != null) {
				StudyItem studyItem = studyItemRepository.findOne(practiceID);
				if (studyItem != null) {
					if (studyItem.isPractice()) {

						for (Practices practice : studyItem.getPractices()) {

							if (practice.getPracticeID() == submissionID
									&& (!user.isStudent() || practice.getOwner().equals(user))) {

								Path pathFile = practicesSubmissionService.getPracticeFile(studyItem, practice);
								try {
									response.addHeader("Content-Disposition",
											"attachment; filename = " + studyItem.getOriginalName());
									response.setContentType("application/octet-stream");
									response.setContentLength((int) pathFile.toFile().length());
									IOUtils.copy(Files.newInputStream(pathFile), response.getOutputStream());

								} catch (IOException e) {
									e.printStackTrace();
									response.sendError(404);
								}
							}
						}

					}
				}
			}
		} else {
			response.sendError(404);
		}
	}

	/* POST */
	/* Adds a submission. Only an student can */
	@JsonView(PraticesDetailed.class)
	@RequestMapping(value = "/api/moodle/{courseInternalName}/{subjectInternalName}/submissions/{practiceID}", method = RequestMethod.POST)
	public ResponseEntity<Practices> PostPracticeSubmission(Pageable pages, @PathVariable String courseInternalName,
			@PathVariable String subjectInternalName, @PathVariable Long practiceID, @RequestBody Practices practice) {

		User user = sessionUserComponent.getLoggedUser();

		if (user != null && user.isStudent()) {
			Subject subject = subjectService.checkForSubject(user, courseInternalName, subjectInternalName);
			if (subject != null) {
				StudyItem studyItem = studyItemRepository.findOne(practiceID);
				if (studyItem != null) {
					if (studyItem.isPractice()) {
						boolean presented = false;
						for (Practices practiceAct : studyItem.getPractices()) {
							if (practiceAct.getOwner().equals(user)) {
								presented = true;
							}
						}
						if (!presented) {
							Practices response = practicesSubmissionService.createSubmission(user, studyItem, practice);
							return new ResponseEntity<>(response, HttpStatus.OK);
						} else {
							return new ResponseEntity<>(HttpStatus.IM_USED);
						}
					} else {
						return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
					}
				}
			}
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	/* Updloads a file within a practice submission */
	@JsonView(StudyItemDetailed.class)
	@RequestMapping(value = "/api/moodle/{courseInternalName}/{subjectInternalName}/submissions/{practiceID}/file/{submissionID}", method = RequestMethod.POST)
	public ResponseEntity<Practices> createPracticeSubmissionFile(@PathVariable String courseInternalName,
			@PathVariable String subjectInternalName, @PathVariable Long practiceID, @PathVariable Long submissionID,
			@RequestParam MultipartFile file) throws IOException {

		User user = sessionUserComponent.getLoggedUser();

		if (user != null && user.isStudent() && !file.isEmpty()) {
			Subject subject = subjectService.checkForSubject(user, courseInternalName, subjectInternalName);
			if (subject != null) {
				StudyItem studyItem = studyItemRepository.findOne(practiceID);
				if (studyItem != null) {
					if (studyItem.isPractice()) {
						Practices practice = practiceSubmissionRepository.findOne(submissionID);
						if (practice != null && practice.getOwner().equals(user) && practice.isPresented()) {
							practice = practicesSubmissionService.createSubmissionFile(user, studyItem, practice, file);
							return new ResponseEntity<>(practice, HttpStatus.OK);
						} else {
							return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
						}
					} else {
						return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
					}
				}
			}
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	/* UPDATE */
	@JsonView(PraticesDetailed.class)
	@RequestMapping(value = "/api/moodle/{courseInternalName}/{subjectInternalName}/submissions/{practiceID}/{submissionID}", method = RequestMethod.PUT)
	public ResponseEntity<Practices> EditPracticeSubmission(@PathVariable String courseInternalName,
			@PathVariable String subjectInternalName, @PathVariable Long submissionID, @PathVariable Long practiceID,
			@RequestBody Practices newPractice) {

		User user = sessionUserComponent.getLoggedUser();

		if (user != null) {
			Subject subject = subjectService.checkForSubject(user, courseInternalName, subjectInternalName);
			if (subject != null) {
				StudyItem studyItem = studyItemRepository.findOne(practiceID);
				Practices oldPractice = practiceSubmissionRepository.findOne(submissionID);

				if (studyItem.getSubject().equals(subject) && (studyItem.getPractices().contains(oldPractice))) {

					if (oldPractice.isPresented()) {

						if (user.isStudent()) {
							oldPractice.setPracticeName(newPractice.getPracticeName());
						} else {
							oldPractice.setCalification(newPractice.getCalification());
							oldPractice.setCorrected(true);
						}

						newPractice = practiceSubmissionRepository.save(oldPractice);
						return new ResponseEntity<>(newPractice, HttpStatus.OK);
					} else {
						return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
					}

				}
			}

		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	/* Updloads a file within a practice submission */
	@JsonView(StudyItemDetailed.class)
	@RequestMapping(value = "/api/moodle/{courseInternalName}/{subjectInternalName}/submissions/{practiceID}/file/{submissionID}", method = RequestMethod.PUT)
	public ResponseEntity<Practices> modifyPracticeSubmissionFile(@PathVariable String courseInternalName,
			@PathVariable String subjectInternalName, @PathVariable Long practiceID, @PathVariable Long submissionID,
			@RequestParam("itemFile") MultipartFile file) throws IOException {

		User user = sessionUserComponent.getLoggedUser();

		if (user != null && user.isStudent() && !file.isEmpty()) {
			Subject subject = subjectService.checkForSubject(user, courseInternalName, subjectInternalName);
			if (subject != null) {
				StudyItem studyItem = studyItemRepository.findOne(practiceID);
				if (studyItem != null) {
					if (studyItem.isPractice()) {
						Practices practice = practiceSubmissionRepository.findOne(submissionID);
						if (practice != null && practice.getOwner().equals(user)) {
							practice = practicesSubmissionService.createSubmissionFile(user, studyItem, practice, file);
							return new ResponseEntity<>(practice, HttpStatus.OK);
						} else {
							return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
						}
					} else {
						return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
					}
				}
			}
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	/* DELETE */

	@JsonView(Practices.BasicPractice.class)
	@RequestMapping(value = "/api/moodle/{courseInternalName}/{subjectInternalName}/submissions/{practiceID}/{submissionID}", method = RequestMethod.DELETE)
	public ResponseEntity<Practices> deletePracticeSubmission(@PathVariable String courseInternalName,
			@PathVariable String subjectInternalName, @PathVariable Long practiceID, @PathVariable Long submissionID) {

		User user = sessionUserComponent.getLoggedUser();

		if (user != null && user.isStudent()) {
			Subject subject = subjectService.checkForSubject(user, courseInternalName, subjectInternalName);
			if (subject != null) {
				StudyItem studyItem = studyItemRepository.findOne(practiceID);
				if (studyItem != null) {
					if (studyItem.isPractice()) {
						Practices practice = practiceSubmissionRepository.findOne(submissionID);
						if (practice != null && practice.getOwner().equals(user)
								&& studyItem.getPractices().contains(practice)) {
							practice = practicesSubmissionService.deletePracticeSubmission(studyItem, practice);
							return new ResponseEntity<>(practice, HttpStatus.OK);
						} else {
							return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
						}
					} else {
						return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
					}
				}
			}
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	/* CONSULT MARKS */
	/*
	 * TO DO: refractor all this autentication and checkings into a service/function
	 */
	@RequestMapping(value = "/api/moodle/practicesMarks/{courseInternalName}/{subjectInternalName}/practices/", method = RequestMethod.GET)
	public ResponseEntity<List<List<Object>>> getMarks(@PathVariable String courseInternalName,
			@PathVariable String subjectInternalName) {

		User user = sessionUserComponent.getLoggedUser();

		if (user != null) {
			/* Return the marks of every practice */
			Subject subject = subjectService.checkForSubject(user, courseInternalName, subjectInternalName);
			if (subject != null) {
				List<StudyItem> studyItemsPract = new ArrayList<>();
				for (StudyItem studyItemAct : subject.getStudyItemsList()) {
					if (studyItemAct.isPractice()) {
						studyItemsPract.add(studyItemAct);
					}
				}

				List<List<Object>> listPractices = new ArrayList<>();
				int i = 0;
				for (StudyItem studyItemAct : studyItemsPract) {
					listPractices.add(new ArrayList<>());
					listPractices.get(i).add(studyItemAct.getName());
					double mean = 0;
					int n = 0;
					for (Practices practiceAct : studyItemAct.getPractices()) {
						if (user.isStudent()) {
							if (practiceAct.getOwner().getUserID() == user.getUserID()) {
								listPractices.get(i).add(practiceAct.getCalification());
							}
						} else {
							if (practiceAct.isCorrected()) {
								mean += practiceAct.getCalification();
								n++;
							}
						}

					}
					if (!user.isStudent()) {
						mean = mean / n;
						listPractices.get(i).add(mean);
					}
					i++;
				}
				return new ResponseEntity<>(listPractices, HttpStatus.OK);
			}
		}
		/* If program reaches here is that there has been an error */
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}
