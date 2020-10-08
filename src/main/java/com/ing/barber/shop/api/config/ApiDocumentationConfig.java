package com.ing.barber.shop.api.config;

import io.swagger.annotations.Contact;
import io.swagger.annotations.ExternalDocs;
import io.swagger.annotations.Info;
import io.swagger.annotations.License;
import io.swagger.annotations.SwaggerDefinition;

/** The interface Api documentation config. */
@SwaggerDefinition(
    info =
        @Info(
            description = "Edwin's Barber Shop",
            version = "V1.0.0",
            title = "Edwin's Barber API",
            contact =
                @Contact(
                    name = "Manoj Mallick",
                    email = "mmalick1990@gmail.com",
                    url = "https://github.com/manojmallick"),
            license =
                @License(name = "Apache 2.0", url = "http://www.apache.org/licenses/LICENSE-2.0")),
    consumes = {"application/json", "application/xml"},
    produces = {"application/json", "application/xml"},
    schemes = {SwaggerDefinition.Scheme.HTTP, SwaggerDefinition.Scheme.HTTPS},
    externalDocs =
        @ExternalDocs(value = "To Know more about me", url = "https://github.com/manojmallick"))
public interface ApiDocumentationConfig {}
