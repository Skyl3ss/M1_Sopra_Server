package ch.uzh.ifi.hase.soprafs24.rest.dto;

import ch.uzh.ifi.hase.soprafs24.constant.UserStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserGetDTO {

  private Long id;
  private String password;
  private String username;
  private UserStatus status;
  private LocalDateTime creationDate;
  private LocalDate birthday;

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

  public UserStatus getStatus() {
    return status;
  }

  public void setStatus(UserStatus status) {
    this.status = status;
  }

  public LocalDateTime getCreationDate() {
        return creationDate;
    }

  public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

  public LocalDate getBirthday() {
        return birthday;
    }

  public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
}
