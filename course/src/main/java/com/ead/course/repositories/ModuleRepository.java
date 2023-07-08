package com.ead.course.repositories;

import com.ead.course.models.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ModuleRepository extends JpaRepository<Module, UUID> {

    //@EntityGraph(attributePaths = "lessons")
    @Query("SELECT m FROM Module m JOIN FETCH m.course c LEFT JOIN FETCH m.lessons l WHERE m.moduleId = :moduleId AND"
            + " c.courseId = :courseId")
    Optional<Module> findByModuleIdAndCourseId(@Param("moduleId") final UUID moduleId,
                                               @Param("courseId") final UUID courseId);

    //@EntityGraph(attributePaths = "course")
    @Query("SELECT m FROM Module m JOIN FETCH m.course c WHERE c.courseId = :courseId")
    List<Module> findAllModulesByCourseId(@Param("courseId") final UUID courseId);

}
