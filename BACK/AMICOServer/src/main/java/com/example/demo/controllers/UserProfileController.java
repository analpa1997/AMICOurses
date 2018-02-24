
package com.example.demo.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.course_package.Course;
import com.example.demo.course_package.CourseRepository;
import com.example.demo.skill_package.Skill;
import com.example.demo.subject_package.Subject;
import com.example.demo.user_package.SessionUserComponent;
import com.example.demo.user_package.User;
import com.example.demo.user_package.UserRepository;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;

@Controller
public class UserProfileController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CourseRepository courseRepository;
	
	@Autowired SessionUserComponent sessionUserComponent; 

	@RequestMapping("/profile/{userInternalName}")
	public String viewProfile(Model model, @PathVariable String userInternalName) {

		User user = userRepository.findByInternalName(userInternalName);

		model.addAttribute("userFirstName", user.getUserFirstName());
		model.addAttribute("userLastName", user.getUserLastName());
		model.addAttribute("username", user.getUsername());
		model.addAttribute("userMail", user.getUserMail());
		model.addAttribute("userAddress", user.getUserAddress());
		model.addAttribute("phoneNumber", user.getPhoneNumber());
		model.addAttribute("isStudent", user.isStudent());
		model.addAttribute("urlProfileImage", user.getUrlProfileImage());
		model.addAttribute("inscribedCourses", user.getInscribedCourses());
		model.addAttribute("completedCourses", user.getCompletedCourses());
		model.addAttribute("interests", user.getInterests());
		model.addAttribute("internalName", user.getInternalName());
model.addAttribute("userID", user.getUserID());
		
		/* Only the user can change its profile, enter in the courses and get certificates */
		//model.addAttribute("isTheUser" ,(user.getUserID() == sessionUserComponent.getLoggedUser().getUserID()));
		model.addAttribute("isTheProfileUser", true);

		return "HTML/Profile/userProfile";
	}

	// Requets from form
	@RequestMapping(value = "/profile/{userInternalName}/updated", method = RequestMethod.POST)
	public ModelAndView updated(Model model, User userUpdated, @PathVariable String userInternalName,
			@RequestParam("profileImage") MultipartFile file) {

		User user = userRepository.findByInternalName(userInternalName);

		/* Image uploading controll. If a profile image exists, it is overwritten */
		/* If there is not file the imageName wont change */
		if (!file.isEmpty()) {
			try {
				Path FILES_FOLDER = Paths.get(System.getProperty("user.dir"),
						"files/image/users/" + user.getUserID() + "/");
				if (!Files.exists(FILES_FOLDER)) {
					Files.createDirectories(FILES_FOLDER);
				}

				String fileName = "profile-" + user.getUserID() + ".jpg";

				File uploadedFile = new File(FILES_FOLDER.toFile(), fileName);
				file.transferTo(uploadedFile);
				user.setUrlProfileImage(fileName);

			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}

		/* End of the image upload section */

		user.setUserFirstName(userUpdated.getUserFirstName());
		user.setUserLastName(userUpdated.getUserLastName());
		user.setUsername(userUpdated.getUsername());
		user.setUserMail(userUpdated.getUserMail());
		user.setUserAddress(userUpdated.getUserAddress());
		user.setCity(userUpdated.getCity());
		user.setCountry(userUpdated.getCountry());
		user.setPhoneNumber(userUpdated.getPhoneNumber());
		user.setInterests(userUpdated.getInterests());

		userRepository.save(user);

		return new ModelAndView("redirect:/profile/" + user.getInternalName());
	}

	// Requets to form
	@RequestMapping("/profile/{userInternalName}/update")
	public String update(Model model, @PathVariable String userInternalName) {

		User user = userRepository.findByInternalName(userInternalName);

		model.addAttribute("interests", user.getInterests());
		model.addAttribute("userFirstName", user.getUserFirstName());
		model.addAttribute("userLastName", user.getUserLastName());
		model.addAttribute("username", user.getUsername());
		model.addAttribute("userMail", user.getUserMail());
		model.addAttribute("password", user.getPassword());
		model.addAttribute("userAddress", user.getUserAddress());
		model.addAttribute("role", user.getRole());
		model.addAttribute("isStudent", user.isStudent());
		model.addAttribute("urlProfileImage", user.getUrlProfileImage());
		model.addAttribute("city", user.getCity());
		model.addAttribute("country", user.getCountry());
		model.addAttribute("phoneNumber", user.getPhoneNumber());
		model.addAttribute("userInternalName", user.getInternalName());

		return "HTML/Profile/profile-update";
	}

	@RequestMapping("/profileimg/{userInternalName}")
	public void getProfileImage(@PathVariable String userInternalName, HttpServletResponse res)
			throws FileNotFoundException, IOException {
		User user = userRepository.findByInternalName(userInternalName);
		Path FILES_FOLDER = Paths.get(System.getProperty("user.dir"), "files/image/users/" + user.getUserID() + "/");

		Path image = FILES_FOLDER.resolve(user.getUrlProfileImage());

		if (!Files.exists(image)) {
			image = Paths.get(System.getProperty("user.dir"), "files/image/users/default/default.jpg");
		}

		res.setContentType("image/jpeg");
		res.setContentLength((int) image.toFile().length());
		FileCopyUtils.copy(Files.newInputStream(image), res.getOutputStream());

	}

	@RequestMapping(value = "/profile/{userInternalName}/certificate/{internalName}-{userID}", method = RequestMethod.GET)
	public void downloadPdf(HttpServletResponse res, @PathVariable String userInternalName, @PathVariable String internalName,
			@PathVariable long userID) throws IOException, DocumentException {
		User user = userRepository.findByInternalName(userInternalName);
		Course course = courseRepository.findByInternalName(internalName);

		Path FILES_FOLDER = Paths.get(System.getProperty("user.dir"), "files/documents/certificates/" + userID + "/");
		if (!Files.exists(FILES_FOLDER)) {
			Files.createDirectories(FILES_FOLDER);
		}

		String fileName = course.getInternalName() + "-" + user.getUserID() + ".pdf";

		Path pdf = FILES_FOLDER.resolve(fileName);
		File file;

		if (!Files.exists(pdf)) {
			file = new File(FILES_FOLDER.toFile(), fileName);
			FileOutputStream fileout = new FileOutputStream(file);

			Document document = new Document();
			PdfWriter.getInstance(document, fileout);
			document.addTitle("Certificate: " + course.getName());

			document.open();

			// Here is the content of the PDF

			Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
			Font fontHeader = FontFactory.getFont(FontFactory.COURIER, 24, BaseColor.BLACK);

			Chunk glue = new Chunk(new VerticalPositionMark());
			Paragraph p = new Paragraph("AMICOurses Certification");
			p.add(new Chunk(glue));
			p.add(course.getEndDateString());

			document.add(p);
			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);

			String pTwo = "We are glad to certificate than our student " + user.getUserFirstName() + " "
					+ user.getUserLastName() + " have passed our course:";
			p = new Paragraph(pTwo);

			document.add(p);
			document.add(Chunk.NEWLINE);

			p = new Paragraph(course.getName(), fontHeader);
			p.setAlignment(Element.ALIGN_CENTER);

			document.add(p);
			document.add(Chunk.NEWLINE);

			p = new Paragraph("Language: " + course.getCourseLanguage());
			p.setAlignment(Element.ALIGN_CENTER);

			document.add(p);
			document.add(Chunk.NEWLINE);

			p = new Paragraph(course.getCourseDescription());

			document.add(p);
			document.add(Chunk.NEWLINE);

			// Create table for subjects in the course

			PdfPTable table = new PdfPTable(2);
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.setWidthPercentage(80);
			PdfPCell cell = new PdfPCell(new Phrase("Subjects of the course"));
			table.addCell(cell);

			// Create a row for subject

			int i = 1;
			for (Subject s : course.getSubjects()) {
				table.addCell(new PdfPCell(new Phrase(i)));
				table.addCell(new PdfPCell(new Phrase(s.getName())));
				i++;
			}

			document.add(table);

			// Create table for skills in the course

			table = new PdfPTable(3);
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.setWidthPercentage(80);
			cell = new PdfPCell(new Phrase("Skills of the course"));
			table.addCell(cell);

			// Create a row for skill

			i = 1;
			for (Skill s : course.getSkills()) {
				table.addCell(new PdfPCell(new Phrase(i)));
				table.addCell(new PdfPCell(new Phrase(s.getSkillName())));
				table.addCell(new PdfPCell(new Phrase(s.getSkillDescription())));
				i++;
			}

			document.add(table);
			document.close();
		} else {
			file = pdf.toFile();
		}

		Path filePath = pdf;
		res.addHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));
		res.setContentType("application/octet-stream");
		res.setContentLength((int) filePath.toFile().length());
		FileCopyUtils.copy(Files.newInputStream(filePath), res.getOutputStream());

	}
}