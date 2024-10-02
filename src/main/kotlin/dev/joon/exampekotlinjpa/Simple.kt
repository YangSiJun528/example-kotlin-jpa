package dev.joon.exampekotlinjpa

import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaRepository

@Entity
class Simple(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    var name: String,
    var description: String,
    @OneToMany(mappedBy = "simple", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var relations: MutableList<SimpleRelation> = mutableListOf()
)

@Entity
class SimpleRelation(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "simple_id")
    var simple: Simple,
    var relationName: String
)

interface SimpleRepository : JpaRepository<Simple, Long>

interface SimpleRelationRepository : JpaRepository<SimpleRelation, Long>
