package com.prot.protbe.rest;

import com.prot.protbe.coreexception.ResourceException;
import com.prot.protbe.domain.Param;
import com.prot.protbe.dto.ParamDto;
import com.prot.protbe.repository.ParamRepository;
import com.prot.protbe.service.ParamService;
import com.prot.protbe.webutils.HeaderUtil;
import com.prot.protbe.webutils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ParamController {

    private final Logger log = LoggerFactory.getLogger(ParamController.class);

    private static final String ENTITY_NAME = "Param";

    //@Value("${clientApp.name}")
    private String applicationName;

    @Autowired
    private ParamService paramService;

    @Autowired
    private ParamRepository paramRepository;

    @PostMapping("/createParam")
    public ResponseEntity<Param> createParam(@RequestBody ParamDto protDTO) throws URISyntaxException, ResourceException {
        log.debug("REST request to save Param : {}", protDTO);
        if (protDTO.getId() != null) {
            throw new ResourceException("A new Param cannot already have an ID");
        }
        Param result = paramService.save(protDTO);
        return ResponseEntity
                .created(new URI("/params/createParam/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    @PutMapping("/updateParam/{id}")
    public ResponseEntity<Object> updateParam(@PathVariable(value = "id", required = false) final Long id, @RequestBody ParamDto protDTO)
            throws URISyntaxException, ResourceException {
        log.debug("REST request to update Param : {}, {}", id, protDTO);
        if (protDTO.getId() == null) {
            throw new ResourceException("Invalid id");
        }
        if (!Objects.equals(id, protDTO.getId())) {
            throw new ResourceException("Invalid ID Object");
        }

        if (!paramRepository.existsById(id)) {
            throw new ResourceException("Entity not found");
        }

        Param result = paramService.save(protDTO);
        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, protDTO.getId().toString()))
                .body(result);
    }

    @PatchMapping(value = "/patchParam/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Object> partialUpdateParam(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody ParamDto protDTO
    ) throws URISyntaxException, ResourceException {
        log.debug("REST request to partial update Param partially : {}, {}", id, protDTO);
        if (protDTO.getId() == null) {
            throw new ResourceException("Invalid id");
        }
        if (!Objects.equals(id, protDTO.getId())) {
            throw new ResourceException("Invalid ID Object");
        }

        if (!paramRepository.existsById(id)) {
            throw new ResourceException("Entity not found");
        }

        Optional<Object> result = Optional.ofNullable(paramService.partialUpdate(protDTO));

        return ResponseUtil.wrapOrNotFound(
                result,
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, protDTO.getId().toString())
        );
    }

    @GetMapping("/allJobsystem")
    public List<Param> getAllParams() {
        log.debug("REST request to get all Param");
        return paramService.findAll();
    }

    @GetMapping("/paramById/{id}")
    public ResponseEntity<Object> getParamById(@PathVariable Long id) {
        log.debug("REST request to get Param : {}", id);
        Optional<Object> protDTO = Optional.ofNullable(paramService.findOne(id));
        return ResponseUtil.wrapOrNotFound(protDTO);
    }

    @DeleteMapping("/deleteParam/{id}")
    public ResponseEntity<Void> deleteParam(@PathVariable Long id) {
        log.debug("REST request to delete Param : {}", id);
        paramService.delete(id);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                .build();
    }

}
