package dev.joon.exampekotlinjpa

import dev.joon.simplecrudapi.TestcontainersConfiguration
import jakarta.persistence.EntityManager
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import org.junit.jupiter.api.Test


@SpringBootTest
@Import(TestcontainersConfiguration::class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class SimpleLazyLoadingTests {

    @Autowired
    private lateinit var simpleRepository: SimpleRepository

    @Autowired
    private lateinit var simpleRelationRepository: SimpleRelationRepository

    @Autowired
    private lateinit var entityManager: EntityManager

    @Test
    @Transactional
    fun `test lazy loading of simple`() {
        val simple = simpleRepository.save(Simple(id = 1, name = "Test Name", description = "Test Description"))
        val simpleRelation = simpleRelationRepository.save(SimpleRelation(id = 1, simple = simple, relationName = "Test Relation"))

        entityManager.flush()
        entityManager.clear()

        val fetchedSimpleRelation = simpleRelationRepository.findById(simpleRelation.id).orElse(null)

        assertNotNull(fetchedSimpleRelation)

        assertFalse(isLoaded(fetchedSimpleRelation))

        println("before call fetchedSimpleRelation.simple.name")
        val simpleName = fetchedSimpleRelation.simple.name
        println("after call fetchedSimpleRelation.simple.name")

        assertTrue(isLoaded(fetchedSimpleRelation))
    }

    private fun isLoaded(simpleRelation: SimpleRelation): Boolean {
        return entityManager.entityManagerFactory.persistenceUnitUtil.isLoaded(simpleRelation, "simple")
    }
}
