package com.ead.course.services.impl;

import com.ead.course.models.Module;
import com.ead.course.repositories.ModuleRepository;
import com.ead.course.services.LessonService;
import com.ead.course.services.ModuleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

@Slf4j
@Service
@RequiredArgsConstructor
public class ModuleServiceImpl implements ModuleService {

    private final ModuleRepository moduleRepository;
    private final LessonService lessonService;

    @Override
    public void deleteModule(final Module module) {
        log.info("{}::deleteModule - Delete module: {}", getClass().getSimpleName(), module);
        if (isNotEmpty(module.getLessons())) {
            lessonService.deleteLessons(module.getLessons());
        }
        moduleRepository.delete(module);
    }

    @Override
    public Module saveModule(final Module module) {
        log.info("{}::saveModule - received: {}", getClass().getSimpleName(), module);
        return moduleRepository.save(module);
    }

    @Override
    public Module findByModuleIdAndCourseId(final UUID moduleId, final UUID courseId) {
        log.info("{}::findByModuleIdAndCourseId - Module id: {} and course id: {}", getClass().getSimpleName(),
                 moduleId, courseId);
        return moduleRepository.findByModuleIdAndCourseId(moduleId, courseId).orElseThrow(() -> new RuntimeException(
                "Module not found for this course"));
    }

    @Override
    public Page<Module> findAllModulesByCourseId(final Specification<Module> spec,
                                                 final Pageable pageable) {
        return moduleRepository.findAll(spec, pageable);
    }

    @Override
    public Module findModuleById(final UUID moduleId) {
        return moduleRepository.findById(moduleId).orElseThrow(() -> new RuntimeException("Module not found"));
    }

}
