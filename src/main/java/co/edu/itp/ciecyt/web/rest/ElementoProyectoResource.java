package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.repository.ElementoProyectoRepository;
import co.edu.itp.ciecyt.service.ElementoProyectoService;
import co.edu.itp.ciecyt.service.dto.ElementoProyectoDTO;
import co.edu.itp.ciecyt.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link co.edu.itp.ciecyt.domain.ElementoProyecto}.
 */
@RestController
@RequestMapping("/api/elemento-proyectos")
public class ElementoProyectoResource {

    private static final Logger log = LoggerFactory.getLogger(ElementoProyectoResource.class);

    private static final String ENTITY_NAME = "elementoProyecto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ElementoProyectoService elementoProyectoService;

    private final ElementoProyectoRepository elementoProyectoRepository;

    public ElementoProyectoResource(
        ElementoProyectoService elementoProyectoService,
        ElementoProyectoRepository elementoProyectoRepository
    ) {
        this.elementoProyectoService = elementoProyectoService;
        this.elementoProyectoRepository = elementoProyectoRepository;
    }

    /**
     * {@code POST  /elemento-proyectos} : Create a new elementoProyecto.
     *
     * @param elementoProyectoDTO the elementoProyectoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new elementoProyectoDTO, or with status {@code 400 (Bad Request)} if the elementoProyecto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ElementoProyectoDTO> createElementoProyecto(@Valid @RequestBody ElementoProyectoDTO elementoProyectoDTO)
        throws URISyntaxException {
        log.debug("REST request to save ElementoProyecto : {}", elementoProyectoDTO);
        if (elementoProyectoDTO.getId() != null) {
            throw new BadRequestAlertException("A new elementoProyecto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        elementoProyectoDTO = elementoProyectoService.save(elementoProyectoDTO);
        return ResponseEntity.created(new URI("/api/elemento-proyectos/" + elementoProyectoDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, elementoProyectoDTO.getId().toString()))
            .body(elementoProyectoDTO);
    }

    /**
     * {@code PUT  /elemento-proyectos/:id} : Updates an existing elementoProyecto.
     *
     * @param id the id of the elementoProyectoDTO to save.
     * @param elementoProyectoDTO the elementoProyectoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated elementoProyectoDTO,
     * or with status {@code 400 (Bad Request)} if the elementoProyectoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the elementoProyectoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ElementoProyectoDTO> updateElementoProyecto(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ElementoProyectoDTO elementoProyectoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ElementoProyecto : {}, {}", id, elementoProyectoDTO);
        if (elementoProyectoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, elementoProyectoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!elementoProyectoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        elementoProyectoDTO = elementoProyectoService.update(elementoProyectoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, elementoProyectoDTO.getId().toString()))
            .body(elementoProyectoDTO);
    }

    /**
     * {@code PATCH  /elemento-proyectos/:id} : Partial updates given fields of an existing elementoProyecto, field will ignore if it is null
     *
     * @param id the id of the elementoProyectoDTO to save.
     * @param elementoProyectoDTO the elementoProyectoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated elementoProyectoDTO,
     * or with status {@code 400 (Bad Request)} if the elementoProyectoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the elementoProyectoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the elementoProyectoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ElementoProyectoDTO> partialUpdateElementoProyecto(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ElementoProyectoDTO elementoProyectoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ElementoProyecto partially : {}, {}", id, elementoProyectoDTO);
        if (elementoProyectoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, elementoProyectoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!elementoProyectoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ElementoProyectoDTO> result = elementoProyectoService.partialUpdate(elementoProyectoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, elementoProyectoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /elemento-proyectos} : get all the elementoProyectos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of elementoProyectos in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ElementoProyectoDTO>> getAllElementoProyectos(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of ElementoProyectos");
        Page<ElementoProyectoDTO> page = elementoProyectoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /elemento-proyectos/:id} : get the "id" elementoProyecto.
     *
     * @param id the id of the elementoProyectoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the elementoProyectoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ElementoProyectoDTO> getElementoProyecto(@PathVariable("id") Long id) {
        log.debug("REST request to get ElementoProyecto : {}", id);
        Optional<ElementoProyectoDTO> elementoProyectoDTO = elementoProyectoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(elementoProyectoDTO);
    }

    /**
     * {@code DELETE  /elemento-proyectos/:id} : delete the "id" elementoProyecto.
     *
     * @param id the id of the elementoProyectoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteElementoProyecto(@PathVariable("id") Long id) {
        log.debug("REST request to delete ElementoProyecto : {}", id);
        elementoProyectoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
