package com.qk.dam.metedata.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MtdAttributes {

  private String owner;

  private String name;

  private String type;

  private String comment;

  private String description;
}
