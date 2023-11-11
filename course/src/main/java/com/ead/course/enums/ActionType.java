package com.ead.course.enums;

import com.ead.course.dtos.UserEventDTO;
import com.ead.course.services.UserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum ActionType {

    CREATE {
        @Override
        public void execute(UserService userService, UserEventDTO userEventDTO) {
            log.info("{}::execute - Save action message", getClass().getSimpleName());
            userService.save(userEventDTO.convertToUser());
        }
    },
    UPDATE {
        @Override
        public void execute(UserService userService, UserEventDTO userEventDTO) {
            log.info("{}::execute - Update action message", getClass().getSimpleName());
            userService.save(userEventDTO.convertToUser());
        }
    },
    DELETE {
        @Override
        public void execute(UserService userService, UserEventDTO userEventDTO) {
            log.info("{}::execute - Delete action message", getClass().getSimpleName());
            userService.deleteById(userEventDTO.getUserId());

        }
    };

    public abstract void execute(final UserService userService, final UserEventDTO userEventDTO);

}
