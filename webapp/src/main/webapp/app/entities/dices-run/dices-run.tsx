import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IDicesRun } from 'app/shared/model/dices-run.model';
import { getEntities } from './dices-run.reducer';

export const DicesRun = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const dicesRunList = useAppSelector(state => state.dicesRun.entities);
  const loading = useAppSelector(state => state.dicesRun.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="dices-run-heading" data-cy="DicesRunHeading">
        <Translate contentKey="ceeloApp.dicesRun.home.title">Dices Runs</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="ceeloApp.dicesRun.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/dices-run/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="ceeloApp.dicesRun.home.createLabel">Create new Dices Run</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {dicesRunList && dicesRunList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="ceeloApp.dicesRun.id">Id</Translate>
                </th>
                <th>
                  <Translate contentKey="ceeloApp.dicesRun.gameId">Game Id</Translate>
                </th>
                <th>
                  <Translate contentKey="ceeloApp.dicesRun.playerId">Player Id</Translate>
                </th>
                <th>
                  <Translate contentKey="ceeloApp.dicesRun.firstDice">First Dice</Translate>
                </th>
                <th>
                  <Translate contentKey="ceeloApp.dicesRun.middleDice">Middle Dice</Translate>
                </th>
                <th>
                  <Translate contentKey="ceeloApp.dicesRun.lastDice">Last Dice</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {dicesRunList.map((dicesRun, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/dices-run/${dicesRun.id}`} color="link" size="sm">
                      {dicesRun.id}
                    </Button>
                  </td>
                  <td>{dicesRun.gameId}</td>
                  <td>{dicesRun.playerId}</td>
                  <td>{dicesRun.firstDice}</td>
                  <td>{dicesRun.middleDice}</td>
                  <td>{dicesRun.lastDice}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/dices-run/${dicesRun.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/dices-run/${dicesRun.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/dices-run/${dicesRun.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="ceeloApp.dicesRun.home.notFound">No Dices Runs found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default DicesRun;
