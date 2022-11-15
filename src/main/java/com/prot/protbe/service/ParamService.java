package com.prot.protbe.service;

import com.prot.protbe.domain.Param;
import com.prot.protbe.dto.ParamDto;
import com.prot.protbe.mapper.ParamMapper;
import com.prot.protbe.repository.ParamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class ParamService {

    private final Logger log = LoggerFactory.getLogger(ParamService.class);

    @Autowired
    private ParamRepository paramRepository;

    @Autowired
    private ParamMapper mapper;

    /**
     * Save a Job System.
     * @param paramDto to transform into entity and save.
     * @return the persisted entity
     */
    public Param save(ParamDto paramDto)
    {
        log.debug("Request to save Param : {}", paramDto);
        Param param = this.mapper.fromDtoToParam(paramDto);
        return paramRepository.save(param);
    }

    /**
     * Partially update a Job System
     * @param paramDto to transform into entity to update partially.
     * @return the persisted entity
     */
    public Optional<Param> partialUpdate(ParamDto paramDto)
    {
        log.debug("Request to partially update Param : {}", paramDto);
        return paramRepository
                .findById(paramDto.getId())
                .map(existingParam -> {
                    if(paramDto.getParamDat() != null)
                        existingParam.setParamDat(paramDto.getParamDat());
                    if(paramDto.getParamApp() != null)
                        existingParam.setParamApp(paramDto.getParamApp());
                    if(paramDto.getParamDoc() != null)
                        existingParam.setParamDat(paramDto.getParamDoc());
                    return existingParam;
                }).map(paramRepository::save);
    }

    /**
     * Get all the Job Systems
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Param> findAll()
    {
        log.debug("Request to get all Params");
        return paramRepository.findAll();
    }

    /**
     * Get one Job System by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public ParamDto findOne(Long id)
    {
        log.debug("Request to get Param : {}", id);
        Optional<Param> param = paramRepository.findById(id);
        ParamDto paramDto = null;
        if(param.isPresent())
            paramDto = this.mapper.fromParamToDto(param.get());
        return paramDto;
    }


    /**
     * Delete the Job System by id.
     * @param id the id of the entity.
     */
    public void delete(Long id)
    {
        log.debug("Request to delete Param : {}", id);
        paramRepository.deleteById(id);
    }



}
