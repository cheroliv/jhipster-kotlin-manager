package ceelo.webapp.repository;

import ceelo.webapp.domain.Player;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the Player entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlayerRepository extends ReactiveCrudRepository<Player, UUID>, PlayerRepositoryInternal {
    @Override
    <S extends Player> Mono<S> save(S entity);

    @Override
    Flux<Player> findAll();

    @Override
    Mono<Player> findById(UUID id);

    @Override
    Mono<Void> deleteById(UUID id);
}

interface PlayerRepositoryInternal {
    <S extends Player> Mono<S> save(S entity);

    Flux<Player> findAllBy(Pageable pageable);

    Flux<Player> findAll();

    Mono<Player> findById(UUID id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<Player> findAllBy(Pageable pageable, Criteria criteria);

}
