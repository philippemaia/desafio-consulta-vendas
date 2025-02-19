package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query(value = """
            SELECT new com.devsuperior.dsmeta.dto.SaleReportDTO(obj.id, obj.date, obj.amount, obj.seller.name) 
            FROM Sale obj 
            WHERE 
            (obj.date BETWEEN :minDate AND :maxDate) 
            AND 
            UPPER(obj.seller.name) LIKE UPPER(CONCAT('%', :name , '%')) 
            """)
    Page<SaleReportDTO> findReport(LocalDate minDate, LocalDate maxDate, String name, Pageable pageable);

    @Query(value = """
            SELECT new com.devsuperior.dsmeta.dto.SaleSummaryDTO( SUM(obj.amount), obj.seller.name) 
            FROM Sale obj 
            WHERE 
            (obj.date BETWEEN :minDate AND :maxDate) 
            GROUP BY obj.seller.name
            """)
    List<SaleSummaryDTO> findSummary(LocalDate minDate, LocalDate maxDate);

}
