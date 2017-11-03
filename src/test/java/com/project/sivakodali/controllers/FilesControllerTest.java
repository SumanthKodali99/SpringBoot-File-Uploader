package com.project.sivakodali.controllers;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = FilesController.class)
public class FilesControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @Ignore
    public void testGet() throws Exception {
        this.mvc.perform(get("/api/files")).andExpect(status().isOk());
    }
}