dataSource {
    pooled = true
    driverClassName = "org.hsqldb.jdbcDriver"
    username = "sa"
    password = ""
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = true
    cache.provider_class = 'net.sf.ehcache.hibernate.EhCacheProvider'
}
// environment specific settings
environments {
    development {
        dataSource {
            dbCreate = "none" // one of 'create', 'create-drop','update'
            url = "jdbc:hsqldb:res:/mbvgdb/mbvgdb"
        }
    }
    test {
        dataSource {
            dbCreate = "none" // one of 'create', 'create-drop','update'
            url = "jdbc:hsqldb:res:/mbvgdb/mbvgdb"
        }
    }
    production {
        dataSource {
            dbCreate = "none" // one of 'create', 'create-drop','update'
            url = "jdbc:hsqldb:res:/mbvgdb/mbvgdb"
        }
    }
}
