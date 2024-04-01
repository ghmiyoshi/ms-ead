package com.ead.course.enums;

import com.ead.course.dtos.UserEventDto;
import com.ead.course.services.UserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum ActionType {

  CREATE {
    @Override
    public void execute(UserService userService, UserEventDto userEventDto) {
      log.info("[method:execute] Save action message");
      userService.save(userEventDto.convertToUser());
    }
  },
  UPDATE {
    @Override
    public void execute(UserService userService, UserEventDto userEventDto) {
      log.info("[method:execute] Update action message");
      userService.save(userEventDto.convertToUser());
    }
  },
  DELETE {
    @Override
    public void execute(UserService userService, UserEventDto userEventDto) {
      log.info("[method:execute] Delete action message");
      userService.deleteById(userEventDto.getUserId());
    }
  };

  public abstract void execute(final UserService userService, final UserEventDto userEventDto);
}
