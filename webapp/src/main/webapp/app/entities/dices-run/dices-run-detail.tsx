import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './dices-run.reducer';

export const DicesRunDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const dicesRunEntity = useAppSelector(state => state.dicesRun.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="dicesRunDetailsHeading">
          <Translate contentKey="ceeloApp.dicesRun.detail.title">DicesRun</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="ceeloApp.dicesRun.id">Id</Translate>
            </span>
          </dt>
          <dd>{dicesRunEntity.id}</dd>
          <dt>
            <span id="gameId">
              <Translate contentKey="ceeloApp.dicesRun.gameId">Game Id</Translate>
            </span>
          </dt>
          <dd>{dicesRunEntity.gameId}</dd>
          <dt>
            <span id="playerId">
              <Translate contentKey="ceeloApp.dicesRun.playerId">Player Id</Translate>
            </span>
          </dt>
          <dd>{dicesRunEntity.playerId}</dd>
          <dt>
            <span id="firstDice">
              <Translate contentKey="ceeloApp.dicesRun.firstDice">First Dice</Translate>
            </span>
          </dt>
          <dd>{dicesRunEntity.firstDice}</dd>
          <dt>
            <span id="middleDice">
              <Translate contentKey="ceeloApp.dicesRun.middleDice">Middle Dice</Translate>
            </span>
          </dt>
          <dd>{dicesRunEntity.middleDice}</dd>
          <dt>
            <span id="lastDice">
              <Translate contentKey="ceeloApp.dicesRun.lastDice">Last Dice</Translate>
            </span>
          </dt>
          <dd>{dicesRunEntity.lastDice}</dd>
        </dl>
        <Button tag={Link} to="/dices-run" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/dices-run/${dicesRunEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default DicesRunDetail;
