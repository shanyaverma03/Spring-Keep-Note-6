package com.stackroute.keepnote.service;

import java.util.*;

import com.stackroute.keepnote.exception.NoteNotFoundExeption;
import com.stackroute.keepnote.model.Note;
import com.stackroute.keepnote.model.NoteUser;
import com.stackroute.keepnote.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
* Service classes are used here to implement additional business logic/validation 
* This class has to be annotated with @Service annotation.
* @Service - It is a specialization of the component annotation. It doesn't currently 
* provide any additional behavior over the @Component annotation, but it's a good idea 
* to use @Service over @Component in service-layer classes because it specifies intent 
* better. Additionally, tool support and additional behavior might rely on it in the 
* future.
* */

@Service
public class NoteServiceImpl implements NoteService{

	/*
	 * Autowiring should be implemented for the NoteRepository and MongoOperation.
	 * (Use Constructor-based autowiring) Please note that we should not create any
	 * object using the new keyword.
	 */
	private NoteRepository noteRepository;
	private NoteUser noteUser;
	private List<Note> list;

	@Autowired
	public NoteServiceImpl(NoteRepository noteRepository) {
		this.noteRepository = noteRepository;
	}

	/*
	 * This method should be used to save a new note.
	 */
	public boolean createNote(Note note) {

		int count=1;
		boolean deleted= false;
		noteUser= new NoteUser();
		list= new ArrayList<>();
		note.setNoteCreationDate(new Date());
		if(noteRepository.existsById(note.getNoteCreatedBy())) {
			list = noteRepository.findById(note.getNoteCreatedBy()).get().getNotes();
			Iterator it = list.iterator();
			Note newNote = new Note();
			while ((it.hasNext())) {
				newNote = (Note) it.next();
			}

			note.setNoteId(newNote.getNoteId() + count);
			list.add(note);
			noteUser.setUserId(note.getNoteCreatedBy());
			noteUser.setNotes(list);
			if (noteRepository.save(noteUser) != null) {
				deleted = true;
			}

		}else {
			note.setNoteId(count);
			list.add(note);
			noteUser.setUserId(note.getNoteCreatedBy());
			noteUser.setNotes(list);
			if(noteRepository.insert(noteUser)!=null){
				deleted=true;
			}
		}
		return deleted;

	}





	/* This method should be used to delete an existing note. */


	public boolean deleteNote(String userId, int noteId) {

		Optional<NoteUser> optional =  noteRepository.findById(userId);
		NoteUser noteUser= optional.get();
		boolean ans=true;
		if(!optional.isPresent()){
			ans=false;

		}

		List<Note> list = noteUser.getNotes();
		for(Note note1: list){
			if(note1.getNoteId()==noteId){
				ans=true;
			}
			else{
				ans= false;
			}
		}
		if(ans==true) {
			noteRepository.deleteById(userId);
		}


		return ans;

	}

	/* This method should be used to delete all notes with specific userId. */


	public boolean deleteAllNotes(String userId) {

		boolean ans=true;
		Optional<NoteUser> optional =  noteRepository.findById(userId);
		if(!optional.isPresent()){
			ans=false;

		}
		noteRepository.deleteById(userId);
		return  ans;
	}

	/*
	 * This method should be used to update a existing note.
	 */
	public Note updateNote(Note note, int id, String userId) throws NoteNotFoundExeption {

		try {
			Optional<NoteUser> optional = noteRepository.findById(userId);
			NoteUser noteUser = optional.get();
			List<Note> noteList = noteUser.getNotes();

			Note noteUpdated = noteList.get(note.getNoteId());

			if (noteUpdated != null) {
				noteUpdated.setNoteId(note.getNoteId());
				noteUpdated.setCategory(note.getCategory());
				noteUpdated.setNoteContent(note.getNoteContent());
				noteUpdated.setNoteCreatedBy(note.getNoteCreatedBy());
				noteUpdated.setNoteStatus(note.getNoteStatus());
				noteUpdated.setNoteTitle(note.getNoteTitle());
				noteUpdated.setReminders(note.getReminders());
				return noteUpdated;
			} else {

				throw new NoteNotFoundExeption("not found");
			}
		} catch(NoSuchElementException e){
			throw new NoteNotFoundExeption("not");
		}

	}

	/*
	 * This method should be used to get a note by noteId created by specific user
	 */
	public Note getNoteByNoteId(String userId, int noteId) throws NoteNotFoundExeption {

		try {
			Optional<NoteUser> optional = noteRepository.findById(userId);
			NoteUser noteUser = optional.get();

			if (noteUser == null) {
				System.out.println("note user is null");
				return null;
			}
			List<Note> list = noteUser.getNotes();
			if (list == null) {
				System.out.println("list is null");
				return null;
			}

			for(Note note1: list){
				if(note1.getNoteId()==noteId){
					return  note1;
				}
			}

		}
		catch(NoSuchElementException e){
			throw  new NoteNotFoundExeption("not found");
		}

		return  null;

	}

	/*
	 * This method should be used to get all notes with specific userId.
	 */
	public List<Note> getAllNoteByUserId(String userId) {

		Optional<NoteUser> optional = noteRepository.findById(userId);
		NoteUser noteUser= optional.get();

		if(noteUser==null){
			return null;
		}
		List<Note> list= noteUser.getNotes();
		return list;
	}

}
