package com.ead.course.services;

import com.ead.course.models.Module;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public interface ModuleService {

    void deleteModule(Module module);

    Module saveModule(Module module);

    Module findByModuleIdAndCourseId(UUID moduleId, UUID courseId);

    Page<Module> findAllModulesByCourseId(Specification<Module> spec, Pageable pageable);

    Module findModuleById(UUID moduleId);

}
