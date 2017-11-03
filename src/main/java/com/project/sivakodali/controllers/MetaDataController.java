package com.project.sivakodali.controllers;

import com.project.sivakodali.dto.MetaDataDto;
import com.project.sivakodali.services.MetaDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/api/metadata")
public class MetaDataController {

    @Autowired
    MetaDataService metaDataService;

    /**
     * GET All metadata
     * @return
     */
    @RequestMapping(value = "", method = GET)
    public ResponseEntity<List<MetaDataDto>> all(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer perPage
    ) {
        if (page == null) page = 0;
        if (perPage == null) perPage = 10;

        List<MetaDataDto> metaDataDtoList = metaDataService.findMetaData(page, perPage);

        return ResponseEntity.ok(metaDataDtoList);
    }

    /**
     * GET Metadata by ID
     */
    @RequestMapping(value = "/{id}" ,method = GET)
    public ResponseEntity<MetaDataDto> get(@PathVariable String id) {
        MetaDataDto found = metaDataService.findMetaData(id);

        if (found == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(found);
    }

    /**
     * POST to create new MetaData
     */
    @RequestMapping(value = "", method = POST)
    public ResponseEntity<String> save(@RequestBody MetaDataDto metaDataDto) {
        String id = metaDataService.save(metaDataDto);

        if(id == null) return ResponseEntity.badRequest().body("Fail save metadata");

        return ResponseEntity.ok(id);
    }
}
