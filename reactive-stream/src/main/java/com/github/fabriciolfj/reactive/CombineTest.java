package com.github.fabriciolfj.reactive;

import com.github.fabriciolfj.Product;
import com.github.fabriciolfj.User;
import io.smallrye.mutiny.Uni;

public class CombineTest {

    public static void main(String[] args) {
        getRecommendation()
                .subscribe()
                .with(System.out::println);
    }

    private static Uni<String> getRecommendation() {
        Uni<User> user = User.userFabricio();
        Uni<Product> product = Uni.createFrom().item(Product.getRecommendationProduct(1L));

        return Uni.combine().all().unis(user, product)
                .asTuple()
                .onItem()
                .transform(t -> "Hello " + t.getItem1().name() + ", we recommend you " + t.getItem2().name());
    }
}
