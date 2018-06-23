[TOC]

# 参考文档
http://start.spring.io/  
https://docs.spring.io/spring-boot/docs/2.0.3.RELEASE/reference/htmlsingle/  
https://cloud.spring.io/spring-cloud-static/Finchley.RELEASE/single/spring-cloud.html#_spring_cloud_config_server  

# 准备工作
1. 安装JCE  
    http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html  
    https://stackoverflow.com/questions/6481627/java-security-illegal-key-size-or-default-parameters  

2. 安装 Rabbit, ElasticSearch, 部署 zipkin-server    

3. 修改配置  
    config-server/src/main/resources/bootstrap.properties  
    ```
    spring.cloud.config.server.git.uri=file://项目本地路径
    
    spring.rabbitmq.host=RABBIT_HOST
    spring.rabbitmq.port=5672
    spring.rabbitmq.username=RABBIT_USER
    spring.rabbitmq.password=RABBIT_PASSWORD
    ```
    configs/dev/application.properties
    ```
    spring.rabbitmq.host=RABBIT_HOST
    spring.rabbitmq.port=5672
    spring.rabbitmq.username=RABBIT_USER
    spring.rabbitmq.password=RABBIT_PASSWORD
    ```
    
# 启动
启动顺序如下：  
config-server  
eureka-server  
gateway
front-service  
backend-service  
hystrix-dashboard  

# 功能
1. 查看节点的健康状况  
    http://localhost:PORT/actuator/health  
    例如：  
    http://localhost:8880/actuator/health  

2. 获取配置  
    下面仅是其中一种模式，更多请参考[官方文档](https://cloud.spring.io/spring-cloud-static/Finchley.RELEASE/single/spring-cloud.html#_quick_start)，  
    http://localhost:8880/{applicationName}/{configType}    
    例如:
    http://localhost:8880/backend-service/default  
    
    查看集群的信息  
    http://localhost:8881  
    
3. 通过 eureka 查看器群中的所有服务      
    http://localhost:8881    
    
4. 用做测试的几个接口
    同一个服务的多种访问形式    
    http://localhost:8900/test  
    http://localhost:8889/test  
    http://localhost:8882/front-service/test  
    集群节点通过bus通信的实例    
    http://localhost:8882/front-service/test/amqp?msg=justTest    
    
5. 刷新配置  
    config-server/src/test/http/refresh.http  
    刷新前后访问 http://localhost:8882/front-service/test 体验配置刷新的效果  
    
6. 加密和解密  
    config-server/src/test/http/encryptDecrypt.http
    
7. 服务调用栈分析
    http://ZIPKIN_SERVER_HOST:9411/zipkin/      
            
8. hystrix dashboard  
    http://localhost:8883/hystrix  
    第一个输入框输入  
    http://localhost:8889/actuator/hystrix.stream  
    然后点击 Monitor Stream  
    
    
# 附录
## 安装 RabbitMQ
```
# 查看 erlang 和 rabbitmq 版本对应关系
https://www.rabbitmq.com/which-erlang.html  

# 下载
wget https://github.com/rabbitmq/erlang-rpm/releases/download/v20.3.6/erlang-20.3.6-1.el7.centos.x86_64.rpm

wget https://dl.bintray.com/rabbitmq/all/rabbitmq-server/3.7.6/rabbitmq-server-3.7.6-1.el7.noarch.rpm  

# 安装 erlang
yum install erlang-20.3.6-1.el7.centos.x86_64.rpm 

# 安装 rabbitmq
rpm --import https://dl.bintray.com/rabbitmq/Keys/rabbitmq-release-signing-key.asc  
yum install rabbitmq-server-3.7.6-1.el7.noarch.rpm    


# start on system boot
chkconfig rabbitmq-server on

# 错误解决
# Error: Too many open files
mkdir -p /etc/systemd/system/rabbitmq-server.service.d
echo '
[Service]
LimitNOFILE=300000
' > /etc/systemd/system/rabbitmq-server.service.d/limits.conf
systemctl daemon-reload

# 启动
systemctl start rabbitmq-server

# 停止
systemctl stop rabbitmq-server

# 状态
systemctl status rabbitmq-server

# 默认端口号
# 4369 (epmd), 25672 (Erlang distribution)
# 5672, 5671 (AMQP 0-9-1 without and with TLS)
# 15672 (if management plugin is enabled)
# 61613, 61614 (if STOMP is enabled)
# 1883, 8883 (if MQTT is enabled)

# 启用management
# https://www.rabbitmq.com/management.html  
# The Web UI is located at: http://server-name:15672/
rabbitmq-plugins enable rabbitmq_management

# 为management添加用户
# https://stackoverflow.com/questions/14699873/how-to-reset-user-for-rabbitmq-management
rabbitmqctl add_user root 123456
rabbitmqctl set_user_tags root administrator
rabbitmqctl set_permissions -p / root ".*" ".*" ".*"

# 打开相应端口
firewall-cmd --zone=public --add-port=5672/tcp --permanent
firewall-cmd --zone=public --add-port=15672/tcp --permanent
firewall-cmd --reload
```

## 安装elasticsearch
```
firewall-cmd --zone=public --add-port=9200/tcp --permanent
firewall-cmd --reload

# 添加 elasticsearch 相关的用户和用户组
groupadd elasticsearch
useradd -g elasticsearch elasticsearch

# 用root用户安装，以elasticsearch用户运行

mkdir -p /usr/env/
cd /usr/env/
wget https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-6.0.0.rpm
rpm -ivh elasticsearch-6.0.0.rpm
rm -f elasticsearch-6.0.0.rpm
systemctl daemon-reload

vi /etc/elasticsearch/elasticsearch.yml
# 修改配置
cluster.name: es
node.name: ${HOSTNAME}
network.host: 0.0.0.0
http.port: 9200

vi /etc/sysconfig/elasticsearch
JAVA_HOME=YOUR_JDK_HOME

# 启动
systemctl start elasticsearch

# 开机启动
systemctl enable elasticsearch
```

## 部署 zipkin-server  
https://github.com/openzipkin/zipkin/tree/master/zipkin-server  
```
firewall-cmd --zone=public --add-port=9411/tcp --permanent
firewall-cmd --reload

mkdir -p /usr/env/zipkin-server
cd /usr/env/zipkin-server

curl -sSL https://zipkin.io/quickstart.sh | bash -s
echo -e '
#!/usr/bin/env bash
export RABBIT_ADDRESSES=localhost
export STORAGE_TYPE=elasticsearch 
export ES_HOSTS=http://localhost:9200
nohup java -jar zipkin.jar &
' > startup.sh
chmod +x startup.sh

echo -e '
#!/usr/bin/env bash
pids=$(ps -ef | grep " zipkin.jar$" | awk '"'"'{print $2}'"'"')
if [ "$pids " != " " ]; then
    for pid in $pids; do
        echo "kill -9 $pid"
        kill -9 $pid
    done
fi
' > shutdown.sh
chmod +x shutdown.sh

./startup.sh
```
