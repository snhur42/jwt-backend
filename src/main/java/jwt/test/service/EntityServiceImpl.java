package jwt.test.service;

import jwt.test.model.AbstractEntity;
import jwt.test.repository.EntityRepository;
import jwt.test.service.EntityService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class EntityServiceImpl<T extends AbstractEntity, R extends EntityRepository<T>> implements EntityService<T> {
    protected final R repository;

    public EntityServiceImpl(R r) {
        super();
        this.repository = r;
    }

    @Override
    public T save(T t) {
        return repository.save(t);
    }

    @Override
    public List<T> saveAll(Iterable<T> ts) {
        return repository.saveAll(ts);
    }

    @Override
    public T update(T t) {
        return repository.save(t);
    }

    @Override
    public T findById(UUID id) {
        return repository.findById(id).get();
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(repository.findAll());
    }

    @Override
    public void delete(T t) {
        repository.delete(t);
    }
}
