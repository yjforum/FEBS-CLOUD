##使用Nacos存储限流规则  
为什么要使用nacos来做Sentinel持久化的原因：
原因是Sentinel控制台新增的流控规则是存在内存里面的，如果重启就会出现丢失规则，所以需要持久化
Sentinel自身就支持了多种不同的数据源来持久化规则配置，目前包括以下几种方式：

文件配置
Nacos配置
ZooKeeper配置
Apollo配置
本文我们就来一起动手尝试一下，使用Spring Cloud Alibaba的中整合的配置中心Nacos存储限流规则。

准备工作
下面我们将同时使用到Nacos和Sentinel Dashboard，所以可以先把Nacos和Sentinel Dashboard启动起来。

默认配置下启动后，它们的访问地址（后续会用到）为：

Nacos：http://localhost:8848/
Sentinel Dashboard：http://localhost:8080/
如果还没入门Nacos和Sentinel Dashboard可以通过文末的系列目录先学习之前的内容。

应用配置
##第一步：在Spring Cloud应用的pom.xml中引入Spring Cloud Alibaba的Sentinel模块和Nacos存储扩展：
 <dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-alibaba-sentinel</artifactId>
    </dependency>
    <dependency>
        <groupId>com.alibaba.csp</groupId>
        <artifactId>sentinel-datasource-nacos</artifactId>
        <version>1.5.2</version>
    </dependency>
</dependencies>
##第二步：在Spring Cloud应用中添加配置信息：
spring:
  application:
    name: alibaba-sentinel-datasource-nacos
  cloud:
    sentinel:
      transport:
        dashboard: ${sentinel.host}:8849  #sentinel dashboard的访问地址，根据上面准备工作中启动的实例配置
      #eager: true
      datasource:
        ds:
          nacos:
            server-addr: ${nacos.url}:8001  #nacos的访问地址，启动的实例配置
            groupId: DEFAULT_GROUP  #nacos中存储规则的groupId
            dataId: ${spring.application.name}-sentinel #nacos中存储规则的dataId
            rule-type: flow   #该参数是spring cloud alibaba升级到0.2.2之后增加的配置，用来定义存储的规则类型。所有的规则类型可查看枚举类：org.springframework.cloud.alibaba.sentinel.datasource.RuleType，每种规则的定义格式可以通过各枚举值中定义的规则对象来查看，比如限流规则可查看：com.alibaba.csp.sentinel.slots.block.flow.FlowRule
spring.cloud.sentinel.transport.dashboard：sentinel dashboard的访问地址，根据上面准备工作中启动的实例配置
spring.cloud.sentinel.datasource.ds.nacos.server-addr：nacos的访问地址，，根据上面准备工作中启动的实例配置
spring.cloud.sentinel.datasource.ds.nacos.groupId：nacos中存储规则的groupId
spring.cloud.sentinel.datasource.ds.nacos.dataId：nacos中存储规则的dataId
spring.cloud.sentinel.datasource.ds.nacos.rule-type：该参数是spring cloud alibaba升级到0.2.2之后增加的配置，用来定义存储的规则类型。所有的规则类型可查看枚举类：org.springframework.cloud.alibaba.sentinel.datasource.RuleType，每种规则的定义格式可以通过各枚举值中定义的规则对象来查看，比如限流规则可查看：com.alibaba.csp.sentinel.slots.block.flow.FlowRule
这里对于dataId使用了${spring.application.name}变量，这样可以根据应用名来区分不同的规则配置。

注意：由于版本迭代关系，Github Wiki中的文档信息不一定适用所有版本。比如：在这里适用的0.2.1版本中，并没有spring.cloud.sentinel.datasource.ds2.nacos.rule-type这个参数。所以，读者在使用的时候，可以通过查看org.springframework.cloud.alibaba.sentinel.datasource.config.DataSourcePropertiesConfiguration和org.springframework.cloud.alibaba.sentinel.datasource.config.NacosDataSourceProperties两个类来分析具体的配置内容，会更为准确。
##第三步：创建应用主类，并提供一个rest接口
##第四步：Nacos中创建限流规则的配置，比如：
**[nacos]http://blog.didispace.com/images/pasted-204.png**
##第五步：通过postman或者curl访问几下localhost:8003/hello接口：
此时，在Sentinel Dashboard中就可以看到当前我们启动的alibaba-sentinel-datasource-nacos服务。点击左侧菜单中的流控规则，可以看到已经存在一条记录了，具体如下：
**[Sentinel]http://blog.didispace.com/images/pasted-205.png**
##深入思考
在完成了上面的整合之后，对于接口流控规则的修改就存在两个地方了：Sentinel控制台、Nacos控制台。

这个时候，需要注意当前版本的Sentinel控制台不具备同步修改Nacos配置的能力，而Nacos由于可以通过在客户端中使用Listener来实现自动更新。所以，在整合了Nacos做规则存储之后，需要知道在下面两个地方修改存在不同的效果：

Sentinel控制台中修改规则：仅存在于服务的内存中，不会修改Nacos中的配置值，重启后恢复原来的值。
Nacos控制台中修改规则：服务的内存中规则会更新，Nacos中持久化规则也会更新，重启后依然保持。


sentinel-dashboard-nacos
sentinel-dashboard-nacos是在sentinel-dashboard基础之上经过改造后的Sentinel Dashboard，实现了Sentinel Dashboard中修改规则同步到Nacos的功能； 代码实现 下面直接来看看如何实现的具体改造步骤，这里参考了Sentinel Dashboard源码中关于Nacos实现的测试用例。但是由于考虑到与Spring Cloud Alibaba的结合使用，略作修改。

