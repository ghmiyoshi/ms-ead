package com.ead.authuser.dtos;

import com.ead.authuser.models.User;
import com.ead.authuser.models.enums.ActionType;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@NoArgsConstructor
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
  private String actionType;

  public UserEventDto(final User userSaved) {
    BeanUtils.copyProperties(userSaved, this);
    this.userStatus = userSaved.getUserStatus().toString();
    this.userType = userSaved.getUserType().toString();
  }

  public static UserEventDto from(final User userSaved, final ActionType actionType) {
    var userEvent = new UserEventDto(userSaved);
    userEvent.setActionType(actionType.toString());
    return userEvent;
  }
}
