import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import ElementoProyectoService from './elemento-proyecto.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import ElementoService from '@/entities/elemento/elemento.service';
import { type IElemento } from '@/shared/model/elemento.model';
import { type IElementoProyecto, ElementoProyecto } from '@/shared/model/elemento-proyecto.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ElementoProyectoUpdate',
  setup() {
    const elementoProyectoService = inject('elementoProyectoService', () => new ElementoProyectoService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const elementoProyecto: Ref<IElementoProyecto> = ref(new ElementoProyecto());

    const elementoService = inject('elementoService', () => new ElementoService());

    const elementos: Ref<IElemento[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'es'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

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

    const initRelationships = () => {
      elementoService()
        .retrieve()
        .then(res => {
          elementos.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      dato: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      descripcion: {},
      elemento: {},
    };
    const v$ = useVuelidate(validationRules, elementoProyecto as any);
    v$.value.$validate();

    return {
      elementoProyectoService,
      alertService,
      elementoProyecto,
      previousState,
      isSaving,
      currentLanguage,
      elementos,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.elementoProyecto.id) {
        this.elementoProyectoService()
          .update(this.elementoProyecto)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('ciecytApp.elementoProyecto.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.elementoProyectoService()
          .create(this.elementoProyecto)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('ciecytApp.elementoProyecto.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
