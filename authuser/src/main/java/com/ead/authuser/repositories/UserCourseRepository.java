package com.ead.authuser.repositories;

import com.ead.authuser.models.User;
import com.ead.authuser.models.UserCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface UserCourseRepository extends JpaRepository<UserCourse, UUID>, JpaSpecificationExecutor<User> {

    boolean existsByUserUserIdAndCourseId(UUID userId, UUID courseId);

    boolean existsByCourseId(UUID courseId);

    @Query("SELECT uc FROM UserCourse uc WHERE uc.user.userId = :userId")
    List<UserCourse> findAllByUserUserId(@Param("userId") UUID userId);

    void deleteAllByCourseId(UUID courseId);

}
