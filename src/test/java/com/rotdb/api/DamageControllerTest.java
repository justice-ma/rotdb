package com.rotdb.api;

import com.rotdb.TestFiles;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DamageControllerIT {

    @Autowired
    private MockMvc mvc;

    @Test
    void baseline_should_work() throws Exception {
        String body = TestFiles.readResource("/golden/baseline.json");

        mvc.perform(post("/damage/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());
    }

    @Test
    void prayer_should_increase_damage() throws Exception {
        String body = TestFiles.readResource("/golden/prayer_only.json");

        mvc.perform(post("/damage/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());
    }

    @Test
    void potion_should_increase_damage() throws Exception {
        String body = TestFiles.readResource("/golden/potion_only.json");

        mvc.perform(post("/damage/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());
    }

    @Test
    void equipment_should_increase_damage() throws Exception {
        String body = TestFiles.readResource("/golden/equipment_only.json");

        mvc.perform(post("/damage/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());
    }
}