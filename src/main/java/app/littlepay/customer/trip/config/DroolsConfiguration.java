package app.littlepay.customer.trip.config;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.io.Resource;
import org.kie.api.runtime.KieContainer;
import org.kie.internal.io.ResourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DroolsConfiguration {

  private static final String PRICING_RULES_XLS = "rules/pricing-rules.xlsx";
  private static final KieServices kieServices = KieServices.Factory.get();

  @Bean
  public KieContainer kieContainer() {

    Resource dt = ResourceFactory.newClassPathResource(PRICING_RULES_XLS, getClass());

    KieFileSystem kieFileSystem = kieServices.newKieFileSystem().write(dt);
    KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem).buildAll();

    return kieServices.newKieContainer(kieBuilder.getKieModule().getReleaseId());
  }
}
