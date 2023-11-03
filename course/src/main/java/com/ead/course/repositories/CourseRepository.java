package com.ead.course.repositories;

import com.ead.course.models.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, UUID>, JpaSpecificationExecutor<Course> {

    @EntityGraph(attributePaths = "modules")
    Optional<Course> findByCourseId(final UUID courseId);

    //@EntityGraph(attributePaths = "modules")
    Page<Course> findAll(final Specification<Course> specification, final Pageable pageable);

    @Query(value = """
            SELECT CASE WHEN COUNT(*) > 0 THEN TRUE ELSE FALSE END 
            FROM tb_courses_users c WHERE c.course_id = :courseId AND c.user_id = :userId""",
            nativeQuery = true)
    Long existsByCourseAndUser(@Param("courseId") UUID courseId, @Param("userId") UUID userId);

    @Modifying
    @Query(value = """
            INSERT INTO tb_courses_users 
            VALUES (:courseId, :userId)""",
            nativeQuery = true)
    void saveCourseUser(@Param("courseId") UUID courseId, @Param("userId") UUID userId);

    @Modifying
    @Query(value = """
            DELETE FROM tb_courses_users 
            WHERE course_id = :courseId""",
            nativeQuery = true)
    void deleteCourseUserByCourse(@Param("courseId") UUID courseId);

    @Modifying
    @Query(value = """
            DELETE FROM tb_courses_users 
            WHERE user_id = :userId""",
            nativeQuery = true)
    void deleteCourseUserByUser(@Param("userId") UUID userId);

}
