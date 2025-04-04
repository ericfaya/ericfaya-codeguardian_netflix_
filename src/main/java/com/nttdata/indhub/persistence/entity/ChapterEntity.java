package com.nttdata.indhub.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "chapter")
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ChapterEntity implements Serializable {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="number")
    private Integer number;

    @Column(name="name")
    private String name;

    @Column(name="duration")
    private Double duration;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinTable(
            name = "rel_chapter_actor",
            joinColumns = {@JoinColumn(name = "id_chapter")},
            inverseJoinColumns = {@JoinColumn(name = "id_actor")}
    )
    private List<ActorEntity> actors;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "season_ref")
    private SeasonEntity season;

}