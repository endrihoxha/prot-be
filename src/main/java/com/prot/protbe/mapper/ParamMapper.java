package com.prot.protbe.mapper;

import com.prot.protbe.domain.Param;
import com.prot.protbe.dto.ParamDto;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class ParamMapper implements Serializable {

    private static final long serialVersionUID = 1L;


    public Param fromDtoToParam(ParamDto paramDto){
        Param param = new Param();
        param.setParamApp(paramDto.getParamApp());
        param.setParamDat(paramDto.getParamDat());
        param.setParamDoc(paramDto.getParamDoc());
        return param;
    }

    public ParamDto fromParamToDto(Param param){
        ParamDto paramDto = new ParamDto();
        paramDto.setParamApp(param.getParamApp());
        paramDto.setParamDat(param.getParamDat());
        paramDto.setParamDoc(param.getParamDoc());
        return paramDto;
    }

}
