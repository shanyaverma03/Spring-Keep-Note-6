package com.stackroute.keepnote.service;

import com.stackroute.keepnote.exceptions.UserAlreadyExistsException;
import com.stackroute.keepnote.exceptions.UserNotFoundException;
import com.stackroute.keepnote.model.User;
import com.stackroute.keepnote.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
public class UserServiceImpl implements UserService {
	/*
	 * Autowiring should be implemented for the UserRepository. (Use
	 * Constructor-based autowiring) Please note that we should not create any
	 * object using the new keyword.
	 */
	UserRepository userRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	/*
	 * This method should be used to save a new user.Call the corresponding method
	 * of Respository interface.
	 */

	public User registerUser(User user) throws UserAlreadyExistsException {

		User userSaved=null;
		Optional<User> optional =  userRepository.findById(user.getUserId());
		if(optional.isPresent())
		{
			userSaved= null;
			throw new UserAlreadyExistsException("User Already Exists");

		}

		userSaved=  userRepository.insert(user);
		if(userSaved==null){
			throw new UserAlreadyExistsException("Null");
		}
		return userSaved;

	}

	/*
	 * This method should be used to update a existing user.Call the corresponding
	 * method of Respository interface.
	 */

	public User updateUser(String userId,User user) throws UserNotFoundException {

		Optional<User> optional= userRepository.findById(userId);
		User updatedUser= optional.get();
		if(updatedUser!=null){
			updatedUser.setUserId(user.getUserId());
			updatedUser.setUserName(user.getUserName());
			updatedUser.setUserPassword(user.getUserPassword());
			updatedUser.setUserMobile(user.getUserMobile());
			userRepository.save(updatedUser);
			return  updatedUser;

		}
		else{
			throw new UserNotFoundException("Product does not exist");
		}




	}

	/*
	 * This method should be used to delete an existing user. Call the corresponding
	 * method of Respository interface.
	 */

	public boolean deleteUser(String userId) throws UserNotFoundException {

		Optional<User> optional= userRepository.findById(userId);
		boolean ans=true;
		if(!optional.isPresent()){
			ans=false;
			throw new UserNotFoundException("User does not exist");
		}

		userRepository.deleteById(userId);


		return ans;
	}

	/*
	 * This method should be used to get a user by userId.Call the corresponding
	 * method of Respository interface.
	 */

	public User getUserById(String userId) throws UserNotFoundException {

		Optional<User> optional= userRepository.findById(userId);
		if(!optional.isPresent()){
			throw new UserNotFoundException("user does not exist");
		}


		return optional.get();

	}

}
