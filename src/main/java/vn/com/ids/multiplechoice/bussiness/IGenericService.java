package vn.com.ids.multiplechoice.bussiness;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IGenericService<D extends Serializable, K extends Object> {

    D findOne(final K id);

    List<D> findAll();

    D save(final D entity);

    void delete(final D entity) throws IOException;

    void deleteById(final K entityId) throws IOException;

    Page<D> findAll(Pageable pageable);
}