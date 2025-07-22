package com.example.server.entity;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Game {

  @Id
  @GeneratedValue
  private UUID id;

  private String name;

  @Column(length = 2000)
  private String description;

  private LocalDate releaseDate;

  private Double metascore;

  private Double userScore;

  private String developer;

  private String publisher;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "header_image_id")
  private Image headerImage;

  @ManyToMany
  @JoinTable( //
      name = "game_genre", //
      joinColumns = @JoinColumn(name = "game_id"), //
      inverseJoinColumns = @JoinColumn(name = "genre_id") //
  )
  private Set<Genre> genres;

  @ManyToMany
  @JoinTable( //
      name = "game_platform", //
      joinColumns = @JoinColumn(name = "game_id"), //
      inverseJoinColumns = @JoinColumn(name = "platform_id") //
  )
  private Set<Platform> platforms;

}
