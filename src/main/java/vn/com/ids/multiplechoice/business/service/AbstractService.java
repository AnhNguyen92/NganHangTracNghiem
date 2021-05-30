package vn.com.ids.multiplechoice.business.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

@Transactional
public abstract class AbstractService<D extends Serializable, K extends Object> implements IGenericService<D, K> {
    private final JpaRepository<D, K> jpaRepository;

    protected AbstractService(JpaRepository<D, K> jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    protected JpaRepository<D, K> repository() {
        return jpaRepository;
    }

    @Override
    public D findOne(K id) {
        Optional<D> item = jpaRepository.findById(id);
        if (item.isPresent()) {
            return item.get();
        } else {
            return null;
        }
    }

    @Override
    public List<D> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public D save(D dto) {
        return jpaRepository.save(dto);
    }

    @Override
    public void delete(D dto) throws IOException {
        jpaRepository.delete(dto);
    }

    @Override
    public void deleteById(K id) throws IOException {
        jpaRepository.deleteById(id);
    }

    @Override
    public Page<D> findAll(Pageable pageable) {
        return jpaRepository.findAll(pageable);
    }

}
