package com.fitTracker.fit.context;

import com.fitTracker.fit.FitApplication;
import com.fitTracker.fit.container.DatabaseContainer;
import org.junit.ClassRule;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public abstract class TestContext {
    @ClassRule
    public static final DatabaseContainer databaseContainer = DatabaseContainer.getInstance();

}
