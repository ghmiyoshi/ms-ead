package com.ead.course.services.impl;

import com.ead.course.async.publishers.NotificationCommandPublisher;
import com.ead.course.dtos.NotificationCommandDTO;
import com.ead.course.models.Course;
import com.ead.course.models.User;
import com.ead.course.repositories.CourseRepository;
import com.ead.course.repositories.UserRepository;
import com.ead.course.services.CourseService;
import com.ead.course.services.ModuleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final ModuleService moduleService;
    private final NotificationCommandPublisher notificationCommandPublisher;

    @Transactional
    @Override
    public void deleteCourse(final UUID courseId) {
        var course = findCourseById(courseId);
        if (isNotEmpty(course.getModules())) {
            course.getModules().stream().forEach(moduleService::deleteModule);
        }
        courseRepository.deleteCourseUserByCourse(courseId);
        courseRepository.delete(course);
    }

    @Override
    public Course saveCourse(final Course course) {
        log.info("{}::saveCourse - Saving course: {}", getClass().getSimpleName(), course);
        return courseRepository.save(course);
    }

    @Override
    public Course findCourseById(final UUID courseId) {
        log.info("{}::findCourseById - Find course by id: {}", getClass().getSimpleName(), courseId);
        return courseRepository.findByCourseId(courseId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                                                                       "Course not found"));
    }

    @Override
    public Page<Course> findAllCourses(final Specification<Course> spec, final Pageable pageable) {
        log.info("{}::findAllCourses - Searching courses with spec: {}", getClass().getSimpleName(), spec);
        return courseRepository.findAll(spec, pageable);
    }

    @Override
    public Page<User> findAllUsersByCourse(final Specification<User> spec, final Pageable pageable) {
        log.info("{}::findAllUsersByCourse - Searching all users by course with spec: {}", getClass().getSimpleName(),
                 spec);
        return userRepository.findAll(spec, pageable);
    }

    @Override
    public void existsByCourseAndUser(UUID courseId, UUID userId) {
        log.info("{}::existsByCourseAndUser - Course id: {} and user id: {}", getClass().getSimpleName(), courseId,
                 userId);
        var existsSubscription = courseRepository.existsByCourseAndUser(courseId, userId).equals(1L) ? true : false;
        if (existsSubscription) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already subscribed in course");
        }
    }

    @Transactional
    @Override
    public void saveSubscriptionUserInCourse(UUID courseId, UUID userId) {
        courseRepository.saveCourseUser(courseId, userId);
        log.info("{}::saveSubscriptionUserInCourse - Course id: {} and user id: {}", getClass().getSimpleName(),
                 courseId,
                 userId);
    }

    @Transactional
    @Override
    public void saveSubscriptionUserInCourseAndSendNotification(final Course course, final User user) {
        courseRepository.saveCourseUser(course.getCourseId(), user.getUserId());
        log.info("{}::saveSubscriptionUserInCourseAndSendNotification - Course id: {} and user id: {}",
                 getClass().getSimpleName(), course.getCourseId(), user.getUserId());
        try {
            var notificationCommandDto = new NotificationCommandDTO("Bem-Vindo(a) ao Curso: " + course.getName(),
                                                                    user.getFullName() + " a sua inscrição foi "
                                                                            + "realizada com sucesso!",
                                                                    user.getUserId());
            notificationCommandPublisher.publishNotificationCommand(notificationCommandDto);
        } catch (Exception e) {
            log.warn("{}::saveSubscriptionUserInCourseAndSendNotification - Error sending notification!",
                     getClass().getSimpleName());
        }
    }
    
}
