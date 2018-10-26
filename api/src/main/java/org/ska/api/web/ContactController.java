package org.ska.api.web;
/**
 * 
 * @author nikola
 *
 */


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.ska.api.util.UtilsWeb;
import org.ska.api.web.beans.ErrorMessage;
import org.ska.business.ContactService;
import org.ska.dao.entity.Contact;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/contacts")
@Api(value="/api/contacts", description = "Contacts Management")
public class ContactController {

	private static final Logger logger = LoggerFactory.getLogger(ContactController.class);
	
	@Autowired
	private ContactService contactService;


	
	
	
	@ApiOperation(value = "Retrieve all contacts", response = Contact.class,responseContainer = "list")
	@ApiResponses(value = {
			@ApiResponse(code = 404, message = "Contact not found",response=Void.class)
	})
	
	@RequestMapping(value ="/all",produces = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.GET)
	public @ResponseBody
	ResponseEntity<?> getAllContacts(){
		ResponseEntity<?> responseEntity=null;
		List<Contact> contactList = null;
		contactList = contactService.findAll();

		if(UtilsWeb.isEmpty(contactList))
			responseEntity=new ResponseEntity(HttpStatus.NOT_FOUND);
		else {
			Contact[] contactArray = contactList.toArray(new Contact[contactList.size()]);
			responseEntity = new ResponseEntity<Contact[]>(contactArray, HttpStatus.OK);
		}
		return responseEntity;
	}


	@ApiOperation(value = "Save contact", response = Contact.class)
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "Contact saving error",response= ErrorMessage.class)
	})
	@RequestMapping(value ="/save",produces = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.POST)
	public @ResponseBody
	ResponseEntity<?> saveContact(@RequestBody Contact contact){
		ResponseEntity<?> responseEntity=null;

		Contact insertedContact=contactService.createContact(contact);

		responseEntity = new ResponseEntity<Contact>(insertedContact, HttpStatus.OK);

		return responseEntity;
	}




}
