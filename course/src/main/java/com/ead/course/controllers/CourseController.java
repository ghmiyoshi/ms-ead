package com.ead.course.controllers;

import com.ead.course.dtos.CourseDTO;
import com.ead.course.enums.CourseLevel;
import com.ead.course.enums.CourseStatus;
import com.ead.course.models.Course;
import com.ead.course.services.CourseService;
import com.ead.course.specs.CourseFilter;
import com.ead.course.validation.CourseValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.ead.course.specs.CourseSpecificationBuilder.toSpec;

@Slf4j
@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseController {

    private final CourseService courseService;
    private final CourseValidator courseValidator;

    @PostMapping
    public ResponseEntity<Object> saveCourse(@RequestBody final CourseDTO courseDto, final Errors errors) {
        log.info("{}::saveCourse - received: {}", getClass().getSimpleName(), courseDto);
        courseValidator.validate(courseDto, errors);
        var course = new Course();
        BeanUtils.copyProperties(courseDto, course);
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.saveCourse(course));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{courseId}")
    public void deleteCourse(@PathVariable final UUID courseId) {
        courseService.deleteCourse(courseId);
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<Course> updateCourse(@PathVariable final UUID courseId,
                                               @RequestBody @Valid final CourseDTO courseDto) {
        var course = courseService.findCourseById(courseId);
        BeanUtils.copyProperties(courseDto, course);
        return ResponseEntity.ok(courseService.saveCourse(course));
    }

    @GetMapping
    public ResponseEntity<Page<Course>> getAllCourses(@RequestParam(required = false) final CourseLevel courseLevel,
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
