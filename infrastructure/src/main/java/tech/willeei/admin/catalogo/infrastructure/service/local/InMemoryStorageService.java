package tech.willeei.admin.catalogo.infrastructure.service.local;

import tech.willeei.admin.catalogo.domain.video.Resource;
import tech.willeei.admin.catalogo.infrastructure.service.StorageService;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryStorageService implements StorageService {

    private final Map<String, Resource> storage;

    public InMemoryStorageService() {
        this.storage = new ConcurrentHashMap<>();
    }

    public Map<String, Resource> storage() {
        return this.storage;
    }

    public void reset() {
        this.storage.clear();
    }

    @Override
    public void deleteAll(final Collection<String> names) {
        names.forEach(storage::remove);
    }

    @Override
    public Resource get(final String name) {
        return this.storage.get(name);
    }

    @Override
    public List<String> list(final String prefix) {
        if (prefix == null) {
            return Collections.emptyList();
        }

        return this.storage.keySet().stream()
                .filter(it -> it.startsWith(prefix))
                .toList();
    }

    @Override
    public void store(final String name, final Resource resource) {
        storage.put(name, resource);
    }
}
