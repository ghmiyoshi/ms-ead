package com.ead.course.controllers;

import static com.ead.course.specs.LessonSpecificationBuilder.toSpec;

import com.ead.course.dtos.LessonDto;
import com.ead.course.models.Lesson;
import com.ead.course.services.LessonService;
import com.ead.course.services.ModuleService;
import com.ead.course.specs.LessonFilter;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class LessonController {

  private final LessonService lessonService;
  private final ModuleService moduleService;

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/modules/{moduleId}/lessons")
  public ResponseEntity<Lesson> saveLesson(@PathVariable final UUID moduleId,
      @RequestBody @Valid final LessonDto lessonDto) {
    var module = moduleService.findModuleById(moduleId);
    var lesson = new Lesson();
    BeanUtils.copyProperties(lessonDto, lesson);
    lesson.setModule(module);
    return ResponseEntity.status(HttpStatus.CREATED).body(lessonService.saveLesson(lesson));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/modules/{moduleId}/lessons/{lessonId}")
  public void deleteModule(@PathVariable final UUID moduleId, @PathVariable final UUID lessonId) {
    var lesson = lessonService.findByModuleIdAndLessonId(moduleId, lessonId);
    lessonService.deleteLesson(lesson);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("/modules/{moduleId}/lessons/{lessonId}")
  public ResponseEntity<Lesson> updateModule(@PathVariable final UUID moduleId,
      @PathVariable final UUID lessonId,
      @RequestBody @Valid final LessonDto lessonDto) {
    var lesson = lessonService.findByModuleIdAndLessonId(moduleId, lessonId);
    BeanUtils.copyProperties(lessonDto, lesson);
    return ResponseEntity.ok(lessonService.saveLesson(lesson));
  }

  @PreAuthorize("hasRole('STUDENT')")
  @GetMapping("/modules/{moduleId}/lessons")
  public ResponseEntity<Page<Lesson>> getAllLessons(@PathVariable final UUID moduleId,
      @RequestParam(required = false) final String title,
      @PageableDefault(page = 0, size = 10, sort = "lessonId",
          direction = Sort.Direction.ASC) final Pageable pageable) {
    final var lessonFilter = LessonFilter.createFilter(title, moduleId);
    return ResponseEntity.ok(
        lessonService.findAllLessonsByModuleId(toSpec(lessonFilter), pageable));
  }

  @PreAuthorize("hasRole('STUDENT')")
  @GetMapping("/modules/{moduleId}/lessons/{lessonId}")
  public ResponseEntity<Lesson> getOneModule(@PathVariable final UUID moduleId,
      @PathVariable final UUID lessonId) {
    return ResponseEntity.ok(lessonService.findByModuleIdAndLessonId(moduleId, lessonId));
  }

}
