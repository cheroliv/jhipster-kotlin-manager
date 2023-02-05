import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IDicesRun } from 'app/shared/model/dices-run.model';
import { getEntity, updateEntity, createEntity, reset } from './dices-run.reducer';

export const DicesRunUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const dicesRunEntity = useAppSelector(state => state.dicesRun.entity);
  const loading = useAppSelector(state => state.dicesRun.loading);
  const updating = useAppSelector(state => state.dicesRun.updating);
  const updateSuccess = useAppSelector(state => state.dicesRun.updateSuccess);

  const handleClose = () => {
    navigate('/dices-run');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...dicesRunEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...dicesRunEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="ceeloApp.dicesRun.home.createOrEditLabel" data-cy="DicesRunCreateUpdateHeading">
            <Translate contentKey="ceeloApp.dicesRun.home.createOrEditLabel">Create or edit a DicesRun</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="dices-run-id"
                  label={translate('ceeloApp.dicesRun.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('ceeloApp.dicesRun.gameId')}
                id="dices-run-gameId"
                name="gameId"
                data-cy="gameId"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('ceeloApp.dicesRun.playerId')}
                id="dices-run-playerId"
                name="playerId"
                data-cy="playerId"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('ceeloApp.dicesRun.firstDice')}
                id="dices-run-firstDice"
                name="firstDice"
                data-cy="firstDice"
                type="text"
                validate={{
                  min: { value: 1, message: translate('entity.validation.min', { min: 1 }) },
                  max: { value: 6, message: translate('entity.validation.max', { max: 6 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('ceeloApp.dicesRun.middleDice')}
                id="dices-run-middleDice"
                name="middleDice"
                data-cy="middleDice"
                type="text"
                validate={{
                  min: { value: 1, message: translate('entity.validation.min', { min: 1 }) },
                  max: { value: 6, message: translate('entity.validation.max', { max: 6 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('ceeloApp.dicesRun.lastDice')}
                id="dices-run-lastDice"
                name="lastDice"
                data-cy="lastDice"
                type="text"
                validate={{
                  min: { value: 1, message: translate('entity.validation.min', { min: 1 }) },
                  max: { value: 6, message: translate('entity.validation.max', { max: 6 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/dices-run" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default DicesRunUpdate;
