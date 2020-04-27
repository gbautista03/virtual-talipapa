package ph.virtual.talipapa.config;

import io.github.jhipster.config.JHipsterProperties;
import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, ph.virtual.talipapa.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, ph.virtual.talipapa.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, ph.virtual.talipapa.domain.User.class.getName());
            createCache(cm, ph.virtual.talipapa.domain.Authority.class.getName());
            createCache(cm, ph.virtual.talipapa.domain.User.class.getName() + ".authorities");
            createCache(cm, ph.virtual.talipapa.domain.Region.class.getName());
            createCache(cm, ph.virtual.talipapa.domain.Country.class.getName());
            createCache(cm, ph.virtual.talipapa.domain.Discount.class.getName());
            createCache(cm, ph.virtual.talipapa.domain.DiscountType.class.getName());
            createCache(cm, ph.virtual.talipapa.domain.UnitOfMeasure.class.getName());
            createCache(cm, ph.virtual.talipapa.domain.Address.class.getName());
            createCache(cm, ph.virtual.talipapa.domain.Order.class.getName());
            createCache(cm, ph.virtual.talipapa.domain.Order.class.getName() + ".items");
            createCache(cm, ph.virtual.talipapa.domain.LineItem.class.getName());
            createCache(cm, ph.virtual.talipapa.domain.Customer.class.getName());
            createCache(cm, ph.virtual.talipapa.domain.Customer.class.getName() + ".orders");
            createCache(cm, ph.virtual.talipapa.domain.Customer.class.getName() + ".addresses");
            createCache(cm, ph.virtual.talipapa.domain.Item.class.getName());
            createCache(cm, ph.virtual.talipapa.domain.Item.class.getName() + ".units");
            createCache(cm, ph.virtual.talipapa.domain.ItemType.class.getName());
            createCache(cm, ph.virtual.talipapa.domain.UserExtra.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache == null) {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }
}
