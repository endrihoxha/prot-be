package com.prot.protbe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParamDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String paramDoc;
    private String paramApp;
    private String paramDat;
}
