package com.thezookaycompany.zookayproject.repositories;

import com.thezookaycompany.zookayproject.model.entity.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Integer> {
//    List<AnimalDto> findAnimalByCage(Cage cage);

    Animal findAnimalsByAnimalId(Integer animalId);
    @Query("SELECT a FROM Animal a JOIN FETCH a.species JOIN FETCH a.cage WHERE a.animalId = :animalId")
    Animal findAnimalWithSpeciesAndCage(@Param("animalId") Integer animalId);

    @Query("SELECT a FROM Animal a WHERE LOWER(a.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Animal> findAnimalsByDescriptionContainingKeyword(@Param("keyword") String keyword);


    @Query("SELECT a FROM Animal a ORDER BY a.height ASC")
    List<Animal> findAllByHeightAsc();
    @Query("SELECT a FROM Animal a ORDER BY a.height DESC")
    List<Animal> findAllByHeightDesc();


    @Query("SELECT a FROM Animal a ORDER BY a.weight ASC")
    List<Animal> findAllByWeightAsc();
    @Query("SELECT a FROM Animal a ORDER BY a.weight DESC")
    List<Animal> findAllByWeightDesc();


    @Query("SELECT a FROM Animal a ORDER BY a.age ASC")
    List<Animal> findAllByAgeAsc();

    @Query("SELECT a FROM Animal a ORDER BY a.age DESC")
    List<Animal> findAllByAgeDesc();

    @Query("SELECT a.imageAnimal, a.name FROM Animal a WHERE a.species.groups = :groups")
    List<Object[]> findAnimalImageAndNameByGroups(String groups);

    @Query("SELECT a FROM Animal a WHERE a.species.groups = :groups")
    List<Animal> findAnimalsBySpeciesGroups(String groups);


}
