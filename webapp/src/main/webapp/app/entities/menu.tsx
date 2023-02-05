import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/game">
        <Translate contentKey="global.menu.entities.game" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/player">
        <Translate contentKey="global.menu.entities.player" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/dices-run">
        <Translate contentKey="global.menu.entities.dicesRun" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
