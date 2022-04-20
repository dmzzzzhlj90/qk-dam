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
public class WorkerApi {
  private final HttpClient memberVarHttpClient;
  private final ObjectMapper memberVarObjectMapper;
  private final String memberVarBaseUri;
  private final Consumer<HttpRequest.Builder> memberVarInterceptor;
  private final Duration memberVarReadTimeout;
  private final Consumer<HttpResponse<InputStream>> memberVarResponseInterceptor;
  private final Consumer<HttpResponse<String>> memberVarAsyncResponseInterceptor;

  public WorkerApi() {
    this(new ApiClient());
  }

  public WorkerApi(ApiClient apiClient) {
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
   * deleteById
   * 通过ID删除worker group
   * @param id Worker Server分组ID (required)
   * @return Result
   * @throws ApiException if fails to make API call
   */
  public Result deleteByIdUsingDELETE(Integer id) throws ApiException {
    ApiResponse<Result> localVarResponse = deleteByIdUsingDELETEWithHttpInfo(id);
    return localVarResponse.getData();
  }

  /**
   * deleteById
   * 通过ID删除worker group
   * @param id Worker Server分组ID (required)
   * @return ApiResponse&lt;Result&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Result> deleteByIdUsingDELETEWithHttpInfo(Integer id) throws ApiException {
    HttpRequest.Builder localVarRequestBuilder = deleteByIdUsingDELETERequestBuilder(id);
    try {
      HttpResponse<InputStream> localVarResponse = memberVarHttpClient.send(
          localVarRequestBuilder.build(),
          HttpResponse.BodyHandlers.ofInputStream());
      if (memberVarResponseInterceptor != null) {
        memberVarResponseInterceptor.accept(localVarResponse);
      }
      if (localVarResponse.statusCode()/ 100 != 2) {
        throw getApiException("deleteByIdUsingDELETE", localVarResponse);
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

  private HttpRequest.Builder deleteByIdUsingDELETERequestBuilder(Integer id) throws ApiException {
    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(400, "Missing the required parameter 'id' when calling deleteByIdUsingDELETE");
    }

    HttpRequest.Builder localVarRequestBuilder = HttpRequest.newBuilder();

    String localVarPath = "/worker-groups/{id}"
        .replace("{id}", ApiClient.urlEncode(id.toString()));

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
   * queryAllWorkerGroupsPaging
   * Worker分组管理
   * @param pageNo 页码号 (required)
   * @param pageSize 页大小 (required)
   * @param searchVal 搜索值 (optional)
   * @return Result
   * @throws ApiException if fails to make API call
   */
  public Result queryAllWorkerGroupsPagingUsingGET(Integer pageNo, Integer pageSize, String searchVal) throws ApiException {
    ApiResponse<Result> localVarResponse = queryAllWorkerGroupsPagingUsingGETWithHttpInfo(pageNo, pageSize, searchVal);
    return localVarResponse.getData();
  }

  /**
   * queryAllWorkerGroupsPaging
   * Worker分组管理
   * @param pageNo 页码号 (required)
   * @param pageSize 页大小 (required)
   * @param searchVal 搜索值 (optional)
   * @return ApiResponse&lt;Result&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Result> queryAllWorkerGroupsPagingUsingGETWithHttpInfo(Integer pageNo, Integer pageSize, String searchVal) throws ApiException {
    HttpRequest.Builder localVarRequestBuilder = queryAllWorkerGroupsPagingUsingGETRequestBuilder(pageNo, pageSize, searchVal);
    try {
      HttpResponse<InputStream> localVarResponse = memberVarHttpClient.send(
          localVarRequestBuilder.build(),
          HttpResponse.BodyHandlers.ofInputStream());
      if (memberVarResponseInterceptor != null) {
        memberVarResponseInterceptor.accept(localVarResponse);
      }
      if (localVarResponse.statusCode()/ 100 != 2) {
        throw getApiException("queryAllWorkerGroupsPagingUsingGET", localVarResponse);
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

  private HttpRequest.Builder queryAllWorkerGroupsPagingUsingGETRequestBuilder(Integer pageNo, Integer pageSize, String searchVal) throws ApiException {
    // verify the required parameter 'pageNo' is set
    if (pageNo == null) {
      throw new ApiException(400, "Missing the required parameter 'pageNo' when calling queryAllWorkerGroupsPagingUsingGET");
    }
    // verify the required parameter 'pageSize' is set
    if (pageSize == null) {
      throw new ApiException(400, "Missing the required parameter 'pageSize' when calling queryAllWorkerGroupsPagingUsingGET");
    }

    HttpRequest.Builder localVarRequestBuilder = HttpRequest.newBuilder();

    String localVarPath = "/worker-groups";

    List<Pair> localVarQueryParams = new ArrayList<>();
    localVarQueryParams.addAll(ApiClient.parameterToPairs("pageNo", pageNo));
    localVarQueryParams.addAll(ApiClient.parameterToPairs("pageSize", pageSize));
    localVarQueryParams.addAll(ApiClient.parameterToPairs("searchVal", searchVal));

    if (!localVarQueryParams.isEmpty()) {
      StringJoiner queryJoiner = new StringJoiner("&");
      localVarQueryParams.forEach(p -> queryJoiner.add(p.getName() + '=' + p.getValue()));
      localVarRequestBuilder.uri(URI.create(memberVarBaseUri + localVarPath + '?' + queryJoiner.toString()));
    } else {
      localVarRequestBuilder.uri(URI.create(memberVarBaseUri + localVarPath));
    }

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
   * queryAllWorkerGroups
   * 查询worker group分组
   * @return Result
   * @throws ApiException if fails to make API call
   */
  public Result queryAllWorkerGroupsUsingGET() throws ApiException {
    ApiResponse<Result> localVarResponse = queryAllWorkerGroupsUsingGETWithHttpInfo();
    return localVarResponse.getData();
  }

  /**
   * queryAllWorkerGroups
   * 查询worker group分组
   * @return ApiResponse&lt;Result&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Result> queryAllWorkerGroupsUsingGETWithHttpInfo() throws ApiException {
    HttpRequest.Builder localVarRequestBuilder = queryAllWorkerGroupsUsingGETRequestBuilder();
    try {
      HttpResponse<InputStream> localVarResponse = memberVarHttpClient.send(
          localVarRequestBuilder.build(),
          HttpResponse.BodyHandlers.ofInputStream());
      if (memberVarResponseInterceptor != null) {
        memberVarResponseInterceptor.accept(localVarResponse);
      }
      if (localVarResponse.statusCode()/ 100 != 2) {
        throw getApiException("queryAllWorkerGroupsUsingGET", localVarResponse);
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

  private HttpRequest.Builder queryAllWorkerGroupsUsingGETRequestBuilder() throws ApiException {

    HttpRequest.Builder localVarRequestBuilder = HttpRequest.newBuilder();

    String localVarPath = "/worker-groups/all";

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
   * queryWorkerAddressList
   * 查询worker地址列表
   * @return Result
   * @throws ApiException if fails to make API call
   */
  public Result queryWorkerAddressListUsingGET() throws ApiException {
    ApiResponse<Result> localVarResponse = queryWorkerAddressListUsingGETWithHttpInfo();
    return localVarResponse.getData();
  }

  /**
   * queryWorkerAddressList
   * 查询worker地址列表
   * @return ApiResponse&lt;Result&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Result> queryWorkerAddressListUsingGETWithHttpInfo() throws ApiException {
    HttpRequest.Builder localVarRequestBuilder = queryWorkerAddressListUsingGETRequestBuilder();
    try {
      HttpResponse<InputStream> localVarResponse = memberVarHttpClient.send(
          localVarRequestBuilder.build(),
          HttpResponse.BodyHandlers.ofInputStream());
      if (memberVarResponseInterceptor != null) {
        memberVarResponseInterceptor.accept(localVarResponse);
      }
      if (localVarResponse.statusCode()/ 100 != 2) {
        throw getApiException("queryWorkerAddressListUsingGET", localVarResponse);
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

  private HttpRequest.Builder queryWorkerAddressListUsingGETRequestBuilder() throws ApiException {

    HttpRequest.Builder localVarRequestBuilder = HttpRequest.newBuilder();

    String localVarPath = "/worker-groups/worker-address-list";

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
   * saveWorkerGroup
   * 创建Worker分组
   * @param addrList worker地址列表 (required)
   * @param name Worker分组名称 (required)
   * @param id Worker Server分组ID (optional, default to 0)
   * @return Result
   * @throws ApiException if fails to make API call
   */
  public Result saveWorkerGroupUsingPOST(String addrList, String name, Integer id) throws ApiException {
    ApiResponse<Result> localVarResponse = saveWorkerGroupUsingPOSTWithHttpInfo(addrList, name, id);
    return localVarResponse.getData();
  }

  /**
   * saveWorkerGroup
   * 创建Worker分组
   * @param addrList worker地址列表 (required)
   * @param name Worker分组名称 (required)
   * @param id Worker Server分组ID (optional, default to 0)
   * @return ApiResponse&lt;Result&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Result> saveWorkerGroupUsingPOSTWithHttpInfo(String addrList, String name, Integer id) throws ApiException {
    HttpRequest.Builder localVarRequestBuilder = saveWorkerGroupUsingPOSTRequestBuilder(addrList, name, id);
    try {
      HttpResponse<InputStream> localVarResponse = memberVarHttpClient.send(
          localVarRequestBuilder.build(),
          HttpResponse.BodyHandlers.ofInputStream());
      if (memberVarResponseInterceptor != null) {
        memberVarResponseInterceptor.accept(localVarResponse);
      }
      if (localVarResponse.statusCode()/ 100 != 2) {
        throw getApiException("saveWorkerGroupUsingPOST", localVarResponse);
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

  private HttpRequest.Builder saveWorkerGroupUsingPOSTRequestBuilder(String addrList, String name, Integer id) throws ApiException {
    // verify the required parameter 'addrList' is set
    if (addrList == null) {
      throw new ApiException(400, "Missing the required parameter 'addrList' when calling saveWorkerGroupUsingPOST");
    }
    // verify the required parameter 'name' is set
    if (name == null) {
      throw new ApiException(400, "Missing the required parameter 'name' when calling saveWorkerGroupUsingPOST");
    }

    HttpRequest.Builder localVarRequestBuilder = HttpRequest.newBuilder();

    String localVarPath = "/worker-groups";

    List<Pair> localVarQueryParams = new ArrayList<>();
    localVarQueryParams.addAll(ApiClient.parameterToPairs("addrList", addrList));
    localVarQueryParams.addAll(ApiClient.parameterToPairs("id", id));
    localVarQueryParams.addAll(ApiClient.parameterToPairs("name", name));

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
}