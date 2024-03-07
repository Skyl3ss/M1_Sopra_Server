package ch.uzh.ifi.hase.soprafs24.entity;

import ch.uzh.ifi.hase.soprafs24.constant.UserStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.lang.reflect.Field;
import java.util.Objects;

/**
 * Internal User Representation
 * This class composes the internal representation of the user and defines how
 * the user is stored in the database.
 * Every variable will be mapped into a database field with the @Column
 * annotation
 * - nullable = false -> this cannot be left empty
 * - unique = true -> this value must be unqiue across the database -> composes
 * the primary key
 */
@Entity
@Table(name = "USER")
public class User implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false, unique = true)
  private String username;

  @Column(nullable = false, unique = true)
  private String token;

  @Column(nullable = false)
  private UserStatus status;

  @Column(nullable = false)
  private LocalDate creationDate;

  @Column(nullable = true)
  private LocalDate birthday;

  //allows to compare attributes with other classes
  public boolean isAttributeSameAs(User other, String attributeName) throws IllegalAccessException {
    // Get the field by name using reflection
    Field field;
    try {
        field = this.getClass().getDeclaredField(attributeName);
    } catch (NoSuchFieldException e) {
        // Handle if attribute does not exist
        return false;
    }

    // Make the field accessible (if it's private)
    field.setAccessible(true);

    // Get the value of the field for this user
    Object thisValue = field.get(this);

    // Get the value of the field for the other user
    Object otherValue = field.get(other);

    // Compare the values
    return Objects.equals(thisValue, otherValue);
    }



  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public UserStatus getStatus() {
    return status;
  }

  public void setStatus(UserStatus status) {
    this.status = status;
  }

  public LocalDate getCreationDate() {
        return creationDate;
    }

  public void setCreationDate() {
        this.creationDate = LocalDate.now();
    }

  public LocalDate getBirthday() {
        return birthday;
    }

  public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
}


