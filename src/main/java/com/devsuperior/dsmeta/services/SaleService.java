package com.devsuperior.dsmeta.services;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public Page<SaleReportDTO> getReport(String minDateStr, String maxDateStr, String name, Pageable pageable) {

		LocalDate maxDate = getMaxDate(maxDateStr);
		LocalDate minDate = getMinDate(minDateStr, maxDate);

		return repository.findReport(minDate, maxDate, name, pageable);
	}

	public List<SaleSummaryDTO> getSummary(String minDateStr, String maxDateStr) {

		LocalDate maxDate = getMaxDate(maxDateStr);
		LocalDate minDate = getMinDate(minDateStr, maxDate);

		return repository.findSummary(minDate, maxDate);

	}

	private static LocalDate getMinDate(String minDateStr, LocalDate maxDate) {

		if(!minDateStr.isEmpty()){
			return LocalDate.of(Integer.parseInt(minDateStr.substring(0,4)),
					Integer.parseInt(minDateStr.substring(5,7)),
					Integer.parseInt(minDateStr.substring(8,10)));
		}else{
			return maxDate.minusYears(1L);
		}
	}

	private static LocalDate getMaxDate(String maxDateStr) {

		if (!maxDateStr.isEmpty()){
			return LocalDate.of(Integer.parseInt(maxDateStr.substring(0,4)),
					Integer.parseInt(maxDateStr.substring(5,7)),
					Integer.parseInt(maxDateStr.substring(8,10)));
		}else{
			return LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		}

	}
}
