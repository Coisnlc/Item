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
    public class Manager implements Serializable {

    private static final long serialVersionUID=1L;

      private Integer id;

    private String name;

    private String phone;

    private String password;

    private String remark;

    private String publickey;


}
