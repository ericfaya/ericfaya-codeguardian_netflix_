package com.nttdata.indhub.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;
import lombok.Setter;

@Entity
@Table(name = "season")
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SeasonEntity implements Serializable {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="number")
    private Integer number;

    @Column(name="name")
    private String name;

    @OneToMany(mappedBy = "season", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ChapterEntity> chapters;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tvshow_ref")
    private TVShowEntity tvShow;
}