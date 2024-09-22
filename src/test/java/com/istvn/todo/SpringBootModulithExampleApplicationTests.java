package com.istvn.todo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest
class SpringBootModulithExampleApplicationTests {
	

	  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
	    "postgres:16-alpine"
	  );

	@Test
	void contextLoads() {
	}
	
	@Test
    void writeDocumentationSnippets() {
        var modules = ApplicationModules.of(SpringBootModulithExampleApplication.class).verify();
        new Documenter(modules)
            .writeModulesAsPlantUml()
            .writeIndividualModulesAsPlantUml()
            .writeDocumentation();
    }

}
