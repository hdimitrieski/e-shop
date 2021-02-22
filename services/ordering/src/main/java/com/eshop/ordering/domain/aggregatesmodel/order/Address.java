package com.eshop.ordering.domain.aggregatesmodel.order;

import com.eshop.ordering.domain.seedwork.ValueObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.util.List;

@Embeddable
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

    @Transient
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
