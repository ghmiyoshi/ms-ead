package com.ead.course.enums;

import com.ead.course.dtos.UserEventDTO;
import com.ead.course.services.UserService;

public enum ActionType {

    CREATE {
        @Override
        public void execute(UserService userService, UserEventDTO userEventDTO) {
            userService.save(userEventDTO.convertToUser());
        }
    },
    UPDATE {
        @Override
        public void execute(UserService userService, UserEventDTO userEventDTO) {
            userService.save(userEventDTO.convertToUser());
        }
    },
    DELETE {
        @Override
        public void execute(UserService userService, UserEventDTO userEventDTO) {
            userService.deleteById(userEventDTO.getUserId());
        }
    };

    public abstract void execute(final UserService userService, final UserEventDTO userEventDTO);

}
