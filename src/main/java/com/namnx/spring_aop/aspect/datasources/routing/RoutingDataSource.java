package com.namnx.spring_aop.aspect.datasources.routing;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class RoutingDataSource extends AbstractRoutingDataSource {

    private static final ThreadLocal<DatabaseType> ROUTE_CONTEXT = new ThreadLocal<>();

    public static DatabaseType getRoute() {
        return ROUTE_CONTEXT.get();
    }

    public static void clearRoute() {
        ROUTE_CONTEXT.remove();
    }

    public static void setReplicaRoute() {
        ROUTE_CONTEXT.set(DatabaseType.REPLICA);
    }

    public static void setPrimaryRoute() {
        ROUTE_CONTEXT.set(DatabaseType.PRIMARY);
    }

    @Override
    public Object determineCurrentLookupKey() {
        return ROUTE_CONTEXT.get();
    }
}
