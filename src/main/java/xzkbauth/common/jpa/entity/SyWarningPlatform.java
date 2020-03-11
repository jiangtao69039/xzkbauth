package xzkbauth.common.jpa.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/** 告警台表 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Entity
public class SyWarningPlatform {
  /** 主键 */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  /** 告警项目 */
  private String item;
  /** 详情 */
  private String detail;
  /** 告警时间 */
  private Date warningDate;
  /** 更新时间 */
  private Date updateDate;
  /** 状态 0-新 1-未处理 2-已处理 */
  private Integer status;
}
