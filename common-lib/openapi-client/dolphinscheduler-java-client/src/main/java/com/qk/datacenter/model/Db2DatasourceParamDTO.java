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
import java.util.Map;
import java.util.HashMap;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


import com.qk.datacenter.client.JSON;
/**
 * Db2DatasourceParamDTO
 */
@JsonPropertyOrder({
  Db2DatasourceParamDTO.JSON_PROPERTY_DATABASE,
  Db2DatasourceParamDTO.JSON_PROPERTY_HOST,
  Db2DatasourceParamDTO.JSON_PROPERTY_ID,
  Db2DatasourceParamDTO.JSON_PROPERTY_NAME,
  Db2DatasourceParamDTO.JSON_PROPERTY_NOTE,
  Db2DatasourceParamDTO.JSON_PROPERTY_OTHER,
  Db2DatasourceParamDTO.JSON_PROPERTY_PASSWORD,
  Db2DatasourceParamDTO.JSON_PROPERTY_PORT,
  Db2DatasourceParamDTO.JSON_PROPERTY_USER_NAME
})
@javax.annotation.processing.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-12-18T14:27:46.433909+08:00[Asia/Shanghai]")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type", visible = true)

public class Db2DatasourceParamDTO extends BaseDataSourceParamDTO {
  public static final String JSON_PROPERTY_DATABASE = "database";
  private String database;

  public static final String JSON_PROPERTY_HOST = "host";
  private String host;

  public static final String JSON_PROPERTY_ID = "id";
  private Integer id;

  public static final String JSON_PROPERTY_NAME = "name";
  private String name;

  public static final String JSON_PROPERTY_NOTE = "note";
  private String note;

  public static final String JSON_PROPERTY_OTHER = "other";
  private Map<String, String> other = null;

  public static final String JSON_PROPERTY_PASSWORD = "password";
  private String password;

  public static final String JSON_PROPERTY_PORT = "port";
  private Integer port;

  public static final String JSON_PROPERTY_USER_NAME = "userName";
  private String userName;


  public Db2DatasourceParamDTO database(String database) {
    this.database = database;
    return this;
  }

   /**
   * Get database
   * @return database
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")
  @JsonProperty(JSON_PROPERTY_DATABASE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getDatabase() {
    return database;
  }


  @JsonProperty(JSON_PROPERTY_DATABASE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setDatabase(String database) {
    this.database = database;
  }


  public Db2DatasourceParamDTO host(String host) {
    this.host = host;
    return this;
  }

   /**
   * Get host
   * @return host
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")
  @JsonProperty(JSON_PROPERTY_HOST)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getHost() {
    return host;
  }


  @JsonProperty(JSON_PROPERTY_HOST)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setHost(String host) {
    this.host = host;
  }


  public Db2DatasourceParamDTO id(Integer id) {
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


  public Db2DatasourceParamDTO name(String name) {
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


  public Db2DatasourceParamDTO note(String note) {
    this.note = note;
    return this;
  }

   /**
   * Get note
   * @return note
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")
  @JsonProperty(JSON_PROPERTY_NOTE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getNote() {
    return note;
  }


  @JsonProperty(JSON_PROPERTY_NOTE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setNote(String note) {
    this.note = note;
  }


  public Db2DatasourceParamDTO other(Map<String, String> other) {
    this.other = other;
    return this;
  }

  public Db2DatasourceParamDTO putOtherItem(String key, String otherItem) {
    if (this.other == null) {
      this.other = new HashMap<>();
    }
    this.other.put(key, otherItem);
    return this;
  }

   /**
   * Get other
   * @return other
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")
  @JsonProperty(JSON_PROPERTY_OTHER)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Map<String, String> getOther() {
    return other;
  }


  @JsonProperty(JSON_PROPERTY_OTHER)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setOther(Map<String, String> other) {
    this.other = other;
  }


  public Db2DatasourceParamDTO password(String password) {
    this.password = password;
    return this;
  }

   /**
   * Get password
   * @return password
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")
  @JsonProperty(JSON_PROPERTY_PASSWORD)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getPassword() {
    return password;
  }


  @JsonProperty(JSON_PROPERTY_PASSWORD)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setPassword(String password) {
    this.password = password;
  }


  public Db2DatasourceParamDTO port(Integer port) {
    this.port = port;
    return this;
  }

   /**
   * Get port
   * @return port
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")
  @JsonProperty(JSON_PROPERTY_PORT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Integer getPort() {
    return port;
  }


  @JsonProperty(JSON_PROPERTY_PORT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setPort(Integer port) {
    this.port = port;
  }


  public Db2DatasourceParamDTO userName(String userName) {
    this.userName = userName;
    return this;
  }

   /**
   * Get userName
   * @return userName
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")
  @JsonProperty(JSON_PROPERTY_USER_NAME)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getUserName() {
    return userName;
  }


  @JsonProperty(JSON_PROPERTY_USER_NAME)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setUserName(String userName) {
    this.userName = userName;
  }


  /**
   * Return true if this Db2DatasourceParamDTO object is equal to o.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Db2DatasourceParamDTO db2DatasourceParamDTO = (Db2DatasourceParamDTO) o;
    return Objects.equals(this.database, db2DatasourceParamDTO.database) &&
        Objects.equals(this.host, db2DatasourceParamDTO.host) &&
        Objects.equals(this.id, db2DatasourceParamDTO.id) &&
        Objects.equals(this.name, db2DatasourceParamDTO.name) &&
        Objects.equals(this.note, db2DatasourceParamDTO.note) &&
        Objects.equals(this.other, db2DatasourceParamDTO.other) &&
        Objects.equals(this.password, db2DatasourceParamDTO.password) &&
        Objects.equals(this.port, db2DatasourceParamDTO.port) &&
        Objects.equals(this.userName, db2DatasourceParamDTO.userName) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(database, host, id, name, note, other, password, port, userName, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Db2DatasourceParamDTO {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    database: ").append(toIndentedString(database)).append("\n");
    sb.append("    host: ").append(toIndentedString(host)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    note: ").append(toIndentedString(note)).append("\n");
    sb.append("    other: ").append(toIndentedString(other)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
    sb.append("    port: ").append(toIndentedString(port)).append("\n");
    sb.append("    userName: ").append(toIndentedString(userName)).append("\n");
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

static {
  // Initialize and register the discriminator mappings.
  Map<String, Class<?>> mappings = new HashMap<String, Class<?>>();
  mappings.put("Db2DatasourceParamDTO", Db2DatasourceParamDTO.class);
  JSON.registerDiscriminator(Db2DatasourceParamDTO.class, "type", mappings);
}
}
