package com.project.sivakodali.controllers;

import com.project.sivakodali.dto.MetaDataDto;
import com.project.sivakodali.services.MetaDataService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collections;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = MetaDataController.class)
public class MetaDataControllerTest {

    @MockBean
    private MetaDataService metaDataService;

    @Autowired
    private MockMvc mvc;

    @Test
    public void testGetAllMetadata() throws Exception {
        this.mvc
                .perform(get("/api/metadata"))
                .andExpect(status().isOk());
    }

    @Test
    @Ignore
    public void testGetAllMetadataByPagePerPage() throws Exception {
        // given
        given(metaDataService.findMetaData(1, 1)).willReturn(
                Collections.singletonList(new MetaDataDto("foo"))
        );

        String expected = getFooMetaData();

        // when
        MvcResult mvcResult = this.mvc
                .perform(get("/api/metadata?page=1&perPage=1"))
                .andExpect(status().isOk())
                .andReturn();

        // then
        String actual = mvcResult.getResponse().getContentAsString();
        JSONAssert.assertEquals(expected, actual, false);
    }

    private String getFooMetaData() {
        return "[{\"id\":\"foo\",\"fullPath\":null,\"name\":null,\"size\":null,\"md5Hash\":null,\"cacheControl\":null,\"contentDisposition\":null,\"contentEncoding\":null,\"contentLanguage\":null,\"contentType\":null,\"custom\":null,\"created\":null,\"updated\":null}]";
    }

    private String getBarMetaData() {
        return "[{\"id\":\"bar\",\"fullPath\":null,\"name\":null,\"size\":null,\"md5Hash\":null,\"cacheControl\":null,\"contentDisposition\":null,\"contentEncoding\":null,\"contentLanguage\":null,\"contentType\":null,\"custom\":null,\"created\":null,\"updated\":null}]";
    }
}