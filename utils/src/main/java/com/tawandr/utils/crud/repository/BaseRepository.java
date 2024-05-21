package com.tawandr.utils.crud.repository;

import com.tawandr.utils.crud.domain.BaseEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@NoRepositoryBean
public interface BaseRepository<Entity extends BaseEntity>
        extends JpaRepository<Entity, Long>, JpaSpecificationExecutor<Entity> {
    @Query("select e from #{#entityName} e where e.createdOn = :datetime")
    List<Entity> findAllByCreationDate(@Param("datetime") LocalDateTime date);

    default List<Entity> findByCreationTime(LocalDateTime time) {
        return findAllByCreationDate(time);
    }

    default List<Entity> findByCreationDate(LocalDateTime dateTime) {
        LocalDateTime dayStart = dateTime.toLocalDate().atTime(LocalTime.MIN);
        LocalDateTime dayEnd = dateTime.toLocalDate().atTime(LocalTime.MAX);
        return findByCreationDateRange(dayStart, dayEnd);
    }

    @Query("select e from #{#entityName} e where e.createdOn BETWEEN :startDate AND :endDate")
    List<Entity> findByCreationDateRange(@Param("startDate") LocalDateTime startDate,
                                    @Param("endDate") LocalDateTime endDate);

    @Override
    default List<Entity> findAll(){
        return findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Query("select e from #{#entityName} e where e.createdOn = (select MAX(e2.createdOn) from #{#entityName} e2)")
    List<Entity> findLatest();
}
