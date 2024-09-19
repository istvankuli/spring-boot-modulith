package com.istvn.todo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

@SpringBootTest
class SpringBootModulithExampleApplicationTests {

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
