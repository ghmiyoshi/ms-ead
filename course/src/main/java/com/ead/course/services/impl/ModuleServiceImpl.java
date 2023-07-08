package com.ead.course.services.impl;

import com.ead.course.models.Module;
import com.ead.course.repositories.ModuleRepository;
import com.ead.course.services.LessonService;
import com.ead.course.services.ModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class ModuleServiceImpl implements ModuleService {

    private final ModuleRepository moduleRepository;
    private final LessonService lessonService;

    @Override
    public void deleteModule(final Module module) {
        if (nonNull(module.getLessons()) && !module.getLessons().isEmpty()) {
            lessonService.deleteLessons(module.getLessons());
        }
        moduleRepository.delete(module);
    }

    @Override
    public Module saveModule(final Module module) {
        return moduleRepository.save(module);
    }

    @Override
    public Module findByModuleIdAndCourseId(final UUID moduleId, final UUID courseId) {
        return moduleRepository.findByModuleIdAndCourseId(moduleId, courseId).orElseThrow(() -> new RuntimeException(
                "Module not found for this course"));
    }

    @Override
    public List<Module> findAllModulesByCourseId(final UUID courseId) {
        return moduleRepository.findAllModulesByCourseId(courseId);
    }

    @Override
    public Module findModuleById(final UUID moduleId) {
        return moduleRepository.findById(moduleId).orElseThrow(() -> new RuntimeException("Module not found"));
    }

}
