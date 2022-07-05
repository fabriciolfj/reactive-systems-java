package com.github.fabriciolfj;

public record Product(Long id, String name) {

    public static Product getRecommendationProduct(final Long id) {
        return new Product(id, "Teste " + id);
    }
}
