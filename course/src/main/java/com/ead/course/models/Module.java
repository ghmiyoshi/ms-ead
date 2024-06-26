package com.ead.course.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "tb_modules")
public class Module extends DefaultModel {

  @EqualsAndHashCode.Include
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID moduleId;

  @Column(nullable = false, length = 150)
  private String title;

  @Column(nullable = false, length = 250)
  private String description;

  /* Essa anotacao diz para ignorar esse campo e ocultar em consultas findAll, findById */
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  /* Quando coloco optional o hibernate usa join nas querys */
  @ManyToOne(optional = false)
  @JoinColumn(name = "course_id")
  private Course course;

  /* Essa anotacao diz para ignorar esse campo e ocultar em consultas findAll, findById */
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @OneToMany(mappedBy = "module")
  private Set<Lesson> lessons;
}
