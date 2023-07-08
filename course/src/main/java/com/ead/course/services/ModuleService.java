package com.ead.course.services;

import com.ead.course.models.Module;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public interface ModuleService {

    void deleteModule(final Module module);

    Module saveModule(final Module module);

    Module findByModuleIdAndCourseId(final UUID moduleId, final UUID courseId);

    Page<Module> findAllModulesByCourseId(final Specification<Module> spec, final Pageable pageable);

    Module findModuleById(final UUID moduleId);

}
