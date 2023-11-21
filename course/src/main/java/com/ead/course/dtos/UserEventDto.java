package com.ead.course.dtos;

import com.ead.course.enums.ActionType;
import com.ead.course.models.User;
import com.ead.course.utils.ObjectMapperUtils;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
public class UserEventDto {

  private UUID userId;
  private String username;
  private String email;
  private String fullName;
  private String userStatus;
  private String userType;
  private String phoneNumber;
  private String cpf;
  private String imageUrl;
  private ActionType actionType;

  public User convertToUser() {
    var user = new User();
    BeanUtils.copyProperties(this, user);
    return user;
  }

  @Override
  public String toString() {
    return ObjectMapperUtils.writeObjectInJson(this);
  }
}
