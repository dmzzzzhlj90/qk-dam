package com.qk.dm.datamodel.rest;

import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.datamodel.service.ModelFactTableService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ModelFactTableController {

    private final ModelFactTableService modelFactTableService;

    public ModelFactTableController(ModelFactTableService modelFactTableService){
        this.modelFactTableService = modelFactTableService;
    }


}
