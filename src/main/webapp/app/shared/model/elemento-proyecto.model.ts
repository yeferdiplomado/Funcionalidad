import { type IElemento } from '@/shared/model/elemento.model';

export interface IElementoProyecto {
  id?: number;
  dato?: string;
  descripcion?: string | null;
  elemento?: IElemento | null;
}

export class ElementoProyecto implements IElementoProyecto {
  constructor(
    public id?: number,
    public dato?: string,
    public descripcion?: string | null,
    public elemento?: IElemento | null,
  ) {}
}
