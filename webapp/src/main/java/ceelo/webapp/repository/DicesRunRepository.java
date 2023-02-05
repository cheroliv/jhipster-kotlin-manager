package ceelo.webapp.repository;

import ceelo.webapp.domain.DicesRun;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the DicesRun entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DicesRunRepository extends ReactiveCrudRepository<DicesRun, UUID>, DicesRunRepositoryInternal {
    @Override
    <S extends DicesRun> Mono<S> save(S entity);

    @Override
    Flux<DicesRun> findAll();

    @Override
    Mono<DicesRun> findById(UUID id);

    @Override
    Mono<Void> deleteById(UUID id);
}

interface DicesRunRepositoryInternal {
    <S extends DicesRun> Mono<S> save(S entity);

    Flux<DicesRun> findAllBy(Pageable pageable);

    Flux<DicesRun> findAll();

    Mono<DicesRun> findById(UUID id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<DicesRun> findAllBy(Pageable pageable, Criteria criteria);

}
