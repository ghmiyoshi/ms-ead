package com.ead.course.controllers;

import com.ead.course.dtos.LessonDTO;
import com.ead.course.models.Lesson;
import com.ead.course.services.LessonService;
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
public class LessonController {

    private final LessonService lessonService;
    private final ModuleService moduleService;

    @PostMapping("/modules/{moduleId}/lessons")
    public ResponseEntity<Lesson> saveLesson(@PathVariable final UUID moduleId,
                                             @RequestBody @Valid final LessonDTO lessonDTO) {
        var module = moduleService.findModuleById(moduleId);
        var lesson = new Lesson();
        BeanUtils.copyProperties(lessonDTO, lesson);
        lesson.setModule(module);
        return ResponseEntity.status(HttpStatus.CREATED).body(lessonService.saveLesson(lesson));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/modules/{moduleId}/lessons/{lessonId}")
    public void deleteModule(@PathVariable final UUID moduleId, @PathVariable final UUID lessonId) {
        var lesson = lessonService.findByModuleIdAndLessonId(moduleId, lessonId);
        lessonService.deleteLesson(lesson);
    }

    @PutMapping("/modules/{moduleId}/lessons/{lessonId}")
    public ResponseEntity<Lesson> updateModule(@PathVariable final UUID moduleId,
                                               @PathVariable final UUID lessonId,
                                               @RequestBody @Valid final LessonDTO lessonDto) {
        var lesson = lessonService.findByModuleIdAndLessonId(moduleId, lessonId);
        BeanUtils.copyProperties(lessonDto, lesson);
        return ResponseEntity.ok(lessonService.saveLesson(lesson));
    }

    @GetMapping("/modules/{moduleId}/lessons")
    public ResponseEntity<List<Lesson>> getAllLessons(@PathVariable final UUID moduleId) {
        return ResponseEntity.ok(lessonService.findAllLessonsByModuleId(moduleId));
    }

    @GetMapping("/modules/{moduleId}/lessons/{lessonId}")
    public ResponseEntity<Lesson> getOneModule(@PathVariable final UUID moduleId,
                                               @PathVariable final UUID lessonId) {
        return ResponseEntity.ok(lessonService.findByModuleIdAndLessonId(moduleId, lessonId));
    }

}
