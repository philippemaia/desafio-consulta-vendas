package com.devsuperior.dsmeta.dto;

import com.devsuperior.dsmeta.entities.Sale;

public class SaleSummaryDTO {

    private String sellerName;
    private Double total;

    public SaleSummaryDTO() {
    }

    public SaleSummaryDTO(Double total, String sellerName) {
        this.total = total;
        this.sellerName = sellerName;
    }

    public SaleSummaryDTO(Sale entity) {
        sellerName = entity.getSeller().getName();
        total = entity.getAmount();
    }

    public String getSellerName() {
        return sellerName;
    }

    public Double getTotal() {
        return total;
    }
}
