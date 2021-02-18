package com.eshop.ordering.domain.aggregatesmodel.buyer;

import com.eshop.ordering.domain.exceptions.OrderingDomainException;
import com.eshop.ordering.domain.seedwork.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

import static java.util.Objects.isNull;

public class PaymentMethod extends Entity {
    private String alias;
    private String cardNumber;
    private String securityNumber;
    private String cardHolderName;
    private LocalDateTime expiration;

    private int cardTypeId;
    @Getter
    @Setter(AccessLevel.PRIVATE)
    private CardType CardType;


    protected PaymentMethod() { }

    public PaymentMethod(int cardTypeId, String alias, String cardNumber, String securityNumber, String cardHolderName, LocalDateTime expiration)
    {
        if (isNull(cardNumber)) {
            throw new OrderingDomainException("Card number");
        }

        if (isNull(securityNumber)) {
            throw new OrderingDomainException("Security number");
        }

        if (isNull(cardHolderName)) {
            throw new OrderingDomainException("Card holder name");
        }

        if (expiration.isBefore(LocalDateTime.now()))
        {
            throw new OrderingDomainException("Expiration");
        }

        this.cardNumber = cardNumber;
        this.securityNumber = securityNumber;
        this.cardHolderName = cardHolderName;
        this.alias = alias;
        this.expiration = expiration;
        this.cardTypeId = cardTypeId;
    }

    public boolean isEqualTo(int cardTypeId, String cardNumber, LocalDateTime expiration)
    {
        return this.cardTypeId == cardTypeId
                && this.cardNumber.equals(cardNumber)
                && this.expiration.equals(expiration);
    }
}
