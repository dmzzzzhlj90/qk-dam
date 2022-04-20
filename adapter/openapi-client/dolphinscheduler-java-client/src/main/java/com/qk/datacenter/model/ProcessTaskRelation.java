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


package com.qk.datacenter.model;

import java.util.Objects;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.OffsetDateTime;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * ProcessTaskRelation
 */
@JsonPropertyOrder({
  ProcessTaskRelation.JSON_PROPERTY_CONDITION_PARAMS,
  ProcessTaskRelation.JSON_PROPERTY_CONDITION_TYPE,
  ProcessTaskRelation.JSON_PROPERTY_CREATE_TIME,
  ProcessTaskRelation.JSON_PROPERTY_ID,
  ProcessTaskRelation.JSON_PROPERTY_NAME,
  ProcessTaskRelation.JSON_PROPERTY_POST_TASK_CODE,
  ProcessTaskRelation.JSON_PROPERTY_POST_TASK_VERSION,
  ProcessTaskRelation.JSON_PROPERTY_PRE_TASK_CODE,
  ProcessTaskRelation.JSON_PROPERTY_PRE_TASK_VERSION,
  ProcessTaskRelation.JSON_PROPERTY_PROCESS_DEFINITION_CODE,
  ProcessTaskRelation.JSON_PROPERTY_PROCESS_DEFINITION_VERSION,
  ProcessTaskRelation.JSON_PROPERTY_PROJECT_CODE,
  ProcessTaskRelation.JSON_PROPERTY_UPDATE_TIME
})
@javax.annotation.processing.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-12-18T14:27:46.433909+08:00[Asia/Shanghai]")
public class ProcessTaskRelation {
  public static final String JSON_PROPERTY_CONDITION_PARAMS = "conditionParams";
  private String conditionParams;

  /**
   * Gets or Sets conditionType
   */
  public enum ConditionTypeEnum {
    NONE("NONE"),
    
    JUDGE("JUDGE"),
    
    DELAY("DELAY");

    private String value;

    ConditionTypeEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static ConditionTypeEnum fromValue(String value) {
      for (ConditionTypeEnum b : ConditionTypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  public static final String JSON_PROPERTY_CONDITION_TYPE = "conditionType";
  private ConditionTypeEnum conditionType;

  public static final String JSON_PROPERTY_CREATE_TIME = "createTime";
  private OffsetDateTime createTime;

  public static final String JSON_PROPERTY_ID = "id";
  private Integer id;

  public static final String JSON_PROPERTY_NAME = "name";
  private String name;

  public static final String JSON_PROPERTY_POST_TASK_CODE = "postTaskCode";
  private Long postTaskCode;

  public static final String JSON_PROPERTY_POST_TASK_VERSION = "postTaskVersion";
  private Integer postTaskVersion;

  public static final String JSON_PROPERTY_PRE_TASK_CODE = "preTaskCode";
  private Long preTaskCode;

  public static final String JSON_PROPERTY_PRE_TASK_VERSION = "preTaskVersion";
  private Integer preTaskVersion;

  public static final String JSON_PROPERTY_PROCESS_DEFINITION_CODE = "processDefinitionCode";
  private Long processDefinitionCode;

  public static final String JSON_PROPERTY_PROCESS_DEFINITION_VERSION = "processDefinitionVersion";
  private Integer processDefinitionVersion;

  public static final String JSON_PROPERTY_PROJECT_CODE = "projectCode";
  private Long projectCode;

  public static final String JSON_PROPERTY_UPDATE_TIME = "updateTime";
  private OffsetDateTime updateTime;


  public ProcessTaskRelation conditionParams(String conditionParams) {
    this.conditionParams = conditionParams;
    return this;
  }

   /**
   * Get conditionParams
   * @return conditionParams
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")
  @JsonProperty(JSON_PROPERTY_CONDITION_PARAMS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getConditionParams() {
    return conditionParams;
  }


  @JsonProperty(JSON_PROPERTY_CONDITION_PARAMS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setConditionParams(String conditionParams) {
    this.conditionParams = conditionParams;
  }


  public ProcessTaskRelation conditionType(ConditionTypeEnum conditionType) {
    this.conditionType = conditionType;
    return this;
  }

   /**
   * Get conditionType
   * @return conditionType
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")
  @JsonProperty(JSON_PROPERTY_CONDITION_TYPE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public ConditionTypeEnum getConditionType() {
    return conditionType;
  }


  @JsonProperty(JSON_PROPERTY_CONDITION_TYPE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setConditionType(ConditionTypeEnum conditionType) {
    this.conditionType = conditionType;
  }


  public ProcessTaskRelation createTime(OffsetDateTime createTime) {
    this.createTime = createTime;
    return this;
  }

   /**
   * Get createTime
   * @return createTime
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")
  @JsonProperty(JSON_PROPERTY_CREATE_TIME)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public OffsetDateTime getCreateTime() {
    return createTime;
  }


  @JsonProperty(JSON_PROPERTY_CREATE_TIME)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setCreateTime(OffsetDateTime createTime) {
    this.createTime = createTime;
  }


  public ProcessTaskRelation id(Integer id) {
    this.id = id;
    return this;
  }

   /**
   * Get id
   * @return id
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")
  @JsonProperty(JSON_PROPERTY_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Integer getId() {
    return id;
  }


  @JsonProperty(JSON_PROPERTY_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setId(Integer id) {
    this.id = id;
  }


  public ProcessTaskRelation name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Get name
   * @return name
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")
  @JsonProperty(JSON_PROPERTY_NAME)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getName() {
    return name;
  }


  @JsonProperty(JSON_PROPERTY_NAME)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setName(String name) {
    this.name = name;
  }


  public ProcessTaskRelation postTaskCode(Long postTaskCode) {
    this.postTaskCode = postTaskCode;
    return this;
  }

   /**
   * Get postTaskCode
   * @return postTaskCode
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")
  @JsonProperty(JSON_PROPERTY_POST_TASK_CODE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Long getPostTaskCode() {
    return postTaskCode;
  }


  @JsonProperty(JSON_PROPERTY_POST_TASK_CODE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setPostTaskCode(Long postTaskCode) {
    this.postTaskCode = postTaskCode;
  }


  public ProcessTaskRelation postTaskVersion(Integer postTaskVersion) {
    this.postTaskVersion = postTaskVersion;
    return this;
  }

   /**
   * Get postTaskVersion
   * @return postTaskVersion
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")
  @JsonProperty(JSON_PROPERTY_POST_TASK_VERSION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Integer getPostTaskVersion() {
    return postTaskVersion;
  }


  @JsonProperty(JSON_PROPERTY_POST_TASK_VERSION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setPostTaskVersion(Integer postTaskVersion) {
    this.postTaskVersion = postTaskVersion;
  }


  public ProcessTaskRelation preTaskCode(Long preTaskCode) {
    this.preTaskCode = preTaskCode;
    return this;
  }

   /**
   * Get preTaskCode
   * @return preTaskCode
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")
  @JsonProperty(JSON_PROPERTY_PRE_TASK_CODE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Long getPreTaskCode() {
    return preTaskCode;
  }


  @JsonProperty(JSON_PROPERTY_PRE_TASK_CODE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setPreTaskCode(Long preTaskCode) {
    this.preTaskCode = preTaskCode;
  }


  public ProcessTaskRelation preTaskVersion(Integer preTaskVersion) {
    this.preTaskVersion = preTaskVersion;
    return this;
  }

   /**
   * Get preTaskVersion
   * @return preTaskVersion
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")
  @JsonProperty(JSON_PROPERTY_PRE_TASK_VERSION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Integer getPreTaskVersion() {
    return preTaskVersion;
  }


  @JsonProperty(JSON_PROPERTY_PRE_TASK_VERSION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setPreTaskVersion(Integer preTaskVersion) {
    this.preTaskVersion = preTaskVersion;
  }


  public ProcessTaskRelation processDefinitionCode(Long processDefinitionCode) {
    this.processDefinitionCode = processDefinitionCode;
    return this;
  }

   /**
   * Get processDefinitionCode
   * @return processDefinitionCode
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")
  @JsonProperty(JSON_PROPERTY_PROCESS_DEFINITION_CODE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Long getProcessDefinitionCode() {
    return processDefinitionCode;
  }


  @JsonProperty(JSON_PROPERTY_PROCESS_DEFINITION_CODE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setProcessDefinitionCode(Long processDefinitionCode) {
    this.processDefinitionCode = processDefinitionCode;
  }


  public ProcessTaskRelation processDefinitionVersion(Integer processDefinitionVersion) {
    this.processDefinitionVersion = processDefinitionVersion;
    return this;
  }

   /**
   * Get processDefinitionVersion
   * @return processDefinitionVersion
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")
  @JsonProperty(JSON_PROPERTY_PROCESS_DEFINITION_VERSION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Integer getProcessDefinitionVersion() {
    return processDefinitionVersion;
  }


  @JsonProperty(JSON_PROPERTY_PROCESS_DEFINITION_VERSION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setProcessDefinitionVersion(Integer processDefinitionVersion) {
    this.processDefinitionVersion = processDefinitionVersion;
  }


  public ProcessTaskRelation projectCode(Long projectCode) {
    this.projectCode = projectCode;
    return this;
  }

   /**
   * Get projectCode
   * @return projectCode
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")
  @JsonProperty(JSON_PROPERTY_PROJECT_CODE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Long getProjectCode() {
    return projectCode;
  }


  @JsonProperty(JSON_PROPERTY_PROJECT_CODE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setProjectCode(Long projectCode) {
    this.projectCode = projectCode;
  }


  public ProcessTaskRelation updateTime(OffsetDateTime updateTime) {
    this.updateTime = updateTime;
    return this;
  }

   /**
   * Get updateTime
   * @return updateTime
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")
  @JsonProperty(JSON_PROPERTY_UPDATE_TIME)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public OffsetDateTime getUpdateTime() {
    return updateTime;
  }


  @JsonProperty(JSON_PROPERTY_UPDATE_TIME)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setUpdateTime(OffsetDateTime updateTime) {
    this.updateTime = updateTime;
  }


  /**
   * Return true if this ProcessTaskRelation object is equal to o.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProcessTaskRelation processTaskRelation = (ProcessTaskRelation) o;
    return Objects.equals(this.conditionParams, processTaskRelation.conditionParams) &&
        Objects.equals(this.conditionType, processTaskRelation.conditionType) &&
        Objects.equals(this.createTime, processTaskRelation.createTime) &&
        Objects.equals(this.id, processTaskRelation.id) &&
        Objects.equals(this.name, processTaskRelation.name) &&
        Objects.equals(this.postTaskCode, processTaskRelation.postTaskCode) &&
        Objects.equals(this.postTaskVersion, processTaskRelation.postTaskVersion) &&
        Objects.equals(this.preTaskCode, processTaskRelation.preTaskCode) &&
        Objects.equals(this.preTaskVersion, processTaskRelation.preTaskVersion) &&
        Objects.equals(this.processDefinitionCode, processTaskRelation.processDefinitionCode) &&
        Objects.equals(this.processDefinitionVersion, processTaskRelation.processDefinitionVersion) &&
        Objects.equals(this.projectCode, processTaskRelation.projectCode) &&
        Objects.equals(this.updateTime, processTaskRelation.updateTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(conditionParams, conditionType, createTime, id, name, postTaskCode, postTaskVersion, preTaskCode, preTaskVersion, processDefinitionCode, processDefinitionVersion, projectCode, updateTime);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProcessTaskRelation {\n");
    sb.append("    conditionParams: ").append(toIndentedString(conditionParams)).append("\n");
    sb.append("    conditionType: ").append(toIndentedString(conditionType)).append("\n");
    sb.append("    createTime: ").append(toIndentedString(createTime)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    postTaskCode: ").append(toIndentedString(postTaskCode)).append("\n");
    sb.append("    postTaskVersion: ").append(toIndentedString(postTaskVersion)).append("\n");
    sb.append("    preTaskCode: ").append(toIndentedString(preTaskCode)).append("\n");
    sb.append("    preTaskVersion: ").append(toIndentedString(preTaskVersion)).append("\n");
    sb.append("    processDefinitionCode: ").append(toIndentedString(processDefinitionCode)).append("\n");
    sb.append("    processDefinitionVersion: ").append(toIndentedString(processDefinitionVersion)).append("\n");
    sb.append("    projectCode: ").append(toIndentedString(projectCode)).append("\n");
    sb.append("    updateTime: ").append(toIndentedString(updateTime)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}
