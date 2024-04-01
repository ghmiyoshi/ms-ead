package com.ead.course.repositories;

import com.ead.course.models.Module;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ModuleRepository extends JpaRepository<Module, UUID>,
    JpaSpecificationExecutor<Module> {

  //@EntityGraph(attributePaths = "lessons")
  @Query(
      "SELECT m FROM Module m JOIN FETCH m.course c LEFT JOIN FETCH m.lessons l WHERE m.moduleId = :moduleId AND"
          + " c.courseId = :courseId")
  Optional<Module> findByModuleIdAndCourseId(@Param("moduleId") final UUID moduleId,
      @Param("courseId") final UUID courseId);

  // @EntityGraph(attributePaths = "course")
  @Query("SELECT m FROM Module m JOIN FETCH m.course c WHERE c.courseId = :courseId")
  Page<Module> findAllModulesByCourseId(@Param("courseId") final UUID courseId,
      Pageable pageable);

  @EntityGraph(attributePaths = "course")
  Page<Module> findAll(Specification<Module> spec, Pageable pageable);
}