第一步：修改pom.xml中的sentinel-datasource-nacos的依赖，将test注释掉，这样才能在主程序中使用。

<dependency>
    <groupId>com.alibaba.csp</groupId>
    <artifactId>sentinel-datasource-nacos</artifactId>
    <!--<scope>test</scope>-->
</dependency>
第二步：找到resources/app/scripts/directives/sidebar/sidebar.html中的这段代码：

<li ui-sref-active="active">
    <a ui-sref="dashboard.flowV1({app: entry.app})">
        <i class="glyphicon glyphicon-filter"></i>&nbsp;&nbsp;流控规则
    </a>
</li>
修改为：

<li ui-sref-active="active">
    <a ui-sref="dashboard.flow({app: entry.app})">
        <i class="glyphicon glyphicon-filter"></i>&nbsp;&nbsp;流控规则
    </a>
</li>
第三步：在com.alibaba.csp.sentinel.dashboard.rule包下新建一个nacos包，用来编写针对Nacos的扩展实现。

第四步：创建Nacos的配置类，具体代码如下：

@Configuration
public class NacosConfig {

    @Bean
    public Converter<List<FlowRuleEntity>, String> flowRuleEntityEncoder() {
        return JSON::toJSONString;
    }

    @Bean
    public Converter<String, List<FlowRuleEntity>> flowRuleEntityDecoder() {
        return s -> JSON.parseArray(s, FlowRuleEntity.class);
    }

    @Bean
    public ConfigService nacosConfigService() throws Exception {
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.SERVER_ADDR, "localhost");
		//properties.put(PropertyKeyConst.NAMESPACE, "130e71fa-97fe-467d-ad77-967456f2c16d");
        return ConfigFactory.createConfigService(properties);
    }
}
如果用到了namespace隔离环境，可以在nacosConfigService方法中再加入配置，比如：properties.put(PropertyKeyConst.NAMESPACE, "130e71fa-97fe-467d-ad77-967456f2c16d");

第五步：实现Nacos的配置拉取。

@Component("flowRuleNacosProvider")
public class FlowRuleNacosProvider implements DynamicRuleProvider<List<FlowRuleEntity>> {

    @Autowired
    private ConfigService configService;
    @Autowired
    private Converter<String, List<FlowRuleEntity>> converter;

    public static final String FLOW_DATA_ID_POSTFIX = "-sentinel";
    public static final String GROUP_ID = "DEFAULT_GROUP";

    @Override
    public List<FlowRuleEntity> getRules(String appName) throws Exception {
        String rules = configService.getConfig(appName + FLOW_DATA_ID_POSTFIX, GROUP_ID, 3000);
        if (StringUtil.isEmpty(rules)) {
            return new ArrayList<>();
        }
        return converter.convert(rules);
    }
}
getRules方法中的appName参数是Sentinel中的服务名称。 configService.getConfig方法是从Nacos中获取配置信息的具体操作。其中，DataId和GroupId分别对应客户端使用时候的对应配置。比如这里的例子对应了之前我们在《Sentinel使用Nacos存储规则》一文中的配置，具体如下：

spring.cloud.sentinel.datasource.ds.nacos.groupId=DEFAULT_GROUP
spring.cloud.sentinel.datasource.ds.nacos.dataId=${spring.application.name}-sentinel
注意：两边的DataId和GroupId必须对应上。

第六步：实现Nacos的配置推送。

@Component("flowRuleNacosPublisher")
public class FlowRuleNacosPublisher implements DynamicRulePublisher<List<FlowRuleEntity>> {

	@Autowired
	private ConfigService configService;

	public static final String FLOW_DATA_ID_POSTFIX = "-sentinel";
	public static final String GROUP_ID = "DEFAULT_GROUP";

    @Override
    public void publish(String app, List<FlowRuleEntity> rules) throws Exception {
        AssertUtil.notEmpty(app, "app name cannot be empty");
        if (rules == null) {
            return;
        }
		List<FlowRule> list = new ArrayList<FlowRule>();
		for (FlowRuleEntity rule : rules) {
			FlowRule flowRule = JSON.parseObject(JSON.toJSONString(rule), FlowRule.class);
			list.add(flowRule);
		}
		// 把对象转成格式化json字符串
		String content = JSON.toJSONString(list, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
				SerializerFeature.WriteDateUseDateFormat);
		configService.publishConfig(app + FLOW_DATA_ID_POSTFIX, GROUP_ID, content);
    }
}


这里的大部分内容与上一步中的实现一致。主要就是Nacos中存储配置的DataId和GroupId不要弄错。

第七步：修改com.alibaba.csp.sentinel.dashboard.controller.v2.FlowControllerV2中DynamicRuleProvider和DynamicRulePublisher注入的Bean，改为上面我们编写的针对Apollo的实现：

@Autowired
@Qualifier("flowRuleNacosProvider")
private DynamicRuleProvider<List<FlowRuleEntity>> ruleProvider;
@Autowired
@Qualifier("flowRuleNacosPublisher")
private DynamicRulePublisher<List<FlowRuleEntity>> rulePublisher;
Sentinel Logo
Sentinel: The Sentinel of Your Microservices
Travis Build Status Codecov Maven Central License Gitter

