package com.ead.course.services;

import com.ead.course.models.Module;

import java.util.List;
import java.util.UUID;

public interface ModuleService {

    void deleteModule(final Module module);

    Module saveModule(final Module module);

    Module findByModuleIdAndCourseId(final UUID moduleId, final UUID courseId);

    List<Module> findAllModulesByCourseId(final UUID courseId);

    Module findModuleById(final UUID moduleId);

}
