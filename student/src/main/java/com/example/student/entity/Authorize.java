package com.example.student.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author wang
 * @since 2024-03-07
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    public class Authorize implements Serializable {

    private static final long serialVersionUID=1L;

      private Integer id;

    private Integer studentId;

    private Integer recordId;

    private String descriptionEnbymag;

    private String remarkEnbymag;

    private String state;

    private String creatime;


}
