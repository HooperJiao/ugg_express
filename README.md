# UGG Express 工资管理系统

本项目是一个针对UGG Express的一个本地运行工资管理工具，可用于工资条导入、展示和管理。后续可能会增加提升工作效率的功能。

##  功能简介

- 工资单图片上传 → 解析识别（OCR）
- 信息校对与导入 MySQL
- 数据可视化 Dashboard（支持图表拖拽）
- 工资记录导出（Excel）
- 连接UGG系统，提升工作效率（未来）

## 技术栈

### 后端（Java）
- Spring Boot 3.5.5
- MyBatis-Plus
- MySQL 8+
- Swagger / Lombok

### 前端（Vue3）
- vue3-grid-layout
- Chart.js / ECharts

### OCR
- Python + PaddleOCR（Java 调用）

## 项目结构
```
ugg_express/
├── controller/      # 控制层，处理前端请求，调用服务层
├── entity/          # 实体类，对应数据库表结构
├── mapper/          # Mapper 接口，定义数据库操作方法（MyBatis-Plus）
├── service/         # 服务接口层，封装业务逻辑
│   └── impl/        # 服务实现类，具体实现业务逻辑
├── util/            # 工具类，放置一些辅助功能（如代码生成器）
├── resources/
│   ├── mapper/      # MyBatis 的 XML 映射文件
│   └── application.yml  # Spring Boot 配置文件
└── UggExpressApplication.java  # Spring Boot 启动入口
```

## 快速启动

### 后端

```bash
cd backend
# 修改 application.yml 中数据库配置
mvn clean install
java -jar target/ugg_express.jar
```

###  前端
```bash
cd frontend
npm install
npm run dev
```

```bash
http://localhost:8080/swagger-ui.html
http://localhost:8080/swagger-ui/index.html
```