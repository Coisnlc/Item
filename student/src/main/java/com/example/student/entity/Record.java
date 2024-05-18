package com.example.student.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
    public class Record implements Serializable {

    private static final long serialVersionUID=1L;

      /**
     * id
     */
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      /**
     * 学生id
     */
      private Integer studentId;

      /**
     * 查询者id
     */
      private Integer searcherId;

      /**
     * 档案创建时间
     */
      private String creatime;

      /**
     * 档案提交（学生公钥加密）
     */
      private String descriptionEnbystu;

      /**
     * 备注（学生公钥加密）
     */
      private String remarkEnbystu;

    private Integer affirm;


}
