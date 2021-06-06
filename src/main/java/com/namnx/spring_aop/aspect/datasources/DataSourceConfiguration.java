package com.namnx.spring_aop.aspect.datasources;

import com.namnx.spring_aop.aspect.datasources.routing.DatabaseType;
import com.namnx.spring_aop.aspect.datasources.routing.RoutingDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Configuration
@Slf4j
public class DataSourceConfiguration implements WebMvcConfigurer {

    @Value("${spring.enable-multiple-database-mode}")
    private boolean isEnableMultipleDatabaseMode;

    private static final String PRIMARY_DATASOURCE_PREFIX = "spring.primary.datasource";
    private static final String REPLICA_DATASOURCE_PREFIX = "spring.replica.datasource";

    @Autowired
    private Environment env;

    @Bean
    @Primary
    public DataSource dataSource() {
        final RoutingDataSource routingDataSource = new RoutingDataSource();

        final Map<Object, Object> targetDataSources = new HashMap<>();
        final DataSource primaryDataSource = buildDataSource("PrimaryHikariPool", PRIMARY_DATASOURCE_PREFIX, true);

        targetDataSources.put(DatabaseType.PRIMARY, primaryDataSource);
        if (isEnableMultipleDatabaseMode) {
            final DataSource replicaDataSource = buildDataSource("ReplicaHikariPool", REPLICA_DATASOURCE_PREFIX, false);
            targetDataSources.put(DatabaseType.REPLICA, replicaDataSource);
        }
        routingDataSource.setTargetDataSources(targetDataSources);
        routingDataSource.setDefaultTargetDataSource(primaryDataSource);

        return routingDataSource;
    }

    private DataSource buildDataSource(String poolName, String dataSourcePrefix, boolean isHasCustomHikari) {
        final HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setPoolName(poolName);
        hikariConfig.setJdbcUrl(env.getProperty(String.format("%s.url", dataSourcePrefix)));
        hikariConfig.setUsername(env.getProperty(String.format("%s.username", dataSourcePrefix)));
        hikariConfig.setPassword(env.getProperty(String.format("%s.password", dataSourcePrefix)));
        hikariConfig.setDriverClassName(env.getProperty(String.format("%s.driverClassName", dataSourcePrefix)));

        if (isHasCustomHikari) {
            hikariConfig.setMaximumPoolSize(Integer.parseInt(Objects.requireNonNull(
                    env.getProperty(String.format("%s.hikari.hikariMaximumPoolSize", dataSourcePrefix))
            )));

            hikariConfig.setLeakDetectionThreshold(Integer.parseInt(Objects.requireNonNull(
                    env.getProperty(String.format("%s.hikari.leakDetectionThreshold", dataSourcePrefix))
            )));

            hikariConfig.setConnectionTestQuery(
                    env.getProperty(String.format("%s.hikari.connectionTestQuery", dataSourcePrefix))
            );
        }
        return new HikariDataSource(hikariConfig);
    }
}
