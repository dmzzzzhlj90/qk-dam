/*
 * Dolphin Scheduler Api Docs
 * Dolphin Scheduler Api Docs
 *
 * The version of the OpenAPI document: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.qk.datacenter.api;

import com.qk.datacenter.client.ApiClient;
import com.qk.datacenter.client.ApiException;
import com.qk.datacenter.client.ApiResponse;
import com.qk.datacenter.client.Pair;

import com.qk.datacenter.model.Result;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.function.Consumer;

import java.util.ArrayList;
import java.util.StringJoiner;
import java.util.List;
import java.util.Map;
import java.util.Set;

@javax.annotation.processing.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-12-18T14:27:46.433909+08:00[Asia/Shanghai]")
public class ProcessTaskRelationTagApi {
  private final HttpClient memberVarHttpClient;
  private final ObjectMapper memberVarObjectMapper;
  private final String memberVarBaseUri;
  private final Consumer<HttpRequest.Builder> memberVarInterceptor;
  private final Duration memberVarReadTimeout;
  private final Consumer<HttpResponse<InputStream>> memberVarResponseInterceptor;
  private final Consumer<HttpResponse<String>> memberVarAsyncResponseInterceptor;

  public ProcessTaskRelationTagApi() {
    this(new ApiClient());
  }

  public ProcessTaskRelationTagApi(ApiClient apiClient) {
    memberVarHttpClient = apiClient.getHttpClient();
    memberVarObjectMapper = apiClient.getObjectMapper();
    memberVarBaseUri = apiClient.getBaseUri();
    memberVarInterceptor = apiClient.getRequestInterceptor();
    memberVarReadTimeout = apiClient.getReadTimeout();
    memberVarResponseInterceptor = apiClient.getResponseInterceptor();
    memberVarAsyncResponseInterceptor = apiClient.getAsyncResponseInterceptor();
  }

  protected ApiException getApiException(String operationId, HttpResponse<InputStream> response) throws IOException {
    String body = response.body() == null ? null : new String(response.body().readAllBytes());
    String message = formatExceptionMessage(operationId, response.statusCode(), body);
    return new ApiException(response.statusCode(), message, response.headers(), body);
  }

  private String formatExceptionMessage(String operationId, int statusCode, String body) {
    if (body == null || body.isEmpty()) {
      body = "[no body]";
    }
    return operationId + " call failed with: " + statusCode + " - " + body;
  }

  /**
   * save
   * CREATE_PROCESS_TASK_RELATION_NOTES
   * @param postTaskCode POST_TASK_CODE (required)
   * @param preTaskCode PRE_TASK_CODE (required)
   * @param processDefinitionCode ?????????????????? (required)
   * @param projectCode PROJECT_CODE (required)
   * @return Result
   * @throws ApiException if fails to make API call
   */
  public Result createProcessTaskRelationUsingPOST(String postTaskCode, String preTaskCode, String processDefinitionCode, String projectCode) throws ApiException {
    ApiResponse<Result> localVarResponse = createProcessTaskRelationUsingPOSTWithHttpInfo(postTaskCode, preTaskCode, processDefinitionCode, projectCode);
    return localVarResponse.getData();
  }

  /**
   * save
   * CREATE_PROCESS_TASK_RELATION_NOTES
   * @param postTaskCode POST_TASK_CODE (required)
   * @param preTaskCode PRE_TASK_CODE (required)
   * @param processDefinitionCode ?????????????????? (required)
   * @param projectCode PROJECT_CODE (required)
   * @return ApiResponse&lt;Result&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Result> createProcessTaskRelationUsingPOSTWithHttpInfo(String postTaskCode, String preTaskCode, String processDefinitionCode, String projectCode) throws ApiException {
    HttpRequest.Builder localVarRequestBuilder = createProcessTaskRelationUsingPOSTRequestBuilder(postTaskCode, preTaskCode, processDefinitionCode, projectCode);
    try {
      HttpResponse<InputStream> localVarResponse = memberVarHttpClient.send(
          localVarRequestBuilder.build(),
          HttpResponse.BodyHandlers.ofInputStream());
      if (memberVarResponseInterceptor != null) {
        memberVarResponseInterceptor.accept(localVarResponse);
      }
      if (localVarResponse.statusCode()/ 100 != 2) {
        throw getApiException("createProcessTaskRelationUsingPOST", localVarResponse);
      }
      return new ApiResponse<Result>(
          localVarResponse.statusCode(),
          localVarResponse.headers().map(),
          memberVarObjectMapper.readValue(localVarResponse.body(), new TypeReference<Result>() {})
        );
    } catch (IOException e) {
      throw new ApiException(e);
    }
    catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new ApiException(e);
    }
  }

  private HttpRequest.Builder createProcessTaskRelationUsingPOSTRequestBuilder(String postTaskCode, String preTaskCode, String processDefinitionCode, String projectCode) throws ApiException {
    // verify the required parameter 'postTaskCode' is set
    if (postTaskCode == null) {
      throw new ApiException(400, "Missing the required parameter 'postTaskCode' when calling createProcessTaskRelationUsingPOST");
    }
    // verify the required parameter 'preTaskCode' is set
    if (preTaskCode == null) {
      throw new ApiException(400, "Missing the required parameter 'preTaskCode' when calling createProcessTaskRelationUsingPOST");
    }
    // verify the required parameter 'processDefinitionCode' is set
    if (processDefinitionCode == null) {
      throw new ApiException(400, "Missing the required parameter 'processDefinitionCode' when calling createProcessTaskRelationUsingPOST");
    }
    // verify the required parameter 'projectCode' is set
    if (projectCode == null) {
      throw new ApiException(400, "Missing the required parameter 'projectCode' when calling createProcessTaskRelationUsingPOST");
    }

    HttpRequest.Builder localVarRequestBuilder = HttpRequest.newBuilder();

    String localVarPath = "/projects/{projectCode}/process-task-relation"
        .replace("{projectCode}", ApiClient.urlEncode(projectCode.toString()));

    List<Pair> localVarQueryParams = new ArrayList<>();
    localVarQueryParams.addAll(ApiClient.parameterToPairs("postTaskCode", postTaskCode));
    localVarQueryParams.addAll(ApiClient.parameterToPairs("preTaskCode", preTaskCode));
    localVarQueryParams.addAll(ApiClient.parameterToPairs("processDefinitionCode", processDefinitionCode));

    if (!localVarQueryParams.isEmpty()) {
      StringJoiner queryJoiner = new StringJoiner("&");
      localVarQueryParams.forEach(p -> queryJoiner.add(p.getName() + '=' + p.getValue()));
      localVarRequestBuilder.uri(URI.create(memberVarBaseUri + localVarPath + '?' + queryJoiner.toString()));
    } else {
      localVarRequestBuilder.uri(URI.create(memberVarBaseUri + localVarPath));
    }

    localVarRequestBuilder.header("Accept", "application/json");

    localVarRequestBuilder.method("POST", HttpRequest.BodyPublishers.noBody());
    if (memberVarReadTimeout != null) {
      localVarRequestBuilder.timeout(memberVarReadTimeout);
    }
    if (memberVarInterceptor != null) {
      memberVarInterceptor.accept(localVarRequestBuilder);
    }
    return localVarRequestBuilder;
  }
  /**
   * deleteDownstreamRelation
   * DELETE_DOWNSTREAM_RELATION_NOTES
   * @param postTaskCodes POST_TASK_CODES (required)
   * @param projectCode PROJECT_CODE (required)
   * @param taskCode TASK_CODE (required)
   * @return Result
   * @throws ApiException if fails to make API call
   */
  public Result deleteDownstreamRelationUsingDELETE(String postTaskCodes, String projectCode, String taskCode) throws ApiException {
    ApiResponse<Result> localVarResponse = deleteDownstreamRelationUsingDELETEWithHttpInfo(postTaskCodes, projectCode, taskCode);
    return localVarResponse.getData();
  }

  /**
   * deleteDownstreamRelation
   * DELETE_DOWNSTREAM_RELATION_NOTES
   * @param postTaskCodes POST_TASK_CODES (required)
   * @param projectCode PROJECT_CODE (required)
   * @param taskCode TASK_CODE (required)
   * @return ApiResponse&lt;Result&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Result> deleteDownstreamRelationUsingDELETEWithHttpInfo(String postTaskCodes, String projectCode, String taskCode) throws ApiException {
    HttpRequest.Builder localVarRequestBuilder = deleteDownstreamRelationUsingDELETERequestBuilder(postTaskCodes, projectCode, taskCode);
    try {
      HttpResponse<InputStream> localVarResponse = memberVarHttpClient.send(
          localVarRequestBuilder.build(),
          HttpResponse.BodyHandlers.ofInputStream());
      if (memberVarResponseInterceptor != null) {
        memberVarResponseInterceptor.accept(localVarResponse);
      }
      if (localVarResponse.statusCode()/ 100 != 2) {
        throw getApiException("deleteDownstreamRelationUsingDELETE", localVarResponse);
      }
      return new ApiResponse<Result>(
          localVarResponse.statusCode(),
          localVarResponse.headers().map(),
          memberVarObjectMapper.readValue(localVarResponse.body(), new TypeReference<Result>() {})
        );
    } catch (IOException e) {
      throw new ApiException(e);
    }
    catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new ApiException(e);
    }
  }

  private HttpRequest.Builder deleteDownstreamRelationUsingDELETERequestBuilder(String postTaskCodes, String projectCode, String taskCode) throws ApiException {
    // verify the required parameter 'postTaskCodes' is set
    if (postTaskCodes == null) {
      throw new ApiException(400, "Missing the required parameter 'postTaskCodes' when calling deleteDownstreamRelationUsingDELETE");
    }
    // verify the required parameter 'projectCode' is set
    if (projectCode == null) {
      throw new ApiException(400, "Missing the required parameter 'projectCode' when calling deleteDownstreamRelationUsingDELETE");
    }
    // verify the required parameter 'taskCode' is set
    if (taskCode == null) {
      throw new ApiException(400, "Missing the required parameter 'taskCode' when calling deleteDownstreamRelationUsingDELETE");
    }

    HttpRequest.Builder localVarRequestBuilder = HttpRequest.newBuilder();

    String localVarPath = "/projects/{projectCode}/process-task-relation/{taskCode}/downstream"
        .replace("{projectCode}", ApiClient.urlEncode(projectCode.toString()))
        .replace("{taskCode}", ApiClient.urlEncode(taskCode.toString()));

    List<Pair> localVarQueryParams = new ArrayList<>();
    localVarQueryParams.addAll(ApiClient.parameterToPairs("postTaskCodes", postTaskCodes));

    if (!localVarQueryParams.isEmpty()) {
      StringJoiner queryJoiner = new StringJoiner("&");
      localVarQueryParams.forEach(p -> queryJoiner.add(p.getName() + '=' + p.getValue()));
      localVarRequestBuilder.uri(URI.create(memberVarBaseUri + localVarPath + '?' + queryJoiner.toString()));
    } else {
      localVarRequestBuilder.uri(URI.create(memberVarBaseUri + localVarPath));
    }

    localVarRequestBuilder.header("Accept", "application/json");

    localVarRequestBuilder.method("DELETE", HttpRequest.BodyPublishers.noBody());
    if (memberVarReadTimeout != null) {
      localVarRequestBuilder.timeout(memberVarReadTimeout);
    }
    if (memberVarInterceptor != null) {
      memberVarInterceptor.accept(localVarRequestBuilder);
    }
    return localVarRequestBuilder;
  }
  /**
   * deleteEdge
   * DELETE_EDGE_NOTES
   * @param postTaskCode POST_TASK_CODE (required)
   * @param preTaskCode PRE_TASK_CODE (required)
   * @param processDefinitionCode ?????????????????? (required)
   * @param projectCode PROJECT_CODE (required)
   * @return Result
   * @throws ApiException if fails to make API call
   */
  public Result deleteEdgeUsingDELETE(String postTaskCode, String preTaskCode, String processDefinitionCode, String projectCode) throws ApiException {
    ApiResponse<Result> localVarResponse = deleteEdgeUsingDELETEWithHttpInfo(postTaskCode, preTaskCode, processDefinitionCode, projectCode);
    return localVarResponse.getData();
  }

  /**
   * deleteEdge
   * DELETE_EDGE_NOTES
   * @param postTaskCode POST_TASK_CODE (required)
   * @param preTaskCode PRE_TASK_CODE (required)
   * @param processDefinitionCode ?????????????????? (required)
   * @param projectCode PROJECT_CODE (required)
   * @return ApiResponse&lt;Result&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Result> deleteEdgeUsingDELETEWithHttpInfo(String postTaskCode, String preTaskCode, String processDefinitionCode, String projectCode) throws ApiException {
    HttpRequest.Builder localVarRequestBuilder = deleteEdgeUsingDELETERequestBuilder(postTaskCode, preTaskCode, processDefinitionCode, projectCode);
    try {
      HttpResponse<InputStream> localVarResponse = memberVarHttpClient.send(
          localVarRequestBuilder.build(),
          HttpResponse.BodyHandlers.ofInputStream());
      if (memberVarResponseInterceptor != null) {
        memberVarResponseInterceptor.accept(localVarResponse);
      }
      if (localVarResponse.statusCode()/ 100 != 2) {
        throw getApiException("deleteEdgeUsingDELETE", localVarResponse);
      }
      return new ApiResponse<Result>(
          localVarResponse.statusCode(),
          localVarResponse.headers().map(),
          memberVarObjectMapper.readValue(localVarResponse.body(), new TypeReference<Result>() {})
        );
    } catch (IOException e) {
      throw new ApiException(e);
    }
    catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new ApiException(e);
    }
  }

  private HttpRequest.Builder deleteEdgeUsingDELETERequestBuilder(String postTaskCode, String preTaskCode, String processDefinitionCode, String projectCode) throws ApiException {
    // verify the required parameter 'postTaskCode' is set
    if (postTaskCode == null) {
      throw new ApiException(400, "Missing the required parameter 'postTaskCode' when calling deleteEdgeUsingDELETE");
    }
    // verify the required parameter 'preTaskCode' is set
    if (preTaskCode == null) {
      throw new ApiException(400, "Missing the required parameter 'preTaskCode' when calling deleteEdgeUsingDELETE");
    }
    // verify the required parameter 'processDefinitionCode' is set
    if (processDefinitionCode == null) {
      throw new ApiException(400, "Missing the required parameter 'processDefinitionCode' when calling deleteEdgeUsingDELETE");
    }
    // verify the required parameter 'projectCode' is set
    if (projectCode == null) {
      throw new ApiException(400, "Missing the required parameter 'projectCode' when calling deleteEdgeUsingDELETE");
    }

    HttpRequest.Builder localVarRequestBuilder = HttpRequest.newBuilder();

    String localVarPath = "/projects/{projectCode}/process-task-relation/{processDefinitionCode}/{preTaskCode}/{postTaskCode}"
        .replace("{postTaskCode}", ApiClient.urlEncode(postTaskCode.toString()))
        .replace("{preTaskCode}", ApiClient.urlEncode(preTaskCode.toString()))
        .replace("{processDefinitionCode}", ApiClient.urlEncode(processDefinitionCode.toString()))
        .replace("{projectCode}", ApiClient.urlEncode(projectCode.toString()));

    localVarRequestBuilder.uri(URI.create(memberVarBaseUri + localVarPath));

    localVarRequestBuilder.header("Accept", "application/json");

    localVarRequestBuilder.method("DELETE", HttpRequest.BodyPublishers.noBody());
    if (memberVarReadTimeout != null) {
      localVarRequestBuilder.timeout(memberVarReadTimeout);
    }
    if (memberVarInterceptor != null) {
      memberVarInterceptor.accept(localVarRequestBuilder);
    }
    return localVarRequestBuilder;
  }
  /**
   * deleteRelation
   * DELETE_PROCESS_TASK_RELATION_NOTES
   * @param processDefinitionCode ?????????????????? (required)
   * @param projectCode PROJECT_CODE (required)
   * @param taskCode TASK_CODE (required)
   * @return Result
   * @throws ApiException if fails to make API call
   */
  public Result deleteTaskProcessRelationUsingDELETE(String processDefinitionCode, String projectCode, String taskCode) throws ApiException {
    ApiResponse<Result> localVarResponse = deleteTaskProcessRelationUsingDELETEWithHttpInfo(processDefinitionCode, projectCode, taskCode);
    return localVarResponse.getData();
  }

  /**
   * deleteRelation
   * DELETE_PROCESS_TASK_RELATION_NOTES
   * @param processDefinitionCode ?????????????????? (required)
   * @param projectCode PROJECT_CODE (required)
   * @param taskCode TASK_CODE (required)
   * @return ApiResponse&lt;Result&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Result> deleteTaskProcessRelationUsingDELETEWithHttpInfo(String processDefinitionCode, String projectCode, String taskCode) throws ApiException {
    HttpRequest.Builder localVarRequestBuilder = deleteTaskProcessRelationUsingDELETERequestBuilder(processDefinitionCode, projectCode, taskCode);
    try {
      HttpResponse<InputStream> localVarResponse = memberVarHttpClient.send(
          localVarRequestBuilder.build(),
          HttpResponse.BodyHandlers.ofInputStream());
      if (memberVarResponseInterceptor != null) {
        memberVarResponseInterceptor.accept(localVarResponse);
      }
      if (localVarResponse.statusCode()/ 100 != 2) {
        throw getApiException("deleteTaskProcessRelationUsingDELETE", localVarResponse);
      }
      return new ApiResponse<Result>(
          localVarResponse.statusCode(),
          localVarResponse.headers().map(),
          memberVarObjectMapper.readValue(localVarResponse.body(), new TypeReference<Result>() {})
        );
    } catch (IOException e) {
      throw new ApiException(e);
    }
    catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new ApiException(e);
    }
  }

  private HttpRequest.Builder deleteTaskProcessRelationUsingDELETERequestBuilder(String processDefinitionCode, String projectCode, String taskCode) throws ApiException {
    // verify the required parameter 'processDefinitionCode' is set
    if (processDefinitionCode == null) {
      throw new ApiException(400, "Missing the required parameter 'processDefinitionCode' when calling deleteTaskProcessRelationUsingDELETE");
    }
    // verify the required parameter 'projectCode' is set
    if (projectCode == null) {
      throw new ApiException(400, "Missing the required parameter 'projectCode' when calling deleteTaskProcessRelationUsingDELETE");
    }
    // verify the required parameter 'taskCode' is set
    if (taskCode == null) {
      throw new ApiException(400, "Missing the required parameter 'taskCode' when calling deleteTaskProcessRelationUsingDELETE");
    }

    HttpRequest.Builder localVarRequestBuilder = HttpRequest.newBuilder();

    String localVarPath = "/projects/{projectCode}/process-task-relation/{taskCode}"
        .replace("{projectCode}", ApiClient.urlEncode(projectCode.toString()))
        .replace("{taskCode}", ApiClient.urlEncode(taskCode.toString()));

    List<Pair> localVarQueryParams = new ArrayList<>();
    localVarQueryParams.addAll(ApiClient.parameterToPairs("processDefinitionCode", processDefinitionCode));

    if (!localVarQueryParams.isEmpty()) {
      StringJoiner queryJoiner = new StringJoiner("&");
      localVarQueryParams.forEach(p -> queryJoiner.add(p.getName() + '=' + p.getValue()));
      localVarRequestBuilder.uri(URI.create(memberVarBaseUri + localVarPath + '?' + queryJoiner.toString()));
    } else {
      localVarRequestBuilder.uri(URI.create(memberVarBaseUri + localVarPath));
    }

    localVarRequestBuilder.header("Accept", "application/json");

    localVarRequestBuilder.method("DELETE", HttpRequest.BodyPublishers.noBody());
    if (memberVarReadTimeout != null) {
      localVarRequestBuilder.timeout(memberVarReadTimeout);
    }
    if (memberVarInterceptor != null) {
      memberVarInterceptor.accept(localVarRequestBuilder);
    }
    return localVarRequestBuilder;
  }
  /**
   * deleteUpstreamRelation
   * DELETE_UPSTREAM_RELATION_NOTES
   * @param preTaskCodes PRE_TASK_CODES (required)
   * @param projectCode PROJECT_CODE (required)
   * @param taskCode TASK_CODE (required)
   * @return Result
   * @throws ApiException if fails to make API call
   */
  public Result deleteUpstreamRelationUsingDELETE(String preTaskCodes, String projectCode, String taskCode) throws ApiException {
    ApiResponse<Result> localVarResponse = deleteUpstreamRelationUsingDELETEWithHttpInfo(preTaskCodes, projectCode, taskCode);
    return localVarResponse.getData();
  }

  /**
   * deleteUpstreamRelation
   * DELETE_UPSTREAM_RELATION_NOTES
   * @param preTaskCodes PRE_TASK_CODES (required)
   * @param projectCode PROJECT_CODE (required)
   * @param taskCode TASK_CODE (required)
   * @return ApiResponse&lt;Result&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Result> deleteUpstreamRelationUsingDELETEWithHttpInfo(String preTaskCodes, String projectCode, String taskCode) throws ApiException {
    HttpRequest.Builder localVarRequestBuilder = deleteUpstreamRelationUsingDELETERequestBuilder(preTaskCodes, projectCode, taskCode);
    try {
      HttpResponse<InputStream> localVarResponse = memberVarHttpClient.send(
          localVarRequestBuilder.build(),
          HttpResponse.BodyHandlers.ofInputStream());
      if (memberVarResponseInterceptor != null) {
        memberVarResponseInterceptor.accept(localVarResponse);
      }
      if (localVarResponse.statusCode()/ 100 != 2) {
        throw getApiException("deleteUpstreamRelationUsingDELETE", localVarResponse);
      }
      return new ApiResponse<Result>(
          localVarResponse.statusCode(),
          localVarResponse.headers().map(),
          memberVarObjectMapper.readValue(localVarResponse.body(), new TypeReference<Result>() {})
        );
    } catch (IOException e) {
      throw new ApiException(e);
    }
    catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new ApiException(e);
    }
  }

  private HttpRequest.Builder deleteUpstreamRelationUsingDELETERequestBuilder(String preTaskCodes, String projectCode, String taskCode) throws ApiException {
    // verify the required parameter 'preTaskCodes' is set
    if (preTaskCodes == null) {
      throw new ApiException(400, "Missing the required parameter 'preTaskCodes' when calling deleteUpstreamRelationUsingDELETE");
    }
    // verify the required parameter 'projectCode' is set
    if (projectCode == null) {
      throw new ApiException(400, "Missing the required parameter 'projectCode' when calling deleteUpstreamRelationUsingDELETE");
    }
    // verify the required parameter 'taskCode' is set
    if (taskCode == null) {
      throw new ApiException(400, "Missing the required parameter 'taskCode' when calling deleteUpstreamRelationUsingDELETE");
    }

    HttpRequest.Builder localVarRequestBuilder = HttpRequest.newBuilder();

    String localVarPath = "/projects/{projectCode}/process-task-relation/{taskCode}/upstream"
        .replace("{projectCode}", ApiClient.urlEncode(projectCode.toString()))
        .replace("{taskCode}", ApiClient.urlEncode(taskCode.toString()));

    List<Pair> localVarQueryParams = new ArrayList<>();
    localVarQueryParams.addAll(ApiClient.parameterToPairs("preTaskCodes", preTaskCodes));

    if (!localVarQueryParams.isEmpty()) {
      StringJoiner queryJoiner = new StringJoiner("&");
      localVarQueryParams.forEach(p -> queryJoiner.add(p.getName() + '=' + p.getValue()));
      localVarRequestBuilder.uri(URI.create(memberVarBaseUri + localVarPath + '?' + queryJoiner.toString()));
    } else {
      localVarRequestBuilder.uri(URI.create(memberVarBaseUri + localVarPath));
    }

    localVarRequestBuilder.header("Accept", "application/json");

    localVarRequestBuilder.method("DELETE", HttpRequest.BodyPublishers.noBody());
    if (memberVarReadTimeout != null) {
      localVarRequestBuilder.timeout(memberVarReadTimeout);
    }
    if (memberVarInterceptor != null) {
      memberVarInterceptor.accept(localVarRequestBuilder);
    }
    return localVarRequestBuilder;
  }
  /**
   * moveRelation
   * MOVE_TASK_TO_OTHER_PROCESS_DEFINITION_NOTES
   * @param processDefinitionCode ?????????????????? (required)
   * @param projectCode PROJECT_CODE (required)
   * @param targetProcessDefinitionCode TARGET_PROCESS_DEFINITION_CODE (required)
   * @param taskCode TASK_CODE (required)
   * @return Result
   * @throws ApiException if fails to make API call
   */
  public Result moveTaskProcessRelationUsingPOST(String processDefinitionCode, String projectCode, String targetProcessDefinitionCode, String taskCode) throws ApiException {
    ApiResponse<Result> localVarResponse = moveTaskProcessRelationUsingPOSTWithHttpInfo(processDefinitionCode, projectCode, targetProcessDefinitionCode, taskCode);
    return localVarResponse.getData();
  }

  /**
   * moveRelation
   * MOVE_TASK_TO_OTHER_PROCESS_DEFINITION_NOTES
   * @param processDefinitionCode ?????????????????? (required)
   * @param projectCode PROJECT_CODE (required)
   * @param targetProcessDefinitionCode TARGET_PROCESS_DEFINITION_CODE (required)
   * @param taskCode TASK_CODE (required)
   * @return ApiResponse&lt;Result&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Result> moveTaskProcessRelationUsingPOSTWithHttpInfo(String processDefinitionCode, String projectCode, String targetProcessDefinitionCode, String taskCode) throws ApiException {
    HttpRequest.Builder localVarRequestBuilder = moveTaskProcessRelationUsingPOSTRequestBuilder(processDefinitionCode, projectCode, targetProcessDefinitionCode, taskCode);
    try {
      HttpResponse<InputStream> localVarResponse = memberVarHttpClient.send(
          localVarRequestBuilder.build(),
          HttpResponse.BodyHandlers.ofInputStream());
      if (memberVarResponseInterceptor != null) {
        memberVarResponseInterceptor.accept(localVarResponse);
      }
      if (localVarResponse.statusCode()/ 100 != 2) {
        throw getApiException("moveTaskProcessRelationUsingPOST", localVarResponse);
      }
      return new ApiResponse<Result>(
          localVarResponse.statusCode(),
          localVarResponse.headers().map(),
          memberVarObjectMapper.readValue(localVarResponse.body(), new TypeReference<Result>() {})
        );
    } catch (IOException e) {
      throw new ApiException(e);
    }
    catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new ApiException(e);
    }
  }

  private HttpRequest.Builder moveTaskProcessRelationUsingPOSTRequestBuilder(String processDefinitionCode, String projectCode, String targetProcessDefinitionCode, String taskCode) throws ApiException {
    // verify the required parameter 'processDefinitionCode' is set
    if (processDefinitionCode == null) {
      throw new ApiException(400, "Missing the required parameter 'processDefinitionCode' when calling moveTaskProcessRelationUsingPOST");
    }
    // verify the required parameter 'projectCode' is set
    if (projectCode == null) {
      throw new ApiException(400, "Missing the required parameter 'projectCode' when calling moveTaskProcessRelationUsingPOST");
    }
    // verify the required parameter 'targetProcessDefinitionCode' is set
    if (targetProcessDefinitionCode == null) {
      throw new ApiException(400, "Missing the required parameter 'targetProcessDefinitionCode' when calling moveTaskProcessRelationUsingPOST");
    }
    // verify the required parameter 'taskCode' is set
    if (taskCode == null) {
      throw new ApiException(400, "Missing the required parameter 'taskCode' when calling moveTaskProcessRelationUsingPOST");
    }

    HttpRequest.Builder localVarRequestBuilder = HttpRequest.newBuilder();

    String localVarPath = "/projects/{projectCode}/process-task-relation/move"
        .replace("{projectCode}", ApiClient.urlEncode(projectCode.toString()));

    List<Pair> localVarQueryParams = new ArrayList<>();
    localVarQueryParams.addAll(ApiClient.parameterToPairs("processDefinitionCode", processDefinitionCode));
    localVarQueryParams.addAll(ApiClient.parameterToPairs("targetProcessDefinitionCode", targetProcessDefinitionCode));
    localVarQueryParams.addAll(ApiClient.parameterToPairs("taskCode", taskCode));

    if (!localVarQueryParams.isEmpty()) {
      StringJoiner queryJoiner = new StringJoiner("&");
      localVarQueryParams.forEach(p -> queryJoiner.add(p.getName() + '=' + p.getValue()));
      localVarRequestBuilder.uri(URI.create(memberVarBaseUri + localVarPath + '?' + queryJoiner.toString()));
    } else {
      localVarRequestBuilder.uri(URI.create(memberVarBaseUri + localVarPath));
    }

    localVarRequestBuilder.header("Accept", "application/json");

    localVarRequestBuilder.method("POST", HttpRequest.BodyPublishers.noBody());
    if (memberVarReadTimeout != null) {
      localVarRequestBuilder.timeout(memberVarReadTimeout);
    }
    if (memberVarInterceptor != null) {
      memberVarInterceptor.accept(localVarRequestBuilder);
    }
    return localVarRequestBuilder;
  }
  /**
   * queryDownstreamRelation
   * QUERY_DOWNSTREAM_RELATION_NOTES
   * @param projectCode PROJECT_CODE (required)
   * @param taskCode TASK_CODE (required)
   * @return Result
   * @throws ApiException if fails to make API call
   */
  public Result queryDownstreamRelationUsingGET(String projectCode, String taskCode) throws ApiException {
    ApiResponse<Result> localVarResponse = queryDownstreamRelationUsingGETWithHttpInfo(projectCode, taskCode);
    return localVarResponse.getData();
  }

  /**
   * queryDownstreamRelation
   * QUERY_DOWNSTREAM_RELATION_NOTES
   * @param projectCode PROJECT_CODE (required)
   * @param taskCode TASK_CODE (required)
   * @return ApiResponse&lt;Result&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Result> queryDownstreamRelationUsingGETWithHttpInfo(String projectCode, String taskCode) throws ApiException {
    HttpRequest.Builder localVarRequestBuilder = queryDownstreamRelationUsingGETRequestBuilder(projectCode, taskCode);
    try {
      HttpResponse<InputStream> localVarResponse = memberVarHttpClient.send(
          localVarRequestBuilder.build(),
          HttpResponse.BodyHandlers.ofInputStream());
      if (memberVarResponseInterceptor != null) {
        memberVarResponseInterceptor.accept(localVarResponse);
      }
      if (localVarResponse.statusCode()/ 100 != 2) {
        throw getApiException("queryDownstreamRelationUsingGET", localVarResponse);
      }
      return new ApiResponse<Result>(
          localVarResponse.statusCode(),
          localVarResponse.headers().map(),
          memberVarObjectMapper.readValue(localVarResponse.body(), new TypeReference<Result>() {})
        );
    } catch (IOException e) {
      throw new ApiException(e);
    }
    catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new ApiException(e);
    }
  }

  private HttpRequest.Builder queryDownstreamRelationUsingGETRequestBuilder(String projectCode, String taskCode) throws ApiException {
    // verify the required parameter 'projectCode' is set
    if (projectCode == null) {
      throw new ApiException(400, "Missing the required parameter 'projectCode' when calling queryDownstreamRelationUsingGET");
    }
    // verify the required parameter 'taskCode' is set
    if (taskCode == null) {
      throw new ApiException(400, "Missing the required parameter 'taskCode' when calling queryDownstreamRelationUsingGET");
    }

    HttpRequest.Builder localVarRequestBuilder = HttpRequest.newBuilder();

    String localVarPath = "/projects/{projectCode}/process-task-relation/{taskCode}/downstream"
        .replace("{projectCode}", ApiClient.urlEncode(projectCode.toString()))
        .replace("{taskCode}", ApiClient.urlEncode(taskCode.toString()));

    localVarRequestBuilder.uri(URI.create(memberVarBaseUri + localVarPath));

    localVarRequestBuilder.header("Accept", "application/json");

    localVarRequestBuilder.method("GET", HttpRequest.BodyPublishers.noBody());
    if (memberVarReadTimeout != null) {
      localVarRequestBuilder.timeout(memberVarReadTimeout);
    }
    if (memberVarInterceptor != null) {
      memberVarInterceptor.accept(localVarRequestBuilder);
    }
    return localVarRequestBuilder;
  }
  /**
   * queryUpstreamRelation
   * QUERY_UPSTREAM_RELATION_NOTES
   * @param projectCode PROJECT_CODE (required)
   * @param taskCode TASK_CODE (required)
   * @return Result
   * @throws ApiException if fails to make API call
   */
  public Result queryUpstreamRelationUsingGET(String projectCode, String taskCode) throws ApiException {
    ApiResponse<Result> localVarResponse = queryUpstreamRelationUsingGETWithHttpInfo(projectCode, taskCode);
    return localVarResponse.getData();
  }

  /**
   * queryUpstreamRelation
   * QUERY_UPSTREAM_RELATION_NOTES
   * @param projectCode PROJECT_CODE (required)
   * @param taskCode TASK_CODE (required)
   * @return ApiResponse&lt;Result&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Result> queryUpstreamRelationUsingGETWithHttpInfo(String projectCode, String taskCode) throws ApiException {
    HttpRequest.Builder localVarRequestBuilder = queryUpstreamRelationUsingGETRequestBuilder(projectCode, taskCode);
    try {
      HttpResponse<InputStream> localVarResponse = memberVarHttpClient.send(
          localVarRequestBuilder.build(),
          HttpResponse.BodyHandlers.ofInputStream());
      if (memberVarResponseInterceptor != null) {
        memberVarResponseInterceptor.accept(localVarResponse);
      }
      if (localVarResponse.statusCode()/ 100 != 2) {
        throw getApiException("queryUpstreamRelationUsingGET", localVarResponse);
      }
      return new ApiResponse<Result>(
          localVarResponse.statusCode(),
          localVarResponse.headers().map(),
          memberVarObjectMapper.readValue(localVarResponse.body(), new TypeReference<Result>() {})
        );
    } catch (IOException e) {
      throw new ApiException(e);
    }
    catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new ApiException(e);
    }
  }

  private HttpRequest.Builder queryUpstreamRelationUsingGETRequestBuilder(String projectCode, String taskCode) throws ApiException {
    // verify the required parameter 'projectCode' is set
    if (projectCode == null) {
      throw new ApiException(400, "Missing the required parameter 'projectCode' when calling queryUpstreamRelationUsingGET");
    }
    // verify the required parameter 'taskCode' is set
    if (taskCode == null) {
      throw new ApiException(400, "Missing the required parameter 'taskCode' when calling queryUpstreamRelationUsingGET");
    }

    HttpRequest.Builder localVarRequestBuilder = HttpRequest.newBuilder();

    String localVarPath = "/projects/{projectCode}/process-task-relation/{taskCode}/upstream"
        .replace("{projectCode}", ApiClient.urlEncode(projectCode.toString()))
        .replace("{taskCode}", ApiClient.urlEncode(taskCode.toString()));

    localVarRequestBuilder.uri(URI.create(memberVarBaseUri + localVarPath));

    localVarRequestBuilder.header("Accept", "application/json");

    localVarRequestBuilder.method("GET", HttpRequest.BodyPublishers.noBody());
    if (memberVarReadTimeout != null) {
      localVarRequestBuilder.timeout(memberVarReadTimeout);
    }
    if (memberVarInterceptor != null) {
      memberVarInterceptor.accept(localVarRequestBuilder);
    }
    return localVarRequestBuilder;
  }
}
