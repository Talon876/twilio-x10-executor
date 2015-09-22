package org.nolat;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

public class TwilioX10Routes extends RouteBuilder {

    public void configure() {
        from("netty4-http:http://0.0.0.0:8000/house")
                .transform(simple("${headers.Body.toLowerCase()}"))
                .log("Twilio Request '${body}' from ${headers.From}")
                .choice()
                    .when(body().contains("on"))
                        .to("exec://cm17a?args=3 A1ON")
                    .otherwise()
                        .to("exec://cm17a?args=3 A1OFF")
                .end()
                .setHeader(Exchange.CONTENT_TYPE, constant("text/plain"))
                .transform(constant("done"));
    }

}
