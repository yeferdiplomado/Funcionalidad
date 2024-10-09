import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import ElementoProyectoService from './elemento-proyecto.service';
import { type IElementoProyecto } from '@/shared/model/elemento-proyecto.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ElementoProyectoDetails',
  setup() {
    const elementoProyectoService = inject('elementoProyectoService', () => new ElementoProyectoService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const elementoProyecto: Ref<IElementoProyecto> = ref({});

    const retrieveElementoProyecto = async elementoProyectoId => {
      try {
        const res = await elementoProyectoService().find(elementoProyectoId);
        elementoProyecto.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.elementoProyectoId) {
      retrieveElementoProyecto(route.params.elementoProyectoId);
    }

    return {
      alertService,
      elementoProyecto,

      previousState,
      t$: useI18n().t,
    };
  },
});
