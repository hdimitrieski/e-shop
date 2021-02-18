package com.eshop.ordering.domain.aggregatesmodel.order;

import com.eshop.ordering.domain.seedwork.ValueObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class Address extends ValueObject {
    @Getter
    public String street;
    @Getter
    public String city;
    @Getter
    public String state;
    @Getter
    public String country;
    @Getter
    public String zipCode;

    @Override
    protected List<Object> getEqualityComponents() {
        return List.of(
                street,
                city,
                state,
                country,
                zipCode
        );
    }
}
