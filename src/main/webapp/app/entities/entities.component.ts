import { defineComponent, provide } from 'vue';

import ProyectoService from './proyecto/proyecto.service';
import ElementoService from './elemento/elemento.service';
import ElementoProyectoService from './elemento-proyecto/elemento-proyecto.service';
import IntegrantesProyectoService from './integrantes-proyecto/integrantes-proyecto.service';
import UserService from '@/entities/user/user.service';
// jhipster-needle-add-entity-service-to-entities-component-import - JHipster will import entities services here

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Entities',
  setup() {
    provide('userService', () => new UserService());
    provide('proyectoService', () => new ProyectoService());
    provide('elementoService', () => new ElementoService());
    provide('elementoProyectoService', () => new ElementoProyectoService());
    provide('integrantesProyectoService', () => new IntegrantesProyectoService());
    // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
  },
});
