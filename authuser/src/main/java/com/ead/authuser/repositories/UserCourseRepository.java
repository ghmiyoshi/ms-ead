package com.ead.authuser.repositories;

import com.ead.authuser.models.User;
import com.ead.authuser.models.UserCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface UserCourseRepository extends JpaRepository<UserCourse, UUID>, JpaSpecificationExecutor<User> {

    boolean existsByUserUserIdAndCourseId(final UUID userId, final UUID courseId);

}
