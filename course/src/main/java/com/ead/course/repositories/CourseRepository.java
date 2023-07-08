package com.ead.course.repositories;

import com.ead.course.models.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, UUID>, JpaSpecificationExecutor<Course> {

    @EntityGraph(attributePaths = "modules")
    Optional<Course> findByCourseId(final UUID courseId);

    @EntityGraph(attributePaths = "modules")
    Page<Course> findAll(final Specification<Course> specification, final Pageable pageable);

}
