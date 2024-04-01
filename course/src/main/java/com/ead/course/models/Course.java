package com.ead.course.models;

import com.ead.course.enums.CourseLevel;
import com.ead.course.enums.CourseStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_courses")
public class Course extends DefaultModel {

  @EqualsAndHashCode.Include
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID courseId;

    @Column(nullable = false, length = 150, unique = true)
  private String name;

  @Column(nullable = false, length = 250)
  private String description;

  private String imageUrl;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private CourseStatus courseStatus;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private CourseLevel courseLevel;

  @Column(nullable = false)
  private UUID userInstructor;

  /* Essa anotacao diz para ignorar esse campo e ocultar em consultas findAll, findById */
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  // @Fetch(FetchMode.SUBSELECT)
  @OneToMany(mappedBy = "course")
  private Set<Module> modules;

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @ManyToMany
  @JoinTable(
      name = "tb_courses_users",
      joinColumns = @JoinColumn(name = "course_id"),
      inverseJoinColumns = @JoinColumn(name = "user_id"))
  private Set<User> users;
}
