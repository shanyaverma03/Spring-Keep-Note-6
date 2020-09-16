package com.stackroute.keepnote.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.stackroute.keepnote.exception.ReminderNotCreatedException;
import com.stackroute.keepnote.exception.ReminderNotFoundException;
import com.stackroute.keepnote.model.Reminder;
import com.stackroute.keepnote.repository.ReminderRepository;
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
public class ReminderServiceImpl implements ReminderService {

	/*
	 * Autowiring should be implemented for the ReminderRepository. (Use
	 * Constructor-based autowiring) Please note that we should not create any
	 * object using the new keyword.
	 */

	private ReminderRepository reminderRepository;

	@Autowired
	public ReminderServiceImpl(ReminderRepository reminderRepository) {
		this.reminderRepository = reminderRepository;
	}

	/*
	 * This method should be used to save a new reminder.Call the corresponding
	 * method of Respository interface.
	 */
	public Reminder createReminder(Reminder reminder) throws ReminderNotCreatedException {

		Reminder reminderCreated=null;
		Optional<Reminder> optional =  reminderRepository.findById(reminder.getReminderId());
		if(optional.isPresent())
		{
			reminderCreated= null;
			throw new ReminderNotCreatedException("Reminder Already Exists");

		}

		reminderCreated=  reminderRepository.insert(reminder);
		if(reminderCreated==null){
			throw new ReminderNotCreatedException("Null");
		}
		return reminderCreated;

	}

	/*
	 * This method should be used to delete an existing reminder.Call the
	 * corresponding method of Respository interface.
	 */
	public boolean deleteReminder(String reminderId) throws ReminderNotFoundException {

		Optional<Reminder> optional =  reminderRepository.findById(reminderId);
		boolean ans=true;
		if(!optional.isPresent()){
			ans=false;
			throw new ReminderNotFoundException("Reminder does not exist");
		}

		reminderRepository.deleteById(reminderId);


		return ans;
	}

	/*
	 * This method should be used to update a existing reminder.Call the
	 * corresponding method of Respository interface.
	 */
	public Reminder updateReminder(Reminder reminder, String reminderId) throws ReminderNotFoundException {

		Optional<Reminder> optional= reminderRepository.findById(reminderId);
		Reminder updated= optional.get();




		if(updated!=null) {
			updated.setReminderId(reminder.getReminderId());
			updated.setReminderCreatedBy(reminder.getReminderCreatedBy());
			updated.setReminderDescription(reminder.getReminderDescription());
			updated.setReminderName(reminder.getReminderName());
			updated.setReminderType(reminder.getReminderType());
			return updated;
		}
		else{

			return  null;
		}
	}

	/*
	 * This method should be used to get a reminder by reminderId.Call the
	 * corresponding method of Respository interface.
	 */
	public Reminder getReminderById(String reminderId) throws ReminderNotFoundException {

		try {
			Optional<Reminder> optional = reminderRepository.findById(reminderId);
			Reminder reminder = optional.get();
			return reminder;
		}
		catch (NoSuchElementException e){
			throw new ReminderNotFoundException(("not found"));
		}

	}

	/*
	 * This method should be used to get all reminders. Call the corresponding
	 * method of Respository interface.
	 */

	public List<Reminder> getAllReminders() {
		List<Reminder> list= reminderRepository.findAll();


		return list;
	}

}
