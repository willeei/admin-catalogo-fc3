package br.com.williamsbarriquero.admin.catalogo.infrastructure.category.persistence;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<CategoryJpaEntity, String> {

    Page<CategoryJpaEntity> findAll(Specification<CategoryJpaEntity> whereClouse, Pageable page);

    @Query(value = "SELECT c.id FROM Category c WHERE c.id in :ids")
    List<String> existsByIds(@Param("ids") List<String> ids);
}
