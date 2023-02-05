import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './game.reducer';

export const GameDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const gameEntity = useAppSelector(state => state.game.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="gameDetailsHeading">
          <Translate contentKey="ceeloApp.game.detail.title">Game</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="ceeloApp.game.id">Id</Translate>
            </span>
          </dt>
          <dd>{gameEntity.id}</dd>
          <dt>
            <span id="winnerPlayerId">
              <Translate contentKey="ceeloApp.game.winnerPlayerId">Winner Player Id</Translate>
            </span>
          </dt>
          <dd>{gameEntity.winnerPlayerId}</dd>
          <dt>
            <span id="date">
              <Translate contentKey="ceeloApp.game.date">Date</Translate>
            </span>
          </dt>
          <dd>{gameEntity.date ? <TextFormat value={gameEntity.date} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
        </dl>
        <Button tag={Link} to="/game" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/game/${gameEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default GameDetail;
