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
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * Property
 */
@JsonPropertyOrder({
  Property.JSON_PROPERTY_DIRECT,
  Property.JSON_PROPERTY_PROP,
  Property.JSON_PROPERTY_TYPE,
  Property.JSON_PROPERTY_VALUE
})
@javax.annotation.processing.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-12-18T14:27:46.433909+08:00[Asia/Shanghai]")
public class Property {
  /**
   * Gets or Sets direct
   */
  public enum DirectEnum {
    IN("IN"),
    
    OUT("OUT");

    private String value;

    DirectEnum(String value) {
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
    public static DirectEnum fromValue(String value) {
      for (DirectEnum b : DirectEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  public static final String JSON_PROPERTY_DIRECT = "direct";
  private DirectEnum direct;

  public static final String JSON_PROPERTY_PROP = "prop";
  private String prop;

  /**
   * Gets or Sets type
   */
  public enum TypeEnum {
    VARCHAR("VARCHAR"),
    
    INTEGER("INTEGER"),
    
    LONG("LONG"),
    
    FLOAT("FLOAT"),
    
    DOUBLE("DOUBLE"),
    
    DATE("DATE"),
    
    TIME("TIME"),
    
    TIMESTAMP("TIMESTAMP"),
    
    BOOLEAN("BOOLEAN"),
    
    LIST("LIST");

    private String value;

    TypeEnum(String value) {
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
    public static TypeEnum fromValue(String value) {
      for (TypeEnum b : TypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  public static final String JSON_PROPERTY_TYPE = "type";
  private TypeEnum type;

  public static final String JSON_PROPERTY_VALUE = "value";
  private String value;


  public Property direct(DirectEnum direct) {
    this.direct = direct;
    return this;
  }

   /**
   * Get direct
   * @return direct
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")
  @JsonProperty(JSON_PROPERTY_DIRECT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public DirectEnum getDirect() {
    return direct;
  }


  @JsonProperty(JSON_PROPERTY_DIRECT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setDirect(DirectEnum direct) {
    this.direct = direct;
  }


  public Property prop(String prop) {
    this.prop = prop;
    return this;
  }

   /**
   * Get prop
   * @return prop
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")
  @JsonProperty(JSON_PROPERTY_PROP)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getProp() {
    return prop;
  }


  @JsonProperty(JSON_PROPERTY_PROP)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setProp(String prop) {
    this.prop = prop;
  }


  public Property type(TypeEnum type) {
    this.type = type;
    return this;
  }

   /**
   * Get type
   * @return type
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")
  @JsonProperty(JSON_PROPERTY_TYPE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public TypeEnum getType() {
    return type;
  }


  @JsonProperty(JSON_PROPERTY_TYPE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setType(TypeEnum type) {
    this.type = type;
  }


  public Property value(String value) {
    this.value = value;
    return this;
  }

   /**
   * Get value
   * @return value
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")
  @JsonProperty(JSON_PROPERTY_VALUE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getValue() {
    return value;
  }


  @JsonProperty(JSON_PROPERTY_VALUE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setValue(String value) {
    this.value = value;
  }


  /**
   * Return true if this Property object is equal to o.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Property property = (Property) o;
    return Objects.equals(this.direct, property.direct) &&
        Objects.equals(this.prop, property.prop) &&
        Objects.equals(this.type, property.type) &&
        Objects.equals(this.value, property.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(direct, prop, type, value);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Property {\n");
    sb.append("    direct: ").append(toIndentedString(direct)).append("\n");
    sb.append("    prop: ").append(toIndentedString(prop)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
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

