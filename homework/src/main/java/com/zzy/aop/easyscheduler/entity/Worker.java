package com.zzy.aop.easyscheduler.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @Copyright (C), 2018,北京同创永益科技发展有限公司
 * @ProjectName: easyscheduler
 * @Package: com.zzy.aop.easyscheduler.entity
 * @ClassName: Worker
 * @Author: zhangzhiyuan
 * @Description: ${description}
 * @Date: 2019/5/22 15:36
 * @Version: 1.0
 */
@Data                       // 快捷生成Getter,Setter,equals,hashCode,toString函数
@NoArgsConstructor          // 快捷生成无参构造函数
@AllArgsConstructor         // 快捷生成全参构造函数
public class Worker {

    private String name;
}
