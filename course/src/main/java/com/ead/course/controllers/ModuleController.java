package com.ead.course.controllers;

import com.ead.course.dtos.ModuleDTO;
import com.ead.course.models.Module;
import com.ead.course.services.CourseService;
import com.ead.course.services.ModuleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class ModuleController {

    private final ModuleService moduleService;
    private final CourseService courseService;

    @PostMapping("/courses/{courseId}/modules")
    public ResponseEntity<Module> saveModule(@RequestBody @Valid final ModuleDTO moduleDto,
                                             @PathVariable final UUID courseId) {
        var course = courseService.findCourseById(courseId);
        var module = new Module();
        BeanUtils.copyProperties(moduleDto, module);
        module.setCourse(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(moduleService.saveModule(module));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/courses/{courseId}/modules/{moduleId}")
    public void deleteModule(@PathVariable final UUID courseId, @PathVariable final UUID moduleId) {
        var module = moduleService.findByModuleIdAndCourseId(moduleId, courseId);
        moduleService.deleteModule(module);
    }

    @PutMapping("/courses/{courseId}/modules/{moduleId}")
    public ResponseEntity<Module> updateModule(@PathVariable final UUID courseId,
                                               @PathVariable final UUID moduleId,
                                               @RequestBody @Valid final ModuleDTO moduletoDto) {
        var module = moduleService.findByModuleIdAndCourseId(moduleId, courseId);
        BeanUtils.copyProperties(moduletoDto, module);
        return ResponseEntity.ok(moduleService.saveModule(module));
    }

    @GetMapping("/courses/{courseId}/modules")
    public ResponseEntity<List<Module>> getAllModules(@PathVariable final UUID courseId) {
        return ResponseEntity.ok(moduleService.findAllModulesByCourseId(courseId));
    }

    @GetMapping("/courses/{courseId}/modules/{moduleId}")
    public ResponseEntity<Module> getOneModule(@PathVariable final UUID courseId,
                                               @PathVariable final UUID moduleId) {
        return ResponseEntity.ok(moduleService.findByModuleIdAndCourseId(moduleId, courseId));
    }

}
