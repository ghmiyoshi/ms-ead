package com.ead.course.services.impl;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

import com.ead.course.models.Module;
import com.ead.course.repositories.ModuleRepository;
import com.ead.course.services.LessonService;
import com.ead.course.services.ModuleService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ModuleServiceImpl implements ModuleService {

  private final ModuleRepository moduleRepository;
  private final LessonService lessonService;

  @Override
  public void deleteModule(final Module module) {
      log.info("[method:deleteModule] Delete module: {}", module);
    if (isNotEmpty(module.getLessons())) {
      lessonService.deleteLessons(module.getLessons());
    }
    moduleRepository.delete(module);
  }

  @Override
  public Module saveModule(final Module module) {
      log.info("[method:saveModule] Received: {}", module);
    return moduleRepository.save(module);
  }

  @Override
  public Module findByModuleIdAndCourseId(final UUID moduleId, final UUID courseId) {
      log.info("[method:findByModuleIdAndCourseId] Module id: {} and course id: {}", moduleId,
              courseId);
    return moduleRepository.findByModuleIdAndCourseId(moduleId, courseId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Module not found for this course"));
  }

  @Override
  public Page<Module> findAllModulesByCourseId(final Specification<Module> spec,
      final Pageable pageable) {
    return moduleRepository.findAll(spec, pageable);
  }

  @Override
  public Module findModuleById(final UUID moduleId) {
    return moduleRepository.findById(moduleId).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Module not found"));
  }
}
