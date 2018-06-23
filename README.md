[TOC]

# 参考文档
http://start.spring.io/  
https://docs.spring.io/spring-boot/docs/2.0.3.RELEASE/reference/htmlsingle/  
https://cloud.spring.io/spring-cloud-static/Finchley.RELEASE/single/spring-cloud.html#_spring_cloud_config_server  

# zipkin-server
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