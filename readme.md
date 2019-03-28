FEBS后台权限管理系统采用spring boot开发. 采用spring boot + mybatis + spring security + thymeleaf + mysql + redis技术栈. 

- 功能模块:

​    1. 系统管理: 用户, 角色, 权限管理

​    2. 系统监控: 用户, 系统日志监控

​    3. 任务调度: 定时任务, 调度日志

​    4. 网络资源: 爬去网络资源展示

- 使用spring security进行权限管理

- 采用springAop切面和自定义注解实现系统日志功能, 记录用户操作日志
- 使用quartz任务调度框架执行定时任务
- 使用poi实现相关报表下载

