## 说明

本模块实现了 redis 的通用工具类

### 本地安装

1.  环境要求
    * 必须安装 git 客户端
    * 必须安装 JDK1.8
    * 设置 JAVA_HOME 和 MVN_HOMME 环境变量
    * 设置 PATH 环境变量，确保 JAVA_HOEM/bin 目录和 MVN_HOMME/bin 目录在PATH环境变量中

2. 执行命令
    
    
    git clone git@bitbucket.org:x9710/irestaurant_common_cacheclient.git
    git branch dev origin/dev
    git checkout dev
    mvn install

### maven 依赖配置

    <dependency>
        <groupId>com.x9710.irestaurant.common</groupId>
        <artifactId>irestaurant-common-cachelient</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>

