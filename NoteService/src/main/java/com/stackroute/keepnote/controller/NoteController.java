package com.stackroute.keepnote.controller;

import com.stackroute.keepnote.exception.NoteNotFoundExeption;
import com.stackroute.keepnote.model.Note;
import com.stackroute.keepnote.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * As in this assignment, we are working with creating RESTful web service, hence annotate
 * the class with @RestController annotation.A class annotated with @Controller annotation
 * has handler methods which returns a view. However, if we use @ResponseBody annotation along
 * with @Controller annotation, it will return the data directly in a serialized 
 * format. Starting from Spring 4 and above, we can use @RestController annotation which 
 * is equivalent to using @Controller and @ResposeBody annotation
 * 
 * @CrossOrigin, @EnableFeignClients and @RibbonClient needs to be added 
 */


@RestController
@RequestMapping("/api/v1/note")
@CrossOrigin


public class NoteController {

	/*
	 * Autowiring should be implemented for the NoteService. (Use Constructor-based
	 * autowiring) Please note that we should not create any object using the new
	 * keyword
	 */

	private  NoteService noteService;
	private ResponseEntity responseEntity;

	@Autowired
	public NoteController(NoteService noteService) {

		this.noteService= noteService;
	}

	/*
	 * Define a handler method which will create a specific note by reading the
	 * Serialized object from request body and save the note details in the
	 * database.This handler method should return any one of the status messages
	 * basis on different situations:
	 * 1. 201(CREATED) - If the note created successfully.
	 * 2. 409(CONFLICT) - If the noteId conflicts with any existing user.
	 *
	 * This handler method should map to the URL "/api/v1/note" using HTTP POST method
	 */

	@PostMapping
	public ResponseEntity<?> saveNote(@RequestBody Note note){

		if(noteService.createNote(note)) {
			responseEntity = new ResponseEntity("created", HttpStatus.CREATED);
		}


		else {
			responseEntity = new ResponseEntity("Some Internal Error Try after sometime", HttpStatus.CONFLICT);
		}


		return responseEntity;
	}
	/*
	 * Define a handler method which will delete a note from a database.
	 * This handler method should return any one of the status messages basis
	 * on different situations:
	 * 1. 200(OK) - If the note deleted successfully from database.
	 * 2. 404(NOT FOUND) - If the note with specified noteId is not found.
	 *
	 * This handler method should map to the URL "/api/v1/note/{id}" using HTTP Delete
	 * method" where "id" should be replaced by a valid noteId without {}
	 */

	@DeleteMapping("/{userId}/{id}")
	public ResponseEntity deleteNote(@PathVariable("userId") String userId ,@PathVariable("id") int id){
		if(noteService.deleteNote(userId, id)){
			responseEntity= new ResponseEntity("deleted", HttpStatus.OK);

		}
		else{
			responseEntity= new ResponseEntity("not deleted", HttpStatus.NOT_FOUND);

		}
		return  responseEntity;

	}


	@DeleteMapping("/{userId}")
	public ResponseEntity deleteAllNotes(@PathVariable("userId") String userId){

		try{
			noteService.deleteAllNotes(userId);
			responseEntity= new ResponseEntity("deleted", HttpStatus.OK);
		}
		catch (NoteNotFoundExeption e){
			responseEntity= new ResponseEntity("not deleted", HttpStatus.NOT_FOUND);
		}
		return  responseEntity;

	}
	/*
	 * Define a handler method which will update a specific note by reading the
	 * Serialized object from request body and save the updated note details in a
	 * database.
	 * This handler method should return any one of the status messages
	 * basis on different situations:
	 * 1. 200(OK) - If the note updated successfully.
	 * 2. 404(NOT FOUND) - If the note with specified noteId is not found.
	 *
	 * This handler method should map to the URL "/api/v1/note/{id}" using HTTP PUT method.
	 */
	@PutMapping("/{userId}/{id}")
	public ResponseEntity updateReminder(@RequestBody Note note, @PathVariable("id") int id, @PathVariable("userId") String userId)  {


		Note created = null;
		try {
			created = noteService.updateNote(note, id,userId);
			if(created!=null) {

				responseEntity = new ResponseEntity("Updated", HttpStatus.OK);
			}
			else {

				responseEntity = new ResponseEntity("not updated", HttpStatus.NOT_FOUND);
			}
		}
		catch (NoteNotFoundExeption e){
			responseEntity = new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}


		return  responseEntity;
	}
	/*
	 * Define a handler method which will get us the all notes by a userId.
	 * This handler method should return any one of the status messages basis on
	 * different situations:
	 * 1. 200(OK) - If the note found successfully.
	 *
	 * This handler method should map to the URL "/api/v1/note" using HTTP GET method
	 */
	@GetMapping("/{Userid}")
	public ResponseEntity getAllNotes(@PathVariable("Userid") String Userid){



		List<Note> notes= noteService.getAllNoteByUserId(Userid);
		if(notes!=null) {
			responseEntity = new ResponseEntity(notes, HttpStatus.OK);
		}
		else{
			responseEntity = new ResponseEntity("not found", HttpStatus.NOT_FOUND);
		}

		return  responseEntity;

	}
	/*
	 * Define a handler method which will show details of a specific note created by specific
	 * user. This handler method should return any one of the status messages basis on
	 * different situations:
	 * 1. 200(OK) - If the note found successfully.
	 * 2. 404(NOT FOUND) - If the note with specified noteId is not found.
	 * This handler method should map to the URL "/api/v1/note/{userId}/{noteId}" using HTTP GET method
	 * where "id" should be replaced by a valid reminderId without {}
	 *
	 */


	@GetMapping("/{userId}/{noteId}")
	public ResponseEntity getSpecificNote(@PathVariable("userId") String userId, @PathVariable("noteId") int id) {

		try {
			Note note= noteService.getNoteByNoteId(userId, id);
			if(note!=null){
				responseEntity = new ResponseEntity(note, HttpStatus.OK);
			}


		} catch (NoteNotFoundExeption e) {
			responseEntity = new ResponseEntity("not found", HttpStatus.NOT_FOUND);
		}
		return responseEntity;
	}
}
