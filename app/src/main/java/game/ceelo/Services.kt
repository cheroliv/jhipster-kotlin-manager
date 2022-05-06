package game.ceelo

import game.ceelo.service.ICeeloService
import game.ceelo.service.local.inmemory.CeeloServiceInMemory

val ceeloService: ICeeloService = CeeloServiceInMemory(null)