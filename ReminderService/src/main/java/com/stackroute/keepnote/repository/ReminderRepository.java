package com.stackroute.keepnote.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.stackroute.keepnote.model.Reminder;
import org.springframework.stereotype.Repository;

/*
* This class is implementing the MongoRepository interface for User.
* Annotate this class with @Repository annotation
* */
@Repository
public interface ReminderRepository extends MongoRepository<Reminder, String> {

}
