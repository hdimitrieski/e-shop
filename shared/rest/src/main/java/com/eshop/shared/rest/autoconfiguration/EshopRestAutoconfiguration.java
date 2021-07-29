package com.eshop.shared.rest.autoconfiguration;

import com.eshop.shared.rest.config.GlobalConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(GlobalConfiguration.class)
public class EshopRestAutoconfiguration {
}
