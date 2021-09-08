package com.qk.dam.openapi;

import org.openapi4j.parser.model.v3.Operation;
import org.openapi4j.parser.model.v3.Parameter;
import org.openapi4j.parser.model.v3.RequestBody;

import java.util.List;

public class OperationUtil {
    public static Operation operation(String operationId,String summary,List<Parameter> parameters) {
        new Operation()
                .setOperationId(operationId)
                .setSummary(summary)
                .setParameters(parameters);
        return new Operation();
    }
    public static Operation operation(String operationId, String summary, RequestBody requestBody) {
        new Operation()
                .setOperationId(operationId)
                .setSummary(summary)
                .setRequestBody(requestBody);
        return new Operation();
    }
}
