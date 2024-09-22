package com.istvn.todo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest
@ActiveProfiles("test")
class SpringBootModulithExampleApplicationTests {
	
	@Test
	void contextLoads(){	
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
