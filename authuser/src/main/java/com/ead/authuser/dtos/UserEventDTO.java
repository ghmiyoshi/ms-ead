package com.ead.authuser.dtos;

import com.ead.authuser.enums.ActionType;
import com.ead.authuser.models.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class UserEventDTO {

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

    public UserEventDTO(final User userSaved) {
        BeanUtils.copyProperties(userSaved, this);
        this.userStatus = userSaved.getUserStatus().toString();
        this.userType = userSaved.getUserType().toString();
    }

    public static UserEventDTO from(final User userSaved, final ActionType actionType) {
        var userEvent = new UserEventDTO(userSaved);
        userEvent.setActionType(actionType.toString());
        return userEvent;
    }

}
