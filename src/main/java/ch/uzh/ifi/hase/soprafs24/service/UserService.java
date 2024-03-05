package ch.uzh.ifi.hase.soprafs24.service;

import ch.uzh.ifi.hase.soprafs24.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs24.entity.User;
import ch.uzh.ifi.hase.soprafs24.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * User Service
 * This class is the "worker" and responsible for all functionality related to
 * the user
 * (e.g., it creates, modifies, deletes, finds). The result will be passed back
 * to the caller.
 */
@Service
@Transactional
public class UserService {

  private final Logger log = LoggerFactory.getLogger(UserService.class);

  private final UserRepository userRepository;

  @Autowired
  public UserService(@Qualifier("userRepository") UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public List<User> getUsers() {
    return this.userRepository.findAll();
  }

  public User createUser(User newUser) {
    newUser.setToken(UUID.randomUUID().toString());
    newUser.setStatus(UserStatus.ONLINE);
    newUser.setCreationDate();
    checkIfUserExists(newUser);
    // saves the given entity but data is only persisted in the database once
    // flush() is called
    newUser = userRepository.save(newUser);
    userRepository.flush();

    log.debug("Created Information for User: {}", newUser);
    return newUser;
  }

  /**
   * This is a helper method that will check the uniqueness criteria of the
   * username and the password
   * defined in the User entity. The method will do nothing if the input is unique
   * and throw an error otherwise.
   *
   * @param userToBeCreated
   * @throws org.springframework.web.server.ResponseStatusException
   * @see User
   */
  private void checkIfUserExists(User userToBeCreated) {
    User userByUsername = userRepository.findByUsername(userToBeCreated.getUsername());

    if (userByUsername != null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This username is already taken. Please choose another one");
    }
  }
//this has some security concerns with the creation and checkability of the password take a later look at it
  public User loginUser(User checkUser) throws ResponseStatusException{
    User userByUsername = userRepository.findByUsername(checkUser.getUsername());

    String baseErrorMessage = "Wrong Username or Password";
      if (userByUsername != null && userByUsername.getPassword().equals(checkUser.getPassword())) {
          //login user and set him to be online
          userByUsername.setStatus(UserStatus.ONLINE);
          changeStatus(userByUsername);
          return userByUsername; // Password matches, return the user
      } else {
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong username or password");
      }
  }

  public User getUser(Long id) throws ResponseStatusException{
    Optional<User> optionalUser = userRepository.findById(id);
    return optionalUser.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with ID: " + id));
    }



  public void changeStatus(User user){
    User userByToken = userRepository.findByToken(user.getToken());

      // Update the status attribute
      if (userByToken != null) {
          userByToken.setStatus(user.getStatus());
          // Save the updated user back to the database
          userRepository.save(userByToken);
      } else {
          // Handle the case when user is not found
          throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User could not be found");
      }
    }

    public User changeUser(User user){
      User userByToken = userRepository.findByToken(user.getToken());
        // Update the status attribute
        if (userByToken != null) {
            userByToken.setBirthday(user.getBirthday());
            userByToken.setUsername(user.getUsername());

            // Save the updated user back to the database
            userRepository.save(userByToken);
            return userByToken;
        } else {
            // Handle the case when user is not found
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User could not be found: Userbytoken is null");
        }
    }

    public boolean tokenCheck(User user,Long Id){
      User userByToken = userRepository.findByToken(user.getToken());
      if (userRepository.findById(Id).isPresent()) {
          User userById = userRepository.findById(Id).get();
          return userByToken != null && userByToken.getPassword().equals(userById.getPassword());
      }
          return false;
    }

}


