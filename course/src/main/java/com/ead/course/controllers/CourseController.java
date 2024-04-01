package com.ead.course.controllers;

import static com.ead.course.specs.CourseSpecificationBuilder.toSpec;

import com.ead.course.dtos.CourseDto;
import com.ead.course.enums.CourseLevel;
import com.ead.course.enums.CourseStatus;
import com.ead.course.models.Course;
import com.ead.course.services.CourseService;
import com.ead.course.specs.CourseFilter;
import com.ead.course.validation.CourseValidator;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseController {

  private final CourseService courseService;
  private final CourseValidator courseValidator;

  @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
  @PostMapping
  public ResponseEntity<Object> saveCourse(@RequestBody @Valid final CourseDto courseDto,
      final Errors errors) {
    log.info("[method:saveCourse] Received: {}", courseDto);
    courseValidator.validate(courseDto, errors);
    var course = new Course();
    BeanUtils.copyProperties(courseDto, course);
    return ResponseEntity.status(HttpStatus.CREATED).body(courseService.saveCourse(course));
  }

  @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{courseId}")
  public void deleteCourse(@PathVariable final UUID courseId) {
    log.info("[method:deleteCourse] Received id: {}", courseId);
    courseService.deleteCourse(courseId);
  }

  @PreAuthorize("hasRole('INSTRUCTOR')")
  @PutMapping("/{courseId}")
  public ResponseEntity<Course> updateCourse(@PathVariable final UUID courseId,
      @RequestBody @Valid final CourseDto courseDto) {
    var course = courseService.findCourseById(courseId);
    BeanUtils.copyProperties(courseDto, course);
    return ResponseEntity.ok(courseService.saveCourse(course));
  }

  @PreAuthorize("hasRole('STUDENT')")
  @GetMapping
  public ResponseEntity<Page<Course>> getAllCourses(
      @RequestParam(required = false) final CourseLevel courseLevel,
      @RequestParam(required = false) final CourseStatus courseStatus,
      @RequestParam(required = false) final String name,
      @RequestParam(required = false) final UUID userId,
      @PageableDefault(page = 0, size = 10, sort = "courseId",
          direction = Sort.Direction.ASC) final Pageable pageable) {
    final var courseFilter = CourseFilter.createFilter(courseLevel, courseStatus, name, userId);
    return ResponseEntity.ok(courseService.findAllCourses(toSpec(courseFilter), pageable));
  }

  @GetMapping("/{courseId}")
  public ResponseEntity<Course> getOneCourse(@PathVariable final UUID courseId) {
    return ResponseEntity.ok(courseService.findCourseById(courseId));
  }
}
