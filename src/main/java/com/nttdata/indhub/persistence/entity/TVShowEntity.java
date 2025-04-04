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
@Table(name = "tvshow")
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class TVShowEntity implements Serializable {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="shortDescription")
    private String shortDescription;

    @Column(name="longDescription")
    private String longDescription;

    @Column(name="year")
    private Integer year;

    @Column(name="recommendedAge")
    private Integer recommendedAge;

    //URL of the trailer video
    @Column(name="advertising")
    private String advertising;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE
    })
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinTable(
        name = "rel_category_tvshows",
        joinColumns = {@JoinColumn(name = "id_category")},
        inverseJoinColumns = {@JoinColumn(name = "id_tvshow")}
    )
    private List<CategoryEntity> categories;

    @OneToMany(mappedBy = "tvShow", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SeasonEntity> seasons;
}