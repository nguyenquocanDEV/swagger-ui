package com.loginscreen.payload;

import com.google.common.base.Optional;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import springfox.documentation.schema.ModelReference;
import springfox.documentation.service.AllowableValues;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseTemplate  {

  private int statusCode;

  private Object data ;

  private String message ;


}
